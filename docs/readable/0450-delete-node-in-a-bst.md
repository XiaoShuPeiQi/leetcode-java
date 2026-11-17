# 450 · 删除二叉搜索树中的节点 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0450-delete-node-in-a-bst/

二叉树 · Delete Node In ABST

## 题意

在二叉搜索树中删除值等于 key 的节点，保留二叉搜索树性质，返回处理后的根节点。key 缺失时保留原树。

官方原题：https://leetcode.cn/problems/delete-node-in-a-bst/

## 课堂示例

输入：root = [4, 2, 6, 1, 3, 5, 7], key = 4

预期结果：[5, 2, 6, 1, 3, null, 7]

实际结果：[5, 2, 6, 1, 3, null, 7]

测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）

## Java 解法

```java
import java.util.*;

class Solution {
    // ① 返回删除后的子树根；删掉当前根时，返回的节点可能改变。
    public TreeNode deleteNode(TreeNode root, int key) {
        // ② 空位置说明没找到目标，返回 null。
        if (root == null) {
            return null;
        }
        // ③ 未命中只改一侧；命中后按孩子数量，决定由谁接替当前根。
        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else {
            // 无孩子或只有右孩子：由右边接替；返回值可能为 null。
            if (root.left == null) {
                return root.right;
            }
            // 只有左孩子：由左边接替。
            if (root.right == null) {
                return root.left;
            }
            // 两个孩子都在：右子树最小节点是中序后继，且没有左孩子。
            TreeNode successor = root.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            root.val = successor.val;
            // 复制后继值后，删掉原后继，避免重复，并接回修改后的右树。
            root.right = deleteNode(root.right, successor.val);
        }
        return root;
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0450DeleteNodeInABST.java

源码指纹：5ca4d4a6a622b43f76a412dd149f1fc11564aa6bb7a23c8ba55aae0387a15885

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：27 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0450-delete-node-in-a-bst/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
