# 93 · 复原 IP 地址 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0093-restore-ip-addresses/

回溯 · Restore IP Addresses

## 题意

**有效 IP 地址** 正好由四个整数（每个整数位于 `0` 到 `255` 之间组成，且不能含有前导 `0`），整数之间用 `'.'` 分隔。

- 例如：`"0.1.2.201"` 和` "192.168.1.1"` 是 **有效** IP 地址，但是 `"0.011.255.245"`、`"192.168.1.312"` 和 `"192.168@1.1"` 是 **无效** IP 地址。

给定一个只包含数字的字符串 `s` ，用以表示一个 IP 地址，返回所有可能的**有效 IP 地址**，这些地址可以通过在 `s` 中插入 `'.'` 来形成。你 **不能** 重新排序或删除 `s` 中的任何数字。你可以按 **任何** 顺序返回答案。

**示例 1：**

```text
输入：s = "25525511135"
输出：["255.255.11.135","255.255.111.35"]
```

**示例 2：**

```text
输入：s = "0000"
输出：["0.0.0.0"]
```

**示例 3：**

```text
输入：s = "101023"
输出：["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
```

**提示：**

- `1 <= s.length <= 20`

- `s` 仅由数字组成

官方原题：https://leetcode.cn/problems/restore-ip-addresses

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0093.Restore%20IP%20Addresses/README.md

## 回放用例

输入：s = 010010

预期结果：[0.10.0.10, 0.100.1.0]

实际结果：[0.10.0.10, 0.100.1.0]

测试来源：飞书回溯笔记 3.7 的执行过程示例

## Java 解法

```java
import java.util.*;

class Solution {
    public List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<>();
        List<String> path = new ArrayList<>();
        backtracking(s, 0, path, result);
        return result;
    }

    private void backtracking(String s, int startIndex,
            List<String> path, List<String> result) {
        // 四段后，必须恰好用完字符串。
        if (path.size() == 4) {
            if (startIndex == s.length()) {
                result.add(String.join(".", path));
            }
            return;
        }
        int segments = 4 - path.size();
        int characters = s.length() - startIndex;
        // 剩余每段需要 1～3 个字符。
        if (characters < segments || characters > 3 * segments) {
            return;
        }
        int value = 0;
        for (int end = startIndex; end < s.length() && end < startIndex + 3; end++) {
            // 只允许单个 0，禁止前导零。
            if (end > startIndex && s.charAt(startIndex) == '0') {
                break;
            }
            value = value * 10 + s.charAt(end) - '0';
            // 继续延长只会更大。
            if (value > 255) {
                break;
            }
            String segment = s.substring(startIndex, end + 1);
            path.add(segment);
            backtracking(s, end + 1, path, result);
            path.remove(path.size() - 1);
        }
    }
}
```

## 来源与版本

最新飞书算法与本地导入版本一致；注释可能不同。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/backtracking/LC0093RestoreIPAddresses.java

源码指纹：0eecd39a3a0f1ea12726e8587b428c28cb7aeffb8f82a24cceb825912fcc12b9

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/VSYBdzUkuoIxf3xuSfVcsgKkn3g（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：211 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0093-restore-ip-addresses/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
