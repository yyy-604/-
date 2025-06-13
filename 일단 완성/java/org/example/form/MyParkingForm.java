package org.example.form;

import org.example.model.Parking;
import org.example.service.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MyParkingForm extends JFrame {
    private final UI ui;
    private List<Parking> myParkings;
    private final DefaultTableModel model;
    private final JTable table;
    private final JButton editButton;
    private final JButton removeButton;

    public MyParkingForm(UI ui) {
        this.ui = ui;

        if (ui.getCurrentUser().getId() == "admin") {
            setTitle("주차장 관리");
        } else {
            setTitle("내 주차장");
        }
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columns = {"이름", "주소"};
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 버튼 및 패널
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("주차장 추가");
        editButton = new JButton("주차장 수정");
        removeButton = new JButton("주차장 삭제");
        JButton backButton = new JButton("뒤로가기");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // 테이블 데이터 최신화
        refreshTable();

        // 주차장 추가
        addButton.addActionListener(e -> {
            // 현재 창 닫고 등록 폼 오픈
            dispose();
            new ParkingRegisterForm(ui).setVisible(true);
        });

        // 주차장 수정
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (myParkings == null || myParkings.size() == 0) {
                JOptionPane.showMessageDialog(this, "수정할 주차장이 없습니다.");
                return;
            }
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "수정할 주차장을 선택하세요.");
                return;
            }
            if (selectedRow < 0 || selectedRow >= myParkings.size()) {
                JOptionPane.showMessageDialog(this, "선택된 행이 올바르지 않습니다. 다시 선택하세요.");
                return;
            }
            Parking selected = myParkings.get(selectedRow);
            // 현재 창 닫고 수정 폼 오픈
            dispose();
            new ParkingEditForm(ui, selected).setVisible(true);
        });

        // 주차장 삭제
        removeButton.addActionListener(_ -> {
            int selectedRow = table.getSelectedRow();
            if (myParkings == null || myParkings.size() == 0) {
                JOptionPane.showMessageDialog(this, "삭제할 주차장이 없습니다.");
                return;
            }
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "삭제할 주차장을 선택하세요.");
                return;
            }
            if (selectedRow < 0 || selectedRow >= myParkings.size()) {
                JOptionPane.showMessageDialog(this, "선택된 행이 올바르지 않습니다. 다시 선택하세요.");
                return;
            }
            Parking selected = myParkings.get(selectedRow);
            ui.removeParking(selected);
            refreshTable();
        });

        // 뒤로가기 → 메인메뉴
        backButton.addActionListener(e -> {
            new MainMenu(ui);
            dispose();
        });
    }

    // 최신화 (등록/수정 후 갱신)
    public void refreshTable() {
        if (ui.getCurrentUser().getId() == "admin") {
            myParkings = ui.getServiceManager().getParkingManager().getAll();
        } else {
            myParkings = ui.getServiceManager().getParkingManager().getMyParkings(ui.getCurrentUser().getId());
        }
        model.setRowCount(0);
        for (Parking p : myParkings) {
            model.addRow(new Object[]{p.getName(), p.getAddress()});
        }
        // 버튼 활성/비활성
        editButton.setEnabled(model.getRowCount() > 0);
        removeButton.setEnabled(model.getRowCount() > 0);
    }
}