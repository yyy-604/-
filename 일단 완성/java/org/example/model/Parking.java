package org.example.model;

public class Parking extends Product {
    private String name;
    private String ownerId;
    private String address;

    private boolean isPublic;
    private boolean isPrivate;
    private boolean isGeneral;
    private boolean isOnSite;
    private boolean isMobile;
    private boolean isFree;
    private boolean isPaid;
    private int spaceCount;

    public Parking(String id, String ownerId, String name, String address,
                   boolean isPublic, boolean isPrivate, boolean isGeneral,
                   boolean isOnSite, boolean isMobile, boolean isFree, boolean isPaid,
                   int spaceCount) {
        super(id);
        this.ownerId=ownerId;
        this.name = name;
        this.address = address;
        this.isPublic = isPublic;
        this.isPrivate = isPrivate;
        this.isGeneral = isGeneral;
        this.isOnSite = isOnSite;
        this.isMobile = isMobile;
        this.isFree = isFree;
        this.isPaid = isPaid;
        this.spaceCount = spaceCount;
    }

    // 소유자 아이디 getter/setter
    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    // 이름
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // 주소
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    // 유형 및 기타 속성
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

    public int getSpaceCount() { return spaceCount; }
    public void setSpaceCount(int spaceCount) { this.spaceCount = spaceCount; }
}