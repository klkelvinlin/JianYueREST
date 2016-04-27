package com.jy.dataaccess.integration.domain;

/**
 * Created with IntelliJ IDEA.
 * User: wdong
 * Date: 13-5-22
 * Time: 下午12:06
 * To change this template use File | Settings | File Templates.
 */
public class DefectRequest {
    private DefectEvent defectEvent;
    private Defect defect;

    public DefectEvent getDefectEvent() {
        return defectEvent;
    }

    public void setDefectEvent(DefectEvent defectEvent) {
        this.defectEvent = defectEvent;
    }

    public Defect getDefect() {
        return defect;
    }

    public void setDefect(Defect defect) {
        this.defect = defect;
    }
}
