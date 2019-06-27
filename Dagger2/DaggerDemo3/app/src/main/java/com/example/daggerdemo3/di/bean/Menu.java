package com.example.daggerdemo3.di.bean;

import java.util.Map;

import javax.inject.Inject;

public class Menu {

    private Map<String,Boolean> menus;

    @Inject
    public Menu(Map<String,Boolean> menus) {
        this.menus = menus;
    }

    public Map<String, Boolean> getMenus() {
        return menus;
    }

    public void setMenus(Map<String, Boolean> menus) {
        this.menus = menus;
    }
}
