# 146 · LRU 缓存 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0146-lru-cache/

Hot100 · 链表 · LRU Cache

## 题意

实现容量固定的 LRU 缓存。get 返回指定键的值，缺失时返回 -1；put 插入或更新，超容量就移除最久未使用的键。读到某键或写入某键都会更新使用顺序，操作须为平均 O(1)。

官方原题：https://leetcode.cn/problems/lru-cache/

## 课堂示例

输入：capacity=2, put(1,1), put(2,2), get(1), put(3,3), get(2), put(4,4), get(1), get(3), get(4)

预期结果：[1, -1, -1, 3, 4]

实际结果：[1, -1, -1, 3, 4]

测试来源：力扣公开示例

## 状态含义

Map 和链表保存同一批真实节点。换顺序只改连接，查找仍通过 Map。

## 状态变化

Map 负责快速找节点，双向链表负责维护最近使用顺序。

## Java 解法

```java
import java.util.*;

class LRUCache {
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
```

## 来源与版本

学习扩展。按本题条件选择主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/linkedlist/LC0146LruCache.java

源码指纹：51fe7a7a19171f5d365e32b7e4a1d8394433f3e23f39a1dca00872ff4342c387

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：147 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0146-lru-cache/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
