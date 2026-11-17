# Hot100 第 60 题 · 力扣 79 · 单词搜索 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0079-word-search/

Hot100 · 回溯 · Word Search

## 题意

判断 word 能否在字符网格中拼出。相邻字母必须分别位于上下左右相邻的格子，同一格不得重复使用。

官方原题：https://leetcode.cn/problems/word-search/

## 课堂示例

输入：board=["ABCE","SFCS","ADEE"], word="ABCCED"

预期结果：true

实际结果：true

测试来源：飞书 Hot100 原有课堂示例

## 解法

原地标记并还原的网格回溯

## 状态含义

`index == word.length()` 代表上一层已经匹配最后一个字符。无论接下来的分支是否成功，都先恢复本层字符，再返回 `found`。

## 状态变化

从每个格子尝试匹配单词，路径上的格子暂时写成 '#'，从递归返回时复原。

## Java 解法

```java
import java.util.*;

class Solution {
    private int rows;
    private int cols;

    public boolean exist(char[][] board, String word) {
        rows = board.length;
        cols = board[0].length;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (backtracking(board, word, row, col, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean backtracking(char[][] board, String word, int row, int col, int index) {
        if (index == word.length()) {
            return true;
        }
        if (row < 0 || row >= rows || col < 0 || col >= cols
                || board[row][col] == '#' || board[row][col] != word.charAt(index)) {
            return false;
        }

        char saved = board[row][col];
        board[row][col] = '#';
        boolean found = backtracking(board, word, row + 1, col, index + 1)
                || backtracking(board, word, row - 1, col, index + 1)
                || backtracking(board, word, row, col + 1, index + 1)
                || backtracking(board, word, row, col - 1, index + 1);
        board[row][col] = saved;
        return found;
    }
}
```

## 来源与版本

算法流程来自代码随想录的 Java 解法，保留飞书原有课堂示例。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/backtracking/LC0079WordSearch.java

源码指纹：e92a7a7ac30b393a9ab7e44870988134ec041384c8ee04b1709007d6bb0e2c4d

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnwLhlvRenAi3AqEiBmYgJYb（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0079.word-search.html#java

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：117 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0079-word-search/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
