package com.vic.parentson;

import lombok.ToString;

/**
 * @Auther: wqp
 * @Date: 2018/12/4 11:48
 * @Description:
 */
@ToString
public abstract class Fruit{

    private String name;

    public Fruit(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
