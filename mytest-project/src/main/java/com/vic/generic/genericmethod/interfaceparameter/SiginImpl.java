package com.vic.generic.genericmethod.interfaceparameter;

import com.vic.generic.genericmethod.mygenericmethod.MyMethodAnnotation;
import com.vic.generic.genericmethod.mygenericmethod.MyParameterAnnotation;

/**
 * @description:
 * @author: wangqp
 * @create: 2020-06-19 17:25
 **/
public class SiginImpl implements ISigin {

    @Override
    public void sigin(@MyParameterAnnotation Object siginParam) {

    }

    @MyMethodAnnotation
    public void excute(@MyParameterAnnotation Object siginParam) {

    }
}
