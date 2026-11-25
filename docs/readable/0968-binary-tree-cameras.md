# 968 · 监控二叉树 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0968-binary-tree-cameras/

贪心 · Binary Tree Cameras

## 题意

给定一个二叉树，我们在树的节点上安装摄像头。

节点上的每个摄影头都可以监视**其父对象、自身及其直接子对象。**

计算监控树的所有节点所需的最小摄像头数量。

**示例 1：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0900-0999/0968.Binary%20Tree%20Cameras/images/bst_cameras_01.png)

```text
输入：[0,0,null,0,0]
输出：1
解释：如图所示，一台摄像头足以监控所有节点。
```

**示例 2：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0900-0999/0968.Binary%20Tree%20Cameras/images/bst_cameras_02.png)

```text
输入：[0,0,null,0,null,0,null,null,0]
输出：2
解释：需要至少两个摄像头来监视树的所有节点。 上图显示了摄像头放置的有效位置之一。
```

**提示：**

1. 给定树的节点数的范围是 `[1, 1000]`。

2. 每个节点的值都是 0。

官方原题：https://leetcode.cn/problems/binary-tree-cameras

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0900-0999/0968.Binary%20Tree%20Cameras/README.md

## 回放用例

输入：root = [0, 0, null, 0, 0]

预期结果：1

实际结果：1

测试来源：飞书贪心章节实例走过程（revision 87）

## Java 解法

```java
import java.util.*;

class Solution {
    private static final int UNCOVERED = 0;
    private static final int CAMERA = 1;
    private static final int COVERED = 2;
    private int cameras;

    public int minCameraCover(TreeNode root) {
        cameras = 0;
        if (traverse(root) == UNCOVERED) cameras++; // 根没有父节点兜底
        return cameras;
    }

    private int traverse(TreeNode node) {
        if (node == null) return COVERED;
        int left = traverse(node.left);
        int right = traverse(node.right);
        if (left == UNCOVERED || right == UNCOVERED) {
            cameras++;
            return CAMERA;
        }
        if (left == CAMERA || right == CAMERA) return COVERED;
        return UNCOVERED;
    }
}
```

## 来源与版本

录制代码与2026-10-14读取的飞书Solution类体一致，只增加static与外层运行适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/greedy/LC0968BinaryTreeCameras.java

源码指纹：036c0cd3506637cfa468b0d45d99ecf711b1f8e36305dcbddc6baacb0c819553

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：51 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0968-binary-tree-cameras/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
