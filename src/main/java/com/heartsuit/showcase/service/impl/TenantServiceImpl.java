package com.heartsuit.showcase.service.impl;

import com.heartsuit.showcase.dao.ComplainOrderDao;
import com.heartsuit.showcase.dao.FixOrderDao;
import com.heartsuit.showcase.dao.TenantDao;
import com.heartsuit.showcase.domain.ComplainOrder;
import com.heartsuit.showcase.domain.FixOrder;
import com.heartsuit.showcase.domain.Tenant;
import com.heartsuit.showcase.service.IMailService;
import com.heartsuit.showcase.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2019/8/25 0025 00:03
 */
@Component
public class TenantServiceImpl implements TenantService{
    private TenantDao tenantDao;
    private FixOrderDao fixOrderDao;
    private ComplainOrderDao complainOrderDao;

    @Autowired
    public TenantServiceImpl(TenantDao tenantDao, FixOrderDao fixOrderDao, ComplainOrderDao complainOrderDao) {
        this.tenantDao = tenantDao;
        this.fixOrderDao = fixOrderDao;
        this.complainOrderDao = complainOrderDao;
    }

    @Override
    public String insert(Tenant tenant) throws Exception {
        if(tenantDao.findRepeatEmail(tenant).equals("0")){
            tenantDao.insert(tenant);
            return "0";
        }
        else {
            return "1";
        }
    }

    @Override
    public String checkCode(Tenant tenant){
        return tenantDao.checkCode(tenant);
    }
    @Override
    public List<Tenant> findAll() {
        return tenantDao.findAll();
    }

    @Override
    public void updateActivationStatus(Tenant tenant) {
        tenantDao.updateActivationStatus(tenant);
    }

    @Override
    public String checkEmail(Tenant tenant)
    {
        return tenantDao.findRepeatEmail(tenant);
    }

    @Override
    public Tenant createTenantByCode(String code){
        return tenantDao.createTenantByCode(code);
    }

    @Override
    public Tenant findEmailByCode(Tenant tenant){
        return  tenantDao.findEmailByCode(tenant);
    }

    @Override
    public String findTenantIdByEmail(Tenant tenant) {
        return tenantDao.findTenantIdByEmail(tenant);
    }

    @Override
    public void updateTenantByLevel(Tenant tenant) {
        tenantDao.updateTenantLevel(tenant);
    }

    @Override
    public void insertFixOrder(FixOrder fixOrder) {
        fixOrderDao.insert(fixOrder);
    }

    @Override
    public void insertComplainOrder(ComplainOrder complainOrder) {
        complainOrderDao.insert(complainOrder);
    }

    @Override
    public void updateTenantInformationByOperator(Tenant tenant) throws Exception {
        tenantDao.updateTenantInformation(tenant);
    }

    @Override
    public void updateTenantCommentByFixOrderId(FixOrder fixOrder) {
        fixOrderDao.updateTenantCommentByFixOrderId(fixOrder);
    }

    @Override
    public Tenant queryTenantByTenantId(Tenant tenant) {
        return tenantDao.getTenantByTenantId(tenant);
    }

    @Override
    public List<Tenant> queryTenantListByTenantId(Tenant tenant) {
        return tenantDao.getTenantListByTenantId(tenant);
    }

    @Override
    public String forgetPassword(Tenant tenant) {
        if(tenantDao.findTenantByEmail(tenant)>0){
            return "0";
        }
        else{
            return "1";
        }
    }

    @Override
    public void reSetPasswordByTenantId(Tenant tenant) throws Exception {
        tenantDao.reSetPasswordByTenantId(tenant);
    }

    @Override
    public Tenant createTenantByTenantId(String tenantId) {
        return tenantDao.createTenantByTenantId(tenantId);
    }

    @Override
    public List<ComplainOrder> findComplainOrderByTenantId(ComplainOrder complainOrder) {
        return complainOrderDao.findComplainOrderByTenantId(complainOrder);
    }

    @Override
    public List<FixOrder> findFixOrderByTenantId(FixOrder fixOrder) {
        return fixOrderDao.findFixOrderByTenantId(fixOrder);
    }

    @Override
    public Tenant findCodeByEmail(Tenant tenant)
    {
        return tenantDao.findCodeByEmail(tenant);
    }

    @Override
    public String login(Tenant tenant) throws Exception {
        long tenantByEmail = tenantDao.findTenantByEmail(tenant);
        long tenantByEmailAndPassWord = tenantDao.findTenantByEmailAndPassWord(tenant);
        return tenantByEmail > 0 ? (tenantByEmailAndPassWord > 0 ? "0" : "1"): "2";
    }

}
