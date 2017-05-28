package com.timerchina.manufactory;

/**
 * @author will.ma
 * @date 2017-5-21
 */
public class Context {

    private Manufactory instance;

    Context(Manufactory instance) {
        this.instance = instance;
    }

    /*
     * TODO:
     * 需提供注册Dispatcher接口
     * 需提供注册Config接口
     * 需提供注册Resource接口
     * 需提供注册Connection接口
     * 需提供提交Task接口
     */
}
