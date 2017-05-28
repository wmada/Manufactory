package com.timerchina.manufactory.pool;

import com.timerchina.constant.Scope;
import com.timerchina.toolkit.utils.ValidateUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author will.ma
 * @date 2017-5-20
 */
public abstract class Pool<T> {

    private String name;
    private Scope  scope;

    private Map<String, T> pool = new HashMap<>();

    public Pool(String name, Scope scope) {
        this.name = name;
        this.scope = scope;
    }

    public String getName() {
        return name;
    }

    public Scope getScope() {
        return scope;
    }

    public void add(String key, T resource) {
        if (ValidateUtils.notEmpty(key) && resource != null) {
            pool.put(key, resource);
        }
    }

    public void del(String key) {
        pool.remove(key);
    }

    public T use(String key) {
        return pool.get(key);
    }

}
