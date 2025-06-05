package org.example.form;

import org.example.service.UI;
import org.example.model.FilterOptions;

import javax.swing.*;
import java.awt.*;

public class SearchForm extends JFrame {
    private UI ui;

    private JTextField queryField;
    private JButton searchBtn;
    private JButton backBtn;

    public SearchForm(UI ui) {
        this.ui = ui;

        setTitle("검색");
        setSize(400, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel queryLabel = new JLabel("검색어:");
        queryField = new JTextField(18);

        searchBtn = new JButton("검색");
        backBtn = new JButton("뒤로가기");

        int row = 0;
        gbc.anchor = GridBagConstraints.EAST;

        gbc.gridx = 0; gbc.gridy = row; panel.add(queryLabel, gbc);
        gbc.gridx = 1; panel.add(queryField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row; gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnPanel.add(searchBtn);
        btnPanel.add(backBtn);
        panel.add(btnPanel, gbc);

        add(panel);

        // 검색 버튼 액션
        searchBtn.addActionListener(e -> {
            String query = queryField.getText().trim();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "검색어를 입력하세요!");
                return;
            }
            // FilterOptions를 사용해 UI에 설정 (옵션 확장 가능)
            FilterOptions filter = new FilterOptions();
            filter.setQuery(query);
            ui.setFilterOptions(filter);

            // 실제 검색 결과 창/리스트 등으로 이동할 수 있음 (여기서는 메시지만)
            JOptionPane.showMessageDialog(this, "검색: " + query);
            // ex) new SearchResultForm(ui);
            // dispose();
        });

        // 뒤로가기 버튼
        backBtn.addActionListener(e -> {
            new MainMenu(ui);
            dispose();
        });

        setVisible(true);
    }
}