package com.fever.event.test.fever_demo_miguelcabezas.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "planList")
public class PlanList {
    private Output output;

    @XmlElement(name = "output")
    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }
}
