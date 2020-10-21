package com.example.demo.dao;

import com.example.demo.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface HouseDao extends JpaRepository<House,Integer> {
    House findHouseById(Integer id);
    List<House> findHousesByNameLike(String name);
    List<House> findHousesByType(Integer type);
    List<House> findHousesByUserId(Integer userId);
    List<House> findAll();
}