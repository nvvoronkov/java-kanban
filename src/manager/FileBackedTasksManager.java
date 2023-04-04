package manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    public void addEpic(Epic epic) {
        // TODO Auto-generated method stub
        super.addEpic(epic);
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public void addTask(Task task) {
        // TODO Auto-generated method stub
        super.addTask(task);
    }

    @Override
    public void deleteAllEpic() {
        // TODO Auto-generated method stub
        super.deleteAllEpic();
    }

    @Override
    public void deleteAllSubtask() {
        // TODO Auto-generated method stub
        super.deleteAllSubtask();
    }

    @Override
    public void deleteAllTask() {
        // TODO Auto-generated method stub
        super.deleteAllTask();
    }

    @Override
    public void deleteEpicById(int id) {
        // TODO Auto-generated method stub
        super.deleteEpicById(id);
    }

    @Override
    public void deleteSubtaskById(int id) {
        // TODO Auto-generated method stub
        super.deleteSubtaskById(id);
    }

    @Override
    public void deleteTaskById(int id) {
        // TODO Auto-generated method stub
        super.deleteTaskById(id);
    }

    @Override
    public List<Epic> getEpics() {
        // TODO Auto-generated method stub
        return super.getEpics();
    }

    @Override
    public Epic getEpicsById(int id) {
        // TODO Auto-generated method stub
        return super.getEpicsById(id);
    }

    @Override
    public List<Task> getHistory() {
        // TODO Auto-generated method stub
        return super.getHistory();
    }

    @Override
    public List<Subtask> getListSubtasksEpic(int id) {
        // TODO Auto-generated method stub
        return super.getListSubtasksEpic(id);
    }

    @Override
    public List<Subtask> getSubtasks() {
        // TODO Auto-generated method stub
        return super.getSubtasks();
    }

    @Override
    public Subtask getSubtasksById(int id) {
        // TODO Auto-generated method stub
        return super.getSubtasksById(id);
    }

    @Override
    public List<Task> getTasks() {
        // TODO Auto-generated method stub
        return super.getTasks();
    }

    @Override
    public Task getTasksById(int id) {
        // TODO Auto-generated method stub
        return super.getTasksById(id);
    }

    @Override
    public Epic updateEpic(Epic epic) {
        // TODO Auto-generated method stub
        return super.updateEpic(epic);
    }

    @Override
    public Subtask updateSubtask(Subtask subtask) {
        // TODO Auto-generated method stub
        return super.updateSubtask(subtask);
    }

    @Override
    public Task updateTask(Task task) {
        // TODO Auto-generated method stub
        return super.updateTask(task);
    }

    public void save() { 
        if (!file.exists()) {
            System.out.println("Данный файл не существует.");
            try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file))) {
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

    static List<Integer> historyFromString(String value) {
        List<Integer> history = new ArrayList<>();
        for (String string : value.split(",")) {
            history.add(Integer.valueOf(string));
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
            br.readLine();
            while (br.ready()) {
                String line = br.readLine();
                if (!line.isEmpty()) {
                    Task task = fromString(line);
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
                    historyFromString(line);
                }
            }
        } catch (IOException exception) {
            throw new ManagerSaveException("Не удалось сохранить данные.", exception);
        }
        manager.save();
        return manager;
    } 
}