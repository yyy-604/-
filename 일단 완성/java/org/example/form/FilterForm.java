package org.example.form;

import org.example.model.Car;
import org.example.model.FilterOptions;
import org.example.model.User;
import org.example.service.UI;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class FilterForm extends JDialog {
    private FilterOptions filterOptions;
    private JComboBox<Car> carComboBox;
    private JCheckBox publicChk, privateChk, generalChk;
    private JCheckBox onSiteChk, mobileChk;
    private JCheckBox freeChk, paidChk;
    private JCheckBox priceLimitChk;
    private JSpinner priceSpinner;

    // 차량종류(특화구역) 체크박스
    private JCheckBox twoWheelChk, householdChk, largeChk, electricChk;
    private JCheckBox disabledChk, familyChk, twoWheelerZoneChk, householdZoneChk, largeZoneChk, electricZoneChk;

    private JButton applyBtn, cancelBtn;

    public interface FilterApplyListener {
        void onFilterApplied(Car selectedCar, FilterOptions filter);
    }

    public FilterForm(UI ui, FilterOptions initialOptions, FilterApplyListener listener) {
        super((JFrame) null, "필터 설정", true);
        setSize(470, 490);
        setLocationRelativeTo(null);

        User user = ui.getCurrentUser();
        this.filterOptions = (user.getFilterOption() != null) ? user.getFilterOption() :
                (initialOptions != null ? initialOptions : new FilterOptions());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // 차량 선택
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("차량 선택:"), gbc);
        gbc.gridx = 1;
        DefaultComboBoxModel<Car> carModel = new DefaultComboBoxModel<>();
        carModel.addElement(null); // "미선택"용
        if (user.getCars() != null) {
            for (Car c : user.getCars()) carModel.addElement(c);
        }
        carComboBox = new JComboBox<>(carModel);
        carComboBox.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
            String text = (value == null) ? "차량 미선택" : value.getCarNumber() + " (" + value.getCarType() + (value.isElectric() ? "/전기차" : "") + ")";
            return new JLabel(text);
        });
        panel.add(carComboBox, gbc);

        // [차량 종류/특화구역] 한 줄 아래
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        panel.add(new JLabel("차량 종류:"), gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        JPanel typePanel = new JPanel();
        twoWheelChk = new JCheckBox("2륜차");
        householdChk = new JCheckBox("일반차");
        largeChk = new JCheckBox("대형차");
        electricChk = new JCheckBox("전기차");
        typePanel.add(twoWheelChk);
        typePanel.add(householdChk);
        typePanel.add(largeChk);
        typePanel.add(electricChk);
        panel.add(typePanel, gbc);

        // 차량 선택 시 체크박스 자동 세팅, 해제/잠금
        carComboBox.addActionListener(e -> {
            Car sel = (Car) carComboBox.getSelectedItem();
            boolean editable = (sel == null);
            if (sel == null) {
                // 미선택 → 직접 체크 가능
                twoWheelChk.setEnabled(true); householdChk.setEnabled(true); largeChk.setEnabled(true); electricChk.setEnabled(true);
                twoWheelChk.setSelected(false); householdChk.setSelected(false); largeChk.setSelected(false); electricChk.setSelected(false);
            } else {
                // 차량 선택 → 자동 체크 + 비활성화
                String type = sel.getCarType();
                twoWheelChk.setSelected(type.equals("2륜차"));
                householdChk.setSelected(type.equals("일반차"));
                largeChk.setSelected(type.equals("대형차"));
                electricChk.setSelected(sel.isElectric());
                twoWheelChk.setEnabled(false); householdChk.setEnabled(false); largeChk.setEnabled(false); electricChk.setEnabled(false);
            }
        });
        // 첫 진입 시 상태 반영
        carComboBox.setSelectedIndex(0);

        // 종류(공용/사설/일반)
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        panel.add(new JLabel("종류:"), gbc);
        gbc.gridx = 1;
        JPanel lotTypePanel = new JPanel();
        publicChk = new JCheckBox("공용", filterOptions.isPublic());
        privateChk = new JCheckBox("사설", filterOptions.isPrivate());
        generalChk = new JCheckBox("일반", filterOptions.isGeneral());
        lotTypePanel.add(publicChk);
        lotTypePanel.add(privateChk);
        lotTypePanel.add(generalChk);
        panel.add(lotTypePanel, gbc);

        // 결제 방식
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("결제 방식:"), gbc);
        gbc.gridx = 1;
        JPanel payPanel = new JPanel();
        onSiteChk = new JCheckBox("현장", filterOptions.isOnSite());
        mobileChk = new JCheckBox("모바일", filterOptions.isMobile());
        payPanel.add(onSiteChk);
        payPanel.add(mobileChk);
        panel.add(payPanel, gbc);

        // 요금
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("요금 조건:"), gbc);
        gbc.gridx = 1;
        JPanel pricePanel = new JPanel();
        freeChk = new JCheckBox("무료", filterOptions.isFree());
        paidChk = new JCheckBox("유료", filterOptions.isPaid());
        pricePanel.add(freeChk);
        pricePanel.add(paidChk);
        panel.add(pricePanel, gbc);

        // 특화구역
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("특화구역:"), gbc);
        gbc.gridx = 1;
        JPanel zonePanel = new JPanel();
        disabledChk = new JCheckBox("장애인", filterOptions.isDisabled());
        familyChk = new JCheckBox("가족", filterOptions.isFamily());
        zonePanel.add(disabledChk);
        zonePanel.add(familyChk);
        panel.add(zonePanel, gbc);

        // 가격 제한 체크박스 + 가격 입력란
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("최대 가격:"), gbc);
        gbc.gridx = 1;
        JPanel priceLimitPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        priceLimitChk = new JCheckBox("가격 제한 사용");
        boolean limitChecked = filterOptions.getPrice() > 0;
        priceLimitChk.setSelected(limitChecked);
        priceSpinner = new JSpinner(new SpinnerNumberModel(
                limitChecked ? filterOptions.getPrice() : 1000,
                0, 100000, 100
        ));
        priceSpinner.setEnabled(limitChecked);

        priceLimitChk.addActionListener(e -> {
            priceSpinner.setEnabled(priceLimitChk.isSelected());
        });

        priceLimitPanel.add(priceLimitChk);
        priceLimitPanel.add(priceSpinner);
        priceLimitPanel.add(new JLabel("원 이하"));
        panel.add(priceLimitPanel, gbc);

        // 버튼
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel btnPanel = new JPanel();
        applyBtn = new JButton("적용");
        cancelBtn = new JButton("취소");
        btnPanel.add(applyBtn);
        btnPanel.add(cancelBtn);
        panel.add(btnPanel, gbc);

        add(panel);

        // 적용 이벤트
        applyBtn.addActionListener(e -> {
            filterOptions.setPublic(publicChk.isSelected());
            filterOptions.setPrivate(privateChk.isSelected());
            filterOptions.setGeneral(generalChk.isSelected());
            filterOptions.setOnSite(onSiteChk.isSelected());
            filterOptions.setMobile(mobileChk.isSelected());
            filterOptions.setFree(freeChk.isSelected());
            filterOptions.setPaid(paidChk.isSelected());

            // 차량 종류 필터
            filterOptions.setTwoWheeler(twoWheelChk.isSelected());
            filterOptions.setHousehold(householdChk.isSelected());
            filterOptions.setLarge(largeChk.isSelected());
            filterOptions.setEV(electricChk.isSelected());

            // 특화구역
            filterOptions.setDisabled(disabledChk.isSelected());
            filterOptions.setFamily(familyChk.isSelected());

            if (priceLimitChk.isSelected()) {
                filterOptions.setPrice((Integer) priceSpinner.getValue());
            } else {
                filterOptions.setPrice(0);
            }

            // 현재 유저에 반영 (유저 간 필터 분리)
            ui.getCurrentUser().setFilterOption(filterOptions);

            Car selectedCar = (Car) carComboBox.getSelectedItem();
            listener.onFilterApplied(selectedCar, filterOptions);

            dispose();
            new SearchForm(ui);
        });

        cancelBtn.addActionListener(e -> {
            dispose();
            new SearchForm(ui);
        });

        setVisible(true);
    }
}