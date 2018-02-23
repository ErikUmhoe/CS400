// starter class for a BalancedSearchTree
// you may implement AVL, Red-Black, 2-3 Tree, or 2-3-4 Tree
// be sure to include in class header which tree you have implemented
public class BalancedSearchTree<T extends Comparable<T>> implements SearchTreeADT<T> {

	// inner node class used to store key items and links to other nodes
	protected class Treenode<K extends Comparable<K>> {
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
		K key;
		Treenode<K> left;
		Treenode<K> right;
		Treenode<K> parent;

		private boolean color; //Color of the node, red or black. If true - black, if false - red.
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
		//TODO : must return comma separated list of keys in ascending order
		return "" ;
	}

	public boolean isEmpty() {
		//TODO return empty if there are no keys in structure
		return root==null;
	}

	public int height() {
		//TODO return the height of this tree
		return 0; 
	}

	public boolean lookup(T item) {
		//TODO must return true if item is in tree, otherwise false
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
		
		
	}

	private void recolor(Treenode<T> node) {
		
		
	}

	public void delete(T item) {
		//TODO if item is null or not found in tree, return without error
		// else remove this item key from the tree and rebalance

		// NOTE: if you are unable to get delete implemented
		// it will be at most 5% of the score for this program assignment
	}


	// HINT: define this helper method that can find the smallest key 
	// in a sub-tree with "node" as its root
	// PRE-CONDITION: node is not null
	private T leftMost(Treenode<T> node) {
		// TODO return the key value of the left most node in this subtree
		// or return node's key if node does not have a left child
		return node.key;
	}

}

