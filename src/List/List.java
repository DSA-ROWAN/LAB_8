package List;
abstract public class List<T> implements MyList<T> {

	public int size(){
		return size;
	}

	public void swap(int postion1, int position2) {
		if((postion1 < 1 || postion1 > size) || (position2 < 1 || position2 > size)){
			throw new IndexOutOfBoundsException();
		}else{
			Node<T> nd1 = this.getNodeAt(postion1);
			Node<T> nd2 = this.getNodeAt(position2);
			
			T tmp1 = (T)nd1.self;
			nd1.self = nd2.self;
			nd2.self = tmp1;
		}
	}
	
	abstract protected  Node<T> getNode(T o);        
	abstract protected  Node<T> getNodeAt(int index);
	abstract protected  Node<T> _add(int index, T o);
	abstract protected  Node<T> _add(T o);           
}

