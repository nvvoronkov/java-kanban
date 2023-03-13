import static model.Status.NEW;

import manager.HistoryManager;
import manager.Manager;
import manager.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;

public class Main {
    public static void main(String[] args) {
        System.out.println(" ");
        System.out.println("******** Вас приветствуюет трекер задач ********");
        System.out.println("Поехали!");
        System.out.println("---------------------------------------------------------------------------");
        testCode(); // Тестирование задачи
        System.out.println("---------------------------------------------------------------------------");
    }

    private static void testCode() {
        HistoryManager inMemoryHistoryManager = Manager.getDefaultHistory();
        TaskManager manager = Manager.getDefault();

        Task taskOne = new Task("Задача №1", "Описание задачи №1", NEW);
        Task taskTwo = new Task("Задача №2", "Описание задачи №2", NEW);
        Epic epicOne = new Epic("Большая задача (эпик) №1", "Описание задачи №1", NEW);
        Subtask subtaskOne = new Subtask("Подзадача №2", "Описание подзадачи №2", NEW, 1);
        Subtask subtaskTwo = new Subtask("Подзадача №3", "Описание подзадачи №3", NEW, 1);
        Subtask subtaskThree = new Subtask("Подзадача №4", "Описание подзадачи №4", NEW, 1);
        Epic epicTwo = new Epic("Большая задача (эпик) №5", "Описание задачи №5", NEW);


        manager.addTask(taskOne);
        manager.addTask(taskTwo);
        manager.addEpic(epicOne);
        manager.addSubtask(subtaskOne);
        manager.addSubtask(subtaskTwo);
        manager.addSubtask(subtaskThree);
        manager.addEpic(epicTwo);

        inMemoryHistoryManager.add(taskOne);
        inMemoryHistoryManager.add(taskTwo);
        inMemoryHistoryManager.add(epicOne);
        inMemoryHistoryManager.add(epicTwo);
        System.out.println(inMemoryHistoryManager.getHistory());
        System.out.println(" ");
        inMemoryHistoryManager.add(epicOne);
        System.out.println(inMemoryHistoryManager.getHistory()); // После запросов выводим историю и смотрим, что в ней нет повторо
        System.out.println(" ");
        inMemoryHistoryManager.remove(7);
        System.out.println(inMemoryHistoryManager.getHistory());
        System.out.println(" ");
        manager.getTasksById(1);
        manager.getTasksById(2);
        manager.deleteTaskById(2);
        System.out.println(manager.getHistory());
        manager.getEpicsById(3);
        manager.getEpicsById(3);
        System.out.println(manager.getHistory());
        System.out.println(" ");
        manager.deleteAllTask();
        System.out.println(manager.getHistory());
    }
}