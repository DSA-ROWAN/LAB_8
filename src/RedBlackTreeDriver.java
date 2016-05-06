import java.math.*;
import java.util.LinkedList;;
public class RedBlackTreeDriver {

	public static void main(String[] args) throws Exception {
		RedBlackTree<Integer> binTree = new RedBlackTree<Integer>();
		
	Integer[] intArray = {15, 43, 18, 10, 7, 60, 50, 1, 72, 67, 
						  57, 9, 67, 91, 81, 34, 20, 11, 31, 52, 
						  14, 71, 29, 4, 80, 76, 85, 24, 24, 10, 
						  64, 53, 86, 29, 44, 98, 9, 99, 45, 29, 
						  31, 30, 35, 65, 62, 79, 25, 11, 43, 51};
		
		
		
		for(int i : intArray){
			try{
				binTree.insert(i);
			}catch(IllegalArgumentException err){
				System.out.println(err.getMessage() + ": " + i);
			}
		}
		
		binTree.printTree();
		
		for(int i : intArray){
			if(RedBlackTree.search(binTree, i)){
				binTree.delete( i);
				System.out.println("Deleted: " + i);
				System.out.println("Size: " + binTree.size);
				binTree.printTree();
			}else{
				System.out.println("Value not found: " + i);
			}
		}
		
 		for(int i : intArray){
	 		try{
	 			binTree = RedBlackTree.insert(binTree, i);
	 		}catch(Exception err){
	 			System.out.println("Duplicate Insert: " + i);
	 		}
 		}
		
		System.out.println();
		System.out.println("Inorder list: ");
		LinkedList<Integer> intList = RedBlackTree.inOrder(binTree);
		for(Integer i : intList){
			System.out.print(i + ",");
		}
		
		System.out.println();
		System.out.println();
		System.out.print("Count Function for 10-35: ");
		
		System.out.println(RedBlackTree.count(binTree, 10, 35));
	}

}

