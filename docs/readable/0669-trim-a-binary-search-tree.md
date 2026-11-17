# 669 · 修剪二叉搜索树 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0669-trim-a-binary-search-tree/

二叉树 · Trim ABinary Search Tree

## 题意

修剪二叉搜索树，使所有剩余节点的值都在闭区间 [low, high] 内。保持剩余节点原有的祖先关系，返回修剪后的根节点。

官方原题：https://leetcode.cn/problems/trim-a-binary-search-tree/

## 课堂示例

输入：root = [3, 1, 4, null, 2], low = 2, high = 4

预期结果：[3, 2, 4]

实际结果：[3, 2, 4]

测试来源：本地设计的代表性用例；原文未提供该题独立输入

## Java 解法

```java
import java.util.*;

class Solution {
    // ① 返回保留 [low, high] 后的子树根，根可能被替换。
    public TreeNode trimBST(TreeNode root, int low, int high) {
        // ② 空树无需修剪，返回 null。
        if (root == null) {
            return null;
        }
        // ③ 利用 BST 舍弃整侧；越界时返回仍可能合法的一侧，合法时接回两侧。
        if (root.val < low) {
            // 当前和左侧都太小；右侧可能合法，要递归保留，不能直接返回 null。
            return trimBST(root.right, low, high);
        }
        if (root.val > high) {
            // 当前和右侧都太大；合法节点只可能留在左侧。
            return trimBST(root.left, low, high);
        }
        // 当前合法，接回修剪后的孩子；孩子的根可能已换掉。
        root.left = trimBST(root.left, low, high);
        root.right = trimBST(root.right, low, high);
        return root;
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0669TrimABinarySearchTree.java

源码指纹：f140836fdfe0c18c49c862cf70bb4d87414c565007ea4210bc714e016a66d56a

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：45 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0669-trim-a-binary-search-tree/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
