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

public class MyParkingPassForm extends JFrame {
    private final UI ui;
    private final User currentUser;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton returnBtn, backBtn;

    public MyParkingPassForm(UI ui) {
        this.ui = ui;
        this.currentUser = ui.getCurrentUser();

        // 문자열 비교는 equals 사용
        if (ui.getCurrentUser().getId().equals("admin")) {
            setTitle("주차권 관리");
        } else {
            setTitle("내 주차권 목록");
        }
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        String[] columns = {"주차장", "공간 타입", "예약 시간", "상태"};
        if (ui.getCurrentUser().getId().equals("admin")) {
            columns = new String[]{"주차장", "공간 타입", "예약 시간", "상태", "사용자"};
        }

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 모든 셀 수정 비활성화
            }
        };
        table = new JTable(tableModel);
        loadParkingPassData();

        returnBtn = new JButton("반납하기");
        returnBtn.addActionListener(e -> onReturn());

        backBtn = new JButton("돌아가기");
        backBtn.addActionListener(e -> {
            new MainMenu(ui).setVisible(true);
            dispose();
        });

        JPanel bottom = new JPanel();
        bottom.add(returnBtn);
        bottom.add(backBtn);

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    private void loadParkingPassData() {
        tableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (ui.getCurrentUser().getId().equals("admin")) {
            for (User user : ui.getServiceManager().getUserManager().getAll()) {
                if (user.getId().equals("admin")) continue;
                List<String> passIds = user.getParkingPassIDList();
                for (String passId : passIds) {
                    ParkingPass pass = ui.getServiceManager().getParkingPassManager().findById(passId);
                    Parking parking = ui.getServiceManager().findParking(pass.getParkingId());
                    Space space = ui.getServiceManager().getSpaceManager().findById(pass.getSpaceId());

                    String formattedTime = pass.getStartTime().format(formatter);
                    
                    tableModel.addRow(new Object[]{
                            parking.getName(),
                            space.getSpaceType(),
                            formattedTime,
                            "사용중",
                            user.getId()
                    });
                }
            }
        } else {
            List<String> passIds = currentUser.getParkingPassIDList();
            for (String passId : passIds) {
                ParkingPass pass = ui.getServiceManager().getParkingPassManager().findById(passId);
                Parking parking = ui.getServiceManager().findParking(pass.getParkingId());
                Space space = ui.getServiceManager().getSpaceManager().findById(pass.getSpaceId());

                String formattedTime = pass.getStartTime().format(formatter);

                tableModel.addRow(new Object[]{
                        parking.getName(),
                        space.getSpaceType(),
                        formattedTime,
                        "사용중"
                });
            }
        }
    }

    private void onReturn() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "반납할 항목을 선택하세요.");
            return;
        }
        User user = currentUser;
        if (ui.getCurrentUser().getId().equals("admin")) {
            String userId = (String) table.getValueAt(row, 4);
            user = ui.getServiceManager().getUserManager().findById(userId);
        }

        String passId = user.getParkingPassIDList().get(row);
        boolean success = ui.getServiceManager().returnParkingPass(passId);
        if (success) {
            JOptionPane.showMessageDialog(this, "반납이 완료되었습니다.");
            loadParkingPassData();
        } else {
            JOptionPane.showMessageDialog(this, "반납에 실패했습니다.");
        }
    }
}