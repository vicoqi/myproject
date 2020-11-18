package com.vic.generic.genericclass.multiparameters.multiparametersannotation;

//给泛型加上注解，相当于加上别名，到时候有多个注解的时候，可以准确的找到

/**
 * 得到这个类上的注解
 * AnnotationUtils.findAnnotation(spi, Spi.class);
 *
 * //得到 父类的泛型数据，这个包括父接口吗？
 * Map<TypeVariable<?>, Type> typeArguments = TypeUtils.getTypeArguments(subClz, superClz);
 */