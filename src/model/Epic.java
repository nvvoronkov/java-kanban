package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subtaskId = new ArrayList<>();// Список идентификаторов в эпике

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    public List<Integer> getSubtasksById() {
        return subtaskId;
    }

    public void addSubtasksById(int idSubtask) { // Методы для добавления, удаления, очистки подзадач в эпике
        subtaskId.add(idSubtask);
    }

    public void deleteSubtasksById(int idSubtask) {
        subtaskId.remove(Integer.valueOf(idSubtask));
    }

    public void deleteAllSubtasksById() {
        subtaskId.clear();
    }

    @Override
    public String toString() {
        return "Epic{" + "name='" + getName() + '\'' + ", description='" + getDescription() +
        '\'' + ", status=" + getStatus() + ", subtaskId=" + subtaskId + ", id=" + getId() + '}';
    }
}