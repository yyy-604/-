package org.example.service.parking;

import org.example.service.AbstractManager;
import org.example.model.ParkingPass;

import java.util.ArrayList;

public class ParkingPassManager extends AbstractManager<ParkingPass> {
    public ParkingPassManager() {
        super();
    }

    @Override
    public void add(ParkingPass parkingPass) {
        products.put(parkingPass.getId(), parkingPass);
    }

    @Override
    public void update(ParkingPass parkingPass) {
        ParkingPass target = products.get(parkingPass.getId());

        target.setUserId(parkingPass.getUserId());
        target.setParkingId(parkingPass.getParkingId());
        target.setSpaceId(parkingPass.getSpaceId());
        target.setTicketId(parkingPass.getTicketId());
        target.setTimestamp(parkingPass.getTimestamp());
    }

    @Override
    public void remove(ParkingPass parkingPass) {
        products.remove(parkingPass.getId());
    }

    @Override
    public ArrayList<ParkingPass> search(ParkingPass filter) {
        ArrayList<ParkingPass> result = new ArrayList<ParkingPass>();

        products.forEach((_, value) -> {
            if (value.getId() == filter.getId()) {
                result.add(value);
            }
        });

        return result;
    }
}