package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "demandmsg")
public class DemandMsg{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 房屋名字
     */
    private String text;
    /**
     * 1求购2求租
     */
    private Integer type;


    private String phone;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 1上架2下架
     */
    private Integer state;


    private Integer userId;
}