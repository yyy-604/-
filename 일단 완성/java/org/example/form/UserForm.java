package org.example.form;

import org.example.model.User;
import org.example.service.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserForm extends JFrame {
    private final UI ui;
    private List<User> users;
    private final DefaultTableModel model;
    private final JTable table;
    private final JButton editButton;
    private final JButton removeButton;

    public UserForm(UI ui) {
        this.ui = ui;

        setTitle("사용자 관리");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columns = {"id", "이름", "전화번호"};
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 버튼 및 패널
        JPanel buttonPanel = new JPanel();
        editButton = new JButton("사용자 수정");
        removeButton = new JButton("사용자 삭제");
        JButton backButton = new JButton("뒤로가기");
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // 테이블 데이터 최신화
        refreshTable();

        // 사용자 수정
        editButton.addActionListener(_ -> {
            int selectedRow = table.getSelectedRow();
            if (users == null || users.size() == 0) {
                JOptionPane.showMessageDialog(this, "수정할 사용자가 없습니다.");
                return;
            }
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "수정할 사용자를 선택하세요.");
                return;
            }
            if (selectedRow < 0 || selectedRow >= users.size()) {
                JOptionPane.showMessageDialog(this, "선택된 행이 올바르지 않습니다. 다시 선택하세요.");
                return;
            }
            User selected = users.get(selectedRow);

            ui.login(selected.getId(), selected.getPassword());
            new InfoEditForm(ui) {
                @Override
                public void dispose() {
                    ui.login("admin", "admin");
                    super.dispose();
                }
            };
            dispose();

            refreshTable();
        });

        // 사용자 삭제
        removeButton.addActionListener(_ -> {
            int selectedRow = table.getSelectedRow();
            if (users == null || users.size() == 0) {
                JOptionPane.showMessageDialog(this, "삭제할 사용자가 없습니다.");
                return;
            }
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "삭제할 사용자를 선택하세요.");
                return;
            }
            if (selectedRow < 0 || selectedRow >= users.size()) {
                JOptionPane.showMessageDialog(this, "선택된 행이 올바르지 않습니다. 다시 선택하세요.");
                return;
            }
            User selected = users.get(selectedRow);
            ui.getServiceManager().getUserManager().remove(selected.getId());
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
        users = ui.getServiceManager().getUserManager().getAll();
        model.setRowCount(0);
        for (User user : users) {
            if (user.getId() != "admin") {
                model.addRow(new Object[]{user.getId(), user.getName(), user.getPhoneNumber()});
            }
        }
        // 버튼 활성/비활성
        editButton.setEnabled(model.getRowCount() > 0);
        removeButton.setEnabled(model.getRowCount() > 0);
    }
}