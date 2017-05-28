package com.timerchina.manufactory;

import com.timerchina.manufactory.config.IConfig;
import com.timerchina.manufactory.dispatcher.Dispatcher;
import com.timerchina.manufactory.log.ConsoleLogger;
import com.timerchina.manufactory.log.ILogger;
import com.timerchina.manufactory.task.Status;
import com.timerchina.manufactory.worker.Worker;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author will.ma
 * @date 2017-5-7
 */
public class Manufactory {

    private static ILogger logger = new ConsoleLogger("Manufactory");

    public static ILogger getLogger() {
        return logger;
    }

    // TODO 此处可以扩展为其它Logger.
    public static void setLogger(ILogger logger) {
        Manufactory.logger = logger;
    }

    private String  name;
    private IConfig config;
    private Status status = Status.ORIGIN;

    private Map<String, Worker>     workers     = new LinkedHashMap<>();
    private Map<String, Dispatcher> dispatchers = new HashMap<>();

    public Manufactory(String name, IConfig config) {
        this.name = name;
        this.config = config;
    }

    public Manufactory(IConfig config) {
        this.name = getClass().getSimpleName();
        this.config = config;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public IConfig getConfig() {
        return config;
    }

    public Manufactory initialize() {
        if (status == Status.ORIGIN) {
            // TODO Check if anything to initialize.
            status = Status.INITIALIZED;
            logger.info("Initialized");
        } else {
            logger.info("Manufactory is already INITIALIZED");
        }
        return this;
    }

    public Manufactory start() {
        switch (status) {
            case ORIGIN:
                initialize();
                break;
            case INITIALIZED:
                break;
            case RUNNING:
                logger.warn("Manufactory is already RUNNING");
                return this;
            case STOPPED:
                break;
            case DESTROYED:
                logger.warn("Manufactory is already DESTROYED");
                return this;
            default:
                logger.warn("Unknown status");
                return this;
        }

        for (Worker worker : workers.values()) {
            worker.start();
        }
        status = Status.RUNNING;
        logger.info("Started");
        return this;
    }

    public Manufactory stop() {
        if (status != Status.RUNNING) {
            logger.warn("Manufactory is not RUNNING");
        } else {
            for (Worker worker : workers.values()) {
                worker.stop();
            }
            status = Status.STOPPED;
            logger.info("Stopped");
        }
        return this;
    }

    public Manufactory eventual() {
        if (status == Status.RUNNING) {
            stop();
        }
        for (Worker worker : workers.values()) {
            worker.eventual();
        }
        // TODO Check if anything to terminate.
        status = Status.DESTROYED;
        logger.info("Eventual");
        return this;
    }

    public Manufactory add(Worker worker) {
        worker.initialize(new Context(this));
        workers.put(worker.getName(), worker);
        return this;
    }

}
