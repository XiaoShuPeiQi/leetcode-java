# 116 · 填充每个节点的下一个右侧节点指针 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0116-populating-next-right-pointers-in-each-node/

二叉树 · Populating Next Right Pointers In Each Node

## 题意

在完美二叉树中，将每个节点的 next 指向同层右边紧邻的节点。每层末尾的 next 为空。返回根节点。

官方原题：https://leetcode.cn/problems/populating-next-right-pointers-in-each-node/

## 课堂示例

输入：root = [1, 2, 3, 4, 5, 6, 7]

预期结果：[[1], [2, 3], [4, 5, 6, 7]]

实际结果：[[1], [2, 3], [4, 5, 6, 7]]

测试来源：本地设计的代表性用例；原文未提供该题独立输入

## Java 解法

```java
import java.util.*;

class Solution {
    public Node connect(Node root) {
        if (root != null) {
            root.next = null;
        }
        link(root);
        return root;
    }

    // ① 进入 link 时，本层 next 已可用；现在负责连接孩子。
    private void link(Node node) {
        // ② 空节点或叶子没有孩子可连接。
        if (node == null || node.left == null) {
            return;
        }
        // ③ 先连同父兄弟，再连跨父邻居；连好后孩子才能继续使用 next。
        node.left.next = node.right;
        node.right.next = null;
        if (node.next != null) {
            // 跨父连接：自己的右孩子，连隔壁父亲的左孩子。
            node.right.next = node.next.left;
        }
        link(node.left);
        link(node.right);
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0116PopulatingNextRightPointersInEachNode.java

源码指纹：99ac950e41363f1e8874df76174be758c1d039748aea25a6556443241b312acb

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：50 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0116-populating-next-right-pointers-in-each-node/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
