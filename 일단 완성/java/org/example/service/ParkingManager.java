package org.example.service;

import org.example.model.Parking;
import org.example.repository.DB;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ParkingManager extends AbstractManager<Parking> {
    public ParkingManager(DB db) {
        super(db);
    }

    @Override
    public void add(Parking parking) {
        db.addParking(parking);
    }

    @Override
    public void update(Parking parking) {
        db.addParking(parking); // update/add 분리 필요시 로직 조정
    }

    @Override
    public void remove(String id) {
        db.removeParking(id);
    }

    @Override
    public Parking findById(String id) {
        return db.getParking(id);
    }

    @Override
    public List<Parking> getAll() {
        return new ArrayList<>(db.getAllParkings());
    }

    // 유저별 주차장 리스트 반환
    public List<Parking> getMyParkings(String userId) {
        List<Parking> result = new ArrayList<>();
        for (Parking p : db.getAllParkings()) {
            if (p.getOwnerId().equals(userId)) {   // Parking에 ownerId(소유자 아이디)가 있어야 함!
                result.add(p);
            }
        }
        return result;
    }

    /**
     * 주차장 등록 - 등록 후 생성된 주차장 ID 반환
     */
    public String registerParking(
            String userId,
            String name,
            String address,
            boolean isPublic,
            boolean isPrivate,
            boolean isGeneral,
            boolean isOnSite,
            boolean isMobile,
            boolean isFree,
            boolean isPaid,
            int spaceCount
    ) {
        String id = UUID.randomUUID().toString();
        Parking parking = new Parking(
                id, userId, name, address,
                isPublic, isPrivate, isGeneral, isOnSite, isMobile, isFree, isPaid,
                spaceCount
        );
        add(parking);
        return id;
    }

    /**
     * 주차장 정보 수정 (ID로 기존 객체 수정)
     */
    public void updateParking(
            String id,
            String name,
            String address,
            boolean isPublic,
            boolean isPrivate,
            boolean isGeneral,
            boolean isOnSite,
            boolean isMobile,
            boolean isFree,
            boolean isPaid,
            int spaceCount
    ) {
        Parking parking = findById(id);
        if (parking != null) {
            parking.setName(name);
            parking.setAddress(address);
            parking.setPublic(isPublic);
            parking.setPrivate(isPrivate);
            parking.setGeneral(isGeneral);
            parking.setOnSite(isOnSite);
            parking.setMobile(isMobile);
            parking.setFree(isFree);
            parking.setPaid(isPaid);
            parking.setSpaceCount(spaceCount);
            update(parking);
        }
    }
}