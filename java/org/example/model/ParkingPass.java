package org.example.model;

public class ParkingPass extends Product {
    private String userId;      // 소유 유저 ID
    private String parkingId;   // 대상 주차장 ID
    private String ticketId;    // 연관 티켓 ID
    private int time;           // 남은 시간 등

    public ParkingPass() {}

    public ParkingPass(String id, String userId, String parkingId, String ticketId, int time) {
        super(id);
        this.userId = userId;
        this.parkingId = parkingId;
        this.ticketId = ticketId;
        this.time = time;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getParkingId() { return parkingId; }
    public void setParkingId(String parkingId) { this.parkingId = parkingId; }

    public String getTicketId() { return ticketId; }
    public void setTicketId(String ticketId) { this.ticketId = ticketId; }

    public int getTime() { return time; }
    public void setTime(int time) { this.time = time; }
}