package leetcode.hashtable;

import java.util.*;

public class LC0049GroupAnagrams {
    public static void main(String[] args) {
        // 测试来源：Hot100笔记中相同的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf
        String[] strs = new String[]{"eat","tea","tan","ate","nat","bat"};
        Object actual = new Solution().groupAnagrams(strs);
        String expected = "[[\"ate\",\"eat\",\"tea\"],[\"bat\"],[\"nat\",\"tan\"]]";
        String result = canonical(actual);
        System.out.println("Input: " + "strs=[\"eat\",\"tea\",\"tan\",\"ate\",\"nat\",\"bat\"]");
        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Actual:");
        System.out.println(result);
        if (!expected.equals(result)) {
            throw new AssertionError("Result: FAIL");
        }
        System.out.println("Result: PASS");
    }


    private static String canonical(Object value) {
        if (value instanceof int[]) {
            return Arrays.toString((int[]) value).replace(" ", "");
        }
        if (value instanceof int[][]) {
            return Arrays.deepToString((int[][]) value).replace(" ", "");
        }
        if (value instanceof List<?>) {
            List<String> parts = new ArrayList<>();
            for (Object item : (List<?>) value) {
                parts.add(canonical(item));
            }
            Collections.sort(parts);
            return "[" + String.join(",", parts) + "]";
        }
        if (value instanceof String) {
            return "\"" + value + "\"";
        }
        return String.valueOf(value);
    }


    static class Solution {
        public List<List<String>> groupAnagrams(String[] strs) {
            List<List<String>> res = new ArrayList<>(); // 保存最终的各组单词
            Map<String, List<String>> map = new HashMap<>();

            for (String str : strs) {
                char[] chars = str.toCharArray();
                Arrays.sort(chars);
                String key = new String(chars);

                if (!map.containsKey(key)) { // 第一次遇到这个key，先创建一组
                    List<String> group = new ArrayList<>();
                    map.put(key, group);
                }
                List<String> group = map.get(key);
                group.add(str); // 把原单词放入对应分组
            }

            for (List<String> group : map.values()) {
                res.add(group);
            }
            return res;
        }
    }

    // 保留此前版本，便于对照；main和Hot100调试器使用上方的清晰写法。
    static class SolutionOriginal {
        public List<List<String>> groupAnagrams(String[] strs) {
            Map<String, List<String>> map = new HashMap<>();
            for (String str : strs) {
                char[] chars = str.toCharArray();
                Arrays.sort(chars);
                String key = new String(chars); // 相同字符组成的单词共用一个key
                map.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
            }
            return new ArrayList<>(map.values());
        }
    }
}
