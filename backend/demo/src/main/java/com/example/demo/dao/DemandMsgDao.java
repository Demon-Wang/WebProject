package com.example.demo.dao;

import com.example.demo.entity.DemandMsg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandMsgDao extends JpaRepository<DemandMsg,Integer> {
    DemandMsg findDemandMsgById(Integer id);
    List<DemandMsg> findDemandMsgsByText(String text);
    List<DemandMsg> findDemandMsgsByType(Integer type);
    List<DemandMsg> findDemandMsgsByUserId(Integer userId);
}
