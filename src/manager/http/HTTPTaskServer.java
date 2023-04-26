package manager.http;


import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import model.Epic;
import model.Subtask;
import model.Task;
import manager.Manager;
import manager.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class HttpTaskServer {
    private static final int PORT = 8078;
    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private final Gson gson;
    private final TaskManager manager;
    private final HttpServer server;

    public HttpTaskServer() throws IOException {
        gson = new Gson()
                .newBuilder()
                .setPrettyPrinting()
                .create();
        manager = Manager.getDefault();
        server = HttpServer.create();
        server.bind(new InetSocketAddress(PORT), 0);
        server.createContext("/tasks", new TasksHandler());
    }

    public void start() {
        server.start();
    }

    void stop() {
        server.stop(1);
    }

    class TasksHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "";
            int statusCode = 404;
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String query = exchange.getRequestURI().getQuery();

            switch (method) {
                case "GET":
                    if (path.endsWith("/tasks") && query == null) {
                        statusCode = 200;
                        response = "Список задач по приоритету: " + gson.toJson(manager.getPrioritizedTasks());
                    }

                    if (path.endsWith("tasks/subtask/epic") && query != null) {
                        statusCode = 200;
                        String[] splitQuery = query.split("=");
                        int taskId = Integer.parseInt(splitQuery[1]);
                        response = "список всех сабтасок эпика " + gson.toJson(manager.getAllSubtaskOfEpic(manager.getEpicsById(taskId)));
                    }

                    if (path.endsWith("tasks/task")) {
                        if (query != null) {
                            List<Task> taskList = manager.getTasks();
                            Task taskForReturn = null;
                            String[] splitQuery = query.split("=");
                            int taskId = Integer.parseInt(splitQuery[1]);
                            for (Task task : taskList) {
                                if (taskId == task.getId()) {
                                    taskForReturn = manager.getTasksById(taskId);
                                    statusCode = 200;
                                    response = gson.toJson(manager.getTasksById(taskId));
                                }
                            }

                            if (taskForReturn == null) {
                                statusCode = 400;
                                response = "Необходимо указать корректный id задачи, task c " + taskId + " не существует";
                            }

                        } else {
                            statusCode = 200;
                            response = gson.toJson(manager.getTasks());
                        }
                    }

                    if (path.endsWith("tasks/history") && query == null) {
                        statusCode = 200;
                        response = gson.toJson(manager.getHistory());
                    }

                    if (path.endsWith("tasks/epic")) {
                        if (query != null) {
                            List<Epic> epicList = manager.getEpics();
                            Epic epicForReturn = null;

                            String[] splitQuery = query.split("=");
                            int epicId = Integer.parseInt(splitQuery[1]);

                            for (Epic epic : epicList) {
                                if (epicId == epic.getId()) {
                                    epicForReturn = manager.getEpicsById(epicId);
                                    statusCode = 200;
                                    response = gson.toJson(manager.getEpicsById(epicId));
                                }
                            }

                            if (epicForReturn == null) {
                                statusCode = 400;
                                response = "Необходимо указать корректный id задачи, epic c " + epicId + " не существует";
                            }
                        } else {
                            statusCode = 200;
                            response = gson.toJson(manager.getEpics());
                        }
                    }

                    if (path.endsWith("tasks/subtask")) {
                        if (query != null) {
                            List<Subtask> subtaskList = manager.getSubtasks();
                            Subtask subtaskForReturn = null;

                            String[] splitQuery = query.split("=");
                            int subtaskId = Integer.parseInt(splitQuery[1]);

                            for (Subtask subtask : subtaskList) {
                                if (subtaskId == subtask.getId()) {
                                    subtaskForReturn = manager.getSubtasksById(subtaskId);
                                    statusCode = 200;
                                    response = gson.toJson(manager.getSubtasksById(subtaskId));
                                }
                            }
                            if (subtaskForReturn == null) {
                                statusCode = 400;
                                response = "Необходимо указать корректный id задачи, Subtask c " + subtaskId + " не существует";
                            }
                        } else {
                            statusCode = 200;
                            response = gson.toJson(manager.getSubtasks());
                        }
                    }
                    break;

                case "POST":
                    InputStream inputStream = exchange.getRequestBody();
                    if (inputStream != null) {
                        String body = new String(inputStream.readAllBytes(), CHARSET);
                        if (query == null) {
                            if (path.endsWith("tasks/task")) {
                                statusCode = 201;
                                Task newTask = gson.fromJson(body, Task.class);
                                manager.updateTask(newTask);
                                response = "Задача добавлена";

                            } else if (path.endsWith("tasks/epic")) {
                                statusCode = 201;
                                Epic newEpic = gson.fromJson(body, Epic.class);
                                manager.updateEpic(newEpic);
                                response = "Epic создан";

                            } else if (path.endsWith("tasks/subtask")) {
                                Subtask newSubtask = gson.fromJson(body, Subtask.class);

                                List<Epic> epicList = manager.getEpics();

                                Epic epicForSubtask = null;

                                for (Epic epic : epicList) {
                                    if (epic.getId() == newSubtask.getId()) {
                                        epicForSubtask = manager.getEpicsById(epic.getId());
                                        statusCode = 201;
                                        manager.createSubtask(epic, newSubtask);
                                        manager.updateSubtask(newSubtask);
                                        response = "Задача добавлена";
                                    }
                                }
                                if (epicForSubtask == null) {
                                    statusCode = 400;
                                    response = "Необходимо указать корректный id Epic в теле Subtask";
                                }
                            }
                        }
                    }
                    break;

                case "DELETE":
                    if (path.endsWith("/tasks") && query == null) {
                        System.out.println("Обработка запроса DELETE /tasks/");
                        statusCode = 200;
                        manager.deleteAllTask();
                        manager.deleteAllEpic();
                        manager.getPrioritizedTasks().clear();

                        response = "Удалены задачи всех видов";
                    }

                    if (path.endsWith("tasks/task")) {
                        if (query != null) {
                            List<Task> taskList = manager.getTasks();
                            Task taskForRemove = null;

                            String[] splitQuery = query.split("=");
                            int taskId = Integer.parseInt(splitQuery[1]);

                            for (Task task : taskList) {
                                if (taskId == task.getId()) {
                                    taskForRemove = manager.getTasksById(taskId);
                                    statusCode = 200;
                                    manager.deleteTaskById(taskId);
                                    response = "Задача типа TASK с ID " + taskId + " удалена";
                                }
                            }

                            if (taskForRemove == null) {
                                statusCode = 400;
                                response = "Необходимо указать корректный id задачи, task c " + taskId + " не существует";
                            }
                        } else {
                            statusCode = 200;
                            manager.deleteAllTask();
                            response = "удалены все задачи типа TASK";
                        }
                    }

                    if (path.endsWith("tasks/epic")) {
                        if (query != null) {
                            List<Epic> epicList = manager.getEpics();
                            Epic epicForRemove = null;

                            String[] splitQuery = query.split("=");
                            int taskId = Integer.parseInt(splitQuery[1]);

                            for (Epic epic : epicList) {
                                if (taskId == epic.getId()) {
                                    epicForRemove = manager.getEpicsById(taskId);
                                    statusCode = 200;

                                    manager.getAllSubtaskOfEpic(epic).clear();
                                    manager.deleteEpicById(taskId);

                                    response = "Задача типа EPIC с ID " + taskId + " удалена";
                                }
                            }

                            if (epicForRemove == null) {
                                statusCode = 400;
                                response = "Необходимо указать корректный id задачи, epic c " + taskId + " не существует";
                            }
                        } else {
                            statusCode = 200;
                            manager.deleteAllEpic();
                            response = "удалены все задачи типа EPIC";
                        }
                    }

                    if (path.endsWith("tasks/subtask")) {
                        if (query != null) {
                            List<Subtask> subtaskList = manager.getSubtasks();
                            Subtask subtaskForRemove = null;

                            String[] splitQuery = query.split("=");
                            int taskId = Integer.parseInt(splitQuery[1]);

                            for (Subtask subtask : subtaskList) {
                                if (taskId == subtask.getId()) {
                                    subtaskForRemove = manager.getSubtasksById(taskId);
                                    statusCode = 200;
                                    manager.deleteSubtaskById(taskId);
                                    response = "Задача типа Subtask с ID " + taskId + " удалена";
                                }
                            }

                            if (subtaskForRemove == null) {
                                statusCode = 400;
                                response = "Необходимо указать корректный id задачи, Subtask c " + taskId + " не существует";
                            }
                        } else {
                            statusCode = 200;
                            manager.deleteAllSubtask();
                            response = "удалены все задачи типа TASK";
                        }
                    } break;
            }

            exchange.sendResponseHeaders(statusCode, response.getBytes(CHARSET).length);
            try (
                    OutputStream outputStream = exchange.getResponseBody()) {
                outputStream.write(response.getBytes(CHARSET));
            }
        }
    }
}



