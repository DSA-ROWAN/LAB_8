import java.util.NoSuchElementException;
import java.util.LinkedList;

//THIS DOES NOT NEED TO BE BALANCED!!! (AVL)



public class RedBlackTree<T extends Comparable<T>> 
{
	private RBNode<T> dummyRoot = new RBNode<T>(null);
	private RBNode<T> root = new RBNode<T>(null);
	public int size = 0;
	
	public RedBlackTree(){
		dummyRoot.setRight(root);
	}
	
	protected RBNode<T> root(T val){
		if(root.getValue() == null){
			root.setValue(val);
		}
		return root;
	}
	
	private RBNode<T> root(){
		return root;
	}
	
	public boolean isEmpty(){
		if(root.getValue() == null) return true;
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
			this.root.setValue(item);
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
							curr = rotateSingle(curr);
						}else if(isRed(curr.getLeft().getRight())){
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
							curr = rotateSingle(curr);
						}else if(isRed(curr.getRight().getLeft())){
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
				return svNd;
			}

			nd.red = 1;
			return nd;
			
		}else{
			svNd = nd.getRight();
			if(svNd != null){
				nd.setRight(svNd.getLeft());
				svNd.setLeft(nd);
				svNd.red = 0;
				return svNd;
			}
			nd.red = 1;
			return nd;
		}
	}
	
	private RBNode<T> rotateDouble(RBNode<T> nd, int direction){
		return rotateSingle(rotateSingle(nd.go(direction)));
	}
	
	public T delete(T object){
		
		if(!this.isEmpty()){
			
			RBNode<T> dummyTmp = this.dummyRoot;
			RBNode<T> tmpNd1 = new RBNode<T>(null);
			RBNode<T> tmpNd2 = new RBNode<T>(null);
			RBNode<T> tmpNd3 = null;
			int direction = 1;
			int otherDirection = -1;
			
			while(dummyTmp.go(direction) != null){
				
				int last = direction;
				int otherLast = otherDirection;
				tmpNd2 = tmpNd1; 
				tmpNd1 = dummyTmp;
				
				dummyTmp = dummyTmp.go(direction);
				
				direction = dummyTmp.getValue().compareTo(object);
				otherDirection = direction > 0 ? -1 : 1;
				
				if (dummyTmp.getValue().compareTo(object) == 0){
	                tmpNd3 = dummyTmp;
	            }
				
				if(this.isRed(dummyTmp) && !this.isRed(dummyTmp.go(direction))){
					if(this.isRed(dummyTmp.go(direction))){
						tmpNd1.put(last, this.rotateSingle(dummyTmp));
					}else if(!this.isRed(dummyTmp.go(otherDirection))){
						RBNode<T> svNd = tmpNd1.go(otherLast);
						if(svNd != null){
							if(!this.isRed(svNd.go(otherLast)) && !this.isRed(svNd.go(last))){
								tmpNd1.red = 0;
	                            svNd.red = 1;
	                            dummyTmp.red = 1;
							}else{
								int direction2 = tmpNd2.go(1) == tmpNd1 ? -1 : 1;
								
								if(this.isRed(svNd.go(last))){
									
									if(last > 0){
										tmpNd1 = rotateSingle(tmpNd1.getRight());
										tmpNd1 = rotateSingle(tmpNd1);
									}else{
										tmpNd1 = rotateSingle(tmpNd1.getLeft());
										tmpNd1 = rotateSingle(tmpNd1);
									}
									tmpNd2.put(direction2, tmpNd1);
								}else if(this.isRed(svNd.go(otherLast))){
									tmpNd2.put(direction2, rotateSingle(tmpNd1));
								}
								
								dummyTmp.red = tmpNd2.go(direction2).red = 1;
								
								if(tmpNd2.go(direction2).go(-1) != null){
									tmpNd2.go(direction2).go(-1).red = 0;
								}
								
								if(tmpNd2.go(direction2).go(1) != null){
									tmpNd2.go(direction2).go(1).red = 0;
								}
							}
						}
					}
				}
			}
			
			if(tmpNd3 != null){
				tmpNd3.setValue(dummyTmp.getValue());			
				int sd1 = tmpNd1.go(1) == dummyTmp ? -1 : 1;
				int sd2 = dummyTmp.go(0) == null ? -1 : 1;
				tmpNd1.put(sd1, dummyTmp.go(sd2));
				dummyTmp = null;
				size--;
			}
			
			this.root = this.dummyRoot.getRight();
			
			if(this.root != null){
				this.root.red = 0;
			}
			return object;
		}

		return null;
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
				newTree.root().setValue(this.root.getValue());
				this._clone(this.root, newTree.root());
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
