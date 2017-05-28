package com.timerchina.manufactory.pool;

import com.timerchina.constant.Scope;
import com.timerchina.manufactory.connection.IConnection;

/**
 * @author will.ma
 * @date 2017-5-7
 */
public class ConnectionPool extends Pool<IConnection> {

    public ConnectionPool(String name, Scope scope) {
        super(name, scope);
    }

}
