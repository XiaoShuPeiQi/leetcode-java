package leetcode.linkedlist;

import java.util.*;
import common.*;
import static common.ListSupport.*;

public class LC0146LruCache {
    public static void main(String[] args) {
        TreeSupport.check("LRUCache", "capacity=2, put(1,1), put(2,2), get(1), put(3,3), get(2), put(4,4), get(1), get(3), get(4)",
                "[1, -1, -1, 3, 4]", String.valueOf(runSolution()));
    }

    // 测试来源：力扣示例与 Hot100 笔记。
    static Object runSolution() {
        LRUCache cache = new LRUCache(2);
        List<Integer> reads = new ArrayList<>();
        cache.put(1, 1);
        cache.put(2, 2);
        reads.add(cache.get(1));
        cache.put(3, 3);
        reads.add(cache.get(2));
        cache.put(4, 4);
        reads.add(cache.get(1));
        reads.add(cache.get(3));
        reads.add(cache.get(4));
        return reads;
    }

    static class LRUCache {
        private static class Node {
            int key;
            int val;
            Node prev;
            Node next;

            Node(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }

        private final int capacity;
        private final Map<Integer, Node> cache = new HashMap<>();
        private final Node head = new Node(0, 0);
        private final Node tail = new Node(0, 0);

        public LRUCache(int capacity) {
            this.capacity = capacity;
            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            Node node = cache.get(key);
            if (node == null) {
                return -1;
            }
            remove(node);
            addFirst(node);
            return node.val;
        }

        public void put(int key, int value) {
            Node node = cache.get(key);
            if (node != null) {
                node.val = value;
                remove(node);
                addFirst(node);
                return;
            }
            node = new Node(key, value);
            cache.put(key, node);
            addFirst(node);
            if (cache.size() > capacity) {
                Node oldest = tail.prev;
                remove(oldest);
                cache.remove(oldest.key); // 两种结构都删掉淘汰项
            }
        }

        private void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void addFirst(Node node) {
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
        }
    }
}
