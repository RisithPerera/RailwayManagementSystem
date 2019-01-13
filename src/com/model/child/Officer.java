package com.model.child;

import com.manifest.Symbol;
import com.model.superb.SuperModel;

import java.util.Objects;

public class Officer extends SuperModel{

    private String name;
    private String password;

    public Officer() {}

    public Officer(int id, String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}