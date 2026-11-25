# 202 · 快乐数 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0202-happy-number/

哈希表 · Happy Number

## 题意

编写一个算法来判断一个数 `n` 是不是快乐数。

**「快乐数」** 定义为：

- 对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和。

- 然后重复这个过程直到这个数变为 1，也可能是 **无限循环** 但始终变不到 1。

- 如果这个过程 **结果为** 1，那么这个数就是快乐数。

如果 `n` 是 *快乐数* 就返回 `true` ；不是，则返回 `false` 。

**示例 1：**

```text
输入：n = 19
输出：true
解释：
12 + 92 = 82
82 + 22 = 68
62 + 82 = 100
12 + 02 + 02 = 1
```

**示例 2：**

```text
输入：n = 2
输出：false
```

**提示：**

- `1 <= n <= 2^(31) - 1`

官方原题：https://leetcode.cn/problems/happy-number

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0200-0299/0202.Happy%20Number/README.md

## 回放用例

输入：n = 19

预期结果：true

实际结果：true

测试来源：与飞书本题“实例走过程”使用相同输入。

## 状态含义

主解用 Set 保留历史状态，便于理解。这里记录的是每一轮的数，不是各个数位；getNext只负责把一个数变成下一个数。扩展用快慢指针检测同一条状态链，省掉集合。

## 状态变化

这道题本质是：反复生成下一个状态，判断最终到1，还是进入重复状态的环。

## Java 解法

```java
import java.util.*;

class Solution {
    public boolean isHappy(int n) {
        Set<Integer> seen = new HashSet<>();
        while (n != 1) {
            if (seen.contains(n)) {
                return false;
            }
            seen.add(n); // 保存本轮整个数，下一轮检查是否重复。
            n = getNext(n);
        }
        return true;
    }

    private int getNext(int n) {
        int sum = 0;
        while (n > 0) {
            int digit = n % 10;
            sum += digit * digit;
            n /= 10;
        }
        return sum;
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/hashtable/LC0202HappyNumber.java

源码指纹：a098bb549040e95168f1d3d8e2df94b0f544331f44984f0fd138f1e699022331

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/F4qJw6Pr2if4LkkJBjBc8aM3nXd（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：86 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0202-happy-number/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
