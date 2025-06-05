package org.example.form;

import org.example.service.UI;
import org.example.model.Parking;

import javax.swing.*;
import java.awt.*;

public class ParkingEditForm extends JFrame {
    private UI ui;
    private Parking parking;

    private JTextField parkIdField;
    private JTextField nameField;
    private JTextField addressField;
    private JCheckBox publicBox, privateBox, generalBox, onSiteBox, mobileBox, freeBox, paidBox;
    private JTextField spaceCountField;
    private JButton updateBtn, backBtn;

    public ParkingEditForm(UI ui, String parkId) {
        this.ui = ui;
        this.parking = ui.findParking(parkId);

        if (parking == null) {
            JOptionPane.showMessageDialog(this, "해당 주차장 정보를 찾을 수 없습니다!");
            new MainMenu(ui);
            dispose();
            return;
        }

        setTitle("주차장 정보 수정");
        setSize(400, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);

        int row = 0;
        gbc.anchor = GridBagConstraints.EAST;

        // 필드 및 라벨 배치
        JLabel idLabel = new JLabel("주차장ID:");
        parkIdField = new JTextField(14);
        parkIdField.setText(parking.getId());
        parkIdField.setEditable(false);
        gbc.gridx = 0; gbc.gridy = row; panel.add(idLabel, gbc);
        gbc.gridx = 1; panel.add(parkIdField, gbc);

        JLabel nameLabel = new JLabel("이름:");
        nameField = new JTextField(14);
        nameField.setText(parking.getName());
        gbc.gridx = 0; gbc.gridy = ++row; panel.add(nameLabel, gbc);
        gbc.gridx = 1; panel.add(nameField, gbc);

        JLabel addressLabel = new JLabel("주소:");
        addressField = new JTextField(14);
        addressField.setText(parking.getAddress());
        gbc.gridx = 0; gbc.gridy = ++row; panel.add(addressLabel, gbc);
        gbc.gridx = 1; panel.add(addressField, gbc);

        publicBox = new JCheckBox("공영", parking.isPublic());
        privateBox = new JCheckBox("사설", parking.isPrivate());
        generalBox = new JCheckBox("일반", parking.isGeneral());
        onSiteBox = new JCheckBox("현장결제", parking.isOnSite());
        mobileBox = new JCheckBox("모바일결제", parking.isMobile());
        freeBox = new JCheckBox("무료", parking.isFree());
        paidBox = new JCheckBox("유료", parking.isPaid());

        JPanel checkPanel = new JPanel(new GridLayout(2, 4, 6, 6));
        checkPanel.add(publicBox);
        checkPanel.add(privateBox);
        checkPanel.add(generalBox);
        checkPanel.add(onSiteBox);
        checkPanel.add(mobileBox);
        checkPanel.add(freeBox);
        checkPanel.add(paidBox);

        gbc.gridx = 0; gbc.gridy = ++row; gbc.gridwidth = 2;
        panel.add(checkPanel, gbc);
        gbc.gridwidth = 1;

        JLabel spaceLabel = new JLabel("주차 공간 수:");
        spaceCountField = new JTextField(8);
        spaceCountField.setText(String.valueOf(parking.getSpaceCount()));
        gbc.gridx = 0; gbc.gridy = ++row; panel.add(spaceLabel, gbc);
        gbc.gridx = 1; panel.add(spaceCountField, gbc);

        updateBtn = new JButton("수정");
        backBtn = new JButton("뒤로가기");

        gbc.gridx = 0; gbc.gridy = ++row; gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnPanel.add(updateBtn);
        btnPanel.add(backBtn);
        panel.add(btnPanel, gbc);

        add(panel);

        // 수정 버튼 액션
        updateBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String address = addressField.getText().trim();
            boolean isPublic = publicBox.isSelected();
            boolean isPrivate = privateBox.isSelected();
            boolean isGeneral = generalBox.isSelected();
            boolean isOnSite = onSiteBox.isSelected();
            boolean isMobile = mobileBox.isSelected();
            boolean isFree = freeBox.isSelected();
            boolean isPaid = paidBox.isSelected();

            int spaceCount = 0;
            try {
                spaceCount = Integer.parseInt(spaceCountField.getText().trim());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "주차 공간 수는 숫자만 입력하세요!");
                return;
            }

            if (name.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(this, "이름, 주소는 필수 입력입니다!");
                return;
            }

            // 정보 반영
            parking.setName(name);
            parking.setAddress(address);
            parking.setPublic(isPublic);
            parking.setPrivate(isPrivate);
            parking.setGeneral(isGeneral);
            parking.setOnSite(isOnSite);
            parking.setMobile(isMobile);
            parking.setFree(isFree);
            parking.setPaid(isPaid);
            parking.setSpaceCount(spaceCount);

            // 저장(업데이트)
            ui.getServiceManager().updateParking(parking);

            JOptionPane.showMessageDialog(this, "주차장 정보가 수정되었습니다!");
            new MainMenu(ui);
            dispose();
        });

        backBtn.addActionListener(e -> {
            new MainMenu(ui);
            dispose();
        });

        setVisible(true);
    }
}