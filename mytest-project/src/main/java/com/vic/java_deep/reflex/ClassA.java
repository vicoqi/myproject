package com.vic.java_deep.reflex;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: wangqp
 * @create: 2020-07-13 15:25
 */
public class ClassA {
    private String name;
    private int age;
    private String address;
    private Map<String, String> projectIdDetailMap = new HashMap<>();

    public void setName(String name) {
        this.name = name;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public void setAddress(String address) {
        this.name = address;
    }
    public void addProjectExperience(String projectName, String details) {
        projectIdDetailMap.put(projectName, details);
    }
}

