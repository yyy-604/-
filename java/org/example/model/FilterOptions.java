package org.example.model;

public class FilterOptions {
    public String query;
    public boolean isPublic;
    public boolean isPrivate;
    public boolean isGeneral;
    public boolean isOnSite;
    public boolean isMobile;
    public boolean isFree;
    public boolean isPaid;
    public boolean isImmediate;
    public boolean isNearby;
    public boolean isRoute;
    public boolean isWeak;
    public int price;
    public int extraPrice;

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
        this.isNearby = false;
        this.isRoute = false;
        this.isWeak = false;
        this.price = 0;
        this.extraPrice = 0;
    }

    // Getter/Setter
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

    public boolean isNearby() { return isNearby; }
    public void setNearby(boolean isNearby) { this.isNearby = isNearby; }

    public boolean isRoute() { return isRoute; }
    public void setRoute(boolean isRoute) { this.isRoute = isRoute; }

    public boolean isWeak() { return isWeak; }
    public void setWeak(boolean isWeak) { this.isWeak = isWeak; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public int getExtraPrice() { return extraPrice; }
    public void setExtraPrice(int extraPrice) { this.extraPrice = extraPrice; }
}