package com.heartsuit.showcase.roa;

import com.heartsuit.showcase.domain.Operator;
import com.heartsuit.showcase.domain.SystemMaster;
import com.heartsuit.showcase.service.OperatorService;
import com.heartsuit.showcase.service.SystemMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/systemMaster")
public class SystemMasterController {
    private SystemMasterService systemMasterService;
    private OperatorService operatorService;
    @Autowired
    public SystemMasterController(OperatorService operatorService, SystemMasterService systemMasterService){
        this.operatorService = operatorService;
        this.systemMasterService = systemMasterService;
    }

    /**
     * 管理员登录
     * @param systemMaster
     * @return “0”成功 “1”失败
     */
    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody SystemMaster systemMaster){
        return systemMasterService.login(systemMaster);
    }

    @RequestMapping(value = "/queryAllOperatorByActivationStatus0", method = RequestMethod.GET)
    @ResponseBody
    public List<Operator> queryAllOperatorByActivationStatus0(){
        return operatorService.findAllOperatorByActivationStatus0();
    }

    @RequestMapping(value = "/updateOperatorActivationStatus", method = RequestMethod.POST)
    @ResponseBody
    public String updateOperatorActivationStatus(@RequestBody Operator operator){
        operatorService.updateOperatorActivationStatus(operator);
        return "true";
    }
}
