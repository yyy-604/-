package org.example.service;

import org.example.model.Favorite;
import org.example.repository.DB;

import java.util.ArrayList;
import java.util.List;

/**
 * 즐겨찾기(Favorite) 관리 매니저
 * - 즐겨찾기 추가/수정/삭제/조회 기능 제공
 * - 특정 유저의 즐겨찾기, 유저-상품(공간) 조합 기반 조회/삭제 기능 포함
 */
public class FavoriteManager extends AbstractManager<Favorite> {
    public FavoriteManager(DB db) {
        super(db);
    }

    @Override
    public void add(Favorite favorite) {
        db.addFavorite(favorite);
    }

    @Override
    public void update(Favorite favorite) {
        db.addFavorite(favorite);
    }

    @Override
    public void remove(String id) {
        db.removeFavorite(id);
    }

    @Override
    public Favorite findById(String id) {
        return db.getFavorite(id);
    }

    @Override
    public List<Favorite> getAll() {
        return new ArrayList<>(db.getAllFavorites());
    }

    /** 특정 유저가 특정 상품(공간/주차장 등)을 즐겨찾기했는지 확인 */
    public Favorite getFavoriteByUserAndProduct(String userId, String productId) {
        for (Favorite f : db.getAllFavorites()) {
            if (f.getUserId().equals(userId) && f.getProductId().equals(productId)) {
                return f;
            }
        }
        return null;
    }

    /** 특정 유저의 모든 즐겨찾기 리스트 반환 */
    public List<Favorite> getFavoritesByUserId(String userId) {
        List<Favorite> result = new ArrayList<>();
        for (Favorite f : db.getAllFavorites()) {
            if (f.getUserId().equals(userId)) {
                result.add(f);
            }
        }
        return result;
    }

    /** userId, productId 조합으로 즐겨찾기 삭제 */
    public void removeByUserAndProduct(String userId, String productId) {
        Favorite toRemove = null;
        for (Favorite f : db.getAllFavorites()) {
            if (f.getUserId().equals(userId) && f.getProductId().equals(productId)) {
                toRemove = f;
                break;
            }
        }
        if (toRemove != null) {
            db.removeFavorite(toRemove.getId());
        }
    }
}