package model;

public class Task {
    private String name; // Наименование задачи
    private String description; // Описание задачи
    private int id; // Уникальный идентификационный номер задачи
    private Status status; // Статус задачи: NEW, IN_PROGRESS, DONE

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
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

    @Override
    public String toString() {
        return "Task{" + "name='" + name + '\'' + ", description='" + description + '\'' + ", id=" + id + ", status=" + status + '}';
    }
}