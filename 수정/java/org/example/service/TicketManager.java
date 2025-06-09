package org.example.service;

import org.example.model.Ticket;
import org.example.repository.DB;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketManager extends AbstractManager<Ticket> {
    public TicketManager(DB db) {
        super(db);
    }

    @Override
    public void add(Ticket ticket) {
        db.addTicket(ticket);
    }

    @Override
    public void update(Ticket ticket) {
        db.updateTicket(ticket); // 실제 update
    }

    @Override
    public void remove(String id) {
        db.removeTicket(id);
    }

    @Override
    public Ticket findById(String id) {
        return db.getTicket(id);
    }

    @Override
    public List<Ticket> getAll() {
        return new ArrayList<>(db.getAllTickets());
    }

    /**
     * 주차장ID로 해당 주차장 티켓 조회
     */
    public Ticket findByParkingId(String parkId) {
        for (Ticket t : getAll()) {
            if (t.getParkId().equals(parkId)) {
                return t;
            }
        }
        return null;
    }

    /**
     * 주차장 신규 등록 시 티켓 등록 (가격, 운영시간 등 포함)
     */
    public void registerTicketForParking(
            String parkId,
            String openTime,
            String closeTime,
            int price,
            int weekendPrice,
            int extraFee
    ) {
        String ticketId = UUID.randomUUID().toString();
        Ticket ticket = new Ticket(
                ticketId,
                parkId,
                openTime,
                closeTime,
                price,
                weekendPrice,
                extraFee
        );
        this.add(ticket);
    }

    /**
     * 티켓 정보 수정 (티켓ID 기준)
     */
    public void updateTicket(
            String ticketId,
            String parkId,
            int price,
            int extraFee
    ) {
        Ticket ticket = findById(ticketId);
        if (ticket != null) {
            ticket.setPrice(price);
            ticket.setExtraPrice(extraFee);
            update(ticket);
        }
    }
}