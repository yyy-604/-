package org.example.form;

import org.example.model.Parking;
import org.example.model.Space;
import org.example.model.User;
import org.example.service.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ParkingReserveForm extends JDialog {
    private final UI ui;
    private final Parking parking;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton reserveBtn, cancelBtn;
    private JComboBox<String> typeComboBox;
    private List<Space> spaceList;
    private User currentUser;

    public ParkingReserveForm(UI ui, Parking parking) {
        this.ui = ui;
        this.parking = parking;
        this.currentUser = ui.getCurrentUser();
        setTitle("주차장 자리 예약");
        setSize(700, 450);
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

        String[] colNames = {"타입", "상태", "사용자"};
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

        JPanel btnPanel = new JPanel();
        reserveBtn = new JButton("예약하기");
        cancelBtn = new JButton("취소");
        btnPanel.add(reserveBtn);
        btnPanel.add(cancelBtn);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(filterPanel, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        setLayout(new BorderLayout(10, 10));
        add(infoPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

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
            if (filterType != null && !s.getSpaceType().equals(filterType)) continue;
            String status = s.isAvailable() ? "비어있음" : "사용중";
            String userId = (s.getUserId() != null) ? s.getUserId() : "-";
            tableModel.addRow(new Object[]{s.getSpaceType(), status, userId});
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

        boolean success = ui.getServiceManager().reserveSpace(currentUser, parking, selectedType);
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