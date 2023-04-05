package manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import model.Epic;
import model.ManagerSaveException;
import model.Status;
import model.Subtask;
import model.Task;
import model.Type;

import static model.Status.DONE;
import static model.Status.NEW;
import static model.Type.*;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private File file = new File("src/model/file.csv");

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public static void main(String[] args) {
        TaskManager manager = loadFromFile(new File("src/model/file.csv"));
        manager.addTask(new Task("Task1", "Description task1", NEW, TASK));
        manager.addEpic(new Epic("Epic2", "Description epic2", EPIC));
        manager.addSubtask(new Subtask("Subtask2", "Description subtask3", DONE, SUBTASK, 2));
        System.out.println(manager.getHistory());
    }

    @Override 
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public List<Subtask> getListSubtasksEpic(int id) {
        return super.getListSubtasksEpic(id);
    }

    @Override
    public void deleteAllTask() {
        super.deleteAllTask();
        save();
    }

    @Override
    public void deleteAllEpic() {
        super.deleteAllEpic();
        save();
    }

    @Override
    public void deleteAllSubtask() {
        super.deleteAllSubtask();
        save();
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void deleteSubtaskById(int id) {
        super.deleteSubtaskById(id);
        save();
    }

    @Override
    public Task updateTask(Task task) {
        return super.updateTask(task);
    }

    @Override
    public Epic updateEpic(Epic epic) {
        return super.updateEpic(epic);
    }

    @Override
    public Subtask updateSubtask(Subtask subtask) {
        return super.updateSubtask(subtask);
    }

    @Override
    public List<Task> getTasks() {
        return super.getTasks();
    }

    @Override
    public List<Epic> getEpics() {
        return super.getEpics();
    }

    @Override
    public List<Subtask> getSubtasks() {
        return super.getSubtasks();
    }

    @Override
    public Task getTasksById(int id) {
        return super.getTasksById(id);
    }

    @Override
    public Epic getEpicsById(int id) {
        return super.getEpicsById(id);
    }

    @Override
    public Subtask getSubtasksById(int id) {
        return super.getSubtasksById(id);
    }

    @Override
    public List<Task> getHistory() {
        return super.getHistory();
    }

    public void save() { 
        if (!file.exists()) {
            System.out.println("Данный файл не существует.");
            try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, 
                                                StandardCharsets.UTF_8))) {
                fileWriter.write("id,type,name,status,description,epic");
                for (Task task : getTasks()) {
                    fileWriter.write(toString(task));
                }
                for (Epic epic : getEpics()) {
                    fileWriter.write(toString(epic));
                }
                for (Subtask subtask : getSubtasks()) {
                    fileWriter.write(toString(subtask));
                }
                fileWriter.write(historyToString((HistoryManager) getHistory()));

            } catch (IOException exception) {
                throw new ManagerSaveException("Не удалось сохранить данные.", exception);
            }
        }
    }

    private String toString(Task task) {
        int id = task.getId();
        Type type = task.getType();
        String name = task.getName();
        Status status = task.getStatus();
        String description = task.getDescription();

        if (task.getType().equals(SUBTASK)) {
            int idEpic = ((Subtask) task).getIdEpic();
            return String.format("%s,%s,%s,%s,%s,%s,\n", id, type, name, status, description, idEpic);
        } else {
            return String.format("%s,%s,%s,%s,%s,\n", id, type, name, status, description);
        }
    }

    public static Task fromString(String value) {
        String[] split = value.split(","); 
        int id = Integer.parseInt(split[0]); 
        Type type = Type.valueOf(split[1]);
        String name = split[2];
        Status status = Status.valueOf(split[3]);
        String description = split[4];
        int idEpic = 0;
        if (split.length > 5) {
            idEpic = Integer.parseInt(split[5]);
        }
        Task task = null;

        switch (Type.valueOf(split[1])) {
            case TASK:
                task = new Task(description, name, status, type);
                task.setId(id);
                break;
            case EPIC:
                task = new Epic(description, name, type);
                task.setId(id);
                break;
            case SUBTASK:
                task = new Subtask(description, name, status, type, idEpic);
                task.setId(id);
                break;
        }
        return task;
        
    }

    private static String historyToString(HistoryManager manager) {
        StringBuilder history = new StringBuilder();
        for (Task task : manager.getHistory()) {
            history.append(task.getId()).append(",");
        }
        return history.toString();
    }

    static List<Integer> historyFromString(String value, FileBackedTasksManager manager) {
        List<Integer> history = new ArrayList<>();
        String[] items = value.split(",");
        for (int i = 0; i < items.length; i++) {
            int id = Integer.valueOf(items[0]);
            history.add(id);
        }
        return history;
    }
/*
 * Подскажите, пожалуйста, в чем моя ошибка
 * Я не очень понимаю какая логика должна быть между loadFromFile() и historyFromString()
 */
    public static FileBackedTasksManager loadFromFile(File file) throws ManagerSaveException {
        FileBackedTasksManager manager = new FileBackedTasksManager(file);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String taskFromString = br.readLine();
            while (!(taskFromString = br.readLine()).equals("")) {
                Task task = fromString(taskFromString);
                if (!taskFromString.isEmpty()) {
                    if (task != null) {
                        switch (task.getType()) {
                            case TASK:
                                manager.addTask(task);
                                break;
                            case EPIC:
                                manager.addEpic((Epic) task);
                                break;
                            case SUBTASK:
                                manager.addSubtask((Subtask) task);
                                break;
                        }
                    }
                } else {
                    historyFromString(taskFromString, manager);
                }
            }
        } catch (IOException exception) {
            throw new ManagerSaveException("Не удалось сохранить данные.", exception);
        }
        manager.save();
        return manager;
    } 
}