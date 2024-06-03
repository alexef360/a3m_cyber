package com.dci.a3m.service;

import com.dci.a3m.entity.Like;

import java.util.List;

public interface LikeService {
    List<Like> findAll();
    Like findById(Long id);
    void save(Like like);
    void update(Like like);
    void deleteById(Long id);
}