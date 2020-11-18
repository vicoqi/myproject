package com.vic.generic.genericclass.multiparameters.multiparametersannotation;


import static com.vic.generic.genericclass.multiparameters.multiparametersannotation.IMultiParamInterface.*;

//相当于标注，找到注解为 value 值的 泛型类
public interface IMultiParamInterface<@MyAxis(FIRST) T extends String,@MyAxis(SECONDE) U extends Integer> extends IParentMultiParamInterface<Boolean> {
    String FIRST="FIRST";
    String SECONDE="SECONDE";

    void process();
}
