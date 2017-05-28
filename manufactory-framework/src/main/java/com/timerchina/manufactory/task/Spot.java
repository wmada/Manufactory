package com.timerchina.manufactory.task;

import java.io.Serializable;
import java.util.Date;

/**
 * @author will.ma
 * @date 2017-5-7
 */
public abstract class Spot implements Serializable {

    private Task task;

    private String  processor;
    private Integer turn;
    private Date    startTime;
    private Date    finishTime;
    private String  message;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public Integer getTurn() {
        return turn;
    }

    public void setTurn(Integer turn) {
        this.turn = turn;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String summary() {
        return task.getLotId() + " > " + task.getTaskId() + " [" + task.getTaskType() + "]: " + task.getStatus() + " (" + task.getWorker() + " -> " + processor + ") ";
    }
}
