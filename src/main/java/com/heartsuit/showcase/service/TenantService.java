package com.heartsuit.showcase.service;

import com.heartsuit.showcase.domain.ComplainOrder;
import com.heartsuit.showcase.domain.FixOrder;
import com.heartsuit.showcase.domain.Tenant;

import java.util.List;

/**
 * Created by Administrator on 2019/8/25 0025 00:01
 */
public interface TenantService {
    String insert(Tenant tenant);

    List<Tenant> findAll();

    String checkCode(Tenant tenant);

    void updateActivationStatus(Tenant tenant);

    String checkEmail(Tenant tenant);

    String login(Tenant tenant);

    Tenant findCodeByEmail(Tenant tenant);

    Tenant createTenantByCode(String code);

    Tenant findEmailByCode(Tenant tenant);

    String findTenantIdByEmail(Tenant tenant);

    void updateTenantByLevel(Tenant tenant);

    void insertFixOrder(FixOrder fixOrder);

    void insertComplainOrder(ComplainOrder complainOrder);

    void updateTenantInformationByOperator(Tenant tenant);

    void updateTenantCommentByFixOrderId(FixOrder fixOrder);

    Tenant queryTenantByTenantId(Tenant tenant);
}
