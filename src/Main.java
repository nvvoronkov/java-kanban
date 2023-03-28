import static model.Status.NEW;

import manager.Manager;
import manager.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;
import model.Type;

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
        
        TaskManager taskManager = Manager.getDefault();
       
        // Task taskOne = new Task("Задача №1", "Описание задачи №1", NEW);
        // Task taskTwo = new Task("Задача №2", "Описание задачи №2", NEW);
        // Epic epicOne = new Epic("Большая задача (эпик) №1", "Описание задачи №1", NEW);
        // Subtask subtaskOne = new Subtask("Подзадача №2", "Описание подзадачи №2", NEW, 1);
        // Subtask subtaskTwo = new Subtask("Подзадача №3", "Описание подзадачи №3", NEW, 1);
        // Subtask subtaskThree = new Subtask("Подзадача №4", "Описание подзадачи №4", NEW, 1);
        // Epic epicTwo = new Epic("Большая задача (эпик) №5", "Описание задачи №5", NEW);

        taskManager.addTask(new Task("Задача №1", "Описание задачи №1", NEW, Type.TASK));
        taskManager.addTask(new Task("Задача №2", "Описание задачи №2", NEW, Type.TASK));
        taskManager.getTasksById(1);
        taskManager.getTasksById(2);
        taskManager.addEpic(new Epic("Большая задача (эпик) №1", "Описание задачи №1", Type.EPIC));
        taskManager.getEpicsById(3);
        taskManager.addSubtask(new Subtask("Подзадача №2", "Описание подзадачи №2", NEW, Type.SUBTASK, 3));
        taskManager.addSubtask(new Subtask("Подзадача №3", "Описание подзадачи №3", NEW, Type.SUBTASK, 3));
        taskManager.addSubtask(new Subtask("Подзадача №4", "Описание подзадачи №4", NEW, Type.SUBTASK, 3));
        taskManager.getSubtasksById(3);
        taskManager.getSubtasksById(3);
        taskManager.getSubtasksById(3);
        taskManager.addEpic(new Epic("Большая задача (эпик) №5", "Описание задачи №5", Type.EPIC));
        taskManager.getEpicsById(7);
    
        System.out.println(taskManager.getHistory());
        System.out.println(" ");
        taskManager.deleteEpicById(3);
        System.out.println(taskManager.getHistory());
        System.out.println(" ");
        taskManager.getTasksById(1);
        taskManager.getTasksById(2);
        taskManager.deleteTaskById(2);
        System.out.println(taskManager.getHistory());
        System.out.println(" ");
        taskManager.deleteAllTask();
        System.out.println(taskManager.getHistory());
    }
}