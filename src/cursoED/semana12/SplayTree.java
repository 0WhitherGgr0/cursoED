package cursoED.semana12;

import java.util.NoSuchElementException;

/**
 * Implements a top-down splay tree. Available at
 * http://www.link.cs.cmu.edu/splay/ Author: Danny Sleator <sleator@cs.cmu.edu>
 * This code is in the public domain.
 */

class BinaryNode<T> {
	BinaryNode(T theKey) {
		key = theKey;
		left = right = null;
	}

	T key; // The data in the node
	BinaryNode<T> left; // Left child
	BinaryNode<T> right; // Right child
	
    public String traverse() {
        StringBuilder result = new StringBuilder();
        traverse(result);
        return result.toString().trim();
    }
    private void traverse(StringBuilder result) {
        if (left != null) {
            left.traverse(result);
        }
        result.append(key).append(" ");
        if (right != null) {
            right.traverse(result);
        }
    }
}

public class SplayTree<T extends Comparable> {
	private BinaryNode<T> root;
	
	public BinaryNode<T> getRoot() {
		return root;
	}
	
	public SplayTree() {
		root = null;
	}

    /**
     * Insert into the tree.
     *
     * @param key the item to insert.
     * @throws IllegalArgumentException if key is already present.
     */
    public void insert(T key) {
        BinaryNode<T> n;
        int c;
        if (root == null) {
            root = new BinaryNode<>(key);
            return;
        }
        splay(key);
        if ((c = key.compareTo(root.key)) == 0) {
            throw new IllegalArgumentException("Duplicate item: " + key.toString());
        }
        n = new BinaryNode<T>(key);
        if (c < 0) {
            n.left = root.left;
            n.right = root;
            root.left = null;
        } else {
            n.right = root.right;
            n.left = root;
            root.right = null;
        }
        root = n;
    }

    /**
     * Remove from the tree.
     *
     * @param key the item to remove.
     * @throws NoSuchElementException if key is not found.
     */
    public void remove(T key) {
        BinaryNode<T> x;
        splay(key);
        if (key.compareTo(root.key) != 0) {
            throw new NoSuchElementException("Item not found: " + key.toString());
        }
        // Now delete the root
        if (root.left == null) {
            root = root.right;
        } else {
            x = root.right;
            root = root.left;
            splay(key);
            root.right = x;
        }
    }
	/**
	 * Find the smallest item in the tree.
	 */
	public T findMin() {
		BinaryNode<T> x = root;
		if (root == null)
			return null;
		while (x.left != null)
			x = x.left;
		splay(x.key);
		return x.key;
	}

	/**
	 * Find the largest item in the tree.
	 */
	public T findMax() {
		BinaryNode<T> x = root;
		if (root == null)
			return null;
		while (x.right != null)
			x = x.right;
		splay(x.key);
		return x.key;
	}

	/**
	 * Find an item in the tree.
	 */
	public T find(T key) {
		if (root == null)
			return null;
		splay(key);
		if (root.key.compareTo( key) != 0)
			return null;
		return root.key;
	}

	/**
	 * Test if the tree is logically empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * this method just illustrates the top-down method of implementing the
	 * move-to-root operation
	 */
	private void moveToRoot(T key) {
		BinaryNode<T> l, r, t, y;
		l = r = header;
		t = root;
		header.left = header.right = null;
		for (;;) {
			if (key.compareTo(t.key) < 0) {
				if (t.left == null)
					break;
				r.left = t; /* link right */
				r = t;
				t = t.left;
			} else if (key.compareTo(t.key) > 0) {
				if (t.right == null)
					break;
				l.right = t; /* link left */
				l = t;
				t = t.right;
			} else {
				break;
			}
		}
		l.right = t.left; /* assemble */
		r.left = t.right;
		t.left = header.right;
		t.right = header.left;
		root = t;
	}

	private static BinaryNode header = new BinaryNode(null); // For splay

	/**
	 * Internal method to perform a top-down splay.
	 * 
	 * splay(key) does the splay operation on the given key. If key is in the tree,
	 * then the BinaryNode containing that key becomes the root. If key is not in
	 * the tree, then after the splay, key.root is either the greatest key < key in
	 * the tree, or the lest key > key in the tree.
	 *
	 * This means, among other things, that if you splay with a key that's larger
	 * than any in the tree, the rightmost node of the tree becomes the root. This
	 * property is used in the delete() method.
	 */

	private void splay(T key) {
		BinaryNode<T> l, r, t, y;
		l = r = header;
		t = root;
		header.left = header.right = null;
		for (;;) {
			if (key.compareTo(t.key) < 0) {
				if (t.left == null)
					break;
				if (key.compareTo(t.left.key) < 0) {
					y = t.left; /* rotate right */
					t.left = y.right;
					y.right = t;
					t = y;
					if (t.left == null)
						break;
				}
				r.left = t; /* link right */
				r = t;
				t = t.left;
			} else if (key.compareTo(t.key) > 0) {
				if (t.right == null)
					break;
				if (key.compareTo(t.right.key) > 0) {
					y = t.right; /* rotate left */
					t.right = y.left;
					y.left = t;
					t = y;
					if (t.right == null)
						break;
				}
				l.right = t; /* link left */
				l = t;
				t = t.right;
			} else {
				break;
			}
		}
		l.right = t.left; /* assemble */
		r.left = t.right;
		t.left = header.right;
		t.right = header.left;
		root = t;
	    moveToRoot(key);

	}
    public String display() {
        return getRoot().traverse();
    }


	/*// test code stolen from Weiss
	public static void main(String[] args) {
		SplayTree t = new SplayTree();
		final int NUMS = 40000;
		final int GAP = 307;

		System.out.println("Checking... (no bad output means success)");

		for (int i = GAP; i != 0; i = (i + GAP) % NUMS)
			t.insert(new Integer(i));
		System.out.println("Inserts complete");

		for (int i = 1; i < NUMS; i += 2)
			t.remove(new Integer(i));
		System.out.println("Removes complete");

		if (((Integer) (t.findMin())).intValue() != 2 || ((Integer) (t.findMax())).intValue() != NUMS - 2)
			System.out.println("FindMin or FindMax error!");

		for (int i = 2; i < NUMS; i += 2)
			if (((Integer) t.find(new Integer(i))).intValue() != i)
				System.out.println("Error: find fails for " + i);

		for (int i = 1; i < NUMS; i += 2)
			if (t.find(new Integer(i)) != null)
				System.out.println("Error: Found deleted item " + i);
	}*/

}