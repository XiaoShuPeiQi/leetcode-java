# 450 · 删除二叉搜索树中的节点 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0450-delete-node-in-a-bst/

二叉树 · Delete Node In ABST

## 题意

给定一个二叉搜索树的根节点 **root** 和一个值 **key**，删除二叉搜索树中的 **key** 对应的节点，并保证二叉搜索树的性质不变。返回二叉搜索树（有可能被更新）的根节点的引用。

一般来说，删除节点可分为两个步骤：

1. 首先找到需要删除的节点；

2. 如果找到了，删除它。

**示例 1:**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0400-0499/0450.Delete%20Node%20in%20a%20BST/images/del_node_1.jpg)

```text
输入：root = [5,3,6,2,4,null,7], key = 3
输出：[5,4,6,2,null,null,7]
解释：给定需要删除的节点值是 3，所以我们首先找到 3 这个节点，然后删除它。
一个正确的答案是 [5,4,6,2,null,null,7], 如下图所示。
另一个正确答案是 [5,2,6,null,4,null,7]。



![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0400-0499/0450.Delete%20Node%20in%20a%20BST/images/del_node_supp.jpg)
```

**示例 2:**

```text
输入: root = [5,3,6,2,4,null,7], key = 0
输出: [5,3,6,2,4,null,7]
解释: 二叉树不包含值为 0 的节点
```

**示例 3:**

```text
输入: root = [], key = 0
输出: []
```

**提示:**

- 节点数的范围 `[0, 10^4]`.

- `-10^5 <= Node.val <= 10^5`

- 节点值唯一

- `root` 是合法的二叉搜索树

- `-10^5 <= key <= 10^5`

**进阶：** 要求算法时间复杂度为 O(h)，h 为树的高度。

官方原题：https://leetcode.cn/problems/delete-node-in-a-bst

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0400-0499/0450.Delete%20Node%20in%20a%20BST/README.md

## 回放用例

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
