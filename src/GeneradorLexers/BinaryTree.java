package GeneradorLexers;

import java.io.Serializable;
import java.util.HashSet;

/**
 * A GeneradorLexers.BinaryTree consists of "nodes"--each "node" is itself a GeneradorLexers.BinaryTree.
 * Each node has a parent (unless it is the root), may have a left child,
 * and may have a right child. This class implements loop-free binary trees,
 * allowing shared subtrees.
 *
 * @author David Matuszek
 * @version Jan 25, 2004
 */
public class BinaryTree  implements Serializable {
    /**
     * The value (data) in this node of the binary tree; may be of
     * any object type.
     */
    public String value;
    private BinaryTree leftChild;
    private BinaryTree rightChild;
    private int position;
    private HashSet<Integer> firstPos, lastPos, followPos;
    private boolean nullable;

    /**
     * Constructor for GeneradorLexers.BinaryTree.
     *
     * @param value The value to be placed in the root.
     * @param leftChild The left child of the root (may be null).
     * @param rightChild The right child of the root (may be null).
     */
    public BinaryTree(String value, BinaryTree leftChild, BinaryTree rightChild) {
        this.value = value;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.firstPos = new HashSet<Integer>();
        this.lastPos = new HashSet<Integer>();
        this.followPos = new HashSet<Integer>();
        this.nullable = false;
    }

    /**
     * Constructor for a GeneradorLexers.BinaryTree leaf node (that is, with no children).
     *
     * @param value The value to be placed in the root.
     */
    public BinaryTree(String value) {
        this(value, null, null);
    }


    /**
     * Getter method for the value in this GeneradorLexers.BinaryTree node.
     *
     * @return The value in this node.
     */
    public String getValue() {
        return value;
    }

    /**
     * Getter method for left child of this GeneradorLexers.BinaryTree node.
     *
     * @return The left child (<code>null</code> if no left child).
     */
    public BinaryTree getLeftChild() {
        return leftChild;
    }

    /**
     * Getter method for right child of this GeneradorLexers.BinaryTree node.
     *
     * @return The right child (<code>null</code> if no right child).
     */
    public BinaryTree getRightChild() {
        return rightChild;
    }

    /**
     * Sets the left child of this GeneradorLexers.BinaryTree node to be the
     * given subtree. If the node previously had a left child,
     * it is discarded. Throws an <code>IllegalArgumentException</code>
     * if the operation would cause a loop in the binary tree.
     *
     * @param subtree The node to be added as the new left child.
     * @throws IllegalArgumentException If the operation would cause
     *         a loop in the binary tree.
     */
    public void setLeftChild(BinaryTree subtree) throws IllegalArgumentException {
        if (contains(subtree, this)) {
            throw new IllegalArgumentException(
                    "Subtree " + this +" already contains " + subtree);
        }
        leftChild = subtree;
    }

    /**
     * Sets the right child of this GeneradorLexers.BinaryTree node to be the
     * given subtree. If the node previously had a right child,
     * it is discarded. Throws an <code>IllegalArgumentException</code>
     * if the operation would cause a loop in the binary tree.
     *
     * @param subtree The node to be added as the new right child.
     * @throws IllegalArgumentException If the operation would cause
     *         a loop in the binary tree.
     */
    public void setRightChild(BinaryTree subtree) throws IllegalArgumentException {
        if (contains(subtree, this)) {
            throw new IllegalArgumentException(
                    "Subtree " + this +" already contains " + subtree);
        }
        rightChild = subtree;
    }

    /**
     * Sets the value in this GeneradorLexers.BinaryTree node.
     *
     * @param value The new value.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Tests whether this node is a leaf node.
     *
     * @return <code>true</code> if this GeneradorLexers.BinaryTree node has no children.
     */
    public boolean isLeaf() {
        return leftChild == null && rightChild == null;
    }

    /**
     * Tests whether this GeneradorLexers.BinaryTree is equal to the given object.
     * To be considered equal, the object must be a GeneradorLexers.BinaryTree,
     * and the two binary trees must have equal values in their
     * roots, equal left subtrees, and equal right subtrees.
     *
     * @return <code>true</code> if the binary trees are equal.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
        if (o == null || !(o instanceof BinaryTree)) {
            return false;
        }
        BinaryTree otherTree = (BinaryTree) o;
        return equals(value, otherTree.value)
                && equals(leftChild, otherTree.getLeftChild())
                && equals(rightChild, otherTree.getRightChild());
    }

    /**
     * Tests whether its two arguments are equal.
     * This method simply checks for <code>null</code> before
     * calling <code>equals(Object)</code> so as to avoid possible
     * <code>NullPointerException</code>s.
     *
     * @param x The first object to be tested.
     * @param y The second object to be tested.
     * @return <code>true</code> if the two objects are equal.
     */
    private boolean equals(Object x, Object y) {
        if (x == null) return y == null;
        return x.equals(y);
    }

    /**
     * Tests whether the <code>tree</code> argument contains within
     * itself the <code>targetNode</code> argument.
     *
     * @param tree The root of the binary tree to search.
     * @param targetNode The node to be searched for.
     * @return <code>true</code> if the <code>targetNode</code> argument can
     *        be found within the binary tree rooted at <code>tree</code>.
     */
    protected boolean contains(BinaryTree tree, BinaryTree targetNode) {
        if (tree == null)
            return false;
        if (tree == targetNode)
            return true;
        return contains(targetNode, tree.getLeftChild())
                || contains(targetNode, tree.getRightChild());
    }

    /**
     * Returns a String representation of this GeneradorLexers.BinaryTree.
     *
     * @see java.lang.Object#toString()
     * @return A String representation of this GeneradorLexers.BinaryTree.
     */
    public String toString() {
        if (isLeaf()) {
            return value;
        }
        else {
            String root, left = "null", right = "null";
            root = value;
            if (getLeftChild() != null) {
                left = getLeftChild().toString();
            }
            if (getRightChild() != null) {
                right = getRightChild().toString();
            }
            return root + " (" + left + ", " + right + ")";
        }
    }

    /**
     * Computes a hash code for the complete binary tree rooted
     * at this GeneradorLexers.BinaryTree node.
     *
     * @return A hash code for the binary tree with this root.
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        int result = value.hashCode();
        if (leftChild != null) {
            result += 3 * leftChild.hashCode();
        }
        if (rightChild != null) {
            result += 7 * leftChild.hashCode();
        }
        return result;
    }

    /**
     * Prints the binary tree rooted at this GeneradorLexers.BinaryTree node.
     */
    public void print() {
        print(this, 0);
    }

    private void print(BinaryTree root, int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("   ");
        }
        if (root == null) {
            System.out.println("null");
            return;
        }
        System.out.println(root.value);
        if (root.isLeaf()) return;
        print(root.leftChild, indent + 1);
        print(root.rightChild, indent + 1);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public HashSet<Integer> getFirstPos() {
        return firstPos;
    }

    public void setFirstPos(HashSet<Integer> firstPos) {
        this.firstPos.addAll(firstPos);
    }

    public HashSet<Integer> getFollowPos() {
        return followPos;
    }

    public void setFollowPos(HashSet<Integer> followPos) {
        this.followPos.addAll(followPos);
    }

    public void addToFollowPos(Integer position){
        this.followPos.add(position);
    }

    public HashSet<Integer> getLastPos() {
        return lastPos;
    }

    public void setLastPos(HashSet<Integer> lastPos) {
        this.lastPos.addAll(lastPos);
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }
}
