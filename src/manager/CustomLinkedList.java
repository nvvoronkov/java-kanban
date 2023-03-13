package manager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.Task;

public class CustomLinkedList<T extends Task> {
    private final Map<Integer, Node<T>> nodeMap = new HashMap<>();  // В ключах будут храниться id задач, а в значениях — узлы связного списка. С помощью номера задачи можно получить соответствующий ему узел связного списка и удалить его
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    public void linkLast(T task) { // Метод, который будет добавлять задачу в конец списка, пример из LL
        final Node<T> oldTail = tail; // Новый элемент станет хвостом
        final Node<T> newNode = new Node<>(oldTail, task, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
        nodeMap.put(task.getId(), tail);
    }


    public List<T> getTasks() { // Метод собирает все задачи из списка в LinkedList
        List<T> tasks = new LinkedList<>();
        Node<T> newNode = head;
        while (newNode != null) {
            tasks.add(newNode.item);
            newNode = newNode.next;
        }
        return tasks;
    }

    private void removeNode(Node<T> nodeToRemove) { //В качестве параметра метод должен принимать объект Node — узел связного списка и вырезать его
        if (nodeToRemove != null) {
            Node<T> next = nodeToRemove.next;
            Node<T> prev = nodeToRemove.prev;

            if (prev == null) {
                head = next;
            } else {
                prev.next = next;
                nodeToRemove.prev = null;
            }
            if (next == null) {
                tail = prev;
            } else {
                next.prev = prev;
                nodeToRemove.next = null;
            }
            nodeToRemove.item = null;

        }
    }

    public void removeById(int id) {
        removeNode(nodeMap.remove(id));
    }
}