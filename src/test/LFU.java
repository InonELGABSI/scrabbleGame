package test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class LFU implements CacheReplacementPolicy {
    private final HashMap<String, Node> cacheMap;
    private final PriorityQueue<Node> minHeap;


    public LFU() {
        this.cacheMap = new HashMap<String, Node>();
        this.minHeap = new PriorityQueue<>(Comparator.comparingInt((Node n) -> n.frequency));
    }
    @Override
    public void add (String word){
        Node existingNode = cacheMap.get(word);
        if (existingNode != null) {
                updateNode(existingNode);
        } else {
            Node newNode = new Node(word,1);
            cacheMap.put(word, newNode);
            minHeap.offer(newNode);
        }
    }

    @Override
    public String remove () {
        if (cacheMap.isEmpty()) {
            return null;
        }

        Node leastFrequent = minHeap.poll();
        if (leastFrequent != null) {
            cacheMap.remove(leastFrequent.word);
            return leastFrequent.word;
        }
        return null;
    }
    private void updateNode (Node node){
        node.frequency+=1;
        minHeap.remove(node);
        minHeap.offer(node);
    }

    public static class Node {
        public String word;
        public int frequency;

        public Node(String word, int frequency) {
            this.word = word;
            this.frequency = frequency;
        }
    }
}

