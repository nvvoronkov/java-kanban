package manager.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import exception.ManagerSaveException;
import manager.history.FileBackedTasksManager;
import model.Epic;
import model.Subtask;
import model.Task;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;


public class HTTPTaskManager extends FileBackedTasksManager {

    public HTTPTaskManager(File file, String url) {
        super(file);
        this.kvTaskClient = new KVTaskClient(url);
    }

    private final KVTaskClient kvTaskClient;
    private final Gson gson = new Gson();

    @Override
    protected void save() {
        try {
            kvTaskClient.put("tasks/task", gson.toJson(tasks));
            kvTaskClient.put("tasks/epic", gson.toJson(epics));
            kvTaskClient.put("tasks/subtask", gson.toJson(subtasks));
            kvTaskClient.put("tasks/history", gson.toJson(getHistory()));
        } catch (Exception e) {
            throw new ManagerSaveException("Ошибка в сохранении");
        }
    }

    public void loadFromServer() {
        String tasksFromJson = kvTaskClient.load("tasks/task");
        if (tasksFromJson != null) {
            Type typeToken = new TypeToken<HashMap<Integer, Task>>() {
            }.getType();

            tasks = gson.fromJson(tasksFromJson, typeToken);
            prioritizedTasks.addAll(tasks.values());
        }

        String epicsFromJson = kvTaskClient.load("tasks/epic");
        if (epicsFromJson != null) {
            Type typeToken = new TypeToken<HashMap<Integer, Epic>>() {
            }.getType();

            epics = gson.fromJson(epicsFromJson, typeToken);
            prioritizedTasks.addAll(epics.values());
        }

        String subTasksFromJson = kvTaskClient.load("tasks/subtask");
        if (subTasksFromJson != null) {
            Type typeToken = new TypeToken<HashMap<Integer, Subtask>>() {
            }.getType();

            subtasks = gson.fromJson(subTasksFromJson, typeToken);
            prioritizedTasks.addAll(subtasks.values());
        }

        String historyFromJson = kvTaskClient.load("tasks/history");
        if (historyFromJson != null) {
            Type typeToken = new TypeToken<List<Task>>() {
            }.getType();

            List<Task> historyList = gson.fromJson(historyFromJson, typeToken);
            for (Task task : historyList) {
                historyManager.add(task);
            }
        }
    }
}