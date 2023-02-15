package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Storage extends Manager { // Класс для хранения всей необходимой информации задач, эпиков, подзадач
    private final Map<Integer, Task> tasks = new HashMap<>(); // Присвоение соответствия между идентификатором и задачей
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private int id = 0;

    private boolean isSubtaskPresent(int id) {
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
        Epic epic = epics.get(subtask.getIdEpic());
        epic.addSubtaskId(id);
        whatStatusEpic(epic);
    }

    @Override
    public List<Subtask> getListSubtaskEpic(int id) { // Метод для получения списка подзадач в эпике
        if (epics.containsKey(id)) {
            List<Subtask> subtask = new ArrayList<>();
            Epic epic = epics.get(id);
            for (int i = 0; i < epic.getSubtasksId().size(); i++) {
                subtask.add(subtasks.get(epic.getSubtasksId().get(i)));
            }
            return subtask;
        } else {
            return null;
        }
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
        List<Integer> subtaskId = epics.get(id).getSubtasksId();
        for (Integer idSubtask : subtaskId) {
            subtasks.remove(idSubtask);
        }
        epics.remove(id);
    }

    @Override
    public void deleteSubtaskID(int id) { // Метод по удалению определенной подзадачи
        if (isSubtaskPresent(id)) {
            int idEpic = subtasks.get(id).getIdEpic();
            Epic epic = epics.get(idEpic);
            epic.deleteSubtaskId(id);
            subtasks.remove(id);
            whatStatusEpic(epic);
        }
    }

    @Override
    protected void whatStatusEpic(Epic epic) {
        int subtaskNew = 0;
         int subtaskDone = 0;
         if (epic.getSubtasksId().isEmpty()) {
             epic.setStatus(Status.NEW);
             return;
         }
         for (Integer subtaskId : epic.getSubtasksId()) {
            if (subtasks.get(subtaskId).getStatus() == Status.NEW) {
                ++subtaskNew;
            } else if (subtasks.get(subtaskId).getStatus() == Status.DONE) {
                ++subtaskDone;
            }
        }
        if (subtaskNew == epic.getSubtasksId().size()) {
             epic.setStatus(Status.NEW);
         } else if (subtaskDone == epic.getSubtasksId().size()) {
             epic.setStatus(Status.DONE);
         } else {
             epic.setStatus(Status.IN_PROGRESS);
         }
    }

    @Override
    public Task updateTask(Task task) { // Методы по обновлению задач, подзадач, эпиков
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
        return null;
    }

    @Override
    public Epic updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
            whatStatusEpic(epic);
        }
        return null;
    }

    @Override
    public Subtask updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(subtask.getIdEpic());
            whatStatusEpic(epic);
        }
        return null;
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

    @Override
    public Task getTaskFromId(int id) {
        return tasks.get(id);
    }

    @Override
    public Epic getEpicFromId(int id) {
        return epics.get(id);
    }

    @Override
    public Subtask getSubtaskFromId(int id) {
        return subtasks.get(id);
    }
}
