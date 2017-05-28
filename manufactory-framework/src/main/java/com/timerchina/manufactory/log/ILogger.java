package com.timerchina.manufactory.log;

import com.timerchina.manufactory.task.Spot;
import com.timerchina.manufactory.task.Task;
import com.timerchina.manufactory.worker.Worker;

/**
 * @author will.ma
 * @date 2017-5-7
 */
public interface ILogger {

    String getName();

    void log(String message);

    void debug(String message);

    void info(String message);

    void warn(String message);

    void error(String message);

    void log(Task task, String message);

    void debug(Task task, String message);

    void info(Task task, String message);

    void warn(Task task, String message);

    void error(Task task, String message);

    void log(Spot spot, String message);

    void debug(Spot spot, String message);

    void info(Spot spot, String message);

    void warn(Spot spot, String message);

    void error(Spot spot, String message);

    void log(Worker worker, String message);

    void debug(Worker worker, String message);

    void info(Worker worker, String message);

    void warn(Worker worker, String message);

    void error(Worker worker, String message);

}
