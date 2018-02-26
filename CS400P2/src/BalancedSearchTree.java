// BalancedSearchTree
// Red-Black Tree
public class BalancedSearchTree<T extends Comparable<T>> implements SearchTreeADT<T> {

	// Inner node class used to store key items and links to other nodes.
	protected class Treenode<K extends Comparable<K>> {
		K key;
		Treenode<K> left;
		Treenode<K> right;
		Treenode<K> parent;
		private boolean color; // Color of the node, red or black. If true - black, if false - red.
		
		public Treenode(K item) {
			this(item,null,null, true);
		}
		public Treenode(K item, Treenode<K> left, Treenode<K> right, boolean color) {
			key = item;
			this.left = left;
			this.right = right;
			this.color = color;
			this.parent = null;
		}
		
		// Looks up item in tree, returns true if found.
		public boolean lookuphelper(T item) {
			if(this.key.equals(item))
				return true;
			if(this.left != null)
				return left.lookuphelper(item);
			if(this.right != null)
				return right.lookuphelper(item);
			return false;
		}
	}

	protected Treenode<T> root;

	public String inAscendingOrder() {
		// Return comma separated list of keys in ascending order.
		String keys = getItems(root);
		return keys;
	}

	public boolean isEmpty() {
		// Return empty if there are no keys in structure.
		return root==null;
	}

	public int height() {
		// Return the height of this tree
		return heightHelper(root); 
	}

	public boolean lookup(T item) {
		// Return true if item is in tree, otherwise false.
		return root.lookuphelper(item);
	}

	public void insert(T item) {
		//TODO if item is null throw IllegalArgumentException, 
		// otherwise insert into balanced search tree
		
		if(item == null)
			throw new IllegalArgumentException();
		if(isEmpty())
			root = new Treenode<T>(item);
		else
		{
			Treenode<T> node = root;
			while(node != null)
			{
				if(node.key.compareTo(item) < 0)
				{
					if(node.left == null)
					{
						node.left = new Treenode<T>(item, null, null, false);
						node.left.parent = node;
						if(node.left.color == false && node.color == false)
						{
							if(node.parent.right==node && node.parent.left == null)
								trinodeRestructure(node);
							else if(node.parent.left == node && node.parent.right == null)
								trinodeRestructure(node);
							else if(node.parent.left == node && node.parent.right != null)
								recolor(node);
							else if(node.parent.right == node && node.parent.right != null)
								recolor(node);
								
						}
						
						
					}
					
					else
						node = node.left;
				}
				
				else if(node.key.compareTo(item) > 0)
				{
					if(node.right == null)
					{
						node.right = new Treenode<T>(item, null, null, false);
						node.right.parent = node;
						if(node.right.color == false && node.color == false)
						{
							if(node.parent.right==node && node.parent.left == null)
								trinodeRestructure(node);
							else if(node.parent.left == node && node.parent.right == null)
								trinodeRestructure(node);
							else if(node.parent.left == node && node.parent.right != null)
								recolor(node);
							else if(node.parent.right == node && node.parent.right != null)
								recolor(node);
						}
					}
					else
						node = node.right;
				}
			}
		}
	}

	private void trinodeRestructure(Treenode<T> node) {
		if(node.parent.left == node && node.left != null)
		{
			if(node.parent.parent.left == node.parent)
			{
				Treenode<T> ggp = node.parent.parent;
				node.right = node.parent;
				ggp.left = node;
				node.color = true;
				node.right.color = false;
				detectProblem(ggp);
			}
			else if(node.parent.parent.right == node.parent)
			{
				Treenode<T> ggp = node.parent.parent;
				node.right = node.parent;
				ggp.right = node;
				node.color = true;
				node.right.color = false;
				node.right.parent = node;
				node.left.parent = node;
				detectProblem(ggp);
			}
		}
		else if(node.parent.left == node && node.right != null)
		{
			if(node.parent.parent.left == node.parent)
			{
				Treenode<T> ggp = node.parent;
				Treenode<T> child = node.right;
				child.right = node.parent;
				child.right.parent = child;
				child.left = node;
				node.parent = child;
				ggp.left = child;
				child.color = true;
				child.left.color = false;
				child.right.color = false;
				detectProblem(ggp);
				
			}
			
			else if(node.parent.parent.right == node.parent)
			{
				Treenode<T> ggp = node.parent.parent;
				Treenode<T> child = node.right;
				child.right = node.parent;
				child.right.parent = child;
				child.left = node;
				node.parent = child;
				ggp.right = child;
				detectProblem(ggp);
			}
		}
		
	}

	private void detectProblem(Treenode<T> node) {
		if(node.parent != null && node.parent.parent != null)
		{
			if(!node.color && !node.parent.color)
			{
				if(node.parent.parent.left == node.parent)
				{
					if(node.parent.parent.right == null 
							|| node.parent.parent.right.color)
						trinodeRestructure(node);
					else if(!node.parent.parent.right.color)
						recolor(node);
				}
				else if(node.parent.parent.right == node.parent) 
				{
					if(node.parent.parent.left == null
							|| node.parent.parent.left.color)
						trinodeRestructure(node);
					else if(!node.parent.parent.left.color)
						recolor(node);
				}
			}
		}
	}

	private void recolor(Treenode<T> parent) {
		if(parent.parent != root)
			parent.parent.color = false;
		parent.parent.left.color = true;
		parent.parent.right.color = true;
	}

	public void delete(T item) {
		//TODO if item is null or not found in tree, return without error
		// else remove this item key from the tree and rebalance

		// NOTE: if you are unable to get delete implemented
		// it will be at most 5% of the score for this program assignment
		if(item == null)
			return;
		Treenode<T> node = findItem(root, item);
		if(node == null)
			return;
		// If node is root with no children
		if(node == root && node.left == null 
				&& node.right == null)
			root = null;
		// If node has no children.
		else if(node.left == null && node.right == null)
		{
			if(node.parent.left == node)
				node.parent.left = null;
			else
				node.parent.right = null;
		}
		// If node only has left child.
		else if(node.left != null && node.right == null)
		{
			if(node.parent.left == node)
				node.parent.left = node.left;
			else
				node.parent.right = node.left;
		}
		// If node only has right child.
		else if(node.left == null && node.right != null)
		{
			if(node.parent.left == node)
				node.parent.left = node.right;
			else
				node.parent.right = node.right;
		}
		// If node has both left and right child.
		else
		{
			// Find the left most node of the right child.
			Treenode<T> temp = leftMostNode(node.right);
			// Delete the left most right child.
			delete(temp.key);
			// Replace this nodes key with the left most right child's key.
			node.key = temp.key;
			detectProblem(node);
		}
		
		
	}


	// HINT: define this helper method that can find the smallest key 
	// in a sub-tree with "node" as its root
	// PRE-CONDITION: node is not null
	private T leftMost(Treenode<T> node) {
		// TODO return the key value of the left most node in this subtree
		// or return node's key if node does not have a left child
		if(node.left != null)
			return leftMost(node.left);
		return node.key;
	}
	
	// Finds the left most node of the tree.
	private Treenode<T> leftMostNode(Treenode<T> node) {
		if(node.left != null)
			return leftMostNode(node.left);
		return node;
	}
	
	private String getItems (Treenode<T> node) {
		String items = "";
		if(node.left != null)
			items += getItems(node.left);
		items += node.key + ", ";
		if(node.right != null)
			items += getItems(node.right);
		return items;
	}
	
	private Treenode<T> findItem (Treenode<T> node, T item) {
		if(node.key.equals(item))
			return node;
		else if(node.key.compareTo(item) < 0 && node.left != null)
			return findItem(node.left, item);
		else if(node.right != null)
			return findItem(node.right, item);
		return null;
	}
	
	private int heightHelper(Treenode<T> node) {
		if (node == null)
			return 0;
		int lHeight = heightHelper(node.left);
		int rHeight = heightHelper(node.right);
		
		if(lHeight > rHeight)
			return lHeight + 1;
		else
			return rHeight + 1;
	}

}