# 538 · 把二叉搜索树转换为累加树 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0538-convert-bst-to-greater-tree/

二叉树 · Convert BSTTo Greater Tree

## 题意

给出二叉 **搜索** 树的根节点 `root`，该树的节点值各不相同，请你将其转换为累加树（Greater Sum Tree），将其转换为一个更大的树，使得原始二叉搜索树中的每个节点值都变为原本值加上原本二叉搜索树中所有比该节点值大的节点值的总和。

提醒一下，二叉搜索树满足下列约束条件：

- 节点的左子树仅包含键 **小于** 节点键的节点。

- 节点的右子树仅包含键 **大于** 节点键的节点。

- 左右子树也必须是二叉搜索树。

**注意：**本题和 1038: [https://leetcode.cn/problems/binary-search-tree-to-greater-sum-tree/](<https://leetcode.cn/problems/binary-search-tree-to-greater-sum-tree/>) 相同

**示例 1：**

**![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0500-0599/0538.Convert%20BST%20to%20Greater%20Tree/images/tree.png)**

```text
输入：[4,1,6,0,2,5,7,null,null,null,3,null,null,null,8]
输出：[30,36,21,36,35,26,15,null,null,null,33,null,null,null,8]
```

**示例 2：**

```text
输入：root = [0,null,1]
输出：[1,null,1]
```

**示例 3：**

```text
输入：root = [1,0,2]
输出：[3,3,2]
```

**示例 4：**

```text
输入：root = [3,2,4,1]
输出：[7,9,4,10]
```

**提示：**

- 树中的节点数介于 `0` 和 `10^4`^ 之间。

- 每个节点的值介于 `-10^4` 和 `10^4` 之间。

- 树中的所有值 **互不相同** 。

- 给定的树为二叉搜索树。

官方原题：https://leetcode.cn/problems/convert-bst-to-greater-tree

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0500-0599/0538.Convert%20BST%20to%20Greater%20Tree/README.md

## 回放用例

输入：root = [4, 2, 6, 1, 3, 5, 7]

预期结果：[22, 27, 13, 28, 25, 18, 7]

实际结果：[22, 27, 13, 28, 25, 18, 7]

测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）

## Java 解法

```java
import java.util.*;

class Solution {
    private int sum;

    public TreeNode convertBST(TreeNode root) {
        // 每次转换重新开始累计，避免复用 Solution 时带入旧结果。
        sum = 0;
        accumulate(root);
        return root;
    }

    // ① sum 是已访问的更大原值之和；遍历时直接修改节点值。
    private void accumulate(TreeNode node) {
        // ② 空节点没有值可累加。
        if (node == null) {
            return;
        }
        // ③ 右→根→左从大到小；先累加当前旧值再写回，供更小节点使用。
        accumulate(node.right);
        sum += node.val;
        node.val = sum;
        accumulate(node.left);
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0538ConvertBSTToGreaterTree.java

源码指纹：26b466de9feefcc8dd4e8e98f14ea15076c48750cf8427685995946b4e76e80c

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：93 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0538-convert-bst-to-greater-tree/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
