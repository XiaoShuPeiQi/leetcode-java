import fs from 'node:fs';
import path from 'node:path';
const sourceUrl='https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng';
const response=JSON.parse(fs.readFileSync('.work/binarytree/tmp/source.feishu.json','utf8').replace(/^\uFEFF/,''));
const text=response.data.document.content.replace(/\r/g,'');
const map={144:'BinaryTreePreorderTraversal',94:'BinaryTreeInorderTraversal',145:'BinaryTreePostorderTraversal',102:'BinaryTreeLevelOrderTraversal',107:'BinaryTreeLevelOrderTraversalII',199:'BinaryTreeRightSideView',637:'AverageOfLevelsInBinaryTree',429:'NAryTreeLevelOrderTraversal',515:'FindLargestValueInEachTreeRow',116:'PopulatingNextRightPointersInEachNode',117:'PopulatingNextRightPointersInEachNodeII',226:'InvertBinaryTree',101:'SymmetricTree',104:'MaximumDepthOfBinaryTree',559:'MaximumDepthOfNAryTree',111:'MinimumDepthOfBinaryTree',222:'CountCompleteTreeNodes',110:'BalancedBinaryTree',257:'BinaryTreePaths',404:'SumOfLeftLeaves',513:'FindBottomLeftTreeValue',112:'PathSum',113:'PathSumII',106:'ConstructBinaryTreeFromInorderAndPostorderTraversal',105:'ConstructBinaryTreeFromPreorderAndInorderTraversal',654:'MaximumBinaryTree',617:'MergeTwoBinaryTrees',700:'SearchInABinarySearchTree',98:'ValidateBinarySearchTree',530:'MinimumAbsoluteDifferenceInBST',501:'FindModeInBinarySearchTree',236:'LowestCommonAncestorOfABinaryTree',235:'LowestCommonAncestorOfABinarySearchTree',701:'InsertIntoABinarySearchTree',450:'DeleteNodeInABST',669:'TrimABinarySearchTree',108:'ConvertSortedArrayToBinarySearchTree',538:'ConvertBSTToGreaterTree'};
const base='4, 2, 6, 1, 3, 5, 7';
const cases={
144:['root','List.of(4, 2, 1, 3, 6, 5, 7)'],94:['root','List.of(1, 2, 3, 4, 5, 6, 7)'],145:['root','List.of(1, 3, 2, 5, 7, 6, 4)'],
102:['root','List.of(List.of(4), List.of(2, 6), List.of(1, 3, 5, 7))'],107:['root','List.of(List.of(1, 3, 5, 7), List.of(2, 6), List.of(4))'],199:['root','List.of(4, 6, 7)'],637:['root','List.of(4.0, 4.0, 4.0)'],
429:['root','List.of(List.of(1), List.of(3, 2, 4), List.of(5, 6))','Node root = naryTree();','N 叉树：1 的孩子为 3、2、4；3 的孩子为 5、6'],
515:['root','List.of(4, 6, 7)'],116:['root','List.of(List.of(1), List.of(2, 3), List.of(4, 5, 6, 7))','Node root = nextTree(1, 2, 3, 4, 5, 6, 7);','root = [1, 2, 3, 4, 5, 6, 7]'],
117:['root','List.of(List.of(1), List.of(2, 3), List.of(4, 5))','Node root = nextTree(1, 2, 3, 4, null, null, 5);','root = [1, 2, 3, 4, null, null, 5]'],
226:['root','TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(4, 6, 2, 7, 5, 3, 1))'],
101:['root','true','TreeNode root = TreeSupport.fromLevelOrder(1, 2, 2, 3, 4, 4, 3);','root = [1, 2, 2, 3, 4, 4, 3]'],104:['root','3'],559:['root','3','Node root = naryTree();','N 叉树：1 的孩子为 3、2、4；3 的孩子为 5、6'],
111:['root','2','TreeNode root = TreeSupport.fromLevelOrder(1, null, 2);','root = [1, null, 2]'],222:['root','6','TreeNode root = TreeSupport.fromLevelOrder(1, 2, 3, 4, 5, 6);','root = [1, 2, 3, 4, 5, 6]'],
110:['root','false','TreeNode root = TreeSupport.fromLevelOrder(1, 2, 3, 4, null, null, null, 5);','root = [1, 2, 3, 4, null, null, null, 5]'],
257:['root','List.of("4->2->1", "4->2->3", "4->6->5", "4->6->7")'],404:['root','6'],513:['root','1'],
112:['root, 7','true','TreeNode root = TreeSupport.fromLevelOrder('+base+');','root = ['+base+'], targetSum = 7'],
113:['root, 7','List.of(List.of(4, 2, 1))','TreeNode root = TreeSupport.fromLevelOrder('+base+');','root = ['+base+'], targetSum = 7'],
106:['inorder, postorder','TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder('+base+'))','int[] inorder = {1, 2, 3, 4, 5, 6, 7};\nint[] postorder = {1, 3, 2, 5, 7, 6, 4};','inorder = [1, 2, 3, 4, 5, 6, 7], postorder = [1, 3, 2, 5, 7, 6, 4]'],
105:['preorder, inorder','TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder('+base+'))','int[] preorder = {4, 2, 1, 3, 6, 5, 7};\nint[] inorder = {1, 2, 3, 4, 5, 6, 7};','preorder = [4, 2, 1, 3, 6, 5, 7], inorder = [1, 2, 3, 4, 5, 6, 7]'],
654:['nums','TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(6, 3, 5, null, 2, 0, null, null, 1))','int[] nums = {3, 2, 1, 6, 0, 5};','nums = [3, 2, 1, 6, 0, 5]'],
617:['root1, root2','TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(3, 4, 5, 5, 4, null, 7))','TreeNode root1 = TreeSupport.fromLevelOrder(1, 3, 2, 5);\nTreeNode root2 = TreeSupport.fromLevelOrder(2, 1, 3, null, 4, null, 7);','root1 = [1, 3, 2, 5], root2 = [2, 1, 3, null, 4, null, 7]'],
700:['root, 2','TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(2, 1, 3))','TreeNode root = TreeSupport.fromLevelOrder('+base+');','root = ['+base+'], val = 2'],
98:['root','false','TreeNode root = TreeSupport.fromLevelOrder(10, null, 15, 6);','root = [10, null, 15, 6]'],530:['root','1'],
501:['root','new int[] {2}','TreeNode root = TreeSupport.fromLevelOrder(2, 1, 2);','root = [2, 1, 2]'],
236:['root, p, q','TreeSupport.toLevelOrder(root.left)','TreeNode root = TreeSupport.fromLevelOrder('+base+');\nTreeNode p = root.left.left;\nTreeNode q = root.left.right;','root = ['+base+'], p = 节点 1, q = 节点 3'],
235:['root, p, q','TreeSupport.toLevelOrder(root.left)','TreeNode root = TreeSupport.fromLevelOrder('+base+');\nTreeNode p = root.left.left;\nTreeNode q = root.left.right;','root = ['+base+'], p = 节点 1, q = 节点 3'],
701:['root, 5','TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(4, 2, 7, 1, 3, 5))','TreeNode root = TreeSupport.fromLevelOrder(4, 2, 7, 1, 3);','root = [4, 2, 7, 1, 3], val = 5'],
450:['root, 4','TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(5, 2, 6, 1, 3, null, 7))','TreeNode root = TreeSupport.fromLevelOrder('+base+');','root = ['+base+'], key = 4'],
669:['root, 2, 4','TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(3, 2, 4))','TreeNode root = TreeSupport.fromLevelOrder(3, 1, 4, null, 2);','root = [3, 1, 4, null, 2], low = 2, high = 4'],
108:['nums','TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder('+base+'))','int[] nums = {1, 2, 3, 4, 5, 6, 7};','nums = [1, 2, 3, 4, 5, 6, 7]'],
538:['root','TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(22, 27, 13, 28, 25, 18, 7))']
};
const nodeNary=`    static class Node {
        public int val;
        public List<Node> children;
        public Node(int val) { this.val = val; this.children = new ArrayList<>(); }
    }

    private static Node naryTree() {
        // 从上到下逐层创建；同层从左到右连接。
        Node root = new Node(1);
        Node three = new Node(3), two = new Node(2), four = new Node(4);
        root.children.addAll(List.of(three, two, four));
        three.children.addAll(List.of(new Node(5), new Node(6)));
        return root;
    }
`;
const nodeNext=`    static class Node {
        public int val;
        public Node left, right, next;
        public Node(int val) { this.val = val; }
    }

    private static Node nextTree(Integer... values) {
        Node root = new Node(values[0]);
        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(root);
        int index = 1;
        // 按层构建，先父节点，再下一层孩子。
        while (!queue.isEmpty() && index < values.length) {
            Node parent = queue.poll();
            Integer left = values[index++];
            if (left != null) { parent.left = new Node(left); queue.offer(parent.left); }
            if (index < values.length) {
                Integer right = values[index++];
                if (right != null) { parent.right = new Node(right); queue.offer(parent.right); }
            }
        }
        return root;
    }

    private static List<List<Integer>> nextLevels(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Node> level = new ArrayList<>();
            for (int size = queue.size(); size > 0; size--) {
                Node node = queue.poll();
                level.add(node);
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            List<Integer> values = new ArrayList<>();
            for (int i = 0; i < level.size(); i++) {
                Node node = level.get(i);
                Node expectedNext = i + 1 < level.size() ? level.get(i + 1) : null;
                if (node.next != expectedNext) throw new AssertionError("next 指针错误: " + node.val);
                values.add(node.val);
            }
            result.add(values);
        }
        return result;
    }
`;
const problems=[];
fs.mkdirSync('src/main/java/leetcode/binarytree',{recursive:true});
for(const s of text.matchAll(/^### (3\.\d+) (.+)\n([\s\S]*?)(?=^### 3\.|^## 4\.)/gm)){
 const heading=s[2].match(/\[(\d+)\. ([^\]]+)\]\(([^)]+)\)/);const id=Number(heading[1]);
 const cls='LC'+String(id).padStart(4,'0')+map[id],file='src/main/java/leetcode/binarytree/'+cls+'.java';
 const all=[...s[3].matchAll(/##### (.+)\n+```Java\n([\s\S]*?)```/g)];
 const variants=all.map((m,i)=>{const className=i===0?'Solution':m[1].includes('迭代')?'SolutionIterative':id===104?'SolutionDepth':'SolutionBounds';
 const source=m[2].trimEnd();const body=source.slice(source.indexOf('class Solution'));
 const local=body.replace(/^class Solution/,'static class '+className).split('\n').map(l=>'    '+l).join('\n');
 const method=source.match(/public\s+(?:[\w<>?, \[\]]+)\s+(\w+)\s*\(/)[1];return {label:m[1],className,source_java:source,local,method};});
 const c=cases[id],setup=c[2]||'TreeNode root = TreeSupport.fromLevelOrder('+base+');',input=c[3]||'root = ['+base+']';
 const testSource=setup.includes(base)||[105,106,108].includes(id)?'飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）':[111,98].includes(id)?'飞书原文 4.4 的最小反例':'本地设计的代表性用例；原文未提供该题独立输入';
 const ret=variants[0].source_java.match(/public\s+([\w<>?, \[\]]+)\s+\w+\s*\(/)[1];
 const normalize=x=>ret==='TreeNode'?'TreeSupport.toLevelOrder('+x+')':ret==='Node'?'nextLevels('+x+')':x;
 const runs=variants.map(v=>`    private static void run${v.className}() {
${setup.split('\n').map(l=>'        '+l).join('\n')}
        Object expected = ${c[1]};
        ${v.className} solution = new ${v.className}();
        var answer = solution.${v.method}(${c[0]});
${[236,235].includes(id)?'        if (answer != root.left) throw new AssertionError("必须返回原树中的祖先节点");\n':''}        Object actual = ${normalize('answer')};
        TreeSupport.check("${v.className}", "${input}", expected, actual${[257,113].includes(id)?', true':''});
    }
`).join('\n');
 const main=`    public static void main(String[] args) {
        // 测试来源：${testSource}。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
${variants.map(v=>'        run'+v.className+'();').join('\n')}
    }
`;
 const content='package leetcode.binarytree;\n\nimport common.TreeNode;\nimport common.TreeSupport;\nimport java.util.*;\n\n// 原文：'+sourceUrl+'，'+s[1]+'。\npublic class '+cls+' {\n\n'+main+'\n'+runs+'\n'+([429,559].includes(id)?nodeNary:[116,117].includes(id)?nodeNext:'')+'\n'+variants.map(v=>v.local).join('\n\n')+'\n}\n';
 if(fs.existsSync(file)) {
  if(fs.readFileSync(file,'utf8')!==content)throw Error('已有题目内容不同，保留并停止：'+id);
 } else fs.writeFileSync(file,content,{flag:'wx'});
 problems.push({id,title:heading[2],english:map[id].replace(/([a-z0-9])([A-Z])/g,'$1 $2'),slug:String(id).padStart(4,'0')+'-'+new URL(heading[3]).pathname.split('/').filter(Boolean).at(-1),section:s[1],java_file:file,className:cls,input,test_source:testSource,variants:variants.map(({local,...v})=>v),expected_java:c[1]});
}
fs.mkdirSync('imports',{recursive:true});
fs.writeFileSync('imports/二叉树算法.feishu.json',JSON.stringify(response,null,2)+'\n');
fs.writeFileSync('imports/二叉树算法.source.md',text+'\n');
fs.writeFileSync('docs/二叉树算法.md',text+'\n');
fs.writeFileSync('imports/二叉树算法.import.json',JSON.stringify({source_url:sourceUrl,revision_id:response.data.document.revision_id,problems},null,2)+'\n');
console.log('导入 '+problems.length+' 题，'+problems.reduce((n,p)=>n+p.variants.length,0)+' 个解法。');
