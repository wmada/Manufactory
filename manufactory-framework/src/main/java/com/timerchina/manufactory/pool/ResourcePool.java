package com.timerchina.manufactory.pool;

import com.timerchina.constant.Scope;
import com.timerchina.manufactory.resource.IResource;

/**
 * @author will.ma
 * @date 2017-5-7
 */
public class ResourcePool extends Pool<IResource> {

    public ResourcePool(String name, Scope scope) {
        super(name, scope);
    }

}
