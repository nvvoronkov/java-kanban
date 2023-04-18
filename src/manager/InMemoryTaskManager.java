package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class InMemoryTaskManager implements TaskManager { // Класс для хранения всей необходимой информации задач, эпиков, подзадач
    protected final Map<Integer, Task> tasks = new HashMap<>(); // Присвоение соответствия между идентификатором и задачей
    protected final Map<Integer, Epic> epics = new HashMap<>();
    protected final Map<Integer, Subtask> subtasks = new HashMap<>();
    protected final HistoryManager historyManager = Manager.getDefaultHistory();
    protected final TreeSet<Task> prioritizedTasks;
    public final HistoryManager inMemoryHistoryManager = Manager.getDefaultHistory();
    private int id = 0;

    public InMemoryTaskManager() {
        this.prioritizedTasks = new TreeSet<>((task1, task2) -> {
            if (task1.getStartTime() == null) {
                return 1;
            }
            if (task2.getStartTime() == null) {
                return -1;
            }
            return task1.getStartTime().compareTo(task2.getStartTime());
        });
    }

    public HistoryManager getInMemoryHistoryManager() {
        return inMemoryHistoryManager;
    }

    @Override
    public void addTask(Task task) { // Метод по добавлению задач в мапу
        id++;
        task.setId(id);
        addPrioritizedTasks(task);
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
        if (epic != null) {
            addPrioritizedTasks(subtask);
            epic.addSubtasksById(id);
            whatStatusOfEpic(epic);
            updateTimeEpic(epic);
        }
    }

    @Override
    public List<Subtask> getListSubtasksEpic(int id) { // Метод для получения списка подзадач в эпике
        if (epics.containsKey(id)) {
            List<Subtask> subtasks = new ArrayList<>();
            Epic epic = epics.get(id);
            for (int subtaskId: epic.getSubtasksById()) {
                subtasks.add(this.subtasks.get(subtaskId));
            }
            return subtasks;
        } else {
            return null;
        }
    }

    @Override
    public void deleteAllTask() { // Метод по удалению всех задач из мапы
        for (Integer id: tasks.keySet()) {
            historyManager.remove(id);
        }
        tasks.clear();
        prioritizedTasks.clear();
    }

    @Override
    public void deleteAllEpic() { // Метод по удалению всех эпиков
        for (Integer id: epics.keySet()) {
            historyManager.remove(id);
        }
        epics.clear();
        for (Integer id: subtasks.keySet()) {
            historyManager.remove(id);
        }
        subtasks.clear();
    }

    @Override
    public void deleteAllSubtask() { // Метод по удалению всех подзадач
        for (Integer id: subtasks.keySet()) {
            historyManager.remove(id);
        }
        subtasks.clear();
        for (Epic epic: epics.values()) {
            epic.deleteAllSubtasksById();
            epic.setStatus(Status.NEW);
        }
    }

    @Override
    public void deleteTaskById(int id) { // Метод по удалению определенной задачи
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            historyManager.remove(id);
        }
        tasks.remove(id);
    }

    @Override
    public void deleteEpicById(int id) { // Метод по удалению определенного эпика
        if (epics.containsKey(id)) {
            List<Integer> subtasksId = epics.get(id).getSubtasksById();
            for (Integer idSubtask: subtasksId) {
                subtasks.remove(idSubtask);
                historyManager.remove(idSubtask);
            }
            epics.remove(id);
            historyManager.remove(id);
        }
    }

    @Override
    public void deleteSubtaskById(int id) { // Метод по удалению определенной подзадачи
        if (subtasks.containsKey(id)) {
            int idEpic = subtasks.get(id).getIdEpic();
            Epic epic = epics.get(idEpic);
            epic.deleteSubtasksById(id);
            prioritizedTasks.remove(subtasks.get(id));
            subtasks.remove(id);
            historyManager.remove(id);
            whatStatusOfEpic(epic);
        }
    }

    private void whatStatusOfEpic(Epic epic) {
        int subtaskNew = 0;
        int subtaskDone = 0;
        int subtasksIds = epic.getSubtasksById().size();
        
        for (Integer subtaskId: epic.getSubtasksById()) {
            if (subtasks.get(subtaskId).getStatus() == Status.NEW) {
                ++subtaskNew;
            } else if (subtasks.get(subtaskId).getStatus() == Status.DONE) {
                ++subtaskDone;
            } else {
                epic.setStatus(Status.IN_PROGRESS);
                return;
            }
        }
        if (subtaskNew == subtasksIds) {
            epic.setStatus(Status.NEW);
        } else if (subtaskDone == subtasksIds) {
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
            whatStatusOfEpic(epic);
        }
        return null;
    }

    @Override
    public Subtask updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(subtask.getIdEpic());
            whatStatusOfEpic(epic);
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
    public Epic getEpicsById(int id) {
        final Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public Subtask getSubtasksById(int id) {
        final Subtask subtask = subtasks.get(id);
        historyManager.add(subtask);
        return subtask;
    }

    @Override
    public Task getTasksById(int id) {
        final Task task = tasks.get(id);
        historyManager.add(task);
        return task;

    }

    @Override
     public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    public void addPrioritizedTasks(Task task) {
        if (task.getStartTime() != null) {
            prioritizedTasks.add(task);
            taskInTimePriority(task);
        }
    }

    public void taskInTimePriority(Task task) { 
        if (task != null) {
            for (Task priorityTask : prioritizedTasks) {
                if (task.getStartTime() != null) {
                    if (task.getId() != priorityTask.getId()) {
                        if (!task.getStartTime().isBefore(priorityTask.getStartTime()) &&
                            !task.getStartTime().isAfter(priorityTask.getStartTime()) ||
                         !priorityTask.getStartTime().isBefore(task.getStartTime()) &&
                           !priorityTask.getStartTime().isAfter(task.getStartTime())) {
                            System.out.println("Задача с таким временем уже существует, невозможно создать данную задачу.");
                        }
                    }
                }
            }
        }
    }

    public void updateTimeEpic(Epic epic) { // Время начала — дата старта самой ранней подзадачи, а время завершения — время окончания самой поздней из задач
        List<Subtask> list = getListSubtasksEpic(epic.getId()); // Лист подзадач в эпикe, находим время начала и продолжения
        if (list.size() > 0) {
            LocalDateTime startTime = null;
            LocalDateTime endTime = null;
            long duration = 0;
            for (Subtask subtasks : list) {
                if (startTime == null) {
                    startTime = subtasks.getStartTime();
                } else if (subtasks.getStartTime().isBefore(startTime)) {
                    startTime = subtasks.getStartTime(); // Время старта-дата старта самой ранней задачи
                    duration += subtasks.getDuration();
                }
                if (endTime == null) {
                    endTime = subtasks.getEndTime();
                } else if (subtasks.getEndTime().isAfter(endTime)) {
                    endTime = subtasks.getEndTime();
                }
                epic.setStartTime(startTime);// Заполняем время старта, продолжительности, окончания задач
                epic.setDuration(duration);
                epic.setEndTime(endTime);
            }
        }
    }
}
