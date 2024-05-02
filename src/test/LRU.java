package test;
import java.util.HashMap;

public class LRU implements CacheReplacementPolicy {
    private final HashMap<String, DoublyLinkedList.Node> cacheMap;
    private final DoublyLinkedList cacheList;

    public LRU() {
        this.cacheMap = new HashMap<String, DoublyLinkedList.Node>();
        this.cacheList = new DoublyLinkedList();
    }

    @Override
    public void add(String word) {
        DoublyLinkedList.Node node = cacheMap.get(word);
        if (node != null) {
            cacheList.moveToHead(node);
            return;
        }
        DoublyLinkedList.Node newNode = new DoublyLinkedList.Node(word);
        cacheList.addHead(newNode);
        cacheMap.put(word, newNode);
    }

    @Override
    public String remove() {
        DoublyLinkedList.Node tail = cacheList.removeTail();
        if (tail != null) {
            cacheMap.remove(tail.word);
            return tail.word;
        }
        return null;
    }

    private static class DoublyLinkedList {

        private Node head;
        private Node tail;
        private int size;

        public static class Node {
            String word;
            Node prev;
            Node next;

            public Node(String word) {
                this.word = word;
            }

            public String getWord() {
                return word;
            }

            public Node getPrev() {
                return prev;
            }

            public Node getNext() {
                return next;
            }

            public void setWord(String word) {
                this.word = word;
            }

            public void setPrev(Node prev) {
                this.prev = prev;
            }

            public void setNext(Node next) {
                this.next = next;
            }

        }

        public DoublyLinkedList() {
            this.head = null;
            this.tail = null;
            this.size = 0;
        }

        private void addHead(Node node) {
            if (head == null) {
                head = tail = node;
            } else {
                node.next = head;
                head.prev = node;
                head = node;
            }
            size+=1;
        }

        private void moveToHead(Node node) {
            if (node == head) {
                return;
            }

            if (node == tail) {
                tail = tail.prev;
                tail.next = null;
            } else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }

            node.next = head;
            head.prev = node;
            head = node;
        }

        private Node removeTail() {
            if (tail == null) {
                return null;
            }

            Node removed = tail;
            if (head == tail) {
                head = tail = null;
            } else {
                tail = tail.prev;
                tail.next = null;
            }
            size-=1;
            return removed;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }
}
