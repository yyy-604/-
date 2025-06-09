package org.example.form;

import org.example.model.Space;
import org.example.model.Parking;
import org.example.service.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Set;
import java.util.UUID;

public class SpaceEditForm extends JFrame {
    private final UI ui;
    private final String parkId;
    private DefaultTableModel tableModel;
    private JTable table;

    private static final String[] SPACE_TYPES = { "장애인", "가족", "전기차", "일반", "이륜", "대형" };
    private Map<String, JLabel> countLabels = new HashMap<>(); // 공간별 개수 라벨

    // 삭제 후보 공간 ID 저장
    private Set<String> deletedSpaceIds = new HashSet<>();

    public SpaceEditForm(UI ui, String parkId) {
        this.ui = ui;
        this.parkId = parkId;

        setTitle("주차장 공간(장소) 관리");
        setSize(640, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 상단 공간별 개수 라벨 패널
        JPanel countPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 4));
        for (String type : SPACE_TYPES) {
            JLabel lbl = new JLabel(type + ": 0");
            countLabels.put(type, lbl);
            countPanel.add(lbl);
        }

        // 테이블 모델 구성
        tableModel = new DefaultTableModel(new String[]{"ID", "유형", "이용가능"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                // ID는 편집불가, 나머지는 가능
                return col != 0;
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2) return Boolean.class;
                return super.getColumnClass(columnIndex);
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(26);

        // 공간 유형 셀 드롭다운으로(ComboBox 에디터)
        JComboBox<String> comboBox = new JComboBox<>(SPACE_TYPES);
        table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(comboBox));

        JScrollPane scrollPane = new JScrollPane(table);

        // 하단 버튼 패널
        JButton addBtn = new JButton("공간 추가");
        JButton delBtn = new JButton("선택 삭제");
        JButton saveBtn = new JButton("저장");
        JButton backBtn = new JButton("뒤로가기");

        JPanel btnPanel = new JPanel();
        btnPanel.add(addBtn);
        btnPanel.add(delBtn);
        btnPanel.add(saveBtn);
        btnPanel.add(backBtn);

        add(countPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        // --- 데이터 로드 및 카운트 표시 ---
        loadSpaces();
        updateTypeCounts();

        // --- 테이블 변경 감지 리스너로 카운트 자동 업데이트 ---
        tableModel.addTableModelListener(e -> updateTypeCounts());

        // --- 버튼 이벤트 ---
        addBtn.addActionListener(e -> {
            Vector<Object> row = new Vector<>();
            row.add(""); // ID
            row.add(SPACE_TYPES[0]);
            row.add(Boolean.TRUE);
            tableModel.addRow(row);
            updateTypeCounts();
        });

        delBtn.addActionListener(e -> {
            int[] selected = table.getSelectedRows();
            for (int i = selected.length - 1; i >= 0; i--) {
                String id = (String) tableModel.getValueAt(selected[i], 0);
                // 삭제할 id를 모아둠(빈 값이면 신규 row라 무시)
                if (id != null && !id.isEmpty()) {
                    deletedSpaceIds.add(id);
                }
                tableModel.removeRow(selected[i]);
            }
            updateTypeCounts();
        });

        saveBtn.addActionListener(e -> {
            // 1. 삭제된 공간 처리
            for (String delId : deletedSpaceIds) {
                ui.getServiceManager().getSpaceManager().remove(delId);
            }
            deletedSpaceIds.clear();

            // 2. 남은 row는 추가/수정 처리
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String id = (String) tableModel.getValueAt(i, 0);
                String type = (String) tableModel.getValueAt(i, 1);
                boolean available = Boolean.TRUE.equals(tableModel.getValueAt(i, 2));
                if (id == null || id.isEmpty()) {
                    // 새 공간
                    Space newSpace = new Space(UUID.randomUUID().toString(), type, available, parkId);
                    ui.getServiceManager().getSpaceManager().add(newSpace);
                    tableModel.setValueAt(newSpace.getId(), i, 0);
                } else {
                    Space space = ui.getServiceManager().getSpaceManager().findById(id);
                    if (space != null) {
                        space.setSpaceType(type);
                        space.setAvailable(available);
                        ui.getServiceManager().getSpaceManager().update(space);
                    }
                }
            }
            JOptionPane.showMessageDialog(this, "공간 정보가 저장되었습니다.");
            openParkingEditForm();
            dispose();
        });

        backBtn.addActionListener(e -> {
            openParkingEditForm(); // 뒤로가기 시에도 주차장 수정 창 열기
            dispose();
        });
    }

    private void loadSpaces() {
        tableModel.setRowCount(0);
        List<Space> spaceList = ui.getServiceManager().getSpaceManager().findByParkingId(parkId);
        for (Space s : spaceList) {
            tableModel.addRow(new Object[]{
                    s.getId(),
                    s.getSpaceType(),
                    s.isAvailable()
            });
        }
    }

    // 공간유형별 개수 카운트 갱신
    private void updateTypeCounts() {
        Map<String, Integer> counts = new HashMap<>();
        for (String t : SPACE_TYPES) counts.put(t, 0);
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String type = (String) tableModel.getValueAt(i, 1);
            if (counts.containsKey(type)) {
                counts.put(type, counts.get(type) + 1);
            }
        }
        for (String t : SPACE_TYPES) {
            countLabels.get(t).setText(t + ": " + counts.get(t));
        }
    }

    // --- 저장 또는 뒤로가기 시 ParkingEditForm 열기 ---
    private void openParkingEditForm() {
        Parking parking = ui.getServiceManager().findParking(parkId);
        if (parking != null) {
            new ParkingEditForm(ui, parking).setVisible(true);
        }
    }
}