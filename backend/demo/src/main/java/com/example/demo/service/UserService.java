package com.example.demo.service;

import com.example.demo.entity.House;
import com.example.demo.entity.User;

import java.util.List;

/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2020-09-26 22:34:01
 */
public interface UserService {
    User findUserById(Integer id);
    User findUserByUsername(String username);
    void save(User user);
    void delete(Integer id);
    void update(User user);
    List<User> getUsers();
    List<User> findUsersByType(Integer type);
}