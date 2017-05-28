package com.timerchina.manufactory.log;

import com.timerchina.manufactory.Manufactory;
import com.timerchina.manufactory.task.Spot;
import com.timerchina.manufactory.task.Task;
import com.timerchina.manufactory.worker.Worker;
import org.apache.log4j.Logger;

/**
 * @author will.ma
 * @date 2017-5-7
 */
public class ConsoleLogger implements ILogger {

    private static Logger logger = Logger.getLogger(Manufactory.class);

    private String name;

    public ConsoleLogger(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void log(String message) {
        logger.info(getName() + " Message: " + message);
    }

    @Override
    public void debug(String message) {
        logger.debug(getName() + " Message: " + message);
    }

    @Override
    public void info(String message) {
        logger.info(getName() + " Message: " + message);
    }

    @Override
    public void warn(String message) {
        logger.warn(getName() + " Message: " + message);
    }

    @Override
    public void error(String message) {
        logger.error(getName() + " Message: " + message);
    }

    @Override
    public void log(Task task, String message) {
        logger.info(task.summary() + "\n\t" + getName() + " Message: " + message);
    }

    @Override
    public void debug(Task task, String message) {
        logger.debug(task.summary() + "\n\t" + getName() + " Message: " + message);
    }

    @Override
    public void info(Task task, String message) {
        logger.info(task.summary() + "\n\t" + getName() + " Message: " + message);
    }

    @Override
    public void warn(Task task, String message) {
        logger.warn(task.summary() + "\n\t" + getName() + " Message: " + message);
    }

    @Override
    public void error(Task task, String message) {
        logger.error(task.summary() + "\n\t" + getName() + " Message: " + message);
    }

    @Override
    public void log(Spot spot, String message) {
        logger.info(spot.summary() + "\n\t" + getName() + " Message: " + message);
    }

    @Override
    public void debug(Spot spot, String message) {
        logger.debug(spot.summary() + "\n\t" + getName() + " Message: " + message);
    }

    @Override
    public void info(Spot spot, String message) {
        logger.info(spot.summary() + "\n\t" + getName() + " Message: " + message);
    }

    @Override
    public void warn(Spot spot, String message) {
        logger.warn(spot.summary() + "\n\t" + getName() + " Message: " + message);
    }

    @Override
    public void error(Spot spot, String message) {
        logger.error(spot.summary() + "\n\t" + getName() + " Message: " + message);
    }

    @Override
    public void log(Worker worker, String message) {
        logger.info(getName() + " " + worker.getName() + " Message: " + message);
    }

    @Override
    public void debug(Worker worker, String message) {
        logger.debug(getName() + " " + worker.getName() + " Message: " + message);
    }

    @Override
    public void info(Worker worker, String message) {
        logger.info(getName() + " " + worker.getName() + " Message: " + message);
    }

    @Override
    public void warn(Worker worker, String message) {
        logger.warn(getName() + " " + worker.getName() + " Message: " + message);
    }

    @Override
    public void error(Worker worker, String message) {
        logger.error(getName() + " " + worker.getName() + " Message: " + message);
    }
}
