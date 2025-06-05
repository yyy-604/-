package org.example.form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import org.example.model.User;
import org.example.model.Car;
import org.example.service.UI;

public class InfoEditForm extends JFrame {
    private User user;
    private UI ui;

    private JTextField nameField;
    private JTextField phoneNumberField;

    private JTextField carNumberField;
    private JTextField carTypeField;

    private DefaultListModel<Car> carListModel;
    private JList<Car> carJList;

    private JButton addCarBtn;
    private JButton saveCarBtn;
    private JButton deleteCarBtn;
    private JButton saveUserBtn;
    private JButton backBtn;

    public InfoEditForm(UI ui) {
        this.ui = ui;
        this.user = ui.getCurrentUser();

        setTitle("정보 수정");
        setSize(430, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // 이름
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("이름:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(user.getName(), 15);
        add(nameField, gbc);

        // 전화번호
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("전화번호:"), gbc);
        gbc.gridx = 1;
        phoneNumberField = new JTextField(user.getPhoneNumber(), 15);
        add(phoneNumberField, gbc);

        // 차량 정보 레이블
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        add(new JLabel("차량 정보"), gbc);

        // 차량번호/차종 입력란
        gbc.gridy = 3; gbc.gridwidth = 1;
        gbc.gridx = 0;
        add(new JLabel("차량번호:"), gbc);
        gbc.gridx = 1;
        carNumberField = new JTextField(10);
        add(carNumberField, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        add(new JLabel("차종:"), gbc);
        gbc.gridx = 1;
        carTypeField = new JTextField(10);
        add(carTypeField, gbc);

        // 차량 목록 JList
        carListModel = new DefaultListModel<>();
        if (user.getCars() != null) {
            for (Car car : user.getCars()) carListModel.addElement(car);
        }
        carJList = new JList<>(carListModel);
        carJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carJList.setVisibleRowCount(4);
        carJList.setFixedCellWidth(340);
        carJList.setCellRenderer(new CarListCellRenderer());

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        add(new JScrollPane(carJList), gbc);

        // 리스트에서 선택 → 입력란 반영
        carJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && carJList.getSelectedValue() != null) {
                Car selected = carJList.getSelectedValue();
                carNumberField.setText(selected.getCarNumber());
                carTypeField.setText(selected.getCarType());
            }
        });

        // 차량 추가 버튼
        addCarBtn = new JButton("차량 추가");
        addCarBtn.addActionListener(e -> {
            String num = carNumberField.getText().trim();
            String type = carTypeField.getText().trim();
            if (!num.isEmpty()) {
                carListModel.addElement(new Car(num, type));
                carNumberField.setText("");
                carTypeField.setText("");
            }
        });

        // 차량 정보 수정(선택) 버튼
        saveCarBtn = new JButton("선택 수정");
        saveCarBtn.addActionListener(e -> {
            int idx = carJList.getSelectedIndex();
            if (idx >= 0) {
                String num = carNumberField.getText().trim();
                String type = carTypeField.getText().trim();
                if (!num.isEmpty()) {
                    carListModel.set(idx, new Car(num, type));
                }
            }
        });

        // 차량 삭제 버튼
        deleteCarBtn = new JButton("삭제");
        deleteCarBtn.addActionListener(e -> {
            int idx = carJList.getSelectedIndex();
            if (idx >= 0) {
                carListModel.remove(idx);
                carNumberField.setText("");
                carTypeField.setText("");
            }
        });

        // 차량 조작 버튼 패널
        JPanel carBtnPanel = new JPanel();
        carBtnPanel.add(addCarBtn);
        carBtnPanel.add(saveCarBtn);
        carBtnPanel.add(deleteCarBtn);

        gbc.gridy = 6;
        add(carBtnPanel, gbc);

        // 하단 버튼 패널 (저장, 돌아가기)
        saveUserBtn = new JButton("저장");
        backBtn = new JButton("돌아가기");

        JPanel bottomBtnPanel = new JPanel();
        bottomBtnPanel.add(saveUserBtn);
        bottomBtnPanel.add(backBtn);

        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(bottomBtnPanel, gbc);

        // 저장 버튼 이벤트 (업데이트 + 메인화면 이동)
        saveUserBtn.addActionListener(e -> saveUserInfo());

        // 돌아가기 버튼 이벤트 (바로 메인화면 이동)
        backBtn.addActionListener(e -> {
            dispose();
            new MainMenu(ui); // 메인화면으로 이동
        });

        setVisible(true);
    }

    private void saveUserInfo() {
        user.setName(nameField.getText().trim());
        user.setPhoneNumber(phoneNumberField.getText().trim());
        List<Car> cars = new ArrayList<>();
        for (int i = 0; i < carListModel.size(); i++) {
            cars.add(carListModel.get(i));
        }
        user.setCars(cars);

        // 업데이트 처리 (DB나 UserManager에 반영)
        ui.updateUser(user);

        JOptionPane.showMessageDialog(this, "정보가 저장되었습니다!");
        dispose();
        new MainMenu(ui); // 메인화면으로 이동
    }

    // Car 정보를 보기 쉽게 표시
    static class CarListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int idx, boolean isSelected, boolean cellHasFocus) {
            Car car = (Car) value;
            String text = "차량번호: " + car.getCarNumber() + "   차종: " + car.getCarType();
            return super.getListCellRendererComponent(list, text, idx, isSelected, cellHasFocus);
        }
    }
}