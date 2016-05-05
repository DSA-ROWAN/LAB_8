package List;
public class Node<T> {
	protected Node<T> _next = null;
	public Object self = null;

	public Node(){
		
	}
	
	public Node(Node<T> prev){
		this.add(prev);
	}
	
	public Node(T o, Node<T> prev){
		this.add(prev);
		self =  o;
	}
	
	public Node<T> delete(){
		Node<T> tmp = _next;
		_next = null;
		return tmp;
	}
	
	public void add(Node<T> nodeToAddAfter){
		this.setNext(nodeToAddAfter.next());
		nodeToAddAfter.setNext(this);
	}
		
	public Node<T> next(){
		return _next;
	}
	
	public void setNext(Node<T> next){
		_next = next;
	}
}
