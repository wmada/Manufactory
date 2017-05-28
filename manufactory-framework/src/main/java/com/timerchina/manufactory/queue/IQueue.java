package com.timerchina.manufactory.queue;

import com.timerchina.manufactory.task.Task;

/**
 * @author will.ma
 * @date 2017-5-7
 */
public interface IQueue {

    void cutIn(Task task);

    void push(Task task);

    Task pull();

    long size();

    void clear();

}
