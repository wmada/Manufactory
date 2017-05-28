package com.timerchina.manufactory.queue;

import com.timerchina.manufactory.task.Task;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author will.ma
 * @date 2017-5-20
 */
public class MemQueue implements IQueue {

    private Deque<Task> queue;

    public MemQueue() {
        queue = new ConcurrentLinkedDeque<>();
    }

    @Override
    public void cutIn(Task task) {
        queue.offerFirst(task);
    }

    @Override
    public void push(Task task) {
        queue.offerLast(task);
    }

    @Override
    public Task pull() {
        return queue.pollFirst();
    }

    @Override
    public long size() {
        return queue.size();
    }

    @Override
    public void clear() {
        queue.clear();
    }
}
