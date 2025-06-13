package org.example.model;

import java.time.LocalDateTime;

public class ParkingPass extends Product {

    private String userId;        // 소유 유저 ID
    private String parkingId;     // 대상 주차장 ID
    private String spaceId;       // 예약된 공간 ID
    private LocalDateTime startTime;  // 예약 시작 시각
    private LocalDateTime endTime;    // 예약 종료 시각

    public ParkingPass(String id, String userId, String parkingId, String spaceId, LocalDateTime startTime,LocalDateTime endTime) {
        super(id);
        this.userId = userId;
        this.parkingId = parkingId;
        this.spaceId = spaceId;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}