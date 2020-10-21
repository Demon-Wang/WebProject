package com.example.demo.controller;

import com.example.demo.annotation.AdminLoginToken;
import com.example.demo.annotation.PassToken;
import com.example.demo.annotation.UserLoginToken;
import com.example.demo.service.TokenService;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.APIException;
import com.example.demo.utils.ResultCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 用户表(User)表控制层
 *
 * @author makejava
 * @since 2020-09-26 22:34:01
 */
@RestController
@RequestMapping("user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;
    @Resource
    private TokenService tokenService;

    @PostMapping("/addUser")
    public String addUser(@RequestBody @Valid User user){
        if(userService.findUserByUsername(user.getUsername())!=null)
            throw new APIException("用户已经存在");
        userService.save(user);
        return "用户添加成功";
    }

    @UserLoginToken
    @PostMapping("/updateUser")
    public String updateUser(@RequestBody @Valid User user){
        userService.update(user);
        return "删除用户成功";
    }

    @UserLoginToken
    @PostMapping("/delUserById/{id}")
    public String delUser(@PathVariable(name = "id" )Integer id){
        userService.delete(id);
        return "删除用户成功";
    }

    @PassToken
    @PostMapping(value="/login")
    public Object login( @RequestBody User user){
        String username = user.getUsername();
        System.out.println(username);
        String password = user.getPassword();
        JSONObject jsonObject=new JSONObject();
        User userForBase=userService.findUserByUsername(username);
        if(userForBase==null){
            throw new APIException(ResultCode.LOGIN_FAILED,"用户名不存在");
        }else if(!userForBase.getPassword().equals(password)){
            throw new APIException(ResultCode.LOGIN_FAILED,"登录失败,密码错误");
            }
        String token = tokenService.getToken(userForBase);
        jsonObject.put("token", token);
        jsonObject.put("user", userForBase);
        return jsonObject;
    }

    @GetMapping("/getUserById/{id}")
    public User getUserById(@PathVariable(name = "id" ) Integer id) {
        if(id == null)
            throw new APIException("参数有误");
        User tmp=userService.findUserById(id);
        if(tmp==null) throw new APIException("用户不存在");
        return tmp;
    }

    @GetMapping("/getUserByName/{name}")
    public User getUserByName(@PathVariable(name = "name") String username) {
        if(username==null) throw new APIException("参数有误");
        User tmp=userService.findUserByUsername(username);
        if(tmp==null) throw new APIException("用户不存在");
        return tmp;
    }

    @GetMapping("/getUsers")
    public List<User> getUsers() {
        return userService.findUsersByType(1);
    }

    @AdminLoginToken
    @GetMapping("/getAdmins")
    public List<User> getAdmins() {
        return userService.findUsersByType(2);
    }

}