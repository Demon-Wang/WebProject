package com.example.demo.dao;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User,Integer> {
    User findUserById(Integer id);
    User findUserByUsername(String username);
    List<User> findUsersByType(Integer type);
    List<User> findAll();
}