package org.example.model;

public class Space extends Product {
    private String spaceType;    // 예: "일반", "장애인", "전기차" 등
    private boolean isAvailable; // 현재 이용 가능 여부
    private String parkId;       // 소속 주차장 ID

    public Space() {}

    public Space(String id, String spaceType, boolean isAvailable, String parkId) {
        super(id);
        this.spaceType = spaceType;
        this.isAvailable = isAvailable;
        this.parkId = parkId;
    }

    public String getSpaceType() { return spaceType; }
    public void setSpaceType(String spaceType) { this.spaceType = spaceType; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean isAvailable) { this.isAvailable = isAvailable; }

    public String getParkId() { return parkId; }
    public void setParkId(String parkId) { this.parkId = parkId; }
}