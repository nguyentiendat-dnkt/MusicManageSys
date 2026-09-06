package structure;

public class MyQueue<T> {

    private class Node {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node front; // đầu hàng - phần tử cũ nhất, sẽ ra trước
    private Node rear;  // cuối hàng - phần tử mới nhất vừa vào
    private int size;

    public MyQueue() {
        front = null;
        rear = null;
        size = 0;
    }

    // Thêm 1 phần tử vào cuối hàng
    public void enqueue(T item) {
        Node newNode = new Node(item);
        if (isEmpty()) {
            front = newNode; // hang rong dau cuoi la mot
            rear = newNode;
        } else {
            rear.next = newNode; // gắn node mới vào sau node cuối hiện tại
            rear = newNode;      // cập nhật lại rear là node mới
        }
        size++;
    }

    // Lấy và xóa phần tử ở đầu hàng (cũ nhất)
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Queue rong, khong the dequeue.");
        }
        T data = front.data;
        front = front.next;
        if (front == null) {
            rear = null; // hàng đã rỗng hoàn toàn
        }
        size--;
        return data;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }

    // In toàn bộ phần tử theo thứ tự MỚI NHẤT -> CŨ NHẤT
    public void printNewestFirst() {
        if (isEmpty()) {
            System.out.println("Chua co gi trong hang doi.");
            return;
        }
        printReverse(front);
    }

    private void printReverse(Node node) {
        if (node == null) {
            return; // đã đi hết tới cuối chuỗi, dừng đệ quy
        }
        printReverse(node.next); // gọi tiếp xuống node sau trước
        System.out.println(node.data); 
    }
}