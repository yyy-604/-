package org.example.form;

import org.example.service.UI;
import org.example.model.Parking;
import org.example.model.Ticket;

import javax.swing.*;
import java.awt.*;

public class ParkingEditForm extends JFrame {
    private final UI ui;
    private final Parking parking;
    private final Ticket ticket; // 주차장별 티켓을 미리 가져오거나 서비스에서 조회

    public ParkingEditForm(UI ui, Parking parking) {
        this.ui = ui;
        this.parking = parking;
        this.ticket = ui.getServiceManager().findTicketByParkingId(parking.getId());

        if (parking == null) {
            JOptionPane.showMessageDialog(this, "해당 주차장 정보를 찾을 수 없습니다!");
            openMyParkingForm();
            dispose();
            return;
        }

        setTitle("주차장 정보 수정");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;
        int row = 0;

        // 주차장 ID(수정불가)
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("주차장ID:"), gbc);
        JTextField parkIdField = new JTextField(14);
        parkIdField.setText(parking.getId());
        parkIdField.setEditable(false);
        gbc.gridx = 1;
        panel.add(parkIdField, gbc);

        // 이름
        gbc.gridx = 0; gbc.gridy = ++row;
        panel.add(new JLabel("이름:"), gbc);
        JTextField nameField = new JTextField(14);
        nameField.setText(parking.getName());
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        // 주소
        gbc.gridx = 0; gbc.gridy = ++row;
        panel.add(new JLabel("주소:"), gbc);
        JTextField addressField = new JTextField(14);
        addressField.setText(parking.getAddress());
        gbc.gridx = 1;
        panel.add(addressField, gbc);

        // 속성 체크박스
        JCheckBox publicBox = new JCheckBox("공용", parking.isPublic());
        JCheckBox privateBox = new JCheckBox("사설", parking.isPrivate());
        JCheckBox generalBox = new JCheckBox("일반", parking.isGeneral());
        JCheckBox onSiteBox = new JCheckBox("현장결제", parking.isOnSite());
        JCheckBox mobileBox = new JCheckBox("모바일결제", parking.isMobile());
        JCheckBox freeBox = new JCheckBox("무료", ticket != null && ticket.getPrice() == 0);
        JCheckBox paidBox = new JCheckBox("유료", ticket != null && ticket.getPrice() != 0);

        // 속성 UI (3줄 배치)
        gbc.gridx = 0; gbc.gridy = ++row;
        panel.add(new JLabel("속성:"), gbc);

        gbc.gridx = 1;
        JPanel filterRow1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        filterRow1.add(publicBox); filterRow1.add(privateBox); filterRow1.add(generalBox);
        JPanel filterRow2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        filterRow2.add(onSiteBox); filterRow2.add(mobileBox);
        JPanel filterRow3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        filterRow3.add(freeBox); filterRow3.add(paidBox);

        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.add(filterRow1);
        filterPanel.add(filterRow2);
        filterPanel.add(filterRow3);
        panel.add(filterPanel, gbc);

        // 가격/초과금 필드 (Ticket 기준)
        gbc.gridx = 0; gbc.gridy = ++row;
        panel.add(new JLabel("가격(원):"), gbc);
        JTextField priceField = new JTextField(8);
        priceField.setText(ticket != null ? String.valueOf(ticket.getPrice()) : "");
        gbc.gridx = 1;
        panel.add(priceField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        panel.add(new JLabel("초과금(원):"), gbc);
        JTextField extraFeeField = new JTextField(8);
        extraFeeField.setText(ticket != null ? String.valueOf(ticket.getExtraPrice()) : "");
        gbc.gridx = 1;
        panel.add(extraFeeField, gbc);

        // 무료 체크: 가격/초과금 필드 활성화/비활성화
        freeBox.addActionListener(e -> {
            boolean isFree = freeBox.isSelected();
            priceField.setEnabled(!isFree);
            extraFeeField.setEnabled(!isFree);
            if (isFree) {
                priceField.setText("0");
                extraFeeField.setText("0");
            } else {
                if (priceField.getText().equals("0")) priceField.setText("");
                if (extraFeeField.getText().equals("0")) extraFeeField.setText("");
            }
        });

        // 공간수 + 공간수정 버튼
        gbc.gridx = 0; gbc.gridy = ++row;
        panel.add(new JLabel("주차 공간 수:"), gbc);

        JPanel spacePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        JTextField spaceCountField = new JTextField(8);
        // 공간수는 항상 SpaceManager에서 parkId로 계산해서 표시 (읽기전용)
        int spaceCount = ui.getServiceManager().getSpaceManager().findByParkingId(parking.getId()).size();
        spaceCountField.setText(String.valueOf(spaceCount));
        spaceCountField.setEditable(false); // 읽기전용

        JButton editSpaceBtn = new JButton("공간수정");
        spacePanel.add(spaceCountField);
        spacePanel.add(editSpaceBtn);
        gbc.gridx = 1;
        panel.add(spacePanel, gbc);

        // 공간수정 버튼 이벤트
        editSpaceBtn.addActionListener(e -> {
            new SpaceEditForm(ui, parking.getId()).setVisible(true);
            dispose();
        });

        // 버튼
        JButton updateBtn = new JButton("수정");
        JButton backBtn = new JButton("뒤로가기");

        gbc.gridx = 0; gbc.gridy = ++row; gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnPanel.add(updateBtn);
        btnPanel.add(backBtn);
        panel.add(btnPanel, gbc);

        add(panel);

        // "수정" 버튼 액션
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

            int price = 0, extraFee = 0;
            try {
                if (!isFree) {
                    price = Integer.parseInt(priceField.getText().trim());
                    extraFee = Integer.parseInt(extraFeeField.getText().trim());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "가격과 초과금은 숫자로 입력하세요!");
                return;
            }

            if (name.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(this, "이름, 주소는 필수 입력입니다!");
                return;
            }

            // Parking 정보만 변경 (공간수는 Space에서 관리)
            ui.getServiceManager().updateParking(
                    parking.getId(),
                    name,
                    address,
                    isPublic,
                    isPrivate,
                    isGeneral,
                    isOnSite,
                    isMobile,
                    isFree,
                    isPaid,
                    spaceCount // 공간수도 갱신, SpaceEditForm 저장시 Parking에 setSpaceCount 하면 더 명확함
            );

            // Ticket 정보 변경
            if (ticket != null) {
                ui.getServiceManager().updateTicket(
                        ticket.getId(),
                        parking.getId(),
                        price,
                        extraFee
                );
            }

            JOptionPane.showMessageDialog(this, "주차장 정보가 수정되었습니다!");
            openMyParkingForm();
            dispose();
        });

        // "뒤로가기" 버튼 액션
        backBtn.addActionListener(e -> {
            openMyParkingForm();
            dispose();
        });

        setVisible(true);
    }

    // 내 주차장 창 새로 열기 (항상 최신 상태)
    private void openMyParkingForm() {
        new MyParkingForm(ui).setVisible(true);
    }
}