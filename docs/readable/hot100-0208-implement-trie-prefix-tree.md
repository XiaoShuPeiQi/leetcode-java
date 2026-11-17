# 208 · 实现 Trie (前缀树) — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0208-implement-trie-prefix-tree/

Hot100 · 图论 · Implement Trie (Prefix Tree)

## 题意

实现前缀树。insert 插入单词，search 判断是否插入过完全相同的单词，startsWith 判断是否有已插入单词以指定字符串开头。

官方原题：https://leetcode.cn/problems/implement-trie-prefix-tree/

## 课堂示例

输入：insert("apple"), search("apple"), search("app"), startsWith("app"), insert("app"), search("app")

预期结果：[true, false, true, true]

实际结果：[true, false, true, true]

测试来源：力扣公开示例

## 状态含义

根代表空前缀，每走一条边就消费一个字母。

## 状态变化

沿字母向下查，只有最后节点标记为词尾才算找到整词。

## Java 解法

```java
import java.util.*;

class Trie {
    private static class Node {
        Node[] children = new Node[26];
        boolean end;
    }

    private final Node root = new Node();

    public Trie() {
    }

    public void insert(String word) {
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            if (node.children[index] == null) {
                node.children[index] = new Node();
            }
            node = node.children[index];
        }
        node.end = true;
    }

    public boolean search(String word) {
        Node node = find(word);
        return node != null && node.end;
    }

    public boolean startsWith(String prefix) {
        return find(prefix) != null;
    }

    private Node find(String text) {
        Node node = root;
        for (int i = 0; i < text.length(); i++) {
            int index = text.charAt(i) - 'a';
            node = node.children[index];
            if (node == null) {
                return null;
            }
        }
        return node;
    }
}
```

## 来源与版本

学习扩展。按本题条件选择主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/graph/LC0208ImplementTriePrefixTree.java

源码指纹：57fd5011102ca8efcf5fbbc702464af4a602b6e76a4bc307812677d6a99da7cd

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：140 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0208-implement-trie-prefix-tree/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
