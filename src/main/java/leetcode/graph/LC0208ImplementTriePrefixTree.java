package leetcode.graph;

import java.util.*;
import common.TreeSupport;


public class LC0208ImplementTriePrefixTree {
    public static void main(String[] args) {
        TreeSupport.check("Trie", "insert(\"apple\"), search(\"apple\"), search(\"app\"), startsWith(\"app\"), insert(\"app\"), search(\"app\")",
                "[true, false, true, true]", String.valueOf(runSolution()));
    }

    // 测试来源：力扣示例与 Hot100 笔记。
    static Object runSolution() {
        Trie trie = new Trie();
        List<Boolean> answers = new ArrayList<>();
        trie.insert("apple");
        answers.add(trie.search("apple"));
        answers.add(trie.search("app"));
        answers.add(trie.startsWith("app"));
        trie.insert("app");
        answers.add(trie.search("app"));
        return answers;
    }

    static class Trie {
        private static class TrieNode {
            final TrieNode[] children = new TrieNode[26];
            boolean isEnd;
        }

        private final TrieNode root = new TrieNode();

        public Trie() {
        }

        public void insert(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int index = c - 'a';
                if (node.children[index] == null) {
                    node.children[index] = new TrieNode();
                }
                node = node.children[index];
            }
            node.isEnd = true;
        }

        public boolean search(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int index = c - 'a';
                if (node.children[index] == null) {
                    return false;
                }
                node = node.children[index];
            }
            return node.isEnd;
        }

        public boolean startsWith(String prefix) {
            TrieNode node = root;
            for (char c : prefix.toCharArray()) {
                int index = c - 'a';
                if (node.children[index] == null) {
                    return false;
                }
                node = node.children[index];
            }
            return true;
        }
    }
}
