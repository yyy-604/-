package org.example.form;

import org.example.model.Parking;
import org.example.model.Ticket;
import org.example.model.Favorite;
import org.example.model.FilterOptions;
import org.example.model.Car;
import org.example.service.UI;
import org.example.service.FavoriteManager;
import org.example.service.TicketManager;
import org.example.service.SpaceManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SearchForm extends JFrame {
    private UI ui;
    private JTextField queryField;
    private JButton searchBtn, backBtn, filterBtn;
    private JTable table;
    private DefaultTableModel model;

    // 현재 표시 중인 Parking의 id를 저장 (row 인덱스 맞춤)
    private List<String> currentProductIds = new ArrayList<>();

    public SearchForm(UI ui) {
        this.ui = ui;

        setTitle("검색");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        int row = 0;

        // 검색어 입력 + 필터 버튼
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        searchPanel.add(new JLabel("검색어:"));
        queryField = new JTextField(14);
        searchPanel.add(queryField);
        filterBtn = new JButton("필터");
        searchPanel.add(filterBtn);

        filterBtn.setEnabled(ui.getCurrentUser() != null);

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(searchPanel, gbc);

        // 버튼 패널
        gbc.gridx = 0; gbc.gridy = ++row; gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        searchBtn = new JButton("검색");
        backBtn = new JButton("뒤로가기");
        btnPanel.add(searchBtn);
        btnPanel.add(backBtn);
        panel.add(btnPanel, gbc);

        // 결과 테이블: ★, 이름, 주소
        gbc.gridx = 0; gbc.gridy = ++row; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        String[] columns = {"즐겨찾기", "이름", "주소"};
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int r, int c) {
                // 즐겨찾기 컬럼만 true
                return c == 0;
            }
        };
        table = new JTable(model) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                if (col == 0 && c instanceof JLabel) { // 즐겨찾기 칸 가운데 정렬
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                }
                return c;
            }
        };
        table.setRowHeight(32);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, gbc);

        add(panel);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int selRow = table.getSelectedRow();
                    if (selRow >= 0 && selRow < currentProductIds.size()) {
                        String parkId = currentProductIds.get(selRow);
                        Parking selectedParking = ui.getServiceManager().getParkingManager().findById(parkId);
                        if (selectedParking != null) {
                            dispose();
                            new ParkingReserveForm(ui, selectedParking);
                        }
                    }
                }
            }
        });

        setVisible(true);

        // 검색 버튼 액션
        searchBtn.addActionListener(e -> {
            String query = queryField.getText().trim();
            refreshTable(query);
        });

        // 엔터키 입력 시 검색 동작
        queryField.addActionListener(e -> {
            String query = queryField.getText().trim();
            refreshTable(query);
        });

        // 뒤로가기
        backBtn.addActionListener(e -> {
            new MainMenu(ui);
            dispose();
        });

        // 필터 버튼 → 필터폼으로 이동
        filterBtn.addActionListener(e -> {
            if (ui.getCurrentUser() == null) return;
            dispose();
            new FilterForm(ui, ui.getFilterOptions(), (Car selectedCar, FilterOptions options) -> {
                ui.setFilterOptions(options);
                new SearchForm(ui);
            });
        });

        setVisible(true);

        // 진입 시 즐겨찾기만 출력 (검색어 없이 진입)
        refreshTable("");
    }

    // 검색어 + 필터옵션 둘 다 적용 (폼에서 직접 if문으로 체크!)
    private void refreshTable(String query) {
        model.setRowCount(0);
        currentProductIds.clear();

        List<Parking> all = ui.getServiceManager().getParkingManager().getAll();
        TicketManager ticketManager = ui.getServiceManager().getTicketManager();
        SpaceManager spaceManager = ui.getServiceManager().getSpaceManager();
        FavoriteManager favoriteManager = ui.getServiceManager().getFavoriteManager();
        String currentUserId = ui.getCurrentUser() != null ? ui.getCurrentUser().getId() : null;
        FilterOptions filter = ui.getFilterOptions();

        // 즐겨찾기 맵: productId → Favorite
        Set<String> favoriteSet = new HashSet<>();
        if (currentUserId != null) {
            List<Favorite> myFavs = favoriteManager.getFavoritesByUserId(currentUserId);
            for (Favorite f : myFavs) favoriteSet.add(f.getProductId());
        }

        if (query == null) query = "";
        boolean hasSearch = !query.trim().isEmpty();

        if (!hasSearch) {
            // 즐겨찾기만 출력 (필터도 적용)
            if (currentUserId != null && !favoriteSet.isEmpty()) {
                for (Parking p : all) {
                    if (!favoriteSet.contains(p.getId())) continue;

                    Ticket t = ticketManager.findByParkingId(p.getId());
                    Map<String, Boolean> hasType = spaceManager.hasSpaceTypeByPark(p.getId());
                    if (!passesFilter(p, t, hasType, filter)) continue;

                    model.addRow(new Object[]{"★", p.getName(), p.getAddress()});
                    currentProductIds.add(p.getId());
                }
            }
        } else {
            // 전체 중 검색어+필터 조건 둘 다 만족하는 것만 출력
            for (Parking p : all) {
                // 검색어 조건
                if (!(p.getName().contains(query.trim())||p.getAddress().contains(query.trim()))) continue;

                Ticket t = ticketManager.findByParkingId(p.getId());
                Map<String, Boolean> hasType = spaceManager.hasSpaceTypeByPark(p.getId());
                // 필터 조건(직접 if문으로 비교)
                if (!passesFilter(p, t, hasType, filter)) continue;

                boolean isFavorite = currentUserId != null && favoriteSet.contains(p.getId());
                model.addRow(new Object[]{isFavorite ? "★" : "☆", p.getName(), p.getAddress()});
                currentProductIds.add(p.getId());
            }
        }

        // 즐겨찾기 클릭 이벤트
        table.getColumn("즐겨찾기").setCellRenderer(new ButtonRenderer());
        table.getColumn("즐겨찾기").setCellEditor(new ButtonEditor(new JCheckBox(), ui, this));
    }

    // 필터 조건 체크를 위한 메서드 (Boolean Map으로 공간 타입 비교)
    private boolean passesFilter(Parking p, Ticket t, Map<String, Boolean> hasType, FilterOptions filter) {
        if (filter == null) return true;

        // ====== 종류 (공용/사설/일반) ======
        int typeCount = 0;
        if (filter.isPublic()) typeCount++;
        if (filter.isPrivate()) typeCount++;
        if (filter.isGeneral()) typeCount++;
        // 세 개 모두 선택 or 모두 해제면 조건 무시
        if (typeCount > 0 && typeCount < 3) {
            if (filter.isPublic() && !p.isPublic()) return false;
            if (filter.isPrivate() && !p.isPrivate()) return false;
            if (filter.isGeneral() && !p.isGeneral()) return false;
        }

        // ====== 결제 방식 (현장/모바일) ======
        int payCount = 0;
        if (filter.isOnSite()) payCount++;
        if (filter.isMobile()) payCount++;
        // 두 개 모두 선택 or 모두 해제면 조건 무시
        if (payCount == 1) {
            if (filter.isOnSite() && !p.isOnSite()) return false;
            if (filter.isMobile() && !p.isMobile()) return false;
        }

        // ====== 요금 조건 (무료/유료) ======
        boolean free = filter.isFree();
        boolean paid = filter.isPaid();
        // 둘 다 체크 or 둘 다 해제면 무시, 하나만 체크 시만 필터
        if (free ^ paid) { // 하나만 true일 때만!
            if (free && (p == null || !p.isFree())) return false;
            if (paid && (p == null || !p.isPaid())) return false;
        }

        // ====== 가격 ======
        if (filter.getPrice() > 0 && (t == null || t.getPrice() > filter.getPrice())) return false;

        // ====== 공간 타입/특화구역 등 기타 조건 ======
        if (filter.isDisabled()   && !hasType.getOrDefault("장애인", false)) return false;
        if (filter.isFamily()     && !hasType.getOrDefault("가족", false)) return false;
        if (filter.isTwoWheeler() && !hasType.getOrDefault("이륜", false)) return false;
        if (filter.isHousehold()  && !hasType.getOrDefault("가정용", false)) return false;
        if (filter.isLarge()      && !hasType.getOrDefault("대형", false)) return false;
        if (filter.isEV()         && !hasType.getOrDefault("전기차", false)) return false;

        // 모두 통과
        return true;
    }

    // 커스텀 버튼 렌더러
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() { setOpaque(true); }
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "☆" : value.toString());
            setFont(getFont().deriveFont(Font.PLAIN, 22));
            setHorizontalAlignment(SwingConstants.CENTER);
            return this;
        }
    }

    // 커스텀 버튼 에디터
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;
        private UI ui;
        private SearchForm parentForm;

        public ButtonEditor(JCheckBox checkBox, UI ui, SearchForm parentForm) {
            super(checkBox);
            this.ui = ui;
            this.parentForm = parentForm;
            button = new JButton();
            button.setFont(button.getFont().deriveFont(Font.PLAIN, 22));
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            label = (value == null) ? "☆" : value.toString();
            button.setText(label);
            button.setHorizontalAlignment(SwingConstants.CENTER);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                int rowIdx = table.getSelectedRow();
                if (rowIdx >= 0 && rowIdx < currentProductIds.size() && ui.getCurrentUser() != null) {
                    String currentUserId = ui.getCurrentUser().getId();
                    String productId = currentProductIds.get(rowIdx);

                    FavoriteManager favoriteManager = ui.getServiceManager().getFavoriteManager();
                    Favorite fav = favoriteManager.getFavoriteByUserAndProduct(currentUserId, productId);
                    if (fav == null) {
                        favoriteManager.add(new Favorite(UUID.randomUUID().toString(), currentUserId, productId, ""));
                        button.setText("★");
                    } else {
                        favoriteManager.removeByUserAndProduct(currentUserId, productId);
                        button.setText("☆");
                    }
                    String query = queryField.getText().trim();
                    SwingUtilities.invokeLater(() -> parentForm.refreshTable(query));
                }
            }
            isPushed = false;
            return label;
        }
    }
}