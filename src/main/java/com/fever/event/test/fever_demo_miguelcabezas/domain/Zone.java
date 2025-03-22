package com.fever.event.test.fever_demo_miguelcabezas.domain;

import javax.xml.bind.annotation.XmlAttribute;

public class Zone {
    private String zoneId;
    private int capacity;
    private double price;
    private String name;
    private boolean numbered;

    @XmlAttribute(name = "zone_id")
    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    @XmlAttribute
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @XmlAttribute
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public boolean isNumbered() {
        return numbered;
    }

    public void setNumbered(boolean numbered) {
        this.numbered = numbered;
    }
}
