/**
 *
 * WAVLTree
 *
 * 
 * 
 * An implementation of a WAVL Tree with
 * distinct integer keys and info
 *
 */

public class WAVLTree {
	
	private int size; // number of nodes in tree
	private WAVLNode root; // pointer to the tree root
	private WAVLNode found; 
	private WAVLNode ex_leaf;
	private WAVLNode insertion_place; 
	private static WAVLNode min = null;
	private static WAVLNode max = null;
	

	  /**
	   * public WAVLTree()
	   *
	   * constructor of an empty WAVL tree
	   * 
	   * O(1) complexity
	   */
	public WAVLTree(){
		this.size=0;
		this.root=null;
		this.ex_leaf=new WAVLNode(0,"EXTERNAL_LEAF",-1,null,null,null);
		}
	
	  /**
	   * private int getSize()
	   *
	   * returns the tree's size
	   * 
	   * O(1) complexity
	   */
   
	
	public int getSize() {
		return size;
	}


	  /**
	   * private void setSize(int size)
	   *
	   * sets the tree's size to given node
	   * 
	   * O(1) complexity
	   */
	private void setSize(int size) {
		this.size = size;
	}


	  /**
	   * private getRoot()
	   *
	   * returns the tree's root
	   * 
	   * O(1) complexity
	   */
	public WAVLNode getRoot() {
		return root;
	}


	  /**
	   * private void setRoot(WAVLNode root)
	   *
	   * sets the tree's root to given node
	   * 
	   * O(1) complexity
	   */
	private void setRoot(WAVLNode root) {
		this.root = root;
	}


  /**
   * public boolean empty()
   *
   * returns true if and only if the tree is empty
   * 
   * O(1) complexity
   */
	public boolean empty(){
		return this.getRoot() == null; 
  }
	
 
	/**
	 * public void Reset()
	 * 
	 * resets given WAVLTree object to an empty tree
	 * 
	 * O(1) complexity
	 */
	public void Reset(){
		this.root = null;
		this.size = 0;
		min = null;
		max = null;
	}
	
	
 /**
   * public String search(int k)
   *
   * returns the info of an item with key k if it exists in the tree
   * otherwise, returns null
   * 
   * O(logn) complexity worst case - using the function searchRec
   * 
   */
	public String search(int k){
			WAVLNode node=this.root;
			if (node==null) {
				found=null;
				
				return null;
			}
			return searchRec(k,node);
	}
	
	
	 /**
	   * public String search_rec(int key,WAVLNode node)
	   * 
	   * recursive function that searches for the node with the key
	   * returns the info of an item with key - "key" if exists in the tree
	   * otherwise, returns null
	   * also, the function saves the potential insertion place
	   * (the father node),if the key is not found
	   * and saves pointer to node if key is found
	   *  
	   *  O(logn) complexity worst case
	   */
	
	public String searchRec(int key,WAVLNode node){
		if (node.isExtLeaf() == true){ // we are "falling" from tree - means node was not found
			found=null;
			return null;
		}
		else if (node.key == key) // node with given key is found, no need to insert
		{ 
			insertion_place=null;
			found=node;
			return node.info;
		}
		else if (node.key > key){ // key has not found yet, meanwhile the node is a potential insertion place 
			insertion_place=node;
			return searchRec(key,node.left);
		}
		else // key has not found yet, meanwhile the node is a potential insertion place
		{
			insertion_place=node; 
			return searchRec(key,node.right);
		}
	}
	
	
	/**
	 * public WAVLNode rotateRight(WAVLNode node)
	 * 
	 * rotates node with left child, i.e., clockwise
	 * used only at insertion case 2, rebalancing after insertion case 2
	 * and every case of double rotation
	 * 
	 * O(1) complexity
	 */
	public WAVLNode rotateRight(WAVLNode node){
		WAVLNode tmp=node.left;
		tmp.parent= node.parent;
		if (tmp.parent!=null){ 
			if (node.key>node.parent.key){ 
				node.parent.right=tmp;
				}
			else{
				node.parent.left=tmp;
				}
		}
		node.left=tmp.right;
		if (node.left.isExtLeaf()==false){ 
			node.left.parent=node;
		}
		tmp.right=node;
		if (node.isExtLeaf()==false){
			node.parent=tmp;
		}
		if (node==this.root) 
			this.root=tmp;
		return tmp;
	}
	
	/**
	 * public WAVLNode rotateLeft(WAVLNode node)
	 * 
	 * rotates node with right child, i.e., counter clockwise
	 * used at every case of double rotation and on deletion case 3,
	 *  rebalancing after insertion case 2
	 *  
	 *  O(1) complexity
	 */
	public WAVLNode rotateLeft(WAVLNode node){
		WAVLNode tmp=node.right;
		tmp.parent= node.parent;
		if (tmp.parent!=null){
			if (node.key>node.parent.key){ 
				node.parent.right=tmp;
				}
			else{
				node.parent.left=tmp;

				}
		}
		node.right=tmp.left;
		if (node.right.isExtLeaf()==false){
			node.right.parent=node;
		}
		tmp.left=node;
		if (node.isExtLeaf()==false){
			node.parent=tmp;
		}
		if (node==this.root)
			this.root=tmp;
		return tmp;
	}
	
	
	/**
	 * public WAVLNode doubleRotateLeftRight(WAVLNode node)
	 * 
	 * n1 - the node is given as an argument
	 * n2 - n1's left child
	 * n3 - n2's right child
	 * at first, rotates n2  with n3  --> rotateLeft(...)
	 * then, rotates n1 with n3 --> rotateRight(...)
	 * used for deletion case 4,case 3 rebalancing after rotations,
	 *  WAVLTree is now valid.
	 *  
	 *  O(1) complexity
	 */
	public WAVLNode doubleRotateLeftRight(WAVLNode node) {
		node.left=rotateLeft(node.left);
		WAVLNode tmp = rotateRight(node);
		return tmp;
		}

	
	
	/**
	 * public WAVLNode doubleRotateRightLeft(WAVLNode node)
	 * 
	 * n1 - the node is given as an argument
	 * n2 - n1's right child
	 * n3 - n2's left child
	 * at first, rotates n2  with n3  --> rotateRight(...)
	 * then, rotates n1 with n3 --> rotateLeft(...)
	 * used for deletion case 4, case 3 rebalancing after rotations,
	 *  WAVLTree is now valid.
	 *  
	 *  O(1) complexity
	 */
	public WAVLNode doubleRotateRightLeft (WAVLNode node) {
		node.right=rotateRight(node.right);
		WAVLNode tmp = rotateLeft(node);
		return tmp;
	}
	
	
/**
   * public int insert(int k, String i)
   *
   * inserts an item with key k and info i to the WAVL tree.
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no
   *  rebalancing operations were necessary.
   * returns -1 if an item with key k already exists in the tree.
   * 
   * O(logn) complexity - using search function - O(logn) + insertRebalance - O(logn) w.c.
   * in some cases calls isBinary- O(1) complexity
   */
	public int insert(int k, String i){
		search(k);
		if ((insertion_place!=null) && (this.size!=0)){ // the key is not in tree and tree is not empty, need to insert
			this.size+=1; // updating tree's size
			WAVLNode node=new WAVLNode(k,i,0,insertion_place); 
			if (min.key>k) // updating minimum if needed
				min = node;
			if (max.key<k) // updating maximum if needed
				max=node;
			if (node.key<insertion_place.key){ // left child insertion
				insertion_place.left=node;
				insertion_place=insertion_place.left;
			}
			else{ // right child insertion
				insertion_place.right=node;
				insertion_place=insertion_place.right;
			}
			if (isBinary(insertion_place.parent)==true) // if inserted node's parent was unary , zero rebalance operations are needed
				return 0;
			return insertRebalance(insertion_place); //sending the new node for rebalancing 
		}
		else if (this.size==0){ // tree is empty, need to insert 
				this.root=new WAVLNode(k,i,0,null); 
				this.size+=1;
				min=root;
				max=root;
				return 0;
			}
		return -1; // key k is already exists
	}

	
	
	/**
	   * private int  insertRebalance(WAVLNode node)
	   *
	   * function which handles rebalancing cases caused by insert
	   * handles with the rebalancing till the root, if needed
	   * returns number of operations
	   * 
	   * O(logn) w.c - using rotate and double rotate functions, promote and demote, getRankDiff - costs (O(1))
	   * 
	   */
	private int  insertRebalance(WAVLNode node){
		int cnt=0; //counts rebalancing operations
		while (node!=this.root){ // for climbing up problem, root is last possible problem
			if (node.key>node.parent.key){
				 if ((node.getRankDiff(node.parent)==0) && // case 1- right child inserted
						 (node.parent.getRankDiff(node.parent.left)==1)){
					 node=node.parent;
					 node.promote();
					 cnt++;
				}
				 else {
					 if ((node.getRankDiff(node.parent)==0) && // case 2&3- right child inserted
							 (node.parent.getRankDiff(node.parent.left)==2)){
						 if ((node.getRankDiff(node.left)==2) && (node.getRankDiff(node.right)==1)){ //case 2
							 node.parent.demote();
							 rotateLeft(node.parent);
							 cnt++;
							 return cnt;
						 }
						 else{ //case 3
							 node.demote();
							 node.parent.demote();
							 node.left.promote();
							 doubleRotateRightLeft(node.parent);
							 cnt+=2;
							 return cnt;
						 }
						 
					 }
					 return cnt;
				 }
			}
			else{
				 if ((node.getRankDiff(node.parent)==0) && // case 1- left child inserted
						 (node.parent.getRankDiff(node.parent.right)==1)){
					 node=node.parent;
					 node.promote();
					 cnt++;
				}
				 else {
					 if ((node.getRankDiff(node.parent)==0) && // case 2&3- left child inserted
							 (node.parent.getRankDiff(node.parent.right)==2)){
						 if ((node.getRankDiff(node.right)==2) && (node.getRankDiff(node.left)==1)){ //case 2
							 node.parent.demote();
							 rotateRight(node.parent);
							 cnt++;
							 return cnt;
						 }
						 else{ //case 3
							 node.demote();
							 node.parent.demote();
							 node.right.promote();
							 doubleRotateLeftRight(node.parent);
							 cnt+=2;
							 return cnt;
						 }
						 
					 }
					 return cnt;
				 }
			}
			
		}
		return cnt;
	}

	
	
	
  /**
   * public int delete(int k)
   *
   * deletes an item with key k from the binary tree, if it is there;
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no
   * rebalancing operations were needed.
   * returns -1 if an item with key k was not found in the tree.
   * 
   * O(logn) complexity - using search function - O(logn) + delRebalance - O(logn) w.c 
   * in some cases calls demote, isBinary, isUnary, Reset- O(1) complexity
   * in some cases calls Successor - O(logn) complexity w.c
   */
	public int delete(int k){
		search(k);
		if (found != null){ // there is node to be deleted
			this.size--; // updating tree's size
			if (found==this.root){ //if root should be deleted
				WAVLNode node=isUnary(this.root); //returns the root's only child if the root is unary
				if (node!=null){ //root unary - updates the pointer of node to the only child 
					this.root=node;
					this.root.parent=null;
					min=this.root;
					max=this.root;
					return 0; // zero rebalance operations are needed
				}
				if (this.root.rank==0){ //root is leaf
					this.Reset(); // resets tree
					return 0; // zero rebalance operations are needed
				}
			}
			WAVLNode node = isUnary(found);
			if (found == min){ // updating minimum if needed
				if (node != null)
					min = node;
				else
					min = found.parent; //has parent because it is not a root
			}
			if (found == max){ // updating maximum if needed
				if (node != null)
					max = node;
				else
					max = found.parent; //has parent because it is not a root
			}
			if (isBinary(found))// binary node should be deleted
				found=successor(found); // swap node with successor
			WAVLNode parent = found.parent; 
			if (found.isLeaf()){// node to be deleted leaf
				if (isUnary(parent)!=null){ // parent is unary node
					if (found==found.parent.right)
						parent.right=ex_leaf;
					else 
						parent.left=ex_leaf;
					parent.demote();
					if (parent==this.root)
						return 1;
					return 1 + delRebalance(parent.parent);
				}
				else{ // deleted node parent is binary node
					if (found.getRankDiff(parent) == 2){
						if (found==parent.left)
							parent.left=ex_leaf;
						else
							parent.right=ex_leaf;
						return delRebalance(parent);
					}
					if (found == parent.left)// in all 3 cases
						parent.left=ex_leaf;
					else
						parent.right=ex_leaf;
					return 0;
				}
			}
			else{ // node to be deleted is unary node
				node=isUnary(found);
				if (found == parent.left)
					parent.left=node;
				else
					parent.right=node;
				node.parent=parent;
				if (node.getRankDiff(parent) == 3)
					return delRebalance(parent);
				return 0;
			}
		}
	return -1; // no node to be deleted
	}

	
	
	/**
	   * private int  delRebalance (WAVLNode node)
	   *
	   * function which handles rebalancing cases caused by delete
	   * handles with the rebalancing till the root, if needed
	   * returns number of operations
	   * 
	   *  O(logn) w.c - using rotate and double rotate functions, promote and demote, getRankDiff,
	   *  isLeaf, isExtLeaf - costs (O(1))
	   */
	private int  delRebalance (WAVLNode node){
		int cnt=0;
		while (node!=null){ //node has parent 
			if ((node.getRankDiff(node.left)==3 && node.getRankDiff(node.right)==2) //case 1
				|| (node.getRankDiff(node.right)==3 && node.getRankDiff(node.left)==2)){
				node.demote();
				cnt++;
				node=node.parent;
				continue;
			}
			else if (node.getRankDiff(node.left)==3 && node.getRankDiff(node.right)==1){ // cases 2 / 3 / 4 left child was removed
				WAVLNode sub_node=node.right;
				if ((sub_node.getRankDiff(sub_node.right)==2) && (sub_node.getRankDiff(sub_node.left)==2)){ //case 2
					node.demote();
					sub_node.demote();
					cnt +=2;
					node=node.parent;
					continue;
				}
				else if (sub_node.getRankDiff(sub_node.right)==1){ //case 3 
					sub_node.promote(); 
					node.demote();
					rotateLeft(node);
					cnt++;
					if ((node.right.isExtLeaf()==true) && (node.left.isExtLeaf()==true) && (node.rank==1)){
						node.demote();
					}
					return cnt;
				}
				else if (sub_node.getRankDiff(sub_node.right)==2){ //case 4
						sub_node.demote(); 
					node.demote();
					node.demote();
					sub_node.left.promote();
					sub_node.left.promote();
					doubleRotateRightLeft(node);
					cnt+=2;
					return cnt;
				}
			}
			else if (node.getRankDiff(node.right)==3 && node.getRankDiff(node.left)==1){ // cases 2 / 3 / 4 right child was removed
				WAVLNode sub_node=node.left;
				if ((sub_node.getRankDiff(sub_node.left)==2) && (sub_node.getRankDiff(sub_node.right)==2)){ //case 2
					node.demote();
					sub_node.demote();
					cnt +=2;
					node=node.parent;
					continue;
				}
				else if (sub_node.getRankDiff(sub_node.left)==1){ //case 3 
					sub_node.promote(); 
					node.demote();
					rotateRight(node);
					cnt++;
					if ((node.right.isExtLeaf()==true) && (node.left.isExtLeaf()==true) && (node.rank==1)){
						node.demote();
					}
					return cnt;
				}
				else if (sub_node.getRankDiff(sub_node.left)==2){ //case 4
					sub_node.demote(); 
					node.demote();
					node.demote();
					sub_node.right.promote();
					sub_node.right.promote();
					doubleRotateLeftRight(node);
					cnt+=2;
					return cnt;
				}
			}
			return cnt;
		}
		return cnt; 
	}
	
	/**
	 * private static boolean isBinary(WAVLNode node)
	 * 
	 * returns true if the given node has two children which are not external leaves
	 * 
	 * O(1) complexity
	 */
	private static boolean isBinary(WAVLNode node){
		if (node.right.isExtLeaf() == false && node.left.isExtLeaf() == false)
			return true;
		return false;
	}
	
	
	/**
	 * private static boolean isUnary(WAVLNode node)
	 * 
	 * if node is unary , returns the only node's child
	 * otherwise, returns null
	 * 
	 * O(1) complexity
	 */
	private static WAVLNode isUnary(WAVLNode node){
		if (node.right.isExtLeaf() == false && node.left.isExtLeaf() == true){ // unary node with right child
			node = node.right;
			return node;
		}
		else if (node.right.isExtLeaf() == true && node.left.isExtLeaf() == false){ // unary node with left child
			node = node.left;	
			return node;
		}
		return null; // given node is not unary node
	}
	


		

	
	/**
	 * private static void successor(WAVLNode n)
	 * the function makes a lazy swapping 
	 * between node and its successor
	 * returns given node in its new place
	 * 
	 * O(logn) complexity - w.c
	 */
	private static WAVLNode successor(WAVLNode n){
		WAVLNode node = n.right;
		while (node.left.isExtLeaf() == false){ // given node is binary, finds his successor
			node = node.left;
		}
		if (max==node)
			max=n;
		int temp = node.key;
		String i = node.info;
		node.info=n.info;
		node.key=n.key;
		n.key=temp;
		n.info=i;
		return node;
	}
	
	
	
   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty
    * 
    * O(1) complexity
    */
	public String min(){
		if(this.root == null)
			return null;
		return min.info;
	}

	
	
   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty
    * 
    * O(1) complexity
    */
	public String max(){
		if(this.root == null)
			return null;
		return max.info;
	}
	
	
	/**
	 * public void inOrderRecKeys(int[] arr, WAVLNode node, int i)
	 *
	 * recursive function which inserts the keys to the
	 * array by the inorder traversal
	 * 
	 * O(n) complexity
	 */
	public int inOrderRecKeys(int[] arr, WAVLNode node, int i){
		if (node.isExtLeaf()==false){
			i=inOrderRecKeys(arr,node.left,i);
			arr[i]=node.key;
			i=i+1;
			i=inOrderRecKeys(arr,node.right,i);
			return i;
		}
		return i;

	}
	
	
	/**
     * public int[] keysToArray()
     *
     * Returns a sorted array which contains all keys in the tree,
     * or an empty array if the tree is empty.
     * 
     * O(n) complexity - using inOrderRecKeys function
     */
	public int[] keysToArray(){
		int[] arr = new int[this.size];
		if (this.size!=0){
			inOrderRecKeys(arr,this.root,0);
		}
        return arr;
    }

	 /**
	  * public void inOrderRecInfo(int[] arr, WAVLNode node, int i)
	  *
	  * recursive function which inserts the info of the node to the
	  * array by the inorder traversal
	  * 
	  * O(n) complexity
	  */
	public int inOrderRecInfo(String[] arr, WAVLNode node, int i){
		if (node.isExtLeaf()==false){
			i=inOrderRecInfo(arr,node.left,i);
			arr[i]=node.info;
			i=i+1;
			i=inOrderRecInfo(arr,node.right,i);
			return i;
		}
		return i;
	}
	
    /**
     * public String[] infoToArray()
     *
     * Returns an array which contains all info in the tree,
     * sorted by their respective keys,
     * or an empty array if the tree is empty.
     * 
     * O(n) complexity - using inOrderRecInfo function
     */
	public String[] infoToArray(){
		String[] arr = new String[this.size];
		if (this.size!=0){
			inOrderRecInfo(arr,this.root,0);
		}
        return arr;
    }

     /**
      * public int size()
      *
      * Returns the number of nodes in the tree.
      * 
      * O(1) complexity
      */
	public int size(){
	   return this.size;
	}

    /**
     * public class WAVLNode
     *
     * implements a WAVLNode that has key, info, rank, parent, left and right
     * the function has setters and getters for each field and couple of function on nodes
     * 
     */
	public class WAVLNode{
		private int key;
	    private String info;
	    private int rank;
        private WAVLNode parent;
	    private WAVLNode left;
	    private WAVLNode right;
		
	    /**
	     * public WAVLNode(int key,String info, int rank, WAVLNode parent, WAVLNode left, WAVLNode right)
	     * 
	     * constructor for a WAVLNode
	     * 
	     * O(1) complexity
	     */
	    public WAVLNode(int key, String info, int rank, WAVLNode parent, WAVLNode left,WAVLNode right){
	    	this.key=key;
	  	    this.info=info;
	  	    this.rank=rank;
	  	    this.parent=parent;
	  	    this.left=left;
	  	    this.right=right;
	  	}
	    
	    /**
		 * public WAVLNode(int key,String info, int rank, WAVLNode parent, WAVLNode left, WAVLNode right)
		 * 
		 * constructor for a WAVLNode - leaf
		 * 
		 * O(1) complexity
		 */
	    public WAVLNode(int key, String info, int rank, WAVLNode parent){
	    	this(key,info,rank,parent,ex_leaf,ex_leaf);
	  	}
	    
	    /**public int getKey()
	     * 
	     * return the node's key
	     * 
	     * O(1) complexity
	     */
		public int getKey() {
			return key;
		}


	    /**public void setKey(int key)
	     * 
	     * sets the node's key field to given one
	     * 
	     * O(1) complexity
	     */
		public void setKey(int key) {
			this.key = key;
		}

	    /**public string getInfo()
	     * 
	     * return the node's info
	     * 
	     * O(1) complexity
	     */
		public String getInfo() {
			return info;
		}

	    /**public void setInfo(String info)
	     * 
	     * sets the node's info to given one
	     * 
	     * O(1) complexity
	     */
		public void setInfo(String info) {
			this.info = info;
		}

	    /**public int getRank()
	     * 
	     * return the node's rank
	     * 
	     * O(1) complexity
	     */
		public int getRank() {
			return rank;
		}

	    /**public void setRank(int rank)
	     * 
	     * sets the node's rank to given one
	     * 
	     * O(1) complexity
	     */
		public void setRank(int rank) {
			this.rank = rank;
		}

	    /**public WAVLNode getParent()
	     * 
	     * return the node's parent node
	     * 
	     * O(1) complexity
	     */
		public WAVLNode getParent() {
			return parent;
		}

	    /**public void setParent(WAVLNode parent)
	     * 
	     * sets the node's parent node to given one
	     * 
	     * O(1) complexity
	     */
		public void setParent(WAVLNode parent) {
			this.parent = parent;
		}

	    /**public WAVLNode getLeft()
	     * 
	     * return the node's left child node
	     * 
	     * O(1) complexity
	     */
		public WAVLNode getLeft() {
			return left;
		}

	    /**public WAVLNode setLeft(WAVLNode left)
	     * 
	     * sets the node's left child to a given node
	     * 
	     * O(1) complexity
	     */
		public void setLeft(WAVLNode left) {
			this.left = left;
		}

	    /**public WAVLNode getRight()
	     * 
	     * return the node's right child node
	     * 
	     * O(1) complexity
	     */
		public WAVLNode getRight() {
			return right;
		}

	    /**public void setRight(WAVLNode right)
	     * 
	     * sets the node's right child node to a given one
	     * 
	     * O(1) complexity
	     */
		public void setRight(WAVLNode right) {
			this.right = right;
		}
		
	    /**public int getRankDiff(WAVLNode other)
	     * 
	     * returns rank difference between two given nodes
	     * 
	     * O(1) complexity
	     */
		public int getRankDiff(WAVLNode other){
			return Math.abs(this.rank-other.rank);
		}
		
		/**
		 * public void promote()
		 * promotes the node's rank by one
		 * 
		 * O(1) complexity
		 */
		public void promote(){
			this.rank=this.rank+1;
		}
		
		/**
		 * public void demote()
		 * demotes the node's rank by one
		 * 
		 * O(1) complexity
		 */
		public void demote(){
			this.rank=this.rank-1;
		}
		
		/**
		 * public boolean isLeaf()
		 * returns true if the node is a leaf (rank==0)
		 * otherwise, returns false
		 * 
		 * O(1) complexity
		 */
		public boolean isLeaf(){
			return (this.rank==0);
		}
		
		/**
		 * public boolean isExtLeaf()
		 * returns true if the node is an external leaf (rank==-1)
		 * otherwise, returns false
		 * 
		 * O(1) complexity
		 */
		public boolean isExtLeaf(){
			return (this.rank==-1);
		}
		
  
  }
  
  

}
  


