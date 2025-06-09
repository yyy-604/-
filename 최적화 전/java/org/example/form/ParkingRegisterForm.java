package org.example.form;

import org.example.service.UI;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ParkingRegisterForm extends JFrame {
    private final UI ui;

    public ParkingRegisterForm(UI ui) {
        this.ui = ui;

        setTitle("주차장 등록");
        setSize(600, 570);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // --- 주차장 필드 ---
        JTextField nameField = new JTextField(13);
        JTextField addressField = new JTextField(13);

        // --- 속성 ---
        JCheckBox publicChk = new JCheckBox("공용");
        JCheckBox privateChk = new JCheckBox("사설");
        JCheckBox generalChk = new JCheckBox("일반");
        JCheckBox onSiteChk = new JCheckBox("현장결제");
        JCheckBox mobileChk = new JCheckBox("모바일결제");
        JCheckBox freeChk = new JCheckBox("무료");
        JCheckBox paidChk = new JCheckBox("유료");

        // --- 운영시간 콤보박스 ---
        JCheckBox openTimeEnableChk = new JCheckBox("운영시간");
        String[] hours = new String[25];
        for (int i = 0; i <= 24; i++) hours[i] = String.format("%02d", i);
        JComboBox<String> startHourCombo = new JComboBox<>(hours);
        JComboBox<String> endHourCombo = new JComboBox<>(hours);

        startHourCombo.setEnabled(false);
        endHourCombo.setEnabled(false);

        startHourCombo.addActionListener(e -> {
            int startIdx = startHourCombo.getSelectedIndex();
            endHourCombo.removeAllItems();
            for (int i = startIdx; i <= 24; i++) {
                endHourCombo.addItem(String.format("%02d", i));
            }
            if (endHourCombo.getItemCount() > 1)
                endHourCombo.setSelectedIndex(1);
        });

        openTimeEnableChk.addActionListener(e -> {
            boolean enabled = openTimeEnableChk.isSelected();
            startHourCombo.setEnabled(enabled);
            endHourCombo.setEnabled(enabled);
        });

        // --- 가격 ---
        JTextField priceField = new JTextField(8);
        JCheckBox weekendVariesChk = new JCheckBox("주말 가격변동");
        JTextField weekendPriceField = new JTextField(8); weekendPriceField.setEnabled(false);
        JTextField extraFeeField = new JTextField(8);

        // "무료" 체크 이벤트: 가격/초과금/주말가격 필드 관리
        freeChk.addActionListener(e -> {
            boolean isFree = freeChk.isSelected();
            priceField.setEnabled(!isFree);
            extraFeeField.setEnabled(!isFree);
            if (isFree) {
                priceField.setText("0");
                extraFeeField.setText("0");
                if (weekendVariesChk.isSelected()) {
                    weekendPriceField.setEnabled(false);
                    weekendPriceField.setText("0");
                }
            } else {
                priceField.setText("");
                extraFeeField.setText("");
                if (weekendVariesChk.isSelected()) weekendPriceField.setEnabled(true);
            }
        });

        // --- 공간 ---
        JTextField totalSpaceField = new JTextField(5);

        // --- 공간유형 ---
        String[] spaceTypes1 = { "장애인", "가족", "전기차", "일반" };
        String[] spaceTypes2 = { "이륜", "대형" };

        Map<String, JCheckBox> spaceTypeChk = new HashMap<>();
        Map<String, JTextField> spaceTypeField = new HashMap<>();

        JPanel spaceTypePanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        for (String type : spaceTypes1) {
            JCheckBox chk = new JCheckBox(type);
            JTextField field = new JTextField(4);
            field.setEnabled(false);
            spaceTypeChk.put(type, chk);
            spaceTypeField.put(type, field);
            chk.addActionListener(e -> field.setEnabled(chk.isSelected()));
            spaceTypePanel1.add(chk);
            spaceTypePanel1.add(field);
        }

        JPanel spaceTypePanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        for (String type : spaceTypes2) {
            JCheckBox chk = new JCheckBox(type);
            JTextField field = new JTextField(4);
            field.setEnabled(false);
            spaceTypeChk.put(type, chk);
            spaceTypeField.put(type, field);
            chk.addActionListener(e -> field.setEnabled(chk.isSelected()));
            spaceTypePanel2.add(chk);
            spaceTypePanel2.add(field);
        }

        // --- 버튼 ---
        JButton registerBtn = new JButton("등록");
        JButton cancelBtn = new JButton("취소");

        // --- 레이아웃 ---
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 12, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;
        int row = 0;

        // --- 주차장 정보 ---
        gbc.gridx = 0; gbc.gridy = row; panel.add(new JLabel("주차장 이름:"), gbc);
        gbc.gridx = 1; panel.add(nameField, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; panel.add(new JLabel("주소:"), gbc);
        gbc.gridx = 1; panel.add(addressField, gbc);

        // --- 속성 ---
        row++; gbc.gridx = 0; gbc.gridy = row; panel.add(new JLabel("속성:"), gbc);
        gbc.gridx = 1;
        JPanel filterRow1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        filterRow1.add(publicChk); filterRow1.add(privateChk); filterRow1.add(generalChk);

        JPanel filterRow2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        filterRow2.add(onSiteChk); filterRow2.add(mobileChk);

        JPanel filterRow3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        filterRow3.add(freeChk); filterRow3.add(paidChk);

        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.add(filterRow1);
        filterPanel.add(filterRow2);
        filterPanel.add(filterRow3);

        panel.add(filterPanel, gbc);

        // --- 운영시간 (체크박스+콤보박스) ---
        row++; gbc.gridx = 0; gbc.gridy = row; panel.add(openTimeEnableChk, gbc);
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        timePanel.add(new JLabel("시작:")); timePanel.add(startHourCombo); timePanel.add(new JLabel("시"));
        timePanel.add(new JLabel("종료:")); timePanel.add(endHourCombo); timePanel.add(new JLabel("시"));
        gbc.gridx = 1; panel.add(timePanel, gbc);

        // --- 가격 ---
        row++; gbc.gridx = 0; gbc.gridy = row; panel.add(new JLabel("가격(원):"), gbc);
        gbc.gridx = 1; panel.add(priceField, gbc);

        // --- 주말 가격변동 ---
        row++; gbc.gridx = 0; gbc.gridy = row; panel.add(weekendVariesChk, gbc);
        gbc.gridx = 1; panel.add(weekendPriceField, gbc);

        // 주말 가격변동 체크박스 이벤트: 무료면 비활성화
        weekendVariesChk.addActionListener(e -> {
            boolean enabled = weekendVariesChk.isSelected() && !freeChk.isSelected();
            weekendPriceField.setEnabled(enabled);
            if (!enabled) weekendPriceField.setText("0");
            else weekendPriceField.setText("");
        });

        // --- 초과금 ---
        row++; gbc.gridx = 0; gbc.gridy = row; panel.add(new JLabel("초과금(원):"), gbc);
        gbc.gridx = 1; panel.add(extraFeeField, gbc);

        // --- 총 공간수 ---
        row++; gbc.gridx = 0; gbc.gridy = row; panel.add(new JLabel("총 공간수:"), gbc);
        gbc.gridx = 1; panel.add(totalSpaceField, gbc);

        // --- 공간유형 (2줄) ---
        row++; gbc.gridx = 0; gbc.gridy = row; panel.add(new JLabel("공간유형:"), gbc);
        gbc.gridx = 1; panel.add(spaceTypePanel1, gbc);

        row++; gbc.gridx = 1; gbc.gridy = row; panel.add(spaceTypePanel2, gbc);

        // --- 버튼 ---
        row++; gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 6));
        btnPanel.add(registerBtn); btnPanel.add(cancelBtn);
        panel.add(btnPanel, gbc);

        add(panel);

        // --- 등록 버튼 이벤트 ---
        registerBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String address = addressField.getText().trim();

            if (name.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(this, "주차장 이름과 주소를 입력하세요.");
                return;
            }

            boolean isPublic = publicChk.isSelected();
            boolean isPrivate = privateChk.isSelected();
            boolean isGeneral = generalChk.isSelected();
            boolean isOnSite = onSiteChk.isSelected();
            boolean isMobile = mobileChk.isSelected();
            boolean isFree = freeChk.isSelected();
            boolean isPaid = paidChk.isSelected();

            // 운영시간
            String openTime = "";
            String closeTime = "";
            if (openTimeEnableChk.isSelected()) {
                openTime = startHourCombo.getSelectedItem() != null ? startHourCombo.getSelectedItem().toString() : "";
                closeTime = endHourCombo.getSelectedItem() != null ? endHourCombo.getSelectedItem().toString() : "";
                if (openTime.isEmpty() || closeTime.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "운영시간을 모두 선택하세요.");
                    return;
                }
                if (Integer.parseInt(openTime) >= Integer.parseInt(closeTime)) {
                    JOptionPane.showMessageDialog(this, "종료시간은 시작시간 이후여야 합니다.");
                    return;
                }
            }

            int price = 0, weekendPrice = 0, extraFee = 0;
            try {
                if (!isFree) {
                    price = Integer.parseInt(priceField.getText().trim());
                    if (weekendVariesChk.isSelected() && weekendPriceField.isEnabled())
                        weekendPrice = Integer.parseInt(weekendPriceField.getText().trim());
                    extraFee = Integer.parseInt(extraFeeField.getText().trim());
                }
                // 무료일 때는 자동으로 0, 이미 텍스트도 0으로 들어가있음
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "가격, 주말가격, 초과금은 숫자로 입력하세요.");
                return;
            }

            // 총 공간수
            int totalSpace;
            try {
                totalSpace = Integer.parseInt(totalSpaceField.getText().trim());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "총 공간수를 숫자로 입력하세요.");
                return;
            }

            // 공간별 개수
            String[] allSpaceTypes = { "장애인", "가족", "전기차", "일반", "이륜", "대형" };
            Map<String, Integer> spaceMap = new HashMap<>();
            int sum = 0;
            for (String type : allSpaceTypes) {
                if (spaceTypeChk.get(type).isSelected()) {
                    String val = spaceTypeField.get(type).getText().trim();
                    int count = 0;
                    try {
                        count = Integer.parseInt(val);
                        if (count < 0) throw new Exception();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, type + " 공간 개수를 올바르게 입력하세요.");
                        return;
                    }
                    spaceMap.put(type, count);
                    sum += count;
                }
            }
            if (spaceMap.isEmpty()) {
                JOptionPane.showMessageDialog(this, "공간 유형을 하나 이상 선택하고 개수를 입력하세요.");
                return;
            }
            if (sum != totalSpace) {
                JOptionPane.showMessageDialog(this, "공간별 개수의 합이 총 공간수와 일치하지 않습니다.");
                return;
            }

            String userId = ui.getCurrentUser().getId();

            boolean success = ui.getServiceManager().registerFullParkingInfo(
                    userId, name, address,
                    isPublic, isPrivate, isGeneral, isOnSite, isMobile, isFree, isPaid, totalSpace,
                    openTime, closeTime, price, weekendPrice, extraFee,
                    spaceMap
            );

            if (success) {
                JOptionPane.showMessageDialog(this, "주차장, 티켓, 공간이 등록되었습니다!");
                new MyParkingForm(ui).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "등록에 실패했습니다.");
            }
        });

        cancelBtn.addActionListener(e -> {
            new MyParkingForm(ui).setVisible(true);
            dispose();
        });
    }
}