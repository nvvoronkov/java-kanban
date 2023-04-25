import java.time.LocalDateTime;
import java.util.stream.Collectors;

import model.Epic;
import model.Subtask;
import model.Task;
import model.constants.Status;
import model.constants.Type;
import manager.InMemoryTaskManager;

public class MainOld {
    public static void mainOld(String[] args) {
        System.out.println(" ");
        System.out.println("******** Вас приветствуюет трекер задач ********");
        System.out.println("Поехали!");
        System.out.println("---------------------------------------------------------------------------");
        testCode();
        System.out.println("---------------------------------------------------------------------------");
    }

    private static void testCode() {

        InMemoryTaskManager manager = new InMemoryTaskManager();
        Epic epic = new Epic("новый эпик 1", "описание эпика 1", Status.NEW);
        manager.addEpic(epic);
//Данная задача должна быть 4 по счёту
        Subtask subtask1 = new Subtask("новая подзадача 1", "описание подзадачи 1", Status.NEW, Type.SUBTASK,  LocalDateTime.of(2022, 12, 30, 0, 30).plusDays(2), 15, epic.getId());
        manager.addSubtask(subtask1);
//Данная задача должна быть 1 по счёту
        Subtask subtask2 = new Subtask("новая подзадача 2", "описание подзадачи 2", Status.NEW, Type.SUBTASK, LocalDateTime.of(2022, 12, 30, 0, 30), 30, epic.getId());
        manager.addSubtask(subtask2);
//Данная задача должна быть 3 по счёту
        Subtask subtask3 = new Subtask( "новая подзадача 3", "описание подзадачи 3", Status.NEW, Type.SUBTASK, LocalDateTime.of(2022, 12, 30, 0, 30).plusDays(1), 45, epic.getId());
        manager.addSubtask(subtask3);
//Данная задача должна быть 2 по счёту
        Subtask subtask4 = new Subtask( "новая подзадача 4", "описание подзадачи 4",  Status.NEW, Type.SUBTASK, LocalDateTime.of(2022, 12, 30, 0, 30).plusHours(12), 60, epic.getId());
        manager.addSubtask(subtask4);
//Порядок добавления следующий subtask1 -> subtask2 -> subtask3 -> subtask4
//Порядок следующий subtask2 -> subtask4 -> subtask3 -> subtask1
        System.out.println(epic.getDuration() + " == " + (subtask1.getDuration()+(subtask2.getDuration()+(subtask3.getDuration()))+(subtask4.getDuration()) + " " + (epic.getDuration()==(subtask1.getDuration()+((subtask2.getDuration()+((subtask3.getDuration())+(subtask4.getDuration()))))))));
        System.out.println(epic.getStartTime() + " == " + subtask2.getStartTime() + " " + epic.getStartTime().equals(subtask2.getStartTime()));
        System.out.println(epic.getEndTime() + " == " + subtask1.getEndTime() + " " + epic.getEndTime().equals(subtask1.getEndTime()));
        System.out.println(manager.getPrioritizedTasks().stream().map(Task::getName).collect(Collectors.toList()));
//Удаление подзадачи subtask3
        manager.deleteSubtaskById(subtask3.getId());
//Новый порядок subtask2 -> subtask4 -> subtask1
        System.out.println(epic.getDuration() + " == " + (subtask1.getDuration()+(subtask2.getDuration()+(subtask4.getDuration())) + " " + (epic.getDuration()==(subtask1.getDuration()+((subtask2.getDuration()))+((subtask4.getDuration()))))));
        System.out.println(epic.getStartTime() + " == " + subtask2.getStartTime() + " " + epic.getStartTime().equals(subtask2.getStartTime()));
        System.out.println(epic.getEndTime() + " == " + subtask1.getEndTime() + " " + epic.getEndTime().equals(subtask1.getEndTime()));
        System.out.println(manager.getPrioritizedTasks().stream().map(Task::getName).collect(Collectors.toList()));
//Удаление подзадачи subtask2
        manager.deleteSubtaskById(subtask2.getId());
//Новый порядок subtask4 -> subtask1
        System.out.println(epic.getDuration() + " == " + (subtask1.getDuration()+(subtask4.getDuration())  + " " + (epic.getDuration()==(subtask1.getDuration()+(subtask4.getDuration())))));
        System.out.println(epic.getStartTime() + " == " + subtask4.getStartTime() + " " + epic.getStartTime().equals(subtask4.getStartTime()));
        System.out.println(epic.getEndTime() + " == " + subtask1.getEndTime() + " " + epic.getEndTime().equals(subtask1.getEndTime()));
        System.out.println(manager.getPrioritizedTasks().stream().map(Task::getName).collect(Collectors.toList()));
//Удаление подзадачи subtask1
        manager.deleteSubtaskById(subtask1.getId());
//Новый порядок subtask4
        System.out.println(epic.getDuration() + " == " + subtask4.getDuration()  + " " + (epic.getDuration() == (subtask4.getDuration())));
        System.out.println(epic.getStartTime() + " == " + subtask4.getStartTime() + " " + epic.getStartTime().equals(subtask4.getStartTime()));
        System.out.println(epic.getEndTime() + " == " + subtask4.getEndTime() + " " + epic.getEndTime().equals(subtask4.getEndTime()));
        System.out.println(manager.getPrioritizedTasks().stream().map(Task::getName).collect(Collectors.toList()));
        
        //TaskManager taskManager = Manager.getDefault();
       
        // Task taskOne = new Task("Задача №1", "Описание задачи №1", NEW);
        // Task taskTwo = new Task("Задача №2", "Описание задачи №2", NEW);
        // Epic epicOne = new Epic("Большая задача (эпик) №1", "Описание задачи №1", NEW);
        // Subtask subtaskOne = new Subtask("Подзадача №2", "Описание подзадачи №2", NEW, 1);
        // Subtask subtaskTwo = new Subtask("Подзадача №3", "Описание подзадачи №3", NEW, 1);
        // Subtask subtaskThree = new Subtask("Подзадача №4", "Описание подзадачи №4", NEW, 1);
        // Epic epicTwo = new Epic("Большая задача (эпик) №5", "Описание задачи №5", NEW);

        /* taskManager.addTask(new Task("Задача №1", "Описание задачи №1", NEW, Type.TASK));
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
        System.out.println(taskManager.getHistory()); */
    }
}