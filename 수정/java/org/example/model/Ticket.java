package org.example.model;

public class Ticket extends Product {
    private String parkId;        // 소속 주차장ID
    private String openTime;      // 운영 시작시간 (예: "09")
    private String closeTime;     // 운영 종료시간 (예: "18")
    private int price;            // 평일 가격
    private int weekendPrice;     // 주말 가격 (없으면 0)
    private int extraPrice;       // 초과 가격


    // 풀 생성자
    public Ticket(String id, String parkId, String openTime, String closeTime,
                  int price, int weekendPrice, int extraPrice) {
        super(id);
        this.parkId = parkId;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.price = price;
        this.weekendPrice = weekendPrice;
        this.extraPrice = extraPrice;
    }

    // 실무에서는 필요에 따라 파라미터를 덜 받는 생성자도 제공 가능

    public String getParkId() { return parkId; }
    public void setParkId(String parkId) { this.parkId = parkId; }

    public String getOpenTime() { return openTime; }
    public void setOpenTime(String openTime) { this.openTime = openTime; }

    public String getCloseTime() { return closeTime; }
    public void setCloseTime(String closeTime) { this.closeTime = closeTime; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public int getWeekendPrice() { return weekendPrice; }
    public void setWeekendPrice(int weekendPrice) { this.weekendPrice = weekendPrice; }

    public int getExtraPrice() { return extraPrice; }
    public void setExtraPrice(int extraPrice) { this.extraPrice = extraPrice; }
}