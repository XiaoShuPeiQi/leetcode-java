# Hot100 第 54 题 · 力扣 208 · 实现 Trie (前缀树) — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0208-implement-trie-prefix-tree/

Hot100 · 图论 · Implement Trie (Prefix Tree)

## 题意

实现前缀树。insert 插入单词，search 判断是否插入过完全相同的单词，startsWith 判断是否有已插入单词以指定字符串开头。

官方原题：https://leetcode.cn/problems/implement-trie-prefix-tree/

## 课堂示例

输入：insert("apple"), search("apple"), search("app"), startsWith("app"), insert("app"), search("app")

预期结果：[true, false, true, true]

实际结果：[true, false, true, true]

测试来源：飞书 Hot100 原有课堂示例

## 解法

固定 26 路子节点的前缀树

## 状态含义

走完字符后，`search` 返回当前节点的 `isEnd`，`startsWith` 直接返回 true。

## 状态变化

每个字符对应一条子节点边，完整单词的最后一个节点单独标记。

## Java 解法

```java
import java.util.*;

class Trie {
    private static class TrieNode {
        final TrieNode[] children = new TrieNode[26];
        boolean isEnd;
    }

    private final TrieNode root = new TrieNode();

    public Trie() {
    }

    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.isEnd = true;
    }

    public boolean search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) {
                return false;
            }
            node = node.children[index];
        }
        return node.isEnd;
    }

    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) {
                return false;
            }
            node = node.children[index];
        }
        return true;
    }
}
```

## 来源与版本

算法流程来自代码随想录的 Java 解法，保留飞书原有课堂示例。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/graph/LC0208ImplementTriePrefixTree.java

源码指纹：3b272d6611286383885e94a8f25d51eb8d6ed78675e1eab45e6f33410a3102b9

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnQux7fjz93AWiRgnoA6Kvgf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0208.implement-trie-prefix-tree.html#java

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：125 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0208-implement-trie-prefix-tree/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
