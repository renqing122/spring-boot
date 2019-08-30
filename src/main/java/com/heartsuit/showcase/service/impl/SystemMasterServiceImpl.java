package com.heartsuit.showcase.service.impl;

import com.heartsuit.showcase.dao.SystemMasterDao;
import com.heartsuit.showcase.domain.SystemMaster;
import com.heartsuit.showcase.service.SystemMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SystemMasterServiceImpl implements SystemMasterService {
    private SystemMasterDao systemMasterDao;
    @Autowired
    public SystemMasterServiceImpl(SystemMasterDao systemMasterDao){
        this.systemMasterDao = systemMasterDao;
    }
    @Override
    public String login(SystemMaster systemMaster) {
        if(systemMaster.getAccount().equals("admin")&&systemMaster.getPassword().equals("123456")){
            return "0";
        }
        else{
            return "1";
        }
    }
}
