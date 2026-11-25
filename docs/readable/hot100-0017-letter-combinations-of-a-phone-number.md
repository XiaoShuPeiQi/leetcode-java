# Hot100 第 57 题 · 力扣 17 · 电话号码的字母组合 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0017-letter-combinations-of-a-phone-number/

Hot100 · 回溯 · Letter Combinations of a Phone Number

## 题意

给定一个仅包含数字 `2-9` 的字符串，返回所有它能表示的字母组合。答案可以按 **任意顺序** 返回。

给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0017.Letter%20Combinations%20of%20a%20Phone%20Number/images/1752723054-mfIHZs-image.png)

**示例 1：**

```text
输入：digits = "23"
输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
```

**示例 2：**

```text
输入：digits = "2"
输出：["a","b","c"]
```

**提示：**

- `1 <= digits.length <= 4`

- `digits[i]` 是范围 `['2', '9']` 的一个数字。

官方原题：https://leetcode.cn/problems/letter-combinations-of-a-phone-number

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0017.Letter%20Combinations%20of%20a%20Phone%20Number/README.md

## 回放用例

输入：digits = 23

预期结果：[ad, ae, af, bd, be, bf, cd, ce, cf]

实际结果：[ad, ae, af, bd, be, bf, cd, ce, cf]

测试来源：飞书 Hot100 原有课堂示例

## 解法

按数字位置逐层回溯

## 状态含义

进入第 `num` 层时，`temp.length() == num`。到达 `digits.length()` 时用 `temp.toString()` 保存结果。

## 状态变化

每层从当前数字的映射中选一个字母，遍历完所有数字后收集字符串。

## Java 解法

```java
import java.util.*;

class Solution {
    private List<String> list;
    private final StringBuilder temp = new StringBuilder();

    public List<String> letterCombinations(String digits) {
        list = new ArrayList<>();
        if (digits == null || digits.isEmpty()) {
            return list;
        }
        String[] numString = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        backtracking(digits, numString, 0);
        return list;
    }

    private void backtracking(String digits, String[] numString, int num) {
        if (num == digits.length()) {
            list.add(temp.toString());
            return;
        }

        String letters = numString[digits.charAt(num) - '0'];
        for (int i = 0; i < letters.length(); i++) {
            temp.append(letters.charAt(i));
            backtracking(digits, numString, num + 1);
            temp.deleteCharAt(temp.length() - 1);
        }
    }
}
```

## 来源与版本

算法流程来自代码随想录的 Java 解法，保留飞书原有课堂示例。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/backtracking/LC0017LetterCombinationsOfAPhoneNumber.java

源码指纹：5e45d78a96be7bd6a5b757b556102b592193e471794bbc0645742839cba5e651

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnJZt5bUxUtIuxQqa58dyt4g（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0017.letter-combinations-of-a-phone-number.html#java

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：124 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0017-letter-combinations-of-a-phone-number/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
