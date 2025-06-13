package org.example.model;

public class FilterOptions {
    // 검색어 (이름 또는 주소)
    private String query;

    // 결제 방식 및 주차장 성격
    private boolean isPublic;        // 공용 주차장
    private boolean isPrivate;       // 사설 주차장
    private boolean isGeneral;       // 일반 주차장
    private boolean isOnSite;        // 현장 결제 가능
    private boolean isMobile;        // 모바일 결제 가능
    private boolean isFree;          // 무료 주차장
    private boolean isPaid;          // 유료 주차장

    // 기타 조건
    private boolean isImmediate;     // 즉시 주차 가능 여부
    private int price;               // 기준 요금

    // 추가 필터
    private boolean isDisabled;      // 장애인 전용
    private boolean isFamily;        // 가족 배려 구역
    private boolean isTwoWheeler;    // 2륜 차량 주차 가능
    private boolean isHousehold;     // 가정용 차량 대상
    private boolean isLarge;         // 대형 차량 주차 가능
    private boolean isEV;            // 전기차 구역 여부

    public FilterOptions() {
        this.query = "";
        this.isPublic = false;
        this.isPrivate = false;
        this.isGeneral = false;
        this.isOnSite = false;
        this.isMobile = false;
        this.isFree = false;
        this.isPaid = false;
        this.isImmediate = false;
        this.price = 0;
        this.isDisabled = false;
        this.isFamily = false;
        this.isTwoWheeler = false;
        this.isHousehold = false;
        this.isLarge = false;
        this.isEV = false;
    }

    // Getter / Setter
    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }

    public boolean isPublic() { return isPublic; }
    public void setPublic(boolean isPublic) { this.isPublic = isPublic; }

    public boolean isPrivate() { return isPrivate; }
    public void setPrivate(boolean isPrivate) { this.isPrivate = isPrivate; }

    public boolean isGeneral() { return isGeneral; }
    public void setGeneral(boolean isGeneral) { this.isGeneral = isGeneral; }

    public boolean isOnSite() { return isOnSite; }
    public void setOnSite(boolean isOnSite) { this.isOnSite = isOnSite; }

    public boolean isMobile() { return isMobile; }
    public void setMobile(boolean isMobile) { this.isMobile = isMobile; }

    public boolean isFree() { return isFree; }
    public void setFree(boolean isFree) { this.isFree = isFree; }

    public boolean isPaid() { return isPaid; }
    public void setPaid(boolean isPaid) { this.isPaid = isPaid; }

    public boolean isImmediate() { return isImmediate; }
    public void setImmediate(boolean isImmediate) { this.isImmediate = isImmediate; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public boolean isDisabled() { return isDisabled; }
    public void setDisabled(boolean isDisabled) { this.isDisabled = isDisabled; }

    public boolean isFamily() { return isFamily; }
    public void setFamily(boolean isFamily) { this.isFamily = isFamily; }

    public boolean isTwoWheeler() { return isTwoWheeler; }
    public void setTwoWheeler(boolean isTwoWheeler) { this.isTwoWheeler = isTwoWheeler; }

    public boolean isHousehold() { return isHousehold; }
    public void setHousehold(boolean isHousehold) { this.isHousehold = isHousehold; }

    public boolean isLarge() { return isLarge; }
    public void setLarge(boolean isLarge) { this.isLarge = isLarge; }

    public boolean isEV() { return isEV; }
    public void setEV(boolean isEV) { this.isEV = isEV; }
}