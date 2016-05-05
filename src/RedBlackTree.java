import java.util.NoSuchElementException;
import java.util.LinkedList;

//THIS DOES NOT NEED TO BE BALANCED!!! (AVL)

public class RedBlackTree<T extends Comparable<T>> 
{
	private RBNode<T> root = null;
	public int size = 0;
	
	public RedBlackTree(){
		
	}
	
	protected RBNode<T> root(T val){
		if(root == null){
			root = new RBNode<T>(val);
		}
		return root;
	}
	
	private RBNode<T> root(){
		return root;
	}
	
	public boolean isEmpty(){
		if(root == null) return true;
		return false;
	}
	
	public void clear(){
		root = null;
	}
	
	private RBNode<T> search(Comparable<T> item){
		if(this.isEmpty()) return null;
		return this.recursiveSearch(this.root(), item);
		
	}
	
	private RBNode<T> recursiveSearch(RBNode<T> node, Comparable<T> item){
		if(node == null) return null;
		int comp = item.compareTo(node.getValue());
		if(comp == 0) return node;
		
		if(comp < 0){
			return recursiveSearch(node.getLeft(), item);
		}else{
			return recursiveSearch(node.getRight(), item);
		}
	}	
	
	public void insert(T item) throws Exception{
		if(this.isEmpty()){
			this.root = new RBNode<T>(item);
			size++;
			return;
		}else{
			RBNode<T> curr = this.root();
			boolean done = false;
			
			RBNode<T> newNode = new RBNode<T>(item);
			
			while(!done){
				int comp = item.compareTo(curr.getValue());
				
				if(comp == 0){
					throw new  IllegalArgumentException("Duplicate Value");
				}
				
				if(comp < 0){
					if(curr.getLeft() == null){
						curr.setLeft(newNode);
						done = true;
					}else{
						curr = curr.getLeft();
					}
				}else{
					if(curr.getRight() == null){
						curr.setRight(newNode);
						done = true;
					}else{
						curr = curr.getRight();
					}
				}
			}
			newNode.setParent(curr);
			size++;
			
			while(curr != this.root()){
				
				if(isRed(curr.getLeft())){
					if(isRed(curr.getRight())){
						curr.red = 1;
						curr.getLeft().red = 0;
						curr.getRight().red = 0;
					}else{
						if(isRed(curr.getLeft().getLeft())){
							System.out.println("Single");
							curr = rotateSingle(curr);
						}else if(isRed(curr.getLeft().getRight())){
							System.out.println("Double");
							curr = rotateSingle(curr.getLeft());
							curr = rotateSingle(curr);
						}					
					}
				}else if(isRed(curr.getRight())){
					if(isRed(curr.getLeft())){
						curr.red = 1;
						curr.getLeft().red = 0;
						curr.getRight().red = 0;
					}else{
						if(isRed(curr.getRight().getRight())){
							System.out.println("Single");
							curr = rotateSingle(curr);
						}else if(isRed(curr.getRight().getLeft())){
							System.out.println("Double");
							curr = rotateSingle(curr.getRight());
							curr = rotateSingle(curr);
						}					
					}
				}
				curr = curr.getParent();
			}
			
			this.root().red = 0;
			return;
		}
	}
	
	
	private boolean isRed(RBNode<T> nd){
		return nd != null && nd.red == 1;
	}
	
	private RBNode<T> rotateSingle(RBNode<T> nd){
		RBNode<T> svNd = null;
		
		if(nd.getParent().getRight() == nd){
			svNd = nd.getLeft();
			if(svNd != null){
				nd.setLeft(svNd.getRight());
				svNd.setRight(nd);
				svNd.red = 0;
			}

			nd.red = 1;
			return nd;
			
		}else{
			svNd = nd.getRight();
			if(svNd != null){
				nd.setRight(svNd.getLeft());
				svNd.setLeft(nd);
				svNd.red = 0;
			}
			nd.red = 1;
			return nd;
		}
	}
	
	public T delete(T object){
		if(this.isEmpty()){
			throw new NoSuchElementException();
		}
		
		RBNode<T> deleteNode = this.search(object);
		
		if(deleteNode == null){
			throw new NoSuchElementException();
		}
		
		RBNode<T> hold;
		
		if(deleteNode.right != null && deleteNode.left != null){
			hold = this.findPredecessor(deleteNode);
			deleteNode.setValue(hold.getValue());
			deleteNode = hold;
		}
		
		if(deleteNode.right == null && deleteNode.left == null){
			deleteHere(deleteNode, null);
			size--;
			return deleteNode.getValue();
		}
		
		if(deleteNode.right != null){
			hold = deleteNode.right;
			deleteNode.right = null;
		}else{
			hold = deleteNode.left;
			deleteNode.left = null;
		}
		
		deleteHere(deleteNode, hold);
		
		if(this.root == deleteNode){

		}
		size--;
		return deleteNode.getValue();
	}
	
	private void deleteHere(RBNode<T> deleteNode, RBNode<T> attach){
		RBNode<T> parent = deleteNode.parent;
		
		if(parent == null){
			this.root = attach;
			if(attach != null){
				attach.setParent(null);
			}
			return;
		}
		
		if(deleteNode == parent.left){
			parent.setLeft(attach);
			if(attach != null){
				attach.setParent(parent);
			}
			deleteNode = null;
			return;
		}
		parent.setRight(attach);
		if(attach != null){
			attach.setParent(parent);
		}
		deleteNode = null;
		return;
	}
	
	private RBNode<T> findPredecessor(RBNode<T> node){
		if(node.left == null){
			return null;
		}
		RBNode<T> pred = node.left;
		while(pred.right != null){
			pred = pred.right;
		}
		return pred;
	}
	
	public LinkedList<T> inOrder(){
		LinkedList<T> lst = new LinkedList<T>();
		this._inOrder(this.root, lst);
		return lst;
	}
	
	private void _inOrder(RBNode<T> node, LinkedList<T> lst){
		if(node != null){
			this._inOrder(node.getLeft(), lst);
			lst.add(node.value);
			this._inOrder(node.getRight(), lst);
		}
	}	
	
	public int count(T val1, T val2){
		int[] count = new int[1];
		
		if(val1.compareTo(val2) > 0){//val1 > val2 = -1
			this._count(this.root, val2, val1, count);
		}else{
			this._count(this.root, val1, val2, count);
		}		
	
		return count[0];
	}
	
	public int _count(RBNode<T> node, T minVal, T maxVal, int[] count){
		if(node != null){
			if(node.getLeft() != null && node.getLeft().getValue().compareTo(node.getValue()) < 0){
				this._count(node.getLeft(), minVal, maxVal, count);
			}
			
			if((node.getValue().compareTo(minVal) > 0) && (node.getValue().compareTo(maxVal) < 0)){
				count[0]++;
			}
			
			if(node.getRight() != null && node.getRight().getValue().compareTo(node.getValue()) > 0){
				this._count(node.getRight(), minVal, maxVal, count);
			}
		}
		return 0;
	}
	
	public RedBlackTree<T> clone(){
		RedBlackTree<T> newTree = new RedBlackTree<T>();
		try {
			if(this.root != null){
				this._clone(this.root, newTree.root(this.root.getValue()));
				newTree.size = this.size;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newTree;
	}
	
	private RBNode<T> _clone(RBNode<T> node, RBNode<T> newNode) throws Exception{
		if(node != null){
			newNode.setValue(node.getValue());
			RBNode<T> newLeft = null;
			RBNode<T> newRight = null;
			
			if(node.getLeft() != null){
				newLeft = new RBNode<T>(node.getLeft().getValue());
				newLeft.setParent(newNode);
				_clone(node.getLeft(), newLeft);
			}
			
			if(node.getRight() != null){
				newRight = new RBNode<T>(node.getRight().getValue());
				newRight.setParent(newNode);
				_clone(node.getRight(), newRight);
			}
			
			newNode.setLeft(newLeft);
			newNode.setRight(newRight);
			
			return null;
		}else{
			return node;
		}
	}
	
    public void printTree()
    {
        boolean first = true;
        String printVal = "";
        int count = 0;
        LinkedList<RBNode<T>> currentLevel = new LinkedList<RBNode<T>>();
        LinkedList<RBNode<T>> nextLevel = new LinkedList<RBNode<T>>();
        
        currentLevel.push(this.root);
        
        while (currentLevel.size() > 0)
        {
        	RBNode<T> currNode = currentLevel.removeLast();
            if(currNode != null)
            {
                if(first)
                {
                    first = false;
                    printVal += count + ": ";
                    count++;
                }
                
                String direction = "";
                
                if(currNode != this.root && currNode.getParent().getLeft() != null
                		&& currNode == currNode.getParent().getLeft()){
                	direction = " left node of " + currNode.getParent().getValue();
                }else if(currNode != this.root){
                	direction = " right node of " + currNode.getParent().getValue();
                }
                
                printVal += currNode.getValue() + direction + " ";
                if(currentLevel.size() > 0)
                {
                    printVal += "         ";
                }
                nextLevel.push(currNode.getLeft());
                nextLevel.push(currNode.getRight());
            }
            if(currentLevel.size() == 0)
            {
            	printVal += "\n";
                first = true;
                currentLevel = nextLevel;
                nextLevel = new LinkedList<RBNode<T>>();
            }
        }
        System.out.print(printVal);
    }
    
    
    public static <E extends Comparable<E>> RedBlackTree<E> insert(RedBlackTree<E> treeToAddTo, E item) throws Exception{
    	RedBlackTree<E> newTree = treeToAddTo.clone();
    	newTree.insert(item);
    	return newTree;
    }
    
    public static <E extends Comparable<E>> RedBlackTree<E> delete(RedBlackTree<E> treeToDeleteFrom, E item) throws Exception{
    	RedBlackTree<E> newTree = treeToDeleteFrom.clone();
    	newTree.delete(item);
    	return newTree;
    }
    
    public static <E extends Comparable<E>> boolean search(RedBlackTree<E> treeToSearch, E item) throws Exception{
    	if(treeToSearch.search(item) != null){
    		return true;
    	}
    	return false;
    }
    
    public static <E extends Comparable<E>> LinkedList<E> inOrder(RedBlackTree<E> treeToSearch) throws Exception{
    	return treeToSearch.inOrder();
    }
    
    public static <E extends Comparable<E>> int count(RedBlackTree<E> treeToSearch, E val1, E val2) throws Exception{
    	return treeToSearch.count(val1, val2);
    }
    
    
    
    
    
    
    
    
    
}
