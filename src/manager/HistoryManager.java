package manager;

import model.Task;

import java.util.List;

public interface HistoryManager { // Интерфейс для управления историей просмотров
    void add(Task task); // Метод, который помечает задачи как просмотренные

    List<Task> getHistory(); // Метод, возвращающий 10 последних просмотренных задач
}