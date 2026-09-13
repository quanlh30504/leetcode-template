import java.util.HashMap;
import java.util.Map;

class LRUCache {

    // =========================
    // Node
    // =========================
    class Node {
        int key;
        int value;

        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    // =========================
    // Fields
    // =========================
    private final Map<Integer, Node> map;

    private final int capacity;

    private final Node head;
    private final Node tail;


    // =========================
    // Constructor
    // =========================
    public LRUCache(int capacity) {
        this.capacity = capacity;

        map = new HashMap<>();

        // Dummy nodes
        head = new Node(0, 0);
        tail = new Node(0, 0);

        head.next = tail;
        tail.prev = head;
    }


    // =========================
    // Get
    // =========================
    public int get(int key) {

        Node node = map.get(key);

        if (node == null) {
            return -1;
        }

        // Recently used → move to MRU
        remove(node);
        addLast(node);

        return node.value;
    }


    // =========================
    // Put
    // =========================
    public void put(int key, int value) {

        // Case 1: key already exists
        if (map.containsKey(key)) {

            Node node = map.get(key);

            node.value = value;

            // Move to MRU
            remove(node);
            addLast(node);

            return;
        }

        // Case 2: new key
        Node node = new Node(key, value);

        map.put(key, node);
        addLast(node);

        // Exceed capacity
        if (map.size() > capacity) {

            Node lru = head.next;

            remove(lru);
            map.remove(lru.key);
        }
    }


    // =========================
    // Remove node
    // =========================
    private void remove(Node node) {

        Node prev = node.prev;
        Node next = node.next;

        prev.next = next;
        next.prev = prev;
    }


    // =========================
    // Add node to MRU
    // =========================
    private void addLast(Node node) {

        Node prev = tail.prev;

        prev.next = node;
        node.prev = prev;

        node.next = tail;
        tail.prev = node;
    }
}