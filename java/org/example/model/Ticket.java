package org.example.model;

import java.util.UUID;

public class Ticket extends Product {
    private UUID parkingId;
    private int time;         // 시간(이용 시간 등)
    private boolean weak;     // 약정 주차 여부(예: 정기권)
    private int price;        // 가격
    private int extraPrice;   // 추가 요금

    public Ticket(
        UUID parkingId,
        int time,
        boolean weak,
        int price,
        int extraPrice) {
        super();

        this.parkingId = parkingId;
        this.time = time;
        this.weak = weak;
        this.price = price;
        this.extraPrice = extraPrice;
    }

    public UUID getParkingId() { return parkingId; }
    public void setParkingId(UUID parkingId) { this.parkingId = parkingId; }

    public int getTime() { return time; }
    public void setTime(int time) { this.time = time; }

    public boolean isWeak() { return weak; }
    public void setWeak(boolean weak) { this.weak = weak; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public int getExtraPrice() { return extraPrice; }
    public void setExtraPrice(int extraPrice) { this.extraPrice = extraPrice; }
}