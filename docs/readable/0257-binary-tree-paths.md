# 257 · 二叉树的所有路径 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0257-binary-tree-paths/

二叉树 · Binary Tree Paths

## 题意

返回二叉树所有从根到叶的路径，以 -> 连接每条路径上的节点值。

官方原题：https://leetcode.cn/problems/binary-tree-paths/

## 课堂示例

输入：root = [4, 2, 6, 1, 3, 5, 7]

预期结果：[4->2->1, 4->2->3, 4->6->5, 4->6->7]

实际结果：[4->2->1, 4->2->3, 4->6->5, 4->6->7]

测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）

## Java 解法

```java
import java.util.*;

class Solution {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new ArrayList<>();
        traversal(root, new StringBuilder(), result);
        return result;
    }

    // ① path 是从根走到当前节点前的共享路径，result 保存完整答案。
    private void traversal(TreeNode node, StringBuilder path, List<String> result) {
        // ② 空节点没有值可追加，直接返回。
        if (node == null) {
            return;
        }
        // ③ 先记旧长度、加自己；孩子结束后恢复，兄弟分支才不会串路径。
        int oldLength = path.length();
        if (oldLength > 0) {
            path.append("->");
        }
        path.append(node.val);
        if (node.left == null && node.right == null) {
            // 到叶子才是一条完整路径；toString 保存独立结果。
            result.add(path.toString());
        } else {
            traversal(node.left, path, result);
            traversal(node.right, path, result);
        }
        // 回到进入前的父路径，防止左分支内容混入右分支。
        path.setLength(oldLength);
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0257BinaryTreePaths.java

源码指纹：d42174addb41a46fd4752f4b43689288cdc165ab9aedb0fb4a8c6ccbf4e84e55

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：84 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0257-binary-tree-paths/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
