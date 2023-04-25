package tests;

import manager.FileBackedTasksManager;
import model.*;
import model.constants.Status;
import model.constants.Type;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    private final File file = new File("src/resources/file.csv");

    @BeforeEach
    public void beforeEach() {
        Task firstTask = new Task("Test taskName", "Test Description task", Status.NEW, Type.TASK,
                                 LocalDateTime.of(2023, 04, 12, 22, 32), 30);
        Epic firstEpic = new Epic("Test taskName", "Test Description task", Status.NEW);
        Subtask firstSubtask = new Subtask("Test taskName", "Test Description task", Status.NEW,
                                          Type.SUBTASK, LocalDateTime.of(2023, 04, 12, 20, 35),
                                    20, firstEpic.getId());
        manager.addTask(firstTask);
        manager.addEpic(firstEpic);
        manager.addSubtask(firstSubtask);
        manager.getTasks();
        manager.getEpics();
        manager.getSubtasks();
    }

    @AfterEach
    public void clear() {
        manager.deleteAllTask();
        manager.deleteAllEpic();
        manager.deleteAllSubtask();
        manager.getPrioritizedTasks().clear();
    }

    @Test
    public void shouldSaveEmptyList() {
        clear();
        FileBackedTasksManager loadedManager = FileBackedTasksManager.loadFromFile(file);
        assertTrue(loadedManager.getTasks().isEmpty(), "Неверное количество задач.");
        assertTrue(loadedManager.getEpics().isEmpty(), "Неверное количество эпиков.");
        assertTrue(loadedManager.getSubtasks().isEmpty(), "Неверное количество подзадач.");
    }

    @Test
    public void shouldSaveEmptyEpic() { // Эпик без подзадач
        manager.deleteSubtaskById(3);
        assertTrue(manager.getListSubtasksEpic(2).isEmpty(), "Эпик содержит задачи.");
        FileBackedTasksManager loadedManager = FileBackedTasksManager.loadFromFile(file);
        assertFalse(loadedManager.getTasks().isEmpty(), "Неверное количество задач.");
        assertFalse(loadedManager.getEpics().isEmpty(), "Неверное количество эпиков.");
        assertTrue(loadedManager.getSubtasks().isEmpty(), "Неверное количество подзадач.");
        assertEquals(loadedManager.getTasks(), manager.getTasks(), "Менеджеры не совпадают.");
        assertEquals(loadedManager.getSubtasks(), manager.getSubtasks(), "Менеджеры не совпадают.");
        assertEquals(loadedManager.getEpics(), manager.getEpics(), "Менеджеры не совпадают.");
    }

    @Test
    public void shouldSaveEmptyHistory() {
        FileBackedTasksManager loadedManager = FileBackedTasksManager.loadFromFile(file);
        assertTrue(loadedManager.getHistory().isEmpty(), "История заполнена.");
    }
}