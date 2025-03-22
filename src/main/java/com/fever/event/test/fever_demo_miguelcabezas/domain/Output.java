package com.fever.event.test.fever_demo_miguelcabezas.domain;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class Output {
    private List<BasePlan> basePlans;

    @XmlElement(name = "base_plan")
    public List<BasePlan> getBasePlans() {
        return basePlans;
    }

    public void setBasePlans(List<BasePlan> basePlans) {
        this.basePlans = basePlans;
    }
}
