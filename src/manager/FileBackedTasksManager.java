package manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import model.Epic;
import model.ManagerSaveException;
import model.Subtask;
import model.Task;

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

    private static TaskManager loadFromFile(File file2) {
        return null;
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
        return null;
    }

    private String historyToString(HistoryManager history) {
        return null;
    }

    

}