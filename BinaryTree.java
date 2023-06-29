public class BinaryTree<T extends Comparable<T>> {

    Node root;

    public boolean add(T value) {
        if (root != null) {
            if (addNode(root, value) != null) {
                root = rebalance(root);
                root.color = Color.Black;
                return true;
            }
        } else {
            root = new Node(value);
            root.color = Color.Black;
            return true;
        }
        return false;
    }

    private Node addNode(Node node, T value) {
        if (node.value.compareTo(value) == 0)
            return null;
        else if (node.value.compareTo(value) > 0) {
            if (node.left == null) {
                node.left = new Node(value);
                return node.left;
            }
            Node resultNode = addNode(node.left, value);
            node.left = rebalance(node.left);
            return resultNode;
        } else {
            if (node.right == null) {
                node.right = new Node(value);
                return node.right;
            }
            Node resultNode = addNode(node.right, value);
            node.right = rebalance(node.right);
            return resultNode;
        }
    }

    private boolean contain(T value) {
        Node currentNode = root;
        while (currentNode != null) {
            if (currentNode.value.compareTo(value) == 0)
                return true;
            else if (currentNode.value.compareTo(value) > 0)
                currentNode = currentNode.left;
            else
                currentNode = currentNode.right;
        }
        return false;
    }

    public boolean remove(T value) {
        if (!contain(value))
            return false;
        Node deleteNode = root;
        Node prevNode = root;
        while (deleteNode != null) {
            if (deleteNode.value.compareTo(value) == 0) {
                Node currentNode = deleteNode.right;
                if (currentNode == null) {
                    if (deleteNode == root) {
                        root = root.left;
                        root.color = Color.Black;
                        return true;
                    }
                    if (deleteNode.left == null) {
                        deleteNode = null;
                        return true;
                    }
                    if (prevNode.left == deleteNode)
                        prevNode.left = deleteNode.left;
                    else
                        prevNode.right = deleteNode.left;
                    return true;
                }
                while (currentNode.left != null)
                    currentNode = currentNode.left;
                deleteNode.value = currentNode.value;
                currentNode = null;
                return true;
            }
            if (prevNode != deleteNode) {
                if (prevNode.value.compareTo(value) > 0)
                    prevNode = prevNode.left;
                else
                    prevNode = prevNode.right;
            }
            if (deleteNode.value.compareTo(value) > 0)
                deleteNode = deleteNode.left;
            else
                deleteNode = deleteNode.right;
        }
        return false;
    }

    private Node rebalance(Node node) {
        Node currentNode = node;
        boolean needRebalance;
        do {
            needRebalance = false;
            if (currentNode.right != null && currentNode.right.color == Color.Red
                    && (currentNode.left == null || currentNode.left.color == Color.Black)) {
                currentNode = rightSwap(currentNode);
                needRebalance = true;
            }
            if (currentNode.left != null && currentNode.left.color == Color.Red
                    && currentNode.left.left != null && currentNode.left.left.color == Color.Red) {
                currentNode = leftSwap(currentNode);
                needRebalance = true;
            }
            if (currentNode.left != null && currentNode.left.color == Color.Red
                    && currentNode.right != null && currentNode.right.color == Color.Red) {
                colorSwap(currentNode);
                needRebalance = true;
            }
        } while (needRebalance);
        return currentNode;
    }

    private void colorSwap(Node currentNode) {
        currentNode.left.color = Color.Black;
        currentNode.right.color = Color.Black;
        currentNode.color = Color.Red;
    }

    private Node leftSwap(Node currentNode) {
        Node changeNode = currentNode.left;
        Node stepchild = changeNode.right;
        changeNode.right = currentNode;
        currentNode.left = stepchild;
        changeNode.color = currentNode.color;
        currentNode.color = Color.Red;
        return changeNode;
    }

    private Node rightSwap(Node currentNode) {
        Node changeNode = currentNode.right;
        Node stepchild = changeNode.left;
        changeNode.left = currentNode;
        currentNode.right = stepchild;
        changeNode.color = currentNode.color;
        currentNode.color = Color.Red;
        return changeNode;
    }

    private class Node {
        T value;
        Color color;
        Node left;
        Node right;

        Node() {
            this.color = Color.Red;
        }

        Node(T value) {
            this.value = value;
            this.color = Color.Red;
            this.left = null;
            this.right = null;
        }
    }

    enum Color {
        Red, Black
    }
}
