package org.example.form; // 차량 객체를 폼에서 만듬 (수정)

import javax.swing.*;
import java.awt.*;
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
    private JRadioButton twoWheelRadio;
    private JRadioButton generalRadio;
    private JRadioButton largeRadio;
    private JCheckBox electricChk;
    private ButtonGroup carTypeGroup;

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
        setSize(470, 440);
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

        // 차량번호 입력란
        gbc.gridy = 3; gbc.gridwidth = 1;
        gbc.gridx = 0;
        add(new JLabel("차량번호:"), gbc);
        gbc.gridx = 1;
        carNumberField = new JTextField(10);
        add(carNumberField, gbc);

        // 차종 라디오 버튼 + 전기차 체크박스
        gbc.gridy = 4;
        gbc.gridx = 0;
        add(new JLabel("차종:"), gbc);
        gbc.gridx = 1;
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        twoWheelRadio = new JRadioButton("2륜차");
        generalRadio = new JRadioButton("일반차");
        largeRadio = new JRadioButton("대형차");
        electricChk = new JCheckBox("전기차");
        carTypeGroup = new ButtonGroup();
        carTypeGroup.add(twoWheelRadio);
        carTypeGroup.add(generalRadio);
        carTypeGroup.add(largeRadio);
        typePanel.add(twoWheelRadio);
        typePanel.add(generalRadio);
        typePanel.add(largeRadio);
        typePanel.add(electricChk);
        add(typePanel, gbc);

        // 차량 목록 JList
        carListModel = new DefaultListModel<>();
        if (user.getCars() != null) {
            for (Car car : user.getCars()) carListModel.addElement(car);
        }
        carJList = new JList<>(carListModel);
        carJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carJList.setVisibleRowCount(4);
        carJList.setFixedCellWidth(370);
        carJList.setCellRenderer(new CarListCellRenderer());

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        add(new JScrollPane(carJList), gbc);

        // 리스트에서 선택 → 입력란 반영
        carJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && carJList.getSelectedValue() != null) {
                Car selected = carJList.getSelectedValue();
                carNumberField.setText(selected.getCarNumber());
                String type = selected.getCarType();
                twoWheelRadio.setSelected(type.equals("이륜차"));
                generalRadio.setSelected(type.equals("일반차"));
                largeRadio.setSelected(type.equals("대형차"));
                electricChk.setSelected(selected.isElectric());
            }
        });

        // 차량 추가 버튼
        addCarBtn = new JButton("차량 추가");
        addCarBtn.addActionListener(e -> {
            String num = carNumberField.getText().trim();
            String type = getSelectedCarType();
            boolean isElectric = electricChk.isSelected();
            if (!num.isEmpty() && type != null) {
                carListModel.addElement(new Car(num, type, isElectric));
                carNumberField.setText("");
                carTypeGroup.clearSelection();
                electricChk.setSelected(false);
            }
        });

        // 차량 정보 수정(선택) 버튼
        saveCarBtn = new JButton("선택 수정");
        saveCarBtn.addActionListener(e -> {
            int idx = carJList.getSelectedIndex();
            String num = carNumberField.getText().trim();
            String type = getSelectedCarType();
            boolean isElectric = electricChk.isSelected();
            if (idx >= 0 && !num.isEmpty() && type != null) {
                carListModel.set(idx, new Car(num, type, isElectric));
            }
        });

        // 차량 삭제 버튼
        deleteCarBtn = new JButton("삭제");
        deleteCarBtn.addActionListener(e -> {
            int idx = carJList.getSelectedIndex();
            if (idx >= 0) {
                carListModel.remove(idx);
                carNumberField.setText("");
                carTypeGroup.clearSelection();
                electricChk.setSelected(false);
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

        // 저장 버튼 이벤트
        saveUserBtn.addActionListener(e -> saveUserInfo());

        // 돌아가기 버튼 이벤트
        backBtn.addActionListener(e -> {
            dispose();
            new MainMenu(ui);
        });

        setVisible(true);
    }

    private void saveUserInfo() {
        String name = nameField.getText().trim();
        String phone = phoneNumberField.getText().trim();
        List<Car> cars = new ArrayList<>();
        for (int i = 0; i < carListModel.size(); i++) {
            cars.add(carListModel.get(i));
        }

        ui.updateUserInfo(name, phone);  // 이름과 전화번호만 전달
        ui.updateUserCars(cars);         // 차량 리스트는 따로 전달

        JOptionPane.showMessageDialog(this, "정보가 저장되었습니다!");
        dispose();
        new MainMenu(ui);
    }

    private String getSelectedCarType() {
        if (twoWheelRadio.isSelected()) return "이륜차";
        if (generalRadio.isSelected()) return "일반차";
        if (largeRadio.isSelected()) return "대형차";
        return null;
    }

    static class CarListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int idx, boolean isSelected, boolean cellHasFocus) {
            Car car = (Car) value;
            String text = "차량번호: " + car.getCarNumber() + "   차종: " + car.getCarType();
            if (car.isElectric()) text += " (전기차)";
            return super.getListCellRendererComponent(list, text, idx, isSelected, cellHasFocus);
        }
    }
}