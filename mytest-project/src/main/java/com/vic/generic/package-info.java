package com.vic.generic;

//这个包下有接口，有抽象类，最好看看，当使用注解的时候

//获取类上的泛型信息 及 方法上泛型的信息


/**
 * 1.注解会被继承吗，在接口中，在抽象类中
 * 子类可以继承到父类上的注解吗--有结论了    https://blog.csdn.net/erlian1992/article/details/70146208
 *
 *
 * 我们知道在编写自定义注解时，可以通过指定@Inherited注解，指明自定义注解是否可以被继承。
 * 通过测试结果来看，@Inherited 只是可控制 对类名上注解是否可以被继承。不能控制方法上的注解是否可以被继承。
*/

/**
 * 得到这个类上的注解
 * AnnotationUtils.findAnnotation(spi, Spi.class);
 *
 * //得到 父类的泛型数据，这个包括父接口吗？
 * Map<TypeVariable<?>, Type> typeArguments = TypeUtils.getTypeArguments(subClz, superClz);
 */
