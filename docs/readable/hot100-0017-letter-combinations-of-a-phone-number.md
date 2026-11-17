# 17 · 电话号码的字母组合 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0017-letter-combinations-of-a-phone-number/

Hot100 · 回溯 · Letter Combinations of a Phone Number

## 题意

输入由 2 到 9 组成的数字串。按电话键盘映射为每一位各选一个字母，返回所有组合。空输入返回空列表。

官方原题：https://leetcode.cn/problems/letter-combinations-of-a-phone-number/

## 课堂示例

输入：digits = 23

预期结果：[ad, ae, af, bd, be, bf, cd, ce, cf]

实际结果：[ad, ae, af, bd, be, bf, cd, ce, cf]

测试来源：算法学习总笔记与项目已有 main

## 状态含义

index 是当前数字位置，path 的长度等于已处理的数字数。

## 状态变化

每层处理一个数字，从它对应的字母里挑一个。

## Java 解法

```java
import java.util.*;

class Solution {
    private final String[] letters = {
        "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
    };

    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits.isEmpty()) {
            return result;
        }
        StringBuilder path = new StringBuilder();
        backtracking(digits, 0, path, result);
        return result;
    }

    private void backtracking(String digits, int index,
            StringBuilder path, List<String> result) {
        if (index == digits.length()) {
            result.add(path.toString());
            return;
        }
        // 当前数字对应的字母。
        String choices = letters[digits.charAt(index) - '0'];
        for (int i = 0; i < choices.length(); i++) {
            path.append(choices.charAt(i));
            backtracking(digits, index + 1, path, result);
            path.deleteCharAt(path.length() - 1);
        }
    }
}
```

## 来源与版本

沿用总笔记及仓库中本题的主解。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/backtracking/LC0017LetterCombinationsOfAPhoneNumber.java

源码指纹：f30bed333f71ae53df738f25c9f98a5edc7955279517c616649dc17657f87544

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：124 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0017-letter-combinations-of-a-phone-number/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
