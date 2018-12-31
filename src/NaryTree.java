import java.util.*;

/*
Given an arbitrary tree remove nodes which have data value 0. 
*/
public class NaryTree {

	public static void main(String... args) {
		Node one = new Node(1);
		Node two = new Node(2);
		Node three = new Node(0);
		Node four = new Node(0);
		Node five = new Node(0);
		three.add(four).add(five);

		Node root = new Node(0);
		root.add(one).add(two).add(three);
		root = remove(root, 0);

		System.out.println(root);
	}

	static class Node {
		int val;
		final List<Node> children;

		Node(int val) {
			this.val = val;
			this.children = new ArrayList<>();
		}

		Node add(Node child) {
			children.add(child);
			return this;
		}

		public String toString() {
			return val+children.toString();
		}
	}

	static Node remove(Node root, int val) {
		if (root == null) {
			return root;
		} else {
			Node newRoot = new Node(root.val);
			for(Node child : root.children) {
				Node newChild = remove(child, val);
				if (newChild != null) {
					newRoot.add(newChild);
				}
			}
			if (newRoot.val == val && newRoot.children.isEmpty()) {
				newRoot = null;
			} else if (newRoot.val == val) {
				// Making the first child as parent
				Node first = newRoot.children.remove(0);
				newRoot.val = first.val;
			}
			return newRoot;
		}
	}
}
