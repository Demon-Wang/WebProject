package com.example.demo.controller;

import com.example.demo.annotation.AdminLoginToken;
import com.example.demo.annotation.UserLoginToken;
import com.example.demo.entity.House;
import com.example.demo.entity.User;
import com.example.demo.service.HouseService;
import com.example.demo.utils.APIException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("house")
public class HouseController {
    /**
     * 服务对象
     */
    @Resource
    private HouseService houseService;

    @UserLoginToken
    @PostMapping("/addHouse")
    public String addHouse(@RequestBody House house){
        houseService.save(house);
        return "房屋信息添加成功";
    }

    @UserLoginToken
    @PostMapping("/delHouseByHouseId/{id}")
    public String delHouseByHouseId(@PathVariable(name = "id") Integer id){
        houseService.delete(id);
        return "房屋信息删除成功";
    }

    @UserLoginToken
    @PostMapping("/updateHouse")
    public String updateHouse(@RequestBody House house){
        houseService.update(house);
        return "房屋信息更新成功";
    }

    @GetMapping("/getHouseByHouseId/{id}")
    public House getHouseByHouseId(@PathVariable(name = "id") Integer id) {
        if(id==null) throw new APIException("参数有误");
        House tmp=houseService.findHouseById(id);
        if(tmp==null) throw new APIException("用户不存在");
        return tmp;
    }

    @GetMapping("/getHousesByUserId/{id}")
    public List<House> getHousesByUserId(@PathVariable(name = "id") Integer id) {
        if(id==null) throw new APIException("参数有误");
        return houseService.findHousesByUserId(id);
    }

    @GetMapping("/getRentHouses")
    public List<House> getRentHouses() {
        return houseService.findHousesByType(2);
    }

    @GetMapping("/getSaleHouses")
    public List<House> getSaleHouses() {
        return houseService.findHousesByType(1);
    }

    @GetMapping("/getHouses")
    public List<House> getHouses() {
        return houseService.getHouses();
    }

    @GetMapping("/getHousesByNameLike/{name}")
    public List<House> getHousesByNameLike(@PathVariable(name = "name") String name) {
        if(name==null) throw new APIException("参数有误");
        List<House> tmp=houseService.findHousesByNameLike("%"+name+"%");
        if(tmp==null) throw new APIException("用户不存在");
        return tmp;
    }
}