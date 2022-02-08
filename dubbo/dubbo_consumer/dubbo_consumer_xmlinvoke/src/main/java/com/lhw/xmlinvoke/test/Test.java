package com.lhw.xmlinvoke.test;

import java.lang.reflect.Field;

public class Test {

    public static void main(String[] args) throws ClassNotFoundException {
        Class c = Class.forName("com.lhw.dubbo_api.model.User");
        Field[] fs = c.getDeclaredFields();
        for (Field f : fs){
            System.out.println(f);
        }
    }

}
