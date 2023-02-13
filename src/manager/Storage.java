package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static model.Status.DONE;
import static model.Status.IN_PROGRESS;

public class Storage implements Manager { // Класс для хранения всей необходимой информации задач, эпиков, подзадач
    private final Map<Integer, Task> tasks = new HashMap<>(); // Присвоение соответствия между идентификатором и задачей
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private int id = 0;

    private boolean presenceSubtask(int id) {
        return subtasks.containsKey(id);
    }

    @Override
    public void addTask(Task task) { // Метод по добавлению задач в мапу
        id++;
        task.setId(id);
        tasks.put(id, task);
    }

    @Override
    public void addEpic(Epic epic) { // Метод по добавлению эпиков в мапу
        id++;
        epic.setId(id);
        epics.put(id, epic);
    }

    @Override
    public void addSubtask(Subtask subtask) { // Метод по добавлению подзадач в мапу
        id++;
        subtask.setId(id);
        subtasks.put(id, subtask);
        int idEpic = subtask.getIdEpic();
        Epic epic = epics.get(subtask.getIdEpic());
        epic.addSubtaskId(id);
        checkStatusEpic(idEpic);
    }

    @Override
    public List<Subtask> getListSubtaskEpic(int id) { // Метод для получения списка подзадач в эпике
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void deleteAllTask() { // Метод по удалению всех задач из мапы
        tasks.clear();
    }

    @Override
    public void deleteAllEpic() { // Метод по удалению всех эпиков
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteAllSubtask() { // Метод по удалению всех подзадач
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.deleteAllSubtaskId();
            epic.setStatus(Status.NEW);
        }
    }

    @Override
    public void deleteTaskID(int id) { // Метод по удалению определенной задачи
        tasks.remove(id);
    }

    @Override
    public void deleteEpicID(int id) { // Метод по удалению определенного эпика
        List<Integer> subtaskId = epics.get(id).getSubtaskId();
        for (Integer idSubtask : subtaskId) {
            subtasks.remove(idSubtask);
        }
        epics.remove(id);
    }

    @Override
    public void deleteSubtaskID(int id) { // Метод по удалению определенной подзадачи
        if (presenceSubtask(id)) {
            int idEpic = subtasks.get(id).getIdEpic();
            Epic epic = epics.get(idEpic);
            epic.deleteSubtaskId(id);
            checkStatusEpic(idEpic);
        }
    }

    @Override
    public void checkStatusEpic(int idEpic) {
        Epic epic = epics.get(idEpic);
        if (epic.getSubtaskId().isEmpty()) {
            epic.setStatus(Status.NEW);
        }
        for (Integer subtaskId : epic.getSubtaskId()) {
            Subtask subtask = subtasks.get(subtaskId);
            if (subtask.getStatus() == Status.NEW) {
                epic.setStatus(Status.NEW);
            } else if (subtask.getStatus() == DONE) {
                epic.setStatus(DONE);
            } else {
                epic.setStatus(IN_PROGRESS);
            }
        }
    }

    @Override
    public Task renewalTask(Task task) { // Методы по обновлению задач, подзадач, эпиков
        if (!tasks.containsKey(id)) {
            return null;
        }
        tasks.put(id, task);
        return task;
    }

    @Override
    public Epic renewalEpic(Epic epic) {
        if (!epics.containsKey(id)) {
            return null;
        }
        epics.put(id, epic);
        return epic;
    }

    @Override
    public Subtask renewalSubtask(int idEpic, Subtask subtask) {
        if (!subtasks.containsKey(id)) {
            return null;
        }
        subtasks.put(id, subtask);
        checkStatusEpic(idEpic);
        return subtask;
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }
}
