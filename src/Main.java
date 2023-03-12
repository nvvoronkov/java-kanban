import static model.Status.NEW;

import manager.Manager;
import manager.TaskManager;
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

        TaskManager manager = Manager.getDefault();
        manager.addTask(new Task("1", "1", NEW));
        manager.addTask(new Task("2", "2", NEW));
        manager.addTask(new Task("3", "3", NEW));
        manager.getTasksById(1);
        manager.getTasksById(2);
        manager.getTasksById(3);
        manager.deleteTaskById(2);
        System.out.println(manager.getHistory());
        manager.deleteAllTask();
        System.out.println(manager.getHistory());
    }
}