package com.timerchina.manufactory.resource;

/**
 * @author will.ma
 * @date 2017-5-7
 */
public interface IResource<T> {

    T require();

    void release(T resource);

}
