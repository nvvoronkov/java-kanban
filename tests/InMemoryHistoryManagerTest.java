package tests;

import manager.*;
import manager.history.HistoryManager;
import manager.history.InMemoryHistoryManager;
import model.*;
import model.constants.Status;
import model.constants.Type;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHistoryManagerTest {
    TaskManager taskManager;
    private HistoryManager manager; //инкапсулировала

    @BeforeEach
    public void beforeEach() {
        taskManager = new InMemoryTaskManager();
        manager = new InMemoryHistoryManager();
        Task firstTask = new Task("Test taskName", "Test Description task", Status.NEW, 
                                  Type.TASK, LocalDateTime.of(2023, 04, 12, 22, 32),
                                  30);
        Epic firstEpic = new Epic("Test taskName", "Test Description task", Status.NEW);
        Subtask firstSubtask = new Subtask("Test taskName", "Test Description task", Status.NEW,
                                          Type.SUBTASK, LocalDateTime.of(2023, 04, 12, 20,
                                           35), 20, firstEpic.getId());
        taskManager.addTask(firstTask);
        taskManager.addEpic(firstEpic);
        taskManager.addSubtask(firstSubtask);
        taskManager.getTasks();
        taskManager.getEpics();
        taskManager.getSubtasks();
    }

    @AfterEach
    public void clear() {
        taskManager.deleteAllTask();
        taskManager.deleteAllEpic();
        taskManager.deleteAllSubtask();
        manager.getHistory().clear();
        taskManager.getPrioritizedTasks().clear();
    }

    @Test
    public void shouldGetHistory() {
        final List<Task> history = manager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(3, history.size(), "История не пустая.");
    }

    @Test
    public void shouldGetHistoryEmpty() { // Метод должен перекладывать задачи из связного списка в AL для формирования ответа
        clear();
        Task task = new Task("Test taskName", "Test Description task", 
                            Status.NEW, Type.TASK, LocalDateTime.of(2023, 04, 12, 
                            22, 32), 30);
        taskManager.addTask(task);
        manager.add(task);
        manager.getHistory().size();
        assertFalse(manager.getHistory().isEmpty(), "История заполнена");
        assertEquals(taskManager.getTasksById(1), manager.getHistory().get(1));
        assertEquals(task, manager.getHistory().get(task.getId()));
    }

    @Test
    public void shouldGetHistoryDuplicate() {
        Task task = new Task("Test taskName", "Test Description task", 
                            Status.NEW, Type.TASK, LocalDateTime.of(2023, 04, 12,
                            22, 32), 30);
        taskManager.addTask(task);
        manager.add(task);
        int size = manager.getHistory().size();
        manager.add(task);
        int size1 = manager.getHistory().size();
        assertEquals(size, size1, "Количество заполненных задач равно.");
        assertFalse(manager.getHistory().isEmpty(), "История заполнена");
    }

    @Test
    public void shouldGRemoveEmptyHistory() {
        clear();
        manager.remove(1);
        assertTrue(manager.getHistory().isEmpty(), "История пуста");
    }

    @Test
    public void shouldGRemoveDuplicateHistory() {
        manager.remove(1);
        manager.remove(1);
        assertEquals(2, manager.getHistory().size(), "История заполнена верно.");
    }

    @Test
    public void shouldGRemoveFirst() {
        manager.remove(1);
        assertEquals(2, manager.getHistory().size(), "История заполнена верно.");
        assertFalse(manager.getHistory().isEmpty(), "История заполнена");
    }

    @Test
    public void shouldGRemoveBetween() {
        manager.remove(2);
        assertEquals(2, manager.getHistory().size(), "История заполнена верно.");
        assertFalse(manager.getHistory().isEmpty(), "История заполнена");
    }

    @Test
    public void shouldGRemoveLast() {
        manager.remove(3);
        assertEquals(2, manager.getHistory().size(), "История заполнена верно.");
        assertFalse(manager.getHistory().isEmpty(), "История заполнена");
    }

    @Test
    public void shouldEmptyHistory() {
        clear();
        final List<Task> history = manager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertTrue(manager.getHistory().isEmpty(), "История пустая.");
    }
}