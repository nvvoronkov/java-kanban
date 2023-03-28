package model;

public class Task {
    private String name; // Наименование задачи
    private String description; // Описание задачи
    private int id; // Уникальный идентификационный номер задачи
    private Status status; // Статус задачи: NEW, IN_PROGRESS, DONE
    private Type type; // Тип задачи: Task, Epic, Subtask

    public Task(String name, String description, Status status, Type type) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.type = type;
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

    @Override
    public String toString() {
        return "Task{" + "name='" + name + '\'' + ", description='" + description +
        '\'' + ", id=" + id + ", status=" + status + '}';
    }
}