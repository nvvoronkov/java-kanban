package manager;

import java.io.File;

import manager.history.HistoryManager;
import manager.http.HTTPTaskManager;

public abstract class Manager { // Утилитарный класс, отвечающий за создание менеджера задач
    private static File file = new File("src/resources/file.csv");

    public static TaskManager getDefault() { // Метод, возвращающий объект-менеджер
        return new HTTPTaskManager(file, "http://localhost:8078");
    }

    public static HistoryManager getDefaultHistory() { // Метод, возвращающий объект— историю просмотров
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefaultFileManager() {
        return new FileBackedTasksManager(file);
    }
}