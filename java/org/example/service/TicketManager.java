package org.example.service;

import org.example.model.Ticket;
import org.example.repository.DB;

import java.util.ArrayList;
import java.util.List;

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
        db.addTicket(ticket);
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
}