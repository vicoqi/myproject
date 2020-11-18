package com.vic.generic.genericclass.multiparameters.multiparametersannotation;

import java.util.AbstractList;

/**
 * @author: wangqp
 * @create: 2020-11-17 17:14
 */
public class MultiParamsImpl extends AbstractList<String> implements IMultiParamInterface<String,Integer>{

    @Override
    public void process() {
        System.out.println("MultiParamsImpl is invoke process");
    }

    @Override
    public String get(int index) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void parentProcess(Boolean aBoolean) {

    }
}
