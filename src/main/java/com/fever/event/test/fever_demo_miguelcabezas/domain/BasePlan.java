package com.fever.event.test.fever_demo_miguelcabezas.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class BasePlan {
    private String basePlanId;
    private String sellMode;
    private String title;
    private List<Plan> plans;

    @XmlAttribute(name = "base_plan_id")
    public String getBasePlanId() {
        return basePlanId;
    }

    public void setBasePlanId(String basePlanId) {
        this.basePlanId = basePlanId;
    }

    @XmlAttribute(name = "sell_mode")
    public String getSellMode() {
        return sellMode;
    }

    public void setSellMode(String sellMode) {
        this.sellMode = sellMode;
    }

    @XmlAttribute
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement(name = "plan")
    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }
}
