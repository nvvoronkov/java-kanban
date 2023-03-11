package manager;

import java.util.List;
import model.Task;

final class CustomLinkedList {
    private final Node tail;
    private final Node head; 

    public CustomLinkedList() {
        this.tail = new Node(-1);
        this.head = new Node(-1);

        tail.prev = head;
        head.next = tail;
    }

    final class Node {
        private Node prev;
        private Node next;
        private final int taskId;
    
        public Node(int taskId) { 
            this.taskId = taskId;
        }
    
    }

    public void addLast(int taskId) {
        Node newNode = new Node(taskId); 
        tail.prev = newNode;
    }

    public void remove(Node nodeToRemove) {
        nodeToRemove.prev.next = null;
        nodeToRemove.next.prev = null;
        nodeToRemove.prev.next = nodeToRemove.next;
        nodeToRemove.next.prev = nodeToRemove.prev; 
        nodeToRemove.next = null;
        nodeToRemove.prev = null;
    }
}

/* 
public class CustomLinkedList<T extends Task> { 
    private final Map<Integer, Node<T>> mapToList = new HashMap<>();  // В ключах будут храниться id задач, а в значениях — узлы связного списка. С помощью номера задачи можно получить соответствующий ему узел связного списка и удалить его
    private int size = 0; 
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
        final Node<T> l = tail; // Новый элемент станет хвостом
        final Node<T> newNode = new Node<>(l, task, null);
        tail = newNode;
        if (l == null) {
            head = newNode;
        } else {
            l.next = newNode;
        }
        size++;
        mapToList.put(task.getId(), tail);
    }


    public List<T> getTasks() { // Метод собирает все задачи из списка в обычный ArrayList
        List<T> tasks = new ArrayList<>(); 
        Node<T> newNode = head;
        while (newNode != null) {
            tasks.add(newNode.item);
            newNode = newNode.next;
        }
        return tasks;
    }

    public void removeNode(Node<T> newNode) { //В качестве параметра метод должен принимать объект Node — узел связного списка и вырезать его
        if (newNode != null) {
            Node<T> next = newNode.next;
            Node<T> prev = newNode.prev;

            if (prev == null) {
                head = next;
            } else {
                prev.next = next;
                newNode.prev = null;
            }
            if (next == null) {
                tail = prev;
            } else {
                next.prev = prev;
                newNode.next = null;
            }
            newNode.item = null;
            size--;
        }
    }

    public void removeId(int id) {
        removeNode(mapToList.remove(id));
    }
}





 */