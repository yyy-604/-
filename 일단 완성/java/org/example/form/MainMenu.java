package org.example.form;

import org.example.service.UI;
import org.example.model.User;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    private UI ui;

    public MainMenu(UI ui) {
        this.ui = ui;

        setTitle("메인 메뉴");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        User currentUser = ui.getCurrentUser();

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 20, 12, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        int row = 0;

        // 항상 보이는 검색 버튼
        JButton searchBtn = new JButton("검색");
        gbc.gridy = row++;
        panel.add(searchBtn, gbc);

        if (currentUser == null) {
            // 비로그인 상태
            JButton loginBtn = new JButton("로그인");
            JButton signUpBtn = new JButton("회원가입");
            JButton exitBtn = new JButton("종료");

            gbc.gridy = row++;
            panel.add(loginBtn, gbc);
            gbc.gridy = row++;
            panel.add(signUpBtn, gbc);
            gbc.gridy = row++;
            panel.add(exitBtn, gbc);

            loginBtn.addActionListener(e -> {
                new LoginForm(ui);
                dispose();
            });

            signUpBtn.addActionListener(e -> {
                new SignUpForm(ui);
                dispose();
            });

            exitBtn.addActionListener(e -> System.exit(0));

        } else {
            // 로그인 상태
            JButton logoutBtn = new JButton("로그아웃");
            JButton infoEditBtn = new JButton("정보수정");
            JButton myParkingBtn = new JButton("내 주차장");
            JButton myPassBtn = new JButton("내 주차권");
            JButton exitBtn = new JButton("종료");

            if (ui.getCurrentUser().getId() == "admin") {
                infoEditBtn.setText("사용자 관리");
                myParkingBtn.setText("주차장 관리");
                myPassBtn.setText("주차권 관리");
            }

            gbc.gridy = row++;
            panel.add(logoutBtn, gbc);
            gbc.gridy = row++;
            panel.add(infoEditBtn, gbc);
            gbc.gridy = row++;
            panel.add(myParkingBtn, gbc);
            gbc.gridy = row++;
            panel.add(myPassBtn, gbc);
            gbc.gridy = row++;
            panel.add(exitBtn, gbc);

            logoutBtn.addActionListener(e -> {
                ui.logout();
                new MainMenu(ui);
                dispose();
            });

            infoEditBtn.addActionListener(e -> {
                if (ui.getCurrentUser().getId() == "admin") {
                    new UserForm(ui).setVisible(true);
                    ;
                } else {
                    new InfoEditForm(ui);
                }
                dispose();
            });

            // 내 주차장 버튼 이벤트
            myParkingBtn.addActionListener(e -> {
                // 폼 띄우기
                new MyParkingForm(ui).setVisible(true);
                dispose();
            });

            myPassBtn.addActionListener(e -> {
                new MyParkingPassForm(ui).setVisible(true);
                dispose();
            });

            exitBtn.addActionListener(e -> System.exit(0));
        }

        searchBtn.addActionListener(e -> {
            new SearchForm(ui);
            dispose();
        });

        add(panel);
        setVisible(true);
    }
}