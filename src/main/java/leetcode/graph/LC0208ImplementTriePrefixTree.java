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
        private static class Node {
            Node[] children = new Node[26];
            boolean end;
        }

        private final Node root = new Node();

        public Trie() {
        }

        public void insert(String word) {
            Node node = root;
            for (int i = 0; i < word.length(); i++) {
                int index = word.charAt(i) - 'a';
                if (node.children[index] == null) {
                    node.children[index] = new Node();
                }
                node = node.children[index];
            }
            node.end = true;
        }

        public boolean search(String word) {
            Node node = find(word);
            return node != null && node.end;
        }

        public boolean startsWith(String prefix) {
            return find(prefix) != null;
        }

        private Node find(String text) {
            Node node = root;
            for (int i = 0; i < text.length(); i++) {
                int index = text.charAt(i) - 'a';
                node = node.children[index];
                if (node == null) {
                    return null;
                }
            }
            return node;
        }
    }
}
