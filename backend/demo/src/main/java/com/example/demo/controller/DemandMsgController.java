package com.example.demo.controller;

import com.example.demo.annotation.AdminLoginToken;
import com.example.demo.annotation.UserLoginToken;
import com.example.demo.entity.DemandMsg;
import com.example.demo.service.DemandMsgService;
import com.example.demo.utils.APIException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("demandMsg")
public class DemandMsgController {
    /**
     * 服务对象
     */
    @Resource
    private DemandMsgService demandMsgService;

    @UserLoginToken
    @PostMapping("/addMsg")
    public String addDemandMsg(@RequestBody DemandMsg demandMsg){
        demandMsgService.save(demandMsg);
        return "告示栏信息添加成功";
    }

    @UserLoginToken
    @PostMapping("/delMsgById/{id}")
    public String delDemandMsgById(@PathVariable(name = "id") Integer id){
        demandMsgService.delete(id);
        return "告示栏信息删除成功";
    }

    @UserLoginToken
    @PostMapping("/updateMsg")
    public String updateDemandMsg(@RequestBody DemandMsg demandMsg){
        demandMsgService.update(demandMsg);
        return "告示栏信息更新成功";
    }

    @GetMapping("/getMsgById/{id}")
    public DemandMsg getDemandMsgById(@PathVariable(name = "id") Integer id) {
        if(id==null) throw new APIException("参数有误");
        return demandMsgService.findMsgByMsgId(id);
    }

    @GetMapping("/getMsgsByUserId/{id}")
    public List<DemandMsg> getDemandMsgsByUserId(@PathVariable(name ="id") Integer id) {
        if(id==null) throw new APIException("参数有误");
        return demandMsgService.findMsgsByUserId(id);
    }

    @GetMapping("/getAskRentMsgs")
    public List<DemandMsg> getAskRentMsgs() {
        return demandMsgService.findMsgsByType(2);
    }

    @GetMapping("/getAskBuyMsgs")
    public List<DemandMsg> getAskBuyMsgs() {
        return demandMsgService.findMsgsByType(1);
    }

    @GetMapping("/getMsgByTextLike/{text}")
    public List<DemandMsg> getMsgByTextLike(@PathVariable(name = "text") String text) {
        if(text==null) throw new APIException("参数有误");
        return demandMsgService.findMsgsByTextLike("%"+text+"%");
    }
}
