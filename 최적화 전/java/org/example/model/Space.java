package org.example.model;

public class Space extends Product {
    private String spaceType; // 예: "일반", "장애인", "전기차", "가족배려", "대형", "이륜" 등
    private String parkId;    // 소속 주차장 ID
    private String userId;    // 사용 중인 유저의 ID (null 또는 빈 문자열이면 비어 있음)

    public Space(String id, String spaceType, String parkId) {
        super(id);
        this.spaceType = spaceType;
        this.parkId = parkId;
        this.userId = null; // 기본값: 비어있음
    }

    // 만약 빈 생성자가 필요하다면 추가
    public Space() {
        super();
        this.spaceType = "";
        this.parkId = "";
        this.userId = null;
    }

    public String getSpaceType() { return spaceType; }
    public void setSpaceType(String spaceType) { this.spaceType = spaceType; }

    public String getParkId() { return parkId; }
    public void setParkId(String parkId) { this.parkId = parkId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    // 사용 가능 여부: userId가 null 또는 빈 문자열일 때
    public boolean isAvailable() {
        return userId == null || userId.isEmpty();
    }

    // 자리 비우기 (퇴실 등)
    public void clear() {
        this.userId = null;
    }
}