package com.timerchina.manufactory.dispatcher;

import com.timerchina.manufactory.queue.IQueue;
import com.timerchina.manufactory.task.Task;
import com.timerchina.toolkit.utils.ValidateUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author will.ma
 * @date 2017-5-20
 */
public class SingleDispatcher extends Dispatcher {

    private String scope;
    private IQueue queue;

    public SingleDispatcher(IQueue queue, String taskType) {
        if (ValidateUtils.isEmpty(taskType)) {
            throw new RuntimeException("Task Type cannot be empty or null");
        }
        this.scope = taskType;
        this.queue = queue;
    }

    @Override
    public boolean accept(String taskType) {
        return scope.equals(taskType);
    }

    @Override
    public Set<String> duties() {
        Set<String> duties = new HashSet<>();
        duties.add(scope);
        return duties;
    }

    @Override
    public void cutIn(Task task) {
        queue.cutIn(task);
    }

    @Override
    public void push(Task task) {
        queue.push(task);
    }

    @Override
    public Task pull() {
        return queue.pull();
    }
}
