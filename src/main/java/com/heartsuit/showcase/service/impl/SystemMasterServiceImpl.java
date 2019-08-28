package com.heartsuit.showcase.service.impl;

import com.heartsuit.showcase.dao.OperatorDao;
import com.heartsuit.showcase.dao.SystemMasterDao;
import com.heartsuit.showcase.domain.Operator;
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
        return (systemMasterDao.findOperatorByEmailAndPassWord(systemMaster) > 0) ? "0" : "1";
    }


}
