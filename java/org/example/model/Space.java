package org.example.model;

import java.util.UUID;

public class Space extends Product {
    private UUID parkingId;       // 소속 주차장 ID

    private String spaceType;    // 예: "일반", "장애인", "전기차" 등
    private boolean isAvailable; // 현재 이용 가능 여부

    public Space(
        UUID parkingId,
        String spaceType) {
        super();

        this.parkingId = parkingId;

        this.spaceType = spaceType;
        this.isAvailable = false;
    }

    public UUID getParkingId() { return parkingId; }

    public String getSpaceType() { return spaceType; }
    public void setSpaceType(String spaceType) { this.spaceType = spaceType; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean isAvailable) { this.isAvailable = isAvailable; }
}