package List;
public class LinkedList<T> extends List<T> {

	private int size = 0;
	private Node<T> head = new Node<T>();
	private Node<T> end = new Node<T>();
	
	public LinkedList() {
		head.setNext(end);
		end.setNext(head);
		
		head.self = "ERROR";
		end.self = "ERROR";
	}
	
	@Override
	protected Node<T> _add(int index, T o) {		
		Node<T> nodeToAdd = new Node<T>(o, this.getNodeAt(index));
		size++;
		return nodeToAdd;
	}

	@Override
	protected Node<T> _add(T o) {
		if(size == 0){
			return this._add(1, o);
		}else{
			return this._add(size, o);
		}

	}
	
	@Override
	public void add(int index, T o) {
		if(index < 1 || index > size && index != 1){
			throw new IndexOutOfBoundsException();
		}else{
			this._add(index - 1, o);
		}
		

	}

	@Override
	public void add(T o) {
		if(size == 0){
			this._add(1, o);
		}else{
			this._add(size, o);
		}

	}
	
	@Override
	public void clear() {
		head.setNext(end);
		size = 0;

	}
	
	@Override
	protected Node<T> getNode(T o) {
		Node<T> curr = head;
		
		while(curr != end){
			if(curr.self.equals(o)){
				return curr;
			}else{
				curr = curr.next();
			}
		}
		return curr;
	}

	@Override
	protected Node<T> getNodeAt(int index) {
		Node<T> curr = head;
		
		for(int i = 1; i <= index && curr.next() != end; i++){
			curr = curr.next();
		}
		return curr;
	}
	
	@Override
	public boolean contains(T o){
		Node<T> nd = this.getNode(o);
		if(nd != end){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public int indexOf(T o) {
		Node<T> curr = head;
		int indx = 0;
		while(curr != end){
			if(curr.self.equals(o)){
				return indx;
			}else{
				indx++;
				curr = curr.next();
			}
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		if(head.next() == end){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public T removeAt(int index) {
		if(index < 1 || index > size){
			throw new IndexOutOfBoundsException();
		}else{
			Node<T> nd = this.getNodeAt(index);
			Node<T> prev = this.getNodeAt(index-1);
			if(nd != null){
				prev.setNext(nd.delete());
				size--;
				return (T)nd.self;
			}
			return null;
		}
	}

	@Override
	public T remove(T o) {
		Node<T> nd = this.getNode(o);
		Node<T> prev = this.getNodeAt(this.indexOf(o)-1);
		if(nd != null){
			prev.setNext(nd.delete());
			size--;
			return (T)nd.self;
		}
		return null;
	}

	@Override
	public void set(int index, T element) {
		if(index < 1 || index > size){
			throw new IndexOutOfBoundsException();
		}else{
			Node<T> nd = this.getNodeAt(index);
			
			if(nd != null){
				nd.self = element;
			}else{
				nd = this._add(element);
			}
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")// This is ok because we check each type as they come in.
	public T get(int index){	
		if(index < 1 || index > size){
			throw new IndexOutOfBoundsException();
		}else{
			return (T)this.getNodeAt(index).self;
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	@SuppressWarnings("unchecked")// This is ok because we check each type as they come in.
	public List<T> subList(int fromIndex, int toIndex) {
		
		if((fromIndex < 1 || fromIndex > size) || (toIndex < 1 || toIndex > size)){
			throw new IndexOutOfBoundsException();
		}else{
			LinkedList<T> tmpList = new LinkedList<T>();
			Node<T> curr = this.getNodeAt(fromIndex);
			
			for(int i = fromIndex; i <= toIndex && curr != end; i++){
				tmpList._add((T)curr.self);
				curr = curr.next();
			}
			return tmpList;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")// This is ok because we check each type as they come in.
	public Object[] toArray() {
		Object[] listArray = new Object[size]; // This is ok because we check each type as they come in.
		
		Node<T> curr = head;
		
		for(int i = 0; curr.next() != end; i++){
			curr = curr.next();
			listArray[i] = curr.self;
		}
		
		return listArray;
	}
	
	public void shift(int pos){
		pos = (0 + pos) % size;
		Node<T> ndNewFirst = this.getNodeAt(pos);
		Node<T> ndNewLast = this.getNodeAt(pos - 1);
		Node<T> ndOldFirst = head.next();
		Node<T> ndOldLast = this.getNodeAt(size);
		
		head.setNext(this.getNodeAt(pos));
		ndNewLast.setNext(end);
		ndOldLast.setNext(ndOldFirst);	
	}
}
