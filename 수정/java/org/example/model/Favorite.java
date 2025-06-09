package org.example.model;

public class Favorite {
    private String id;         // 즐겨찾기 ID
    private String userId;     // 소유 유저 ID
    private String productId;  // 대상(Product)의 ID
    private String memo;       // 메모, 별명 등

    public Favorite() {}

    public Favorite(String id, String userId, String productId, String memo) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.memo = memo;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getMemo() { return memo; }
    public void setMemo(String memo) { this.memo = memo; }
}