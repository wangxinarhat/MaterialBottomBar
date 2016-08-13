package com.wangxinarhat.bottombar.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by wang on 2016/8/13.
 */
public class DynamicProxy implements InvocationHandler {

    private Object mObj;

    public DynamicProxy(Object obj) {
        this.mObj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        return method.invoke(mObj, args);
    }
}
