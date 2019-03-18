package com.vic.parentson;

import lombok.ToString;

/**
 * @Auther: wqp
 * @Date: 2018/12/4 11:49
 * @Description:
 */
@ToString(callSuper = true)
public class Apple extends Fruit{

    public Apple(String name,int a){
       super(name);
       this.id = a;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
