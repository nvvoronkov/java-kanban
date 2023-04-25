package manager;

import java.util.List;

import manager.history.HistoryManager;
import model.Task;

public class InMemoryHistoryManager implements HistoryManager { /* Класс для работы с историей,
    перенос функционала из InMemoryTaskManager, реализует интерфейс HistoryManager */
    private final CustomLinkedList<Task> list = new CustomLinkedList<>();

    @Override
    public void add(Task task) {
        if (task != null) {
            list.removeById(task.getId());
            list.linkLast(task);
        }
    }

    @Override
    public List<Task> getHistory() { // Метод, возвращающий 10 последних просмотренных задач списком
        return list.getTasks();
    }

    @Override
    public void remove(int id) {
        list.removeById(id);
    }
}