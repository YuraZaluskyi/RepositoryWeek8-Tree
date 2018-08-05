

public class BinaryTree<K extends Comparable<K>, V> {

    private TreeNode<K, V> root;
    private int size;

    private static class TreeNode<K, V> {
        K key;
        V value;

        TreeNode<K, V> root;
        TreeNode<K, V> leftHeir;
        TreeNode<K, V> rightHeir;

        public TreeNode(K key, V value, TreeNode<K, V> root, TreeNode<K, V> leftHeir, TreeNode<K, V> rightHeir) {
            this.key = key;
            this.value = value;
            this.root = root;
            this.leftHeir = leftHeir;
            this.rightHeir = rightHeir;
        }
    }


    //    Methods_____________________________________________________________________________________________________

    //    1. put in a tree
    public void put(K key, V value) {
        TreeNode<K, V> newTreeNode = new TreeNode<>(key, value, null, null, null);
        if (root == null) {
            root = newTreeNode;
            size++;
        } else {
            TreeNode<K, V> currentRoot = root;
            TreeNode<K, V> ancestor;
            while (true) {
                Comparable<K> currentKey = currentRoot.key;
                int compare = currentKey.compareTo(key);
                ancestor = currentRoot;
                if (compare > 0) {
                    currentRoot = currentRoot.leftHeir;
                    if (currentRoot == null) {
                        ancestor.leftHeir = newTreeNode;
                        newTreeNode.root = ancestor;
                        size++;
                        return;
                    }
                } else {
                    currentRoot = currentRoot.rightHeir;
                    if (currentRoot == null) {
                        ancestor.rightHeir = newTreeNode;
                        newTreeNode.root = ancestor;
                        size++;
                        return;
                    }
                }

            }

        }

    }

    //    2. print a tree
    public String toString() {
        String s = "[";
        return s + getString(root) + "]" + " \n" + "size = " + size;
    }

    private String getString(TreeNode<K, V> root) {
        return root == null ? "" : getString(root.leftHeir) + " " + root.key + " -> " + root.value + " " + getString(root.rightHeir);
    }


    //    3. find a Node by key
    public TreeNode findNode(K key) {
        TreeNode<K, V> currentNode = root;
        if (currentNode == null) {
            return null;
        }
        while (currentNode.key != key) {
            Comparable<K> currentKey = currentNode.key;
            int compare = currentKey.compareTo(key);
            if (compare > 0) {
                currentNode = currentNode.leftHeir;
            } else {
                currentNode = currentNode.rightHeir;
            }
        }
        return currentNode;
    }

    //    4. print a node
    public String printNode(TreeNode<K, V> root) {
        return root == null ? "" : "[ " + root.key + " -> " + root.value + " ]";
    }

    //    5. set a value by key
    public void setValue(K key, V value) {
        TreeNode<K, V> treeNode;
        treeNode = findNode(key);
        treeNode.value = value;
    }

    //    6. get a max value from tree
    public TreeNode getNodeMaxKey() {
        TreeNode<K, V> currentNode = root;
        while (currentNode.rightHeir != null) {
            currentNode = currentNode.rightHeir;
        }
        return currentNode;
    }

    //    7. get min value from tree
    public TreeNode getNodeMinKey() {
        TreeNode<K, V> currentNode = root;
        while (currentNode.leftHeir != null) {
            currentNode = currentNode.leftHeir;
        }
        return currentNode;
    }


    //    7W. find successor (it is auxiliary for methods №8 "remove a node by key")
    private TreeNode<K, V> findSuccessor(TreeNode<K, V> delNodeRightHeir) {
        while (delNodeRightHeir.leftHeir != null) {
            delNodeRightHeir = delNodeRightHeir.leftHeir;
        }
        return delNodeRightHeir;
    }


    //    8. remove a node by key
    public void removeNode(K key) {
        TreeNode<K, V> delNode;
        TreeNode<K, V> ancestor;
        TreeNode<K, V> successor;
        TreeNode<K, V> ancestorSuccessor;
        delNode = findNode(key);
        ancestor = delNode.root;

//   if deleted node has not any descendent //////////////////////
        if (delNode.leftHeir == null && delNode.rightHeir == null) {
            if (ancestor == null) {
                root = null;
                size--;
            } else {
                if (ancestor.rightHeir == delNode) {
                    ancestor.rightHeir = null;
                } else {
                    ancestor.leftHeir = null;
                }
                size--;
            }

//  if deleted node has at least one descendent  /////////////////////
        } else if ((delNode.leftHeir != null && delNode.rightHeir == null) || (delNode.leftHeir == null && delNode.rightHeir != null)) {
            if (ancestor == null) {
                if (delNode.leftHeir != null) {
                    root = delNode.leftHeir;
                    root.root = null;
                    size--;
                } else {
                    root = delNode.rightHeir;
                    root.root = null;
                    size--;
                }
            } else {
                if (ancestor.rightHeir == delNode && delNode.leftHeir != null) {
                    ancestor.rightHeir = delNode.leftHeir;
                    size--;
                } else if (ancestor.rightHeir == delNode && delNode.rightHeir != null) {
                    ancestor.rightHeir = delNode.rightHeir;
                    size--;
                } else if (ancestor.leftHeir == delNode && delNode.leftHeir != null) {
                    ancestor.leftHeir = delNode.leftHeir;
                    size--;
                } else if (ancestor.leftHeir == delNode && delNode.rightHeir != null) {
                    ancestor.leftHeir = delNode.rightHeir;
                    size--;
                }

            }

//  if deleted node has both descendents /////////////////////////////////////////////////////////////
        } else {

//  if right heir of deleted node has not left heir //////////////////////////////////////////////////
            if (delNode.rightHeir.leftHeir == null) {
                successor = delNode.rightHeir;
                successor.root = ancestor;
                successor.leftHeir = delNode.leftHeir;
                delNode.leftHeir.root = successor;

                if (ancestor.rightHeir == delNode) {

                    ancestor.rightHeir = successor;
                    size--;

                } else if (ancestor.leftHeir == delNode) {

                    ancestor.leftHeir = successor;
                    size--;
                }

//  if right heir of deleted node has left heir //////////////////////////////////////////////////
            } else if (delNode.rightHeir.leftHeir != null) {

                successor = findSuccessor(delNode.rightHeir);
                ancestorSuccessor = successor.root;
                successor.root = ancestor;
                successor.leftHeir = delNode.leftHeir;
                successor.rightHeir = delNode.rightHeir;
                delNode.leftHeir.root = successor;
                ancestorSuccessor.leftHeir = null;
                ancestorSuccessor = successor.rightHeir;

                if (successor.rightHeir == null) {

                    if (ancestor.rightHeir == delNode) {
                        ancestor.rightHeir = successor;
                        size--;

                    } else if (ancestor.leftHeir == delNode) {
                        ancestor.leftHeir = successor;
                        size--;
                    }

                } else if (successor.rightHeir != null) {

                    if (ancestor.rightHeir == delNode) {
                        ancestor.rightHeir = successor;
                        successor.leftHeir = delNode.leftHeir;
                        size--;

                    } else if (ancestor.leftHeir == delNode) {
                        ancestor.leftHeir = successor;
                        successor.leftHeir = delNode.leftHeir;
                        size--;
                    }
                }
            }
        }
    }
}
