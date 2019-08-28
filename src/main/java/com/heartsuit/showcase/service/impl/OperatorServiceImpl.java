package com.heartsuit.showcase.service.impl;

import com.heartsuit.showcase.dao.OperatorDao;
import com.heartsuit.showcase.domain.Operator;
import com.heartsuit.showcase.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OperatorServiceImpl implements OperatorService {
    private OperatorDao operatorDao;
    @Autowired
    public OperatorServiceImpl(OperatorDao operatorDao){
        this.operatorDao = operatorDao;
    }
    @Override
    public String insert(Operator operator) {
        if(operatorDao.findRepeatEmail(operator).equals("0")){
            operatorDao.insert(operator);
            return "0";
        }
        else {
            return "1";
        }
    }

    @Override
    public List<Operator> findAll() {
        return operatorDao.findAll();
    }

    @Override
    public String login(Operator operator) {
        long operatorByEmail = operatorDao.findOperatorByEmail(operator);
        long operatorByEmailAndPassword = operatorDao.findOperatorByEmailAndPassWord(operator);
        return operatorByEmail > 0 ? (operatorByEmailAndPassword > 0 ? "0" : "1") : "2";
    }

    @Override
    public void updateOperatorActivationStatus(Operator operator) {
        operatorDao.updateActivationStatus(operator);
    }

    @Override
    public List<Operator> findAllOperatorByActivationStatus0() {
        return operatorDao.findAllOperatorByActivationStatus0();
    }


}
