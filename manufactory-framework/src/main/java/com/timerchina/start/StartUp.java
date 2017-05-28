package com.timerchina.start;

import com.timerchina.manufactory.Manufactory;
import com.timerchina.manufactory.config.PropKit;

public class StartUp {

    public static void main(String[] args) {
        Manufactory factory = new Manufactory(PropKit.use("config.properties"));
        factory.initialize().add(null).start().stop().eventual();
    }
}
