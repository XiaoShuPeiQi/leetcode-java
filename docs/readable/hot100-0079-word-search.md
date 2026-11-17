# 79 · 单词搜索 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0079-word-search/

Hot100 · 回溯 · Word Search

## 题意

判断 word 能否在字符网格中拼出。相邻字母必须分别位于上下左右相邻的格子，同一格不得重复使用。

官方原题：https://leetcode.cn/problems/word-search/

## 课堂示例

输入：board=["ABCE","SFCS","ADEE"], word="ABCCED"

预期结果：true

实际结果：true

测试来源：力扣公开示例

## 状态含义

index 对应当前要匹配的字母。只有当前字母匹配且 index 到最后时才成功。

## 状态变化

按顺序匹配单词，每次只走到相邻且当前路径没用过的格子。

## Java 解法

```java
import java.util.*;

class Solution {
    public boolean exist(char[][] board, String word) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        boolean[][] used = new boolean[board.length][board[0].length];
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (backtracking(board, word, row, col, 0, used, directions)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean backtracking(char[][] board, String word, int row, int col,
            int index, boolean[][] used, int[][] directions) {
        if (used[row][col] || board[row][col] != word.charAt(index)) {
            return false;
        }
        if (index == word.length() - 1) {
            return true;
        }
        used[row][col] = true;
        boolean found = false;
        for (int[] direction : directions) {
            int nextRow = row + direction[0];
            int nextCol = col + direction[1];
            if (nextRow < 0 || nextRow >= board.length
                    || nextCol < 0 || nextCol >= board[0].length) {
                continue;
            }
            if (backtracking(board, word, nextRow, nextCol, index + 1, used, directions)) {
                found = true;
                break;
            }
        }
        used[row][col] = false;
        return found;
    }
}
```

## 来源与版本

学习扩展。按本题条件选择主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/backtracking/LC0079WordSearch.java

源码指纹：6f1645ae521214f7e85474eef4dec13b20afdc0e4b7353291ca1d634866918ae

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：153 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0079-word-search/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
