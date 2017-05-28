package com.timerchina.manufactory.worker;

import com.timerchina.manufactory.Context;
import com.timerchina.manufactory.Manufactory;
import com.timerchina.manufactory.processor.IProcessor;
import com.timerchina.manufactory.task.Status;
import com.timerchina.manufactory.trigger.ITrigger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author will.ma
 * @date 2017-5-7
 */
public class Worker implements Runnable{

    private String   name;
    private ITrigger trigger;

    private Status status = Status.ORIGIN;

    private Context context;

    private List<IProcessor> processors = new ArrayList<>();

    public Worker(String name, ITrigger trigger) {
        this.name = name;
        this.trigger = trigger;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public Worker initialize(Context context) {
        if (status == Status.ORIGIN) {
            this.context = context;
            /*
             * TODO:
             * 1. 设置其可接受的Task类型，即Dispatcher(可选)
             * 2. 设置其配置信息(可选)
             * 3. 设置其连接信息(可选)
             * 4. 设置其资源信息(可选)
             */
            // TODO Check if anything to initialize.
            status = Status.INITIALIZED;
            Manufactory.getLogger().info("Initialized");
        } else {
            Manufactory.getLogger().info(this, "Worker is already INITIALIZED");
        }
        return this;
    }

    public Worker start() {
        switch (status) {
            case ORIGIN:
                Manufactory.getLogger().warn(this, "Initialize Required, Current status is ORIGIN");
                return this;
            case INITIALIZED:
                break;
            case RUNNING:
                Manufactory.getLogger().warn(this, "Worker is already RUNNING");
                return this;
            case STOPPED:
                break;
            case DESTROYED:
                Manufactory.getLogger().warn(this, "Worker is already DESTROYED");
                return this;
            default:
                Manufactory.getLogger().warn(this, "Unknown status");
                return this;
        }

        // TODO start running.
        status = Status.RUNNING;
        Manufactory.getLogger().info(this, "Initialized");
        return this;
    }

    public Worker stop() {
        if (status != Status.RUNNING) {
            Manufactory.getLogger().warn(this, "Worker is not RUNNING");
        } else {
            // TODO stop running.
            status = Status.STOPPED;
            Manufactory.getLogger().info(this, "Stopped");
        }
        return this;
    }

    public Worker eventual() {
        if (status == Status.RUNNING) {
            stop();
        }
        // TODO Check if anything to terminate.
        status = Status.DESTROYED;
        Manufactory.getLogger().info(this, "Initialized");
        return this;
    }

    public Worker add(IProcessor processor) {
        processors.add(processor);
        return this;
    }

    @Override
    public void run() {

    }
}
