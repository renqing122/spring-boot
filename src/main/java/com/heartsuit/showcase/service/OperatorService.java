package com.heartsuit.showcase.service;

import com.heartsuit.showcase.domain.Operator;

import java.util.List;

public interface OperatorService {
    String insert(Operator operator);
    List<Operator> findAll();
    String login(Operator operator);
    void updateOperatorActivationStatus(Operator operator);
    List<Operator> findAllOperatorByActivationStatus0();
}
