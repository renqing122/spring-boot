package com.heartsuit.showcase.roa;

import com.heartsuit.showcase.domain.FixOrder;
import com.heartsuit.showcase.domain.Repairman;
import com.heartsuit.showcase.service.RepairmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repairman")
public class RepairmanController {
    private RepairmanService repairmanService;
    @Autowired
    public RepairmanController(RepairmanService repairmanService){
        this.repairmanService = repairmanService;
    }

    /**
     * 登录
     * @param repairman
     * @return “0”成功 “1”账号或密码错误 “2”账号不存在
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody Repairman repairman) throws Exception {
        return repairmanService.login(repairman);
    }

    @RequestMapping(value = "/getId", method = RequestMethod.POST)
    @ResponseBody
    public String getRepairmanByEmailAndPassWord(@RequestBody Repairman repairman){
        return repairmanService.getId(repairman).getRepairmanId();
    }

    @RequestMapping(value = "/queryAllFixOrder", method = RequestMethod.POST)
    @ResponseBody
    public List<FixOrder> queryAllFixOrder(@RequestBody FixOrder fixOrder){
        return repairmanService.queryAllFixOrder(fixOrder);
    }

    @RequestMapping(value = "/queryFixOrderByOrderStatus", method = RequestMethod.POST)
    @ResponseBody
    public List<FixOrder> queryFixOrderByOrderStatus(@RequestBody FixOrder fixOrder){
        return repairmanService.queryFixOrderByOrderStatus(fixOrder);
    }

    @RequestMapping(value = "/findFixOrderByFixOrderId", method = RequestMethod.POST)
    @ResponseBody
    public FixOrder findFixOrderByFixOrderId(@RequestBody FixOrder fixOrder){
        return repairmanService.findFixOrderByFixOrderId(fixOrder);
    }

    @RequestMapping(value = "/updateOrderStatusByRepairmanId", method = RequestMethod.POST)
    @ResponseBody
    public void updateOrderStatusByRepairmanId(@RequestBody FixOrder fixOrder){
        repairmanService.updateOrderStatusByRepairmanId(fixOrder);
    }
}
