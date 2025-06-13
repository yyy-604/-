package org.example.form;

import javax.swing.*;
import java.awt.*;

import org.example.service.UI;

public class SignUpForm extends JFrame {
    private UI ui;

    private JTextField idField;
    private JPasswordField passwordField;
    private JTextField nameField;
    private JTextField phoneNumberField;
    private JButton signUpBtn;
    private JButton backBtn;

    public SignUpForm(UI ui) {
        this.ui = ui;
        setTitle("회원가입");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // ID 입력
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("아이디:"), gbc);
        gbc.gridx = 1;
        idField = new JTextField(15);
        add(idField, gbc);

        // 비밀번호 입력
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("비밀번호:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        add(passwordField, gbc);

        // 이름 입력
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("이름:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(15);
        add(nameField, gbc);

        // 전화번호 입력
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("전화번호:"), gbc);
        gbc.gridx = 1;
        phoneNumberField = new JTextField(15);
        add(phoneNumberField, gbc);

        // 하단 버튼 패널 (회원가입 + 뒤로가기)
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        signUpBtn = new JButton("회원가입");
        backBtn = new JButton("뒤로가기");
        buttonPanel.add(signUpBtn);
        buttonPanel.add(backBtn);
        add(buttonPanel, gbc);

        // 회원가입 버튼 이벤트
        signUpBtn.addActionListener(e -> handleSignUp());

        // 뒤로가기 버튼 이벤트
        backBtn.addActionListener(e -> {
            dispose();
            new LoginForm(ui);
        });

        setVisible(true);
    }

    private void handleSignUp() {
        String id = idField.getText().trim();
        String pw = new String(passwordField.getPassword()).trim();
        String name = nameField.getText().trim();
        String phone = phoneNumberField.getText().trim();

        if (id.isEmpty() || pw.isEmpty() || name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "모든 필수 정보를 입력하세요!");
            return;
        }

        // ID 중복 체크
        if (ui.getServiceManager().getUserManager().findById(id) != null) {
            JOptionPane.showMessageDialog(this, "이미 존재하는 아이디입니다!");
            return;
        }

        // 유저 생성 책임을 유저 매니저에 위임
        ui.getServiceManager().getUserManager().createUser(id, pw, name, phone);

        JOptionPane.showMessageDialog(this, "회원가입이 완료되었습니다!");
        dispose();
        new LoginForm(ui);
    }
}