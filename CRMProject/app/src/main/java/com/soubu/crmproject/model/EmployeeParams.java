package com.soubu.crmproject.model;

import com.soubu.crmproject.utils.CharacterParser;

/**
 * Created by lakers on 16/9/15.
 */
public class EmployeeParams {
    String id;
    String name;
    String department;
    String position;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLetter(){
        return Character.toString(CharacterParser.getInstance().getSelling(name).charAt(0)).toUpperCase();
    }
}
