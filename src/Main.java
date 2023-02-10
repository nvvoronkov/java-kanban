import manager.Storage;
import model.Epic;
import model.Subtask;
import model.Task;
import static model.Status.NEW;

public class Main {
    public static void main(String[] args) {
        System.out.println("Поехали!");
        System.out.println("Добро пожаловать в Трекер задач!");
        testCode(); // Тестирование задачи
    }

    private static void testCode() { // Проверка работы приложения
        Storage storage = new Storage();
        Task taskOne = new Task("Задача №1", "Описание задачи №1", NEW);
        Task taskTwo = new Task("Задача №2", "Описание задачи №2", NEW);
        Epic epicOne = new Epic("Большая задача (эпик) №3", "Описание задачи №3", NEW);
        Subtask subtaskOne = new Subtask("Подзадача №4", "Описание подзадачи №4", NEW, 3);
        Subtask subtaskTwo = new Subtask("Подзадача №5", "Описание подзадачи №5", NEW, 3);
        Epic epicTwo = new Epic("Большая задача (эпик) №6", "Описание задачи №6", NEW);
        Subtask subtaskThree = new Subtask("Подзадача №7", "Описание подзадачи №7", NEW, 6);
        storage.addTask(taskOne); // Внесение всех задач, эпиков, подзадач
        storage.addTask(taskTwo);
        storage.addEpic(epicOne);
        storage.addSubtask(subtaskOne);
        storage.addSubtask(subtaskTwo);
        storage.addEpic(epicTwo);
        storage.addSubtask(subtaskThree);
        System.out.println(storage.getTasks()); // Печать задач, эпиков, подзадач
        System.out.println(storage.getEpics());
        System.out.println(storage.getSubtasks());
        storage.getListSubtaskEpic(6);
        System.out.println(storage.getEpics());
        storage.deleteTaskID(2); // Удаление определенных задач
        storage.deleteEpicID(3);
        storage.deleteSubtaskID(4);
        System.out.println(storage.getTasks()); // Проверка работы метода по удалению
        System.out.println(storage.getEpics());
        System.out.println(storage.getSubtasks());
        storage.deleteAllSubtask();
        System.out.println(storage.getTasks());
        System.out.println(storage.getSubtasks());
        System.out.println(storage.getEpics());
        storage.renewalTask(taskOne);
        System.out.println(storage.getTasks());
        storage.checkStatusEpic(6);
    }
}
