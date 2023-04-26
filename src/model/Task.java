package model;

import java.time.LocalDateTime;

import model.constants.Status;
import model.constants.Type;

public class Task {
    private String name; // Наименование задачи
    private String description; // Описание задачи
    private int id; // Уникальный идентификационный номер задачи
    private Status status; // Статус задачи: NEW, IN_PROGRESS, DONE
    private Type type; // Тип задачи: Task, Epic, Subtask
    private LocalDateTime startTime; //Cтарт задачи
    protected LocalDateTime endTime; //Конец задачи
    private long duration; // Продолжительность задачи

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.type = Type.TASK;
    }

    public Task(String name, String description, Status status, Type type, 
                LocalDateTime startTime, long duration) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.type = type;
        this.startTime = startTime;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public LocalDateTime getEndTime() { 
        return startTime.plusMinutes(duration); 
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Task{" + "name='" + name + '\'' + ", description='" + description +
        '\'' + ", id=" + id + ", status=" + status + "startTime=" + startTime + "duration=" + duration + '}';
    }
}