package org.example.service.parking;

import org.example.service.AbstractManager;
import org.example.model.Space;

import java.util.ArrayList;

public class SpaceManager extends AbstractManager<Space> {
    public SpaceManager() {
        super();
    }

    @Override
    public void add(Space space) {
        products.put(space.getId(), space);
    }

    @Override
    public void update(Space space) {
        Space target = products.get(space.getId());

        target.setAvailable(space.isAvailable());
        target.setSpaceType(space.getSpaceType());
    }

    @Override
    public void remove(Space space) {
        products.remove(space.getId());
    }

    @Override
    public ArrayList<Space> search(Space filter) {
        ArrayList<Space> result = new ArrayList<Space>();

        products.forEach((_, value) -> {
            if (value.getId() == filter.getId()) {
                result.add(value);
            }
        });

        return result;
    }
}