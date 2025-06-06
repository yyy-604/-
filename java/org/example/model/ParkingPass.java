package org.example.model;

import java.util.UUID;
import java.time.Instant;

public class ParkingPass extends Product {
    private UUID userId;       // 소유 유저 ID
    private UUID parkingId;    // 대상 주차장 ID
    private UUID spaceId;
    private UUID ticketId;     // 연관 티켓 ID
    private Instant timestamp; // 남은 시간 등

    public ParkingPass(
        UUID userId,
        UUID parkingId,
        UUID spaceId,
        UUID ticketId,
        Instant timestamp) {
        super();

        this.userId = userId;
        this.parkingId = parkingId;
        this.spaceId = spaceId;
        this.ticketId = ticketId;
        this.timestamp = timestamp;
    }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public UUID getParkingId() { return parkingId; }
    public void setParkingId(UUID parkingId) { this.parkingId = parkingId; }

    public UUID getSpaceId() { return spaceId; }
    public void setSpaceId(UUID spaceId) { this.spaceId = spaceId; }

    public UUID getTicketId() { return ticketId; }
    public void setTicketId(UUID ticketId) { this.ticketId = ticketId; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}