package com.example.demo.service.impl;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2020-09-26 22:34:01
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private final UserDao userDao;
    final
    TokenServiceImpl tokenService;

    public UserServiceImpl(UserDao userDao, TokenServiceImpl tokenService) {
        this.userDao = userDao;
        this.tokenService = tokenService;
    }
    @Override
    public User findUserById(Integer id) {
        return userDao.findUserById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public void delete(Integer id) {
        userDao.deleteById(id);
    }

    @Override
    public void update(User user) {
        userDao.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userDao.findAll();
    }

    @Override
    public List<User> findUsersByType(Integer type) {
        return userDao.findUsersByType(type);
    }
}