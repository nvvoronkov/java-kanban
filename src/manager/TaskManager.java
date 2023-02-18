package manager;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.List;

public interface TaskManager {
    void addTask(Task task); // Методы по добавлению задач, эпиков, подзадач

    void addEpic(Epic epic);

    void addSubtask(Subtask subtask);

    List<Subtask> getListSubtasksEpic(int id); // Метод для получения списка подзадач в эпике

    void deleteAllTask(); // Методы по удалению всех задач, эпиков, подзадач

    void deleteAllEpic();

    void deleteAllSubtask();

    void deleteTaskID(int id); // Методы по удалению определенных задач, эпиков, подзадач по ID

    void deleteEpicID(int id);

    void deleteSubtaskID(int id);

    void whatStatusEpic(Epic epic); // Проверка статуса эпика

    Task updateTask(Task task); // Методы по обновлению задач, подзадач, эпиков

    Epic updateEpic(Epic epic);

    Subtask updateSubtask(Subtask subtask);

    List<Task> getTasks(); // Методы для получения задач, эпиков, подзадач

    List<Epic> getEpics();

    List<Subtask> getSubtasks();

    List<Task> getHistory(); // Метод, возвращающий 10 последних просмотренных задач

    Task getTasksID(int id);

    Epic getEpicsID(int id);

    Subtask getSubtasksID(int id);

}