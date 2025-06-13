package org.example.form;

import org.example.model.Parking;
import org.example.model.ParkingPass;
import org.example.model.Space;
import org.example.model.User;
import org.example.service.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class ParkingReserveForm extends JDialog {
    private final UI ui;
    private final Parking parking;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton reserveBtn, cancelBtn;
    private JComboBox<String> typeComboBox;
    private JComboBox<String> hourComboBox;    // [추가] 예약 시간 콤보박스
    private List<Space> spaceList;
    private User currentUser;

    public ParkingReserveForm(UI ui, Parking parking) {
        this.ui = ui;
        this.parking = parking;
        this.currentUser = ui.getCurrentUser();
        setTitle("주차장 자리 예약");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createTitledBorder("주차장 정보"));
        infoPanel.add(makeInfoRow("이름:", parking.getName()));
        infoPanel.add(makeInfoRow("주소:", parking.getAddress()));
        infoPanel.add(makeInfoRow("등록자:", parking.getOwnerId() != null ? parking.getOwnerId() : "-"));

        String[] colNames = {"타입", "상태", "사용자", "예약 예정"};
        tableModel = new DefaultTableModel(colNames, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(tableModel);

        loadSpaceData(null);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("자리 타입 선택:"));

        List<String> allTypes = spaceList.stream()
                .map(Space::getSpaceType)
                .distinct()
                .collect(java.util.stream.Collectors.toList());
        allTypes.add(0, "전체");

        typeComboBox = new JComboBox<>(allTypes.toArray(new String[0]));
        filterPanel.add(typeComboBox);

        typeComboBox.addActionListener(e -> {
            String selectedType = (String) typeComboBox.getSelectedItem();
            loadSpaceData("전체".equals(selectedType) ? null : selectedType);
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String selType = (String) tableModel.getValueAt(row, 0);
                typeComboBox.setSelectedItem(selType);
            }
        });

        // 예약 시간 콤보박스 패널
        JPanel hourPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        hourPanel.add(new JLabel("예약 시간 선택:"));
        String[] hourOptions = new String[13];
        hourOptions[0] = "미지정";
        for (int i = 1; i <= 12; i++) {
            hourOptions[i] = i + "시간";
        }
        hourComboBox = new JComboBox<>(hourOptions);
        hourPanel.add(hourComboBox);

        JPanel btnPanel = new JPanel();
        reserveBtn = new JButton("예약하기");
        cancelBtn = new JButton("취소");
        btnPanel.add(reserveBtn);
        btnPanel.add(cancelBtn);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(filterPanel, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // [수정] 아래 두 패널을 남쪽에 수직으로 붙이기 위한 패널 생성
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.add(hourPanel);   // [추가] 시간 콤보박스
        southPanel.add(btnPanel);

        setLayout(new BorderLayout(10, 10));
        add(infoPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);  // [수정] 기존 btnPanel -> southPanel

        reserveBtn.addActionListener(e -> onReserve());
        cancelBtn.addActionListener(e -> {
            new SearchForm(ui).setVisible(true);
            dispose();
        });
    }

    private void loadSpaceData(String filterType) {
        tableModel.setRowCount(0);
        spaceList = ui.getServiceManager().getSpaceManager().findByParkingId(parking.getId());
        for (Space s : spaceList) {
            String status = s.isAvailable() ? "비어있음" : "사용중";
            String userId = "-";
            String reserveUntilStr = "-";

            if (!s.isAvailable()) { // 사용중이면
                userId = s.getUserId();
                // "현재 유효한 ParkingPass" 찾아서 정보 채우기
                ParkingPass pass = ui.getServiceManager().getParkingPassManager().findActivePassBySpaceId(s.getId());
                if (pass != null) {
                    if (pass.getEndTime() != null) {
                        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        reserveUntilStr = pass.getEndTime().format(fmt) + "까지 예약 예정";
                    }
                }
            }

            tableModel.addRow(new Object[]{
                    s.getSpaceType(), status, userId, reserveUntilStr
            });
        }

    }

    private void onReserve() {
        int row = table.getSelectedRow();

        if (row < 0) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String status = (String) tableModel.getValueAt(i, 1);
                if ("비어있음".equals(status)) {
                    row = i;
                    break;
                }
            }
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "예약 가능한 자리가 없습니다.");
                return;
            }
        }

        String selectedType = (String) tableModel.getValueAt(row, 0);

        // 예약 시간 값 읽어서 int로 파싱 ("미지정"이면 0)
        String hourText = (String) hourComboBox.getSelectedItem();
        int useTime = 0;
        if (!"미지정".equals(hourText)) {
            useTime = Integer.parseInt(hourText.replace("시간", ""));
        }

        boolean success = ui.getServiceManager().reserveSpace(currentUser, parking, selectedType, useTime);
        if (success) {
            JOptionPane.showMessageDialog(this, "예약이 완료되었습니다.");
            new SearchForm(ui).setVisible(true); // 검색창 다시 열기
            dispose();                           // 현재 예약창 닫기
        } else {
            JOptionPane.showMessageDialog(this, "예약에 실패했습니다.");
        }
    }

    private JPanel makeInfoRow(String label, String value) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JLabel l = new JLabel(label);
        l.setPreferredSize(new Dimension(60, 20));
        JLabel v = new JLabel(value);
        v.setFont(v.getFont().deriveFont(Font.BOLD));
        row.add(l);
        row.add(v);
        return row;
    }
}
