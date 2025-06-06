package org.example.service.parking;

import org.example.service.AbstractManager;
import org.example.model.Ticket;

import java.util.ArrayList;

public class TicketManager extends AbstractManager<Ticket> {
    public TicketManager() {
        super();
    }

    @Override
    public void add(Ticket ticket) {
        products.put(ticket.getId(), ticket);
    }

    @Override
    public void update(Ticket ticket) {
        Ticket target = products.get(ticket.getId());

        target.setTime(ticket.getTime());
        target.setWeak(ticket.isWeak());
        target.setPrice(ticket.getPrice());
        target.setExtraPrice(ticket.getExtraPrice());
    }

    @Override
    public void remove(Ticket ticket) {
        products.remove(ticket.getId());
    }

    @Override
    public ArrayList<Ticket> search(Ticket filter) {
        ArrayList<Ticket> result = new ArrayList<Ticket>();

        products.forEach((_, value) -> {
            if (value.getId() == filter.getId()) {
                result.add(value);
            }
        });

        return result;
    }
}