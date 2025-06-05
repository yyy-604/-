package org.example.form;

import org.example.service.UI;
import org.example.model.User;

import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {
    private UI ui;

    private JTextField idField;
    private JPasswordField pwField;
    private JButton loginBtn;
    private JButton backBtn;

    public LoginForm(UI ui) {
        this.ui = ui;

        setTitle("로그인");
        setSize(330, 210);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);

        JLabel idLabel = new JLabel("아이디:");
        idField = new JTextField(14);

        JLabel pwLabel = new JLabel("비밀번호:");
        pwField = new JPasswordField(14);

        loginBtn = new JButton("로그인");
        backBtn = new JButton("뒤로가기");

        int row = 0;
        gbc.anchor = GridBagConstraints.EAST;

        gbc.gridx = 0; gbc.gridy = row; panel.add(idLabel, gbc);
        gbc.gridx = 1; panel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row; panel.add(pwLabel, gbc);
        gbc.gridx = 1; panel.add(pwField, gbc);

        // 버튼
        gbc.gridx = 0; gbc.gridy = ++row; gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnPanel.add(loginBtn);
        btnPanel.add(backBtn);
        panel.add(btnPanel, gbc);

        add(panel);

        // 로그인 버튼 액션
        loginBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            String pw = new String(pwField.getPassword()).trim();

            if (id.isEmpty() || pw.isEmpty()) {
                JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 입력하세요!");
                return;
            }

            // UI -> ServiceManager -> UserManager 에서 로그인 시도
            User user = ui.login(id, pw);

            if (user != null) {
                JOptionPane.showMessageDialog(this, "로그인 성공!");
                new MainMenu(ui);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "로그인 실패! 아이디/비밀번호를 확인하세요.");
            }
        });

        // 뒤로가기
        backBtn.addActionListener(e -> {
            new MainMenu(ui);
            dispose();
        });

        setVisible(true);
    }
}