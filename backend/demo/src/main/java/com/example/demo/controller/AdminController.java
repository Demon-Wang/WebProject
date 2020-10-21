package com.example.demo.controller;

import com.example.demo.annotation.*;
import com.example.demo.entity.DemandMsg;
import com.example.demo.entity.House;
import com.example.demo.entity.User;
import com.example.demo.service.*;
import com.example.demo.utils.APIException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Resource
    private UserService userService;
    @Resource
    private HouseService houseService;
    @Resource
    private DemandMsgService demandMsgService;

    @AdminLoginToken
    @PostMapping("/addUser")
    public String addUser(@RequestBody @Valid User user){
        if(userService.findUserByUsername(user.getUsername())!=null)
            throw new APIException("用户已经存在");
        userService.save(user);
        return "用户添加成功";
    }

    @AdminLoginToken
    @PostMapping("/delUserById/{id}")
    public String delUser(@PathVariable(name = "id" )Integer id){
        userService.delete(id);
        return "删除用户成功";
    }

    @AdminLoginToken
    @PostMapping("/updateUser")
    public String updateUser(@RequestBody @Valid User user){
        userService.update(user);
        return "删除用户成功";
    }

    @AdminLoginToken
    @GetMapping("/getUserById/{id}")
    public User getUserById(@PathVariable(name = "id" ) Integer id) {
        if(id == null)
            throw new APIException("参数有误");
        User tmp=userService.findUserById(id);
        if(tmp==null) throw new APIException("用户不存在");
        return tmp;
    }

    @AdminLoginToken
    @GetMapping("/getUserByName/{name}")
    public User getUserByName(@PathVariable(name = "name") String username) {
        if(username==null) throw new APIException("参数有误");
        User tmp=userService.findUserByUsername(username);
        if(tmp==null) throw new APIException("用户不存在");
        return tmp;
    }

    @AdminLoginToken
    @GetMapping("/getAdmins")
    public List<User> getAdmins() {
        return userService.findUsersByType(2);
    }

    @AdminLoginToken
    @PostMapping("/addHouse")
    public String addHouse(@RequestBody House house){
        houseService.save(house);
        return "房屋信息添加成功";
    }

    @AdminLoginToken
    @PostMapping("/delHouseByHouseId/{id}")
    public String delHouseByHouseId(@PathVariable(name = "id") Integer id){
        houseService.delete(id);
        return "房屋信息删除成功";
    }

    @AdminLoginToken
    @PostMapping("/updateHouse")
    public String updateHouse(@RequestBody House house){
        houseService.update(house);
        return "房屋信息更新成功";
    }

    @AdminLoginToken
    @PostMapping("/addMsg")
    public String addDemandMsg(@RequestBody DemandMsg demandMsg){
        demandMsgService.save(demandMsg);
        return "房屋信息添加成功";
    }

    @AdminLoginToken
    @PostMapping("/delMsgById/{id}")
    public String delDemandMsgById(@PathVariable(name = "id") Integer id){
        demandMsgService.delete(id);
        return "房屋信息删除成功";
    }

    @AdminLoginToken
    @PostMapping("/updateMsg")
    public String updateDemandMsg(@RequestBody DemandMsg demandMsg){
        demandMsgService.update(demandMsg);
        return "房屋信息更新成功";
    }

}