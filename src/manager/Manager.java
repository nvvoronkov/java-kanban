package manager;

public abstract class Manager { // Утилитарный класс, отвечающий за создание менеджера задач
    private static final TaskManager taskManager = new InMemoryTaskManager();
    private static final HistoryManager historyManager = new InMemoryHistoryManager();

    public static TaskManager getDefault() { // Метод, возвращающий объект-менеджер
        return taskManager;
    }

    public static HistoryManager getDefaultHistory() { // Метод, возвращающий объект— историю просмотров
        return historyManager;
    }
}