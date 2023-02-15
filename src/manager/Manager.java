package manager;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.List;

public abstract class Manager {

    void addTask(Task task){   // Методы по добавлению задач, эпиков, подзадач
    }

    void addEpic(Epic epic){
    }

    void addSubtask(Subtask subtask){
    }

    List<Subtask> getListSubtaskEpic(int id){ // Метод для получения списка подзадач в эпике
        return null;
    }

    void deleteAllTask(){     // Методы по удалению всех задач, эпиков, подзадач
    }

    void deleteAllEpic(){
    }

    void deleteAllSubtask(){
    }

    void deleteTaskID(int id){   // Методы по удалению определенных задач, эпиков, подзадач по ID
    }
    void deleteEpicID(int id){
    }

    void deleteSubtaskID(int id){
    }

    protected void whatStatusEpic(Epic epic){ // Проверка статуса эпика
    }

    Task updateTask(Task task){  // Методы по обновлению задач, подзадач, эпиков
        return task;
    }

    Epic updateEpic(Epic epic){
        return epic;
    }

    Subtask updateSubtask(Subtask subtask){
        return subtask;
    }

    List<Task> getTasks(){  // Методы для получения задач, эпиков, подзадач
        return null;
    }

    List<Epic> getEpics(){
        return null;
    }

    List<Subtask> getSubtasks(){
        return null;
    }

    Subtask getSubtaskFromId(int id){
        return null;
    }

    Task getTaskFromId(int id){
        return null;
    }

    Epic getEpicFromId(int id){
        return null;
    }

}
