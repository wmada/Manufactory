package com.timerchina.manufactory.dispatcher;

import com.timerchina.manufactory.task.Task;

import java.util.HashSet;
import java.util.Set;

/**
 * @author will.ma
 * @date 2017-5-20
 */
public abstract class Dispatcher {

    public abstract boolean accept(String taskType);

    public abstract Set<String> duties();

    public abstract void cutIn(Task task);

    public abstract void push(Task task);

    public abstract Task pull();
}
