package tests;

import manager.TaskManager;
import model.*;
import model.constants.Status;
import model.constants.Type;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


abstract class TaskManagerTest<T extends TaskManager> {
    protected T manager;

    @Test
    public void shouldAddTask() { // Тесты по добавлению задач, эпиков, подзадач
        Task task = new Task("Test taskName", "Test Description task",
                             Status.NEW, Type.TASK, null, 0);
        manager.addTask(task);
        final Task savedTask = manager.getTasksById(task.getId());
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");
        assertEquals(Status.NEW, task.getStatus(), "У задачи неверный статус.");
        final List<Task> tasks = manager.getTasks();
        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    public void shouldAddEpic() {
        Epic epic = new Epic("Test epicName", "Test Description epic", Status.NEW);
        manager.addEpic(epic);
        final Task savedEpic = manager.getEpicsById(epic.getId());
        assertNotNull(savedEpic, "Эпик не найден.");
        assertEquals(epic, savedEpic, "Эпики не совпадают.");
        assertEquals(Status.NEW, epic.getStatus(), "У эпика неверный статус.");
        assertEquals(0, epic.getSubtasksById().size(), "У эпика неверное количество подзадач.");
        final List<Epic> epics = manager.getEpics();
        assertNotNull(epics, "Эпики не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество эпиков.");
        assertEquals(epic, epics.get(0), "Эпики не совпадают.");
    }

    @Test
    public void shouldAddSubtask() {
        Epic epic = new Epic("Test epicName", "Test Description epic", Status.NEW);
        manager.addEpic(epic);
        Subtask subtask = new Subtask("Test subtaskName", "Test Description subtask",
                                      Status.NEW, epic.getId());
        manager.addSubtask(subtask);
        manager.getSubtasks();
        manager.getEpics();
        final Task savedTask = manager.getSubtasksById(subtask.getId());
        assertNotNull(savedTask, "Подзадача не найдена.");
        assertEquals(subtask, savedTask, "Подзадачи не совпадают.");
        assertEquals(Status.NEW, subtask.getStatus(), "У подзадачи неверный статус.");
        assertEquals(epic.getId(), subtask.getIdEpic(), "У подзадачи неверный ID эпика.");
        final List<Subtask> subtasks = manager.getSubtasks();
        assertNotNull(subtasks, "Подзадачи не возвращаются.");
        assertEquals(1, subtasks.size(), "Неверное количество подзадач.");
        assertEquals(subtask, subtasks.get(0), "Подзадачи не совпадают.");
    }

    @Test
    public void shouldDeleteAllTask() {// Тесты по удалению всех задач, эпиков, подзадач
        Task firstTask = new Task("Test taskName", "Test Description task", Status.NEW);
        Task secondTask = new Task("Test taskName2", "Test Description task2", Status.NEW);
        manager.addTask(firstTask);
        manager.addTask(secondTask);
        assertNotNull(firstTask, "Первая задача не найдена.");
        assertNotNull(secondTask, "Вторая задача не найдена.");
        assertEquals(Status.NEW, firstTask.getStatus(), "У задачи неверный статус.");
        assertEquals(Status.NEW, secondTask.getStatus(), "У задачи неверный статус.");
        manager.deleteAllTask();
        assertEquals(0, manager.getTasks().size(), "Неверное количество задач.");
    }

    @Test
    public void shouldDeleteAllTaskEmpty() {
        assertEquals(0, manager.getTasks().size(), "Неверное количество задач.");
    }

    @Test
    public void shouldDeleteAllEpic() {
        Epic firstEpic = new Epic("Test taskName", "Test Description task", Status.NEW);
        Epic secondEpic = new Epic("Test taskName2", "Test Description task2", Status.NEW);
        manager.addEpic(firstEpic);
        manager.addTask(secondEpic);
        assertNotNull(firstEpic, "Первый эпик не найден.");
        assertNotNull(secondEpic, "Второй эпик не найден.");
        assertEquals(Status.NEW, firstEpic.getStatus(), "У эпика неверный статус.");
        assertEquals(Status.NEW, secondEpic.getStatus(), "У эпика неверный статус.");
        manager.deleteAllEpic();
        assertEquals(0, manager.getEpics().size(), "Неверное количество эпиков.");
    }

    @Test
    public void shouldDeleteAllEpicEmpty() {
        assertEquals(0, manager.getEpics().size(), "Неверное количество эпиков.");
    }

    @Test
    public void shouldDeleteAllSubtask() {
        Epic firstEpic = new Epic("Test taskName", "Test Description task", Status.NEW);
        Subtask firstSubtask = new Subtask("Test taskName", "Test Description task",
                                           Status.NEW, firstEpic.getId()); 
        Subtask secondSubtask = new Subtask("Test taskName2", "Test Description task2",
                                            Status.NEW, firstEpic.getId());
        manager.addSubtask(firstSubtask);
        manager.addSubtask(secondSubtask);
        manager.addEpic(firstEpic);
        assertNotNull(firstSubtask, "Первая подзадача не найдена.");
        assertNotNull(secondSubtask, "Вторая подзадача не найдена.");
        assertNotNull(firstEpic, "Эпик, к которому относятся подзадачи, не найден.");
        assertEquals(Status.NEW, firstSubtask.getStatus(), "У подзадачи неверный статус.");
        assertEquals(Status.NEW, secondSubtask.getStatus(), "У подзадачи неверный статус.");
        manager.deleteAllSubtask();
        assertEquals(Status.NEW, firstEpic.getStatus(), "У эпика неверный статус.");
        assertEquals(0, manager.getSubtasks().size(), "Неверное количество подзадач.");
    }

    @Test
    public void shouldDeleteAllSubtasksEmpty() {
        assertEquals(0, manager.getSubtasks().size(), "Неверное количество подзадач.");
    }

    @Test
    public void shouldDeleteTaskId() { // Тесты по удалению определенных задач, эпиков, подзадач по ID
        Task firstTask = new Task("Test taskName", "Test Description task", Status.NEW);
        manager.addTask(firstTask);
        assertNotNull(firstTask, "Первая задача не найдена.");
        assertEquals(Status.NEW, firstTask.getStatus(), "У задачи неверный статус.");
        assertEquals(1, manager.getTasks().size(), "Неверное количество задач.");
        manager.deleteEpicById(firstTask.getId());
        assertEquals(0, manager.getTasks().size(), "Неверное количество задач.");
    }

    @Test
    public void shouldDeleteTaskIdUnknown() {
        final int id = 1;
        manager.deleteEpicById(id);
        assertEquals(0, manager.getTasks().size(), "Неверное количество задач.");
    }

    @Test
    public void shouldDeleteEpicId() {
        Epic firstEpic = new Epic("Test taskName", "Test Description task", Status.NEW);
        manager.addEpic(firstEpic);
        assertNotNull(firstEpic, "Первый эпик не найден.");
        assertEquals(Status.NEW, firstEpic.getStatus(), "У эпика неверный статус.");
        assertEquals(1, manager.getEpics().size(), "Неверное количество задач.");
        manager.deleteEpicById(firstEpic.getId());
        assertEquals(0, manager.getEpics().size(), "Неверное количество эпиков.");
    }

    @Test
    public void shouldDeleteEpicIdUnknown() {
        final int id = 1;
        manager.deleteEpicById(id);
        assertEquals(0, manager.getEpics().size(), "Неверное количество эпиков.");
    }

    @Test
    public void shouldDeleteSubtaskId() {
        Epic firstEpic = new Epic("Test taskName", "Test Description task", Status.NEW);
        Subtask firstSubtask = new Subtask("Test taskName", "Test Description task",
                                           Status.NEW, firstEpic.getId());
        manager.addSubtask(firstSubtask);
        manager.addEpic(firstEpic);
        manager.getSubtasks();
        assertNotNull(firstSubtask, "Первая подзадача не найдена.");
        assertNotNull(firstEpic, "Эпик, к которому относятся подзадачи, не найден.");
        assertEquals(Status.NEW, firstSubtask.getStatus(), "У подзадачи неверный статус.");
        assertEquals(Status.NEW, firstEpic.getStatus(), "У эпика неверный статус.");
        assertEquals(1, manager.getSubtasks().size(), "Неверное количество задач.");
        manager.deleteEpicById(firstSubtask.getId());
        assertEquals(0, manager.getSubtasks().size(), "Неверное количество подзадач.");
    }

    @Test
    public void shouldDeleteSubtaskIdUnknown() {
        final int id = 1;
        manager.deleteSubtaskById(id);
        assertEquals(0, manager.getSubtasks().size(), "Неверное количество подзадач.");
    }

    @Test
    public void shouldCheckStatusEpicEmpty() {// Тест по проверке статуса эпика, если пуст
        Epic firstEpic = new Epic("Test taskName", "Test Description task", Status.NEW);
        manager.addEpic(firstEpic);
        assertEquals(Status.NEW, firstEpic.getStatus(), "Неверный статус эпика.");
    }

    @Test
    public void shouldCheckStatusEpicNewSubtask() {// Тест по проверке статуса эпика, если подзадачи со статусом NEW
        Epic firstEpic = new Epic("Test taskName", "Test Description task", Status.NEW);
        Subtask firstSubtask = new Subtask("Test taskName", "Test Description task",
                                           Status.NEW, firstEpic.getId());
        Subtask secondSubtask = new Subtask("Test taskName2", "Test Description task2",
                                            Status.NEW, firstEpic.getId());
        manager.addEpic(firstEpic);
        manager.addSubtask(firstSubtask);
        manager.addSubtask(secondSubtask);
        assertEquals(Status.NEW, firstEpic.getStatus(), "Неверный статус эпика.");
    }

    @Test
    public void shouldCheckStatusEpicDoneSubtask() {// Тест по проверке статуса эпика, если подзадачи со статусом Done
        Epic firstEpic = new Epic("Test taskName", "Test Description task", Status.NEW);
        Subtask firstSubtask = new Subtask("Test taskName", "Test Description task",
                                           Status.NEW, firstEpic.getId());
        Subtask secondSubtask = new Subtask("Test taskName2", "Test Description task2",
                                            Status.NEW, firstEpic.getId());
        manager.addEpic(firstEpic);
        manager.addSubtask(firstSubtask);
        manager.addSubtask(secondSubtask);
        firstSubtask.setStatus(Status.DONE);
        secondSubtask.setStatus(Status.DONE);
        firstEpic.setStatus(Status.DONE);
        manager.updateSubtask(firstSubtask);
        manager.updateSubtask(secondSubtask);
        assertEquals(Status.DONE, firstEpic.getStatus(), "Неверный статус эпика.");
    }

    @Test
    public void shouldCheckStatusEpicDoneNewSubtask() {// Тест по проверке статуса эпика, если подзадачи со статусом Done, New
        Epic firstEpic = new Epic("Test taskName", "Test Description task", Status.NEW);
        Subtask firstSubtask = new Subtask("Test taskName", "Test Description task",
                                           Status.NEW, firstEpic.getId());
        Subtask secondSubtask = new Subtask("Test taskName2", "Test Description task2",
                                            Status.NEW, firstEpic.getId());
        manager.addEpic(firstEpic);
        manager.addSubtask(firstSubtask);
        manager.addSubtask(secondSubtask);
        firstSubtask.setStatus(Status.DONE);
        firstEpic.setStatus(Status.IN_PROGRESS);
        manager.updateSubtask(firstSubtask);
        manager.updateSubtask(secondSubtask);
        assertEquals(Status.IN_PROGRESS, firstEpic.getStatus(), "Неверный статус эпика.");
    }

    @Test
    public void shouldCheckStatusEpicProgressSubtask() {// Тест по проверке статуса эпика, если подзадачи со статусом In Progress
        Epic firstEpic = new Epic("Test taskName", "Test Description task", Status.NEW);
        Subtask firstSubtask = new Subtask("Test taskName", "Test Description task",
                                          Status.NEW, firstEpic.getId());
        Subtask secondSubtask = new Subtask("Test taskName2", "Test Description task2",
                                            Status.NEW, firstEpic.getId());
        manager.addEpic(firstEpic);
        manager.addSubtask(firstSubtask);
        manager.addSubtask(secondSubtask);
        firstSubtask.setStatus(Status.IN_PROGRESS);
        secondSubtask.setStatus(Status.IN_PROGRESS);
        firstEpic.setStatus(Status.IN_PROGRESS);
        manager.updateSubtask(firstSubtask);
        manager.updateSubtask(secondSubtask);
        assertEquals(Status.IN_PROGRESS, firstEpic.getStatus(), "Неверный статус эпика.");
    }

    @Test
    public void shouldGetListSubtaskEpic() {
        Epic firstEpic = new Epic("Test taskName", "Test Description task", Status.NEW);
        Subtask firstSubtask = new Subtask("Test taskName", "Test Description task",
                                           Status.NEW, firstEpic.getId());
        Subtask secondSubtask = new Subtask("Test taskName2", "Test Description task2",
                                            Status.NEW, firstEpic.getId());
        manager.addEpic(firstEpic);
        manager.addSubtask(firstSubtask);
        manager.addSubtask(secondSubtask);
        List<Subtask> subtaskList = new ArrayList<>();
        subtaskList.add(firstSubtask);
        subtaskList.add(secondSubtask);
        assertArrayEquals(subtaskList.toArray(), manager.getEpics().toArray());
    }

    @Test
    public void shouldUpdateTask() {// Тесты по обновлению задач, подзадач, эпиков
        Task firstTask = new Task("Test taskName", "Test Description task", Status.NEW);
        manager.addTask(firstTask);
        assertNotNull(firstTask, "Задача не найдена.");
        assertEquals(Status.NEW, firstTask.getStatus(), "У задачи неверный статус.");
        manager.updateTask(firstTask);
        assertEquals(firstTask, manager.getTasksById(firstTask.getId()), "Задача не обновлена.");
    }

    @Test
    public void shouldUpdateTaskUnknown() {
        Task firstTask = new Task("Test taskName", "Test Description task", Status.NEW);
        manager.updateTask(firstTask);
        assertNull(manager.getTasksById(firstTask.getId()), "Выбран неверный ID.");
    }

    @Test
    public void shouldUpdateEpic() {
        Epic firstEpic = new Epic("Test taskName", "Test Description task", Status.NEW);
        manager.addEpic(firstEpic);
        assertNotNull(firstEpic, "Эпик не найден.");
        assertEquals(Status.NEW, firstEpic.getStatus(), "У эпика неверный статус.");
        manager.updateEpic(firstEpic);
        assertEquals(firstEpic, manager.getEpicsById(firstEpic.getId()), "Эпик не обновлен.");
    }

    @Test
    public void shouldUpdateEpicUnknown() {
        Epic firstTask = new Epic("Test taskName", "Test Description task", Status.NEW);
        manager.updateEpic(firstTask);
        assertNull(manager.getEpicsById(firstTask.getId()), "Выбран неверный ID.");
    }

    @Test
    public void shouldUpdateSubtask() {
        Epic firstTask = new Epic("Test taskName", "Test Description task", Status.NEW);
        Subtask firstSubtask = new Subtask("Test taskName", "Test Description task",
                                           Status.NEW, firstTask.getId());
        manager.addSubtask(firstSubtask);
        assertNotNull(firstSubtask, "Подзадача не найдена.");
        assertEquals(Status.NEW, firstSubtask.getStatus(), "У подзадачи неверный статус.");
        manager.updateSubtask(firstSubtask);
        assertEquals(firstSubtask, manager.getSubtasksById(firstSubtask.getId()), "Подзадача не обновлена.");
    }

    @Test
    public void shouldUpdateSubtaskUnknown() {
        Epic firstTask = new Epic("Test taskName", "Test Description task", Status.NEW);
        Subtask firstSubtask = new Subtask("Test taskName", "Test Description task",
                                           Status.NEW, firstTask.getId());
        manager.updateSubtask(firstSubtask);
        assertNull(manager.getSubtasksById(firstSubtask.getId()), "Выбран неверный ID.");
    }

    @Test
    public void shouldGetTasks() { // Тесты для получения задач, эпиков, подзадач
        Task firstTask = new Task("Test taskName", "Test Description task", Status.NEW);
        Task secondTask = new Task("Test taskName2", "Test Description task2", Status.NEW);
        manager.addTask(firstTask);
        manager.addTask(secondTask);
        assertNotNull(firstTask, "Первая задача не найдена.");
        assertNotNull(secondTask, "Вторая задача не найдена.");
        assertEquals(Status.NEW, firstTask.getStatus(), "У задачи неверный статус.");
        assertEquals(Status.NEW, secondTask.getStatus(), "У задачи неверный статус.");
        final List<Task> tasks = manager.getTasks();
        assertEquals(2, tasks.size(), "Неверное количество задач.");
    }

    @Test
    public void shouldGetTasksEmpty() {
        assertEquals(0, manager.getTasks().size(), "Неверное количество задач.");
    }

    @Test
    public void shouldGetEpics() {
        Epic firstEpic = new Epic("Test taskName", "Test Description task", Status.NEW);
        Epic secondEpic = new Epic("Test taskName2", "Test Description task2", Status.NEW);
        manager.addEpic(firstEpic);
        manager.addTask(secondEpic);
        assertNotNull(firstEpic, "Первый эпик не найден.");
        assertNotNull(secondEpic, "Второй эпик не найден.");
        assertEquals(Status.NEW, firstEpic.getStatus(), "У эпика неверный статус.");
        assertEquals(Status.NEW, secondEpic.getStatus(), "У эпика неверный статус.");
        final List<Epic> epics = manager.getEpics();
        assertEquals(2, epics.size(), "Неверное количество эпиков.");
    }

    @Test
    public void shouldGetEpicsEmpty() {
        assertEquals(0, manager.getEpics().size(), "Неверное количество эпиков.");
    }

    @Test
    public void shouldGetSubtasks() {
        Epic firstEpic = new Epic("Test taskName", "Test Description task", Status.NEW);
        Subtask firstSubtask = new Subtask("Test taskName", "Test Description task",
                                           Status.NEW, firstEpic.getId());
        Subtask secondSubtask = new Subtask("Test taskName2", "Test Description task2",
                                            Status.NEW, firstEpic.getId());
        manager.addSubtask(firstSubtask);
        manager.addSubtask(secondSubtask);
        assertNotNull(firstSubtask, "Первая подзадача не найдена.");
        assertNotNull(secondSubtask, "Вторая подзадача не найдена.");
        assertNotNull(firstEpic, "Эпик, к которому относятся подзадачи, не найден.");
        assertEquals(Status.NEW, firstSubtask.getStatus(), "У подзадачи неверный статус.");
        assertEquals(Status.NEW, secondSubtask.getStatus(), "У подзадачи неверный статус.");
        assertEquals(Status.NEW, firstEpic.getStatus(), "У эпика неверный статус.");
        final List<Subtask> subtasks = manager.getSubtasks();
        assertEquals(2, subtasks.size(), "Неверное количество подзадач.");
    }

    @Test
    public void shouldGetSubtasksEmpty() {
        assertEquals(0, manager.getSubtasks().size(), "Неверное количество подзадач.");
    }

    @Test
    public void shouldGetHistory() { // Тест, возвращающий 10 последних просмотренных задач
        Task firstTask = new Task("Test taskName", "Test Description task", Status.NEW);
        Epic firstEpic = new Epic("Test taskName", "Test Description task", Status.NEW);
        Subtask firstSubtask = new Subtask("Test taskName", "Test Description task",
                                           Status.NEW, firstEpic.getId());
        manager.addTask(firstTask);
        manager.addEpic(firstEpic);
        manager.addSubtask(firstSubtask);
        assertNotNull(firstTask, "Задача не найдена.");
        assertEquals(Status.NEW, firstTask.getStatus(), "У задачи неверный статус.");
        assertNotNull(firstEpic, "Эпик не найден.");
        assertEquals(Status.NEW, firstEpic.getStatus(), "У эпика неверный статус.");
        assertNotNull(firstSubtask, "Подзадача не найдена.");
        assertEquals(Status.NEW, firstSubtask.getStatus(), "У подзадачи неверный статус.");
        manager.getTasksById(firstTask.getId());
        final List<Task> history = manager.getHistory();
        assertEquals(1, history.size(), "История отображается неверно.");
    }

    @Test
    public void shouldGetHistoryEmpty() {
        assertTrue(manager.getHistory().isEmpty(), "История заполнена.");
    }

    @Test
    public void shouldGetTasksId() {
        Task firstTask = new Task("Test taskName", "Test Description task", Status.NEW);
        manager.addTask(firstTask);
        assertNotNull(firstTask, "Задача не найдена.");
        assertEquals(Status.NEW, firstTask.getStatus(), "У задачи неверный статус.");
        final int id = firstTask.getId();
        assertEquals(firstTask, manager.getTasksById(id), "Задачи не совпадают.");
    }

    @Test
    public void shouldGetTasksIdUnknown() {
        assertNull(manager.getTasksById(1), "Выбран неверный ID.");
    }

    @Test
    public void shouldGetEpicsId() {
        Epic firstEpic = new Epic("Test taskName", "Test Description task", Status.NEW);
        manager.addEpic(firstEpic);
        assertNotNull(firstEpic, "Эпик не найден.");
        assertEquals(Status.NEW, firstEpic.getStatus(), "У эпика неверный статус.");
        final int id = firstEpic.getId();
        assertEquals(firstEpic, manager.getEpicsById(id), "Эпики не совпадают.");
    }

    @Test
    public void shouldGetEpicIdUnknown() {
        assertNull(manager.getEpicsById(1), "Выбран неверный ID.");
    }

    @Test
    public void shouldGetSubtasksId() {
        Epic firstEpic = new Epic("Test taskName", "Test Description task", Status.NEW);
        Subtask firstSubtask = new Subtask("Test taskName", "Test Description task",
                                           Status.NEW, firstEpic.getId());
        manager.addSubtask(firstSubtask);
        assertNotNull(firstSubtask, "Подзадача не найдена.");
        assertNotNull(firstEpic, "Эпик, к которому относятся подзадачи, не найден.");
        assertEquals(Status.NEW, firstEpic.getStatus(), "У эпика неверный статус.");
        assertEquals(Status.NEW, firstSubtask.getStatus(), "У подзадачи неверный статус.");
        final int id = firstSubtask.getId();
        assertEquals(firstSubtask, manager.getSubtasksById(id), "Подзадачи не совпадают.");
    }

    @Test
    public void shouldGetSubtasksIdUnknown() {
        assertNull(manager.getSubtasksById(1), "Выбран неверный ID.");
    }
}