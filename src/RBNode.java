public class RBNode<T extends Comparable<T>> {
	RBNode<T> parent = null;
	RBNode<T> right = null;
	RBNode<T> left = null;
	RBNode[] links = {left, right};
	T value = null;
	public int red = 1;
	
	public RBNode(T item){
		value = item;
	}
	
	public T getValue(){
		return value;
	}
	
	public void setValue(T val){
		value = val;
	}
	
	public RBNode<T> getParent(){
		return parent;
	}
	
	public RBNode<T> getRight(){
		return right;
	}
	
	public RBNode<T> getLeft(){
		return left;
	}
	
	public int getColor(){
		return red;
	}
	
	public RBNode<T> setParent(RBNode<T> bNode){
		return parent = bNode;
	}
	
	public RBNode<T> setRight(RBNode<T> bNode){
		return right = bNode;
	}

	public RBNode<T> setLeft(RBNode<T> bNode){
		return left = bNode;
	}
	
	public int setColor(int newColor){
		return red = newColor;
	}
}
