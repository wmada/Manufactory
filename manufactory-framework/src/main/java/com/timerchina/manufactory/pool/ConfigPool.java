package com.timerchina.manufactory.pool;

import com.timerchina.constant.Scope;
import com.timerchina.manufactory.config.IConfig;
import com.timerchina.toolkit.utils.ValidateUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author will.ma
 * @date 2017-5-7
 */
public class ConfigPool extends Pool<IConfig> {

    public ConfigPool(String name, Scope scope) {
        super(name, scope);
    }

}
