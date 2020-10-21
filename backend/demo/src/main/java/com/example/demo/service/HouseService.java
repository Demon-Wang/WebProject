package com.example.demo.service;

import com.example.demo.entity.House;

import java.util.List;

public interface HouseService {
    void save(House house);
    void delete(Integer id);
    void update(House house);
    House findHouseById(Integer id);
    List<House> findHousesByNameLike(String username);
    List<House> findHousesByType(Integer type);
    List<House> findHousesByUserId(Integer userId);
    List<House> getHouses();
}