package com.yhjia.rxjava_helloworld.bean;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String name;
    private List<Plain> planList = new ArrayList<>();

    public Person(String name,List<Plain> planList) {
        this.name = name;
        this.planList = planList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Plain> getPlanList() {
        return planList;
    }

    public void setPlanList(List<Plain> planList) {
        this.planList = planList;
    }
}
