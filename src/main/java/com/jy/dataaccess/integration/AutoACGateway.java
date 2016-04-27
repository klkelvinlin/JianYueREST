package com.jy.dataaccess.integration;


import org.springframework.messaging.Message;

import com.jy.dataaccess.integration.domain.DefectRequest;

/**
 * Created with IntelliJ IDEA.
 * User: wdong
 * Date: 13-5-22
 * Time: 下午12:29
 * To change this template use File | Settings | File Templates.
 */
public interface AutoACGateway {
    public DefectRequest getDefectRequest(Message<DefectRequest> request);
}
