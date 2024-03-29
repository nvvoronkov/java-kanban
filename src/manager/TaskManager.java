package manager;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.List;
import java.util.TreeSet;

public interface TaskManager {

    void addTask(Task task); // Методы по добавлению задач, эпиков, подзадач

    void addEpic(Epic epic);

    void addSubtask(Subtask subtask);

    List<Subtask> getListSubtasksEpic(int id); // Метод для получения списка подзадач в эпике

    void deleteAllTask(); // Методы по удалению всех задач, эпиков, подзадач

    void deleteAllEpic();

    void deleteAllSubtask();

    void deleteTaskById(int id); // Методы по удалению определенных задач, эпиков, подзадач по ID

    void deleteEpicById(int id);

    void deleteSubtaskById(int id);

    int createTask(Task task);

    int createEpic(Epic epic);

    int createSubtask(Epic epic, Subtask subtask);

    Task updateTask(Task task); // Методы по обновлению задач, подзадач, эпиков

    Epic updateEpic(Epic epic);

    Subtask updateSubtask(Subtask subtask);

    List<Task> getTasks(); // Методы для получения задач, эпиков, подзадач

    List<Epic> getEpics();

    List<Subtask> getSubtasks();

    List<Subtask> getAllSubtaskOfEpic(Epic epic);

    List<Task> getHistory(); // Метод, возвращающий 10 последних просмотренных задач

    Task getTasksById(int id);

    Epic getEpicsById(int id);

    Subtask getSubtasksById(int id);

    TreeSet<Task> getPrioritizedTasks();

}