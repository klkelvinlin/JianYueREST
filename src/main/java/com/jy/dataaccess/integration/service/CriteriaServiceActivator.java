package com.jy.dataaccess.integration.service;


import org.springframework.messaging.Message;

import com.jy.dataaccess.integration.domain.DefectRequest;

/**
 * Created with IntelliJ IDEA.
 * User: wdong
 * Date: 13-5-22
 * Time: 下午12:23
 * To change this template use File | Settings | File Templates.
 */
public class CriteriaServiceActivator {
    public void verify(Message<DefectRequest> request) {
        System.out.println(request.getHeaders().get("AUTO_PULL_AC"));
        System.out.println("verify: " + request.getPayload());
    }
}
