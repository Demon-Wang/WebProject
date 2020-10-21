package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 用户表(User)实体类
 *
 * @author makejava
 * @since 2020-09-26 22:34:00
 */
@Data
@Entity
@Table(name = "user")
public class User{
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 姓名
     */
    @NotNull(message = "用户账号不能为空")
    private String username;
    /**
     * 电话
     */
    @NotNull(message = "手机号码不能为空")
    private String phone;
    /**
     * 电子邮件地址
     */
    @NotNull(message = "用户邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;
    /**
     * 自我介绍
     */
    private String aboutme;
    /**
     * 经过md5加密的密码
     */
    @NotNull(message = "用户密码不能为空")
    @Size(min = 6, max = 11, message = "密码长度必须是6-16个字符")
    private String password;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 类型 1普通用户 2管理员
     */
    private Integer type;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否启用 1启用 0停用
     */
    private Integer enable;
}