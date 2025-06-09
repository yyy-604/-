package org.example.model;

import java.time.LocalDateTime;

public class ParkingPass extends Product {

    private String userId;        // 소유 유저 ID
    private String parkingId;     // 대상 주차장 ID
    private String spaceId;       // 예약된 공간 ID
    private LocalDateTime time;   // 사용 시작 시간 등

    public ParkingPass(String id, String userId, String parkingId, String spaceId, LocalDateTime time) {
        super(id);
        this.userId = userId;
        this.parkingId = parkingId;
        this.spaceId = spaceId;
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getParkingId() {
        return parkingId;
    }

    public void setParkingId(String parkingId) {
        this.parkingId = parkingId;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}