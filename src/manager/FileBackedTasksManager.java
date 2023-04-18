package manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import exception.ManagerSaveException;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import model.Type;

import static model.Type.*;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private File file = new File("src/resources/file.csv");

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public static void main(String[] args) {
        System.out.println();
        File file = new File("src/resources/file.csv");
        FileBackedTasksManager firstManager = new FileBackedTasksManager(file); //Заполняем задачами файл
        Task task1 = new Task("Task1", "Description task1", Status.NEW, TASK, LocalDateTime.of(2023, 04, 12, 22, 32), 30);
        firstManager.addTask(task1);
        Epic epic2 = new Epic("Epic2", "Description epic2", Status.DONE);
        firstManager.addEpic(epic2);
        Subtask subtask3 = new Subtask("Subtask3", "Description subtask3", Status.DONE, SUBTASK, LocalDateTime.of(2023, 04, 12, 20, 35), 20, epic2.getId());
        firstManager.addSubtask(subtask3);
        Subtask subtask4 = new Subtask("Subtask4", "Description subtask4", Status.DONE, SUBTASK, LocalDateTime.of(2023, 04, 12, 21, 35), 200, epic2.getId());
        firstManager.addSubtask(subtask4);
        firstManager.getSubtasksById(subtask3.getId());
        firstManager.getEpicsById(epic2.getId());
        firstManager.getTasksById(task1.getId());
        System.out.println("История первого менеджера:" + firstManager.getHistory()); //Печатаем историю первого менеджера
        FileBackedTasksManager secondManager = loadFromFile(file); // Создаем новый FileBackedTasksManager менеджер из этого же файла
        System.out.println(secondManager.getTasks());
        System.out.println(secondManager.getEpics());
        System.out.println(secondManager.getSubtasks());
        System.out.println();
        System.out.println("История второго менеджера:" + secondManager.getHistory()); //Печатаем историю второго менеджера
        System.out.println();
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
        Task task = null;

        switch (type) {
            case TASK:
                task = new Task(name, description, status, type, 
                                LocalDateTime.parse(split[5]), Long.parseLong(split[6]));
                task.setId(id);
                return task;
            case EPIC:
                Epic epic = new Epic(description, name, status);
                epic.setId(id);
                epic.setStartTime(LocalDateTime.parse(split[5]));
                epic.setDuration(Long.parseLong(split[6]));
                return epic;
            case SUBTASK:
                int idEpic = Integer.parseInt(split[7]);
                Subtask subtask = new Subtask(description, name, status, type, LocalDateTime.parse(split[5]), 
                                              Long.parseLong(split[6]), idEpic);
                subtask.setId(id);
                subtask.setId(idEpic);
                return subtask;
        }
        return task; 
    }

    private static String historyToString(HistoryManager manager) {
        StringBuilder history = new StringBuilder();
        for (Task task : manager.getHistory()) {
            history.append(task.getId()).append(",");
        }
        if (history.length() != 0) {
            history.deleteCharAt(history.length() - 1);
        }
        return history.toString();
    }

    static List<Integer> historyFromString(String value) {
        List<Integer> history = new ArrayList<>();
        String[] items = value.split(",");
        for (int i = items.length - 1; i >= 0; i--) {
            int id = Integer.valueOf(items[0]);
            history.add(id);
        }
        return history;
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager manager = new FileBackedTasksManager(file);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.ready()) {
                String line = br.readLine();
                if (!line.isEmpty()) {
                    if (line.contains("TASK") || line.contains("EPIC") || line.contains("SUBTASK")) { 
                        Task task = fromString(line);
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
                    } else if (!line.isBlank() && (!line.startsWith("id"))) { 
                        List<Integer> history = historyFromString(line);
                        for (Integer id : history) {
                            manager.getTasksById(id);
                            manager.getSubtasksById(id);
                            manager.getEpicsById(id);
                        }
                    }
                }
            }
        } catch (IOException exception) {
            throw new ManagerSaveException("Не удалось сохранить данные.", exception);
        }
        return manager;
    }
}