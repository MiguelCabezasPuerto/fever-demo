package com.fever.event.test.fever_demo_miguelcabezas.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class Plan {
    private String planStartDate;
    private String planEndDate;
    private String planId;
    private String sellFrom;
    private String sellTo;
    private boolean soldOut;
    private List<Zone> zones;

    @XmlAttribute(name = "plan_start_date")
    public String getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(String planStartDate) {
        this.planStartDate = planStartDate;
    }

    @XmlAttribute(name = "plan_end_date")
    public String getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(String planEndDate) {
        this.planEndDate = planEndDate;
    }

    @XmlAttribute(name = "plan_id")
    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    @XmlAttribute(name = "sell_from")
    public String getSellFrom() {
        return sellFrom;
    }

    public void setSellFrom(String sellFrom) {
        this.sellFrom = sellFrom;
    }

    @XmlAttribute(name = "sell_to")
    public String getSellTo() {
        return sellTo;
    }

    public void setSellTo(String sellTo) {
        this.sellTo = sellTo;
    }

    @XmlAttribute(name = "sold_out")
    public boolean isSoldOut() {
        return soldOut;
    }

    public void setSoldOut(boolean soldOut) {
        this.soldOut = soldOut;
    }

    @XmlElement(name = "zone")
    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }
}

