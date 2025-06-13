package org.example.service;

import org.example.model.Space;
import org.example.repository.DB;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;


public class SpaceManager extends AbstractManager<Space> {
    public SpaceManager(DB db) {
        super(db);
    }

    @Override
    public void add(Space space) {
        db.addSpace(space);
    }

    @Override
    public void update(Space space) {
        db.addSpace(space); // 보통 update와 add는 분리되어야 하는데, 기존 구조에 맞췄음
    }

    @Override
    public void remove(String id) {
        db.removeSpace(id);
    }

    @Override
    public Space findById(String id) {
        return db.getSpace(id);
    }

    @Override
    public List<Space> getAll() {
        return new ArrayList<>(db.getAllSpaces());
    }

    /**
     * 주차장별 공간(장소) 객체를 한번에 등록
     * @param parkId - 주차장 ID
     * @param spaceTypeCountMap - {공간유형: 개수} (ex: "장애인":2, "일반":3)
     */
    public void registerSpacesForParking(String parkId, Map<String, Integer> spaceTypeCountMap) {
        for (Map.Entry<String, Integer> entry : spaceTypeCountMap.entrySet()) {
            String type = entry.getKey();
            int count = entry.getValue();
            for (int i = 0; i < count; i++) {
                // 각 공간마다 고유 ID를 생성
                String spaceId = UUID.randomUUID().toString();
                Space space = new Space(spaceId, type, parkId);
                this.add(space);
            }
        }
    }

    public List<Space> findByParkingId(String parkId) {
        List<Space> result = new ArrayList<>();
        for (Space s : getAll()) {
            if (s.getParkId().equals(parkId)) {
                result.add(s);
            }
        }
        return result;
    }

    public Map<String, Boolean> hasSpaceTypeByPark(String parkId) {
        List<Space> spaces = findByParkingId(parkId);
        Map<String, Boolean> result = new HashMap<>();
        for (Space s : spaces) {
            result.put(s.getSpaceType(), true); // 한 번이라도 등장하면 true
        }
        return result;
    }

    public void reserveSpaceForUser(Space space, String userId) {
        space.setUserId(userId);
        update(space); // 내부 DB 반영
    }

}