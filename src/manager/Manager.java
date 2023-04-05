package manager;

import java.io.File;

public abstract class Manager { // Утилитарный класс, отвечающий за создание менеджера задач
    private static File file = new File("src/model/file.csv");

    public static TaskManager getDefault() { // Метод, возвращающий объект-менеджер
        return new FileBackedTasksManager(file);
    }

    public static HistoryManager getDefaultHistory() { // Метод, возвращающий объект— историю просмотров
        return new InMemoryHistoryManager();
    }
}