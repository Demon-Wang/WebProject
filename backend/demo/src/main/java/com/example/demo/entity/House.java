package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "house")
public class House{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 房屋名字
     */
    private String name;
    /**
     * 1出售2出租
     */
    private Integer type;
    /**
     * 1公产2私产
     */
    private Integer property;
    /**
     * 朝向:1东2西3南4北
     */
    private Integer towards;
    /**
     * 1有电梯0没有电梯
     */
    private Integer elevator;
    /**
     * 价格
     */
    private Integer price;
    /**
     * 图片地址
     */
    private String images;
    /**
     * 建筑面积
     */
    private Integer area;
    /**
     * 房屋配置 几室几厅几卫
     */
    private String framework;
    /**
     * 总楼层
     */
    private Integer totalFloor;
    /**
     * 所处楼层
     */
    private Integer currentFloor;
    /**
     * 0:清水1:简装2:精装
     */
    private Integer renovation;
    /**
     * 更多房屋描述
     */
    private String remarks;
    /**
     * 户型图地址
     */
    private String floorPlan;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 1上架2下架
     */
    private Integer state;
    /**
     * 租期(天)
     */
    private Integer leaseTerm;
    /**
     * 家具家电信息
     */
    private String furniture;
    /**
     * 0整租1合租
     */
    private Integer share;
    /**
     * 合租要求
     */
    private String requirements;
    /**
     * 用户ID
     */
    private Integer userId;
}