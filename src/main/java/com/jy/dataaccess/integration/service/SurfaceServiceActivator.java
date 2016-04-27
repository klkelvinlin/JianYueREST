package com.jy.dataaccess.integration.service;

import com.jy.dataaccess.integration.domain.DefectRequest;

/**
 * Created with IntelliJ IDEA.
 * User: wdong
 * Date: 13-5-22
 * Time: 下午12:24
 * To change this template use File | Settings | File Templates.
 */
public class SurfaceServiceActivator {
    public void surface(DefectRequest request) {
        System.out.println("surface: " + request);
    }
}
