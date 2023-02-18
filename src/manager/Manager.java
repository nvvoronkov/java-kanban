package manager;

public class Manager { // Утилитарный класс, отвечающий за создание менеджера задач
    public static TaskManager getDefault() { // Метод, возвращающий объект-менеджер
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() { // Метод, возвращающий объект— историю просмотров
        return new InMemoryHistoryManager();
    }
}