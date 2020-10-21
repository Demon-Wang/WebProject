package com.example.demo.service.impl;

import com.example.demo.dao.DemandMsgDao;
import com.example.demo.entity.DemandMsg;
import com.example.demo.service.DemandMsgService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DemandMsgServiceImpl implements DemandMsgService {

    private final DemandMsgDao demandMsgDao;

    public DemandMsgServiceImpl(DemandMsgDao demandMsgDao) {
        this.demandMsgDao = demandMsgDao;
    }

    @Override
    public void save(DemandMsg msg) {
        demandMsgDao.save(msg);
    }

    @Override
    public void delete(Integer id) {
        demandMsgDao.deleteById(id);
    }

    @Override
    public void update(DemandMsg msg) {
        demandMsgDao.save(msg);
    }

    @Override
    public DemandMsg findMsgByMsgId(Integer id) {
        return demandMsgDao.findDemandMsgById(id);
    }


    @Override
    public List<DemandMsg> findMsgsByTextLike(String text) {
        return demandMsgDao.findDemandMsgsByText(text);
    }

    @Override
    public List<DemandMsg> findMsgsByType(Integer type) {
        return demandMsgDao.findDemandMsgsByType(type);
    }

    @Override
    public List<DemandMsg> findMsgsByUserId(Integer userId) {

        return demandMsgDao.findDemandMsgsByUserId(userId);
    }
}
