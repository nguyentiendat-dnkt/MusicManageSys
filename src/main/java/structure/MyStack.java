package structure;

public class MyStack<T> {

    // Lớp Node nội bộ - chỉ dùng riêng bên trong MyStack, không ai bên ngoài cần biết đến nó
    private class Node {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node top; // luôn trỏ tới phần tử "trên cùng" của Stack
    private int size;

    public MyStack() {
        top = null;
        size = 0;
    }

    // Thêm 1 phần tử lên trên cùng
    public void push(T item) {
        Node newNode = new Node(item);
        newNode.next = top; // node mới trỏ xuống node cũ đang là top
        top = newNode;       // top giờ là node mới
        size++;
    }

    // Lấy và xóa phần tử trên cùng
    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack rong, khong the pop.");
        }
        T data = top.data;
        top = top.next; // top giờ trỏ xuống node kế tiếp
        size--;
        return data;
    }

    // Xem phần tử trên cùng mà không xóa
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack rong.");
        }
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    // Xóa sạch toàn bộ Stack
    public void clear() {
        top = null;
        size = 0;
    }
}