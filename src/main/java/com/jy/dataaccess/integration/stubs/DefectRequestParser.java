package com.jy.dataaccess.integration.stubs;

import com.jy.dataaccess.integration.domain.DefectRequest;

/**
 * Created with IntelliJ IDEA.
 * User: wdong
 * Date: 13-5-22
 * Time: 下午12:11
 * To change this template use File | Settings | File Templates.
 */
public class DefectRequestParser {

    public boolean needAutoACPull(DefectRequest request) {
        if(request.getDefect().getDefectCategory().equalsIgnoreCase("ITEM_LABEL_MISSING")) {
            return true;
        }
        return false;
    }
}
