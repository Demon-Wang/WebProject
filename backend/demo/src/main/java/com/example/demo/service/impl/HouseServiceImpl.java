package com.example.demo.service.impl;

import com.example.demo.entity.House;
import com.example.demo.service.HouseService;
import org.springframework.stereotype.Service;
import com.example.demo.dao.HouseDao;

import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {
    private final HouseDao houseDao;

    public HouseServiceImpl(HouseDao houseDao) {
        this.houseDao = houseDao;
    }

    @Override
    public void save(House house){
        houseDao.save(house);
    }
    @Override
    public void update(House house){
        houseDao.save(house);
    }
    @Override
    public void delete(Integer id) {
        houseDao.deleteById(id);
    }

    @Override
    public House findHouseById(Integer id) {
        return houseDao.findHouseById(id);
    }

    @Override
    public List<House> findHousesByType(Integer type) {
        return houseDao.findHousesByType(type);
    }

    @Override
    public List<House> findHousesByUserId(Integer userId) {
        return houseDao.findHousesByUserId(userId);
    }

    @Override
    public List<House> getHouses() {
        return houseDao.findAll();
    }

    @Override
    public List<House> findHousesByNameLike(String name){
        return houseDao.findHousesByNameLike(name);
    }
}
