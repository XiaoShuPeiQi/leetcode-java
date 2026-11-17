# 559 · N 叉树的最大深度（关联补充） — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0559-maximum-depth-of-n-ary-tree/

二叉树 · Maximum Depth Of NAry Tree

## 题意

求 N 叉树从根节点到最深叶子经过的节点数。空树返回 0。

官方原题：https://leetcode.cn/problems/maximum-depth-of-n-ary-tree/

## 课堂示例

输入：N 叉树：1 的孩子为 3、2、4；3 的孩子为 5、6

预期结果：3

实际结果：3

测试来源：本地设计的代表性用例；原文未提供该题独立输入

## Java 解法

```java
import java.util.*;

class Solution {
    // ① 返回当前 N 叉子树高度，供父节点选最高的孩子。
    public int maxDepth(Node root) {
        // ② 空树高度为 0。
        if (root == null) {
            return 0;
        }
        // ③ 所有孩子取最大高度；没有孩子时保留 0，再加自己一层。
        int childHeight = 0;
        if (root.children != null) {
            for (Node child : root.children) {
                childHeight = Math.max(childHeight, maxDepth(child));
            }
        }
        return childHeight + 1;
    }
}
```

## 来源与版本

递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/binarytree/LC0559MaximumDepthOfNAryTree.java

源码指纹：6e6a867f5e760e1297fc6d89dd60148b0a07e513d4edc6b11376369659d5fb56

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：52 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0559-maximum-depth-of-n-ary-tree/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
