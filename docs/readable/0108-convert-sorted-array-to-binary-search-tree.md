# 108 · 将有序数组转换为二叉搜索树 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0108-convert-sorted-array-to-binary-search-tree/

二叉树 · Convert Sorted Array To Binary Search Tree

## 题意

将一个升序整数数组转成高度平衡的二叉搜索树，返回任一满足条件的根节点。

官方原题：https://leetcode.cn/problems/convert-sorted-array-to-binary-search-tree/

## 课堂示例

输入：nums = [1, 2, 3, 4, 5, 6, 7]

预期结果：[4, 2, 6, 1, 3, 5, 7]

实际结果：[4, 2, 6, 1, 3, 5, 7]

测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）

## Java 解法

```java
import java.util.*;

class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        return build(nums, 0, nums.length);
    }

    // ① 返回 nums[left, right) 构成的平衡 BST 根。
    private TreeNode build(int[] nums, int left, int right) {
        // ② 空区间没有节点，返回 null。
        if (left == right) {
            return null;
        }
        // ③ 有序保证左小右大；选中点让两侧数量接近，再分别建孩子。
        int middle = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[middle]);
        root.left = build(nums, left, middle);
        root.right = build(nums, middle + 1, right);
        return root;
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0108ConvertSortedArrayToBinarySearchTree.java

源码指纹：23ed3147b2573652c792f12bed699e4e8a9d9261d42bbc559db30c14d56fc491

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：91 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0108-convert-sorted-array-to-binary-search-tree/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
