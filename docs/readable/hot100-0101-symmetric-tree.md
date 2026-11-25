# 101 · 对称二叉树 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0101-symmetric-tree/

Hot100 · 二叉树 · Symmetric Tree

## 题意

给你一个二叉树的根节点 `root` ， 检查它是否轴对称。

**示例 1：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0100-0199/0101.Symmetric%20Tree/images/1698026966-JDYPDU-image.png)

```text
输入：root = [1,2,2,3,4,4,3]
输出：true
```

**示例 2：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0100-0199/0101.Symmetric%20Tree/images/1698027008-nPFLbM-image.png)

```text
输入：root = [1,2,2,null,3,null,3]
输出：false
```

**提示：**

- 树中节点数目在范围 `[1, 1000]` 内

- `-100 <= Node.val <= 100`

**进阶：**你可以运用递归和迭代两种方法解决这个问题吗？

官方原题：https://leetcode.cn/problems/symmetric-tree

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0100-0199/0101.Symmetric%20Tree/README.md

## 回放用例

输入：root = [1, 2, 2, 3, 4, 4, 3]

预期结果：true

实际结果：true

测试来源：算法学习总笔记与项目已有 main

## 状态含义

每次递归判断的是一对完整子树能否镜像重合。

## 状态变化

把左右子树成对比较，外侧对外侧，内侧对内侧。

## Java 解法

```java
import java.util.*;

class Solution {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return mirror(root.left, root.right);
    }

    // ① 两个参数是待比较的子树根；返回它们是否镜像。
    private boolean mirror(TreeNode left, TreeNode right) {
        // ② 两边都空才匹配；只有一边空说明结构不对称。
        if (left == null || right == null) {
            return left == right;
        }
        // ③ 值相同后，再同时检查外侧和内侧的镜像关系。
        if (left.val != right.val) {
            return false;
        }
        // 外侧对外侧、内侧对内侧；两组都匹配才是镜像。
        return mirror(left.left, right.right)
                && mirror(left.right, right.left);
    }
}
```

## 来源与版本

沿用总笔记及仓库中本题的主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0101SymmetricTree.java

源码指纹：f527fbfbb38a67e7cbef56f8d0447454b4bbee26833b5a2ad036170ff877f0bc

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：41 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0101-symmetric-tree/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
