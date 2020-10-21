package com.example.demo.service;

import com.example.demo.entity.DemandMsg;

import java.util.List;

public interface DemandMsgService {
    void save(DemandMsg house);
    void delete(Integer id);
    void update(DemandMsg house);
    DemandMsg findMsgByMsgId(Integer id);
    List<DemandMsg> findMsgsByTextLike(String text);
    List<DemandMsg> findMsgsByType(Integer type);
    List<DemandMsg> findMsgsByUserId(Integer userId);
}
