package foodCourt;

/************************************************************************
 * LinkedList used to manage a Queue. Most of it has been coded
 * in class.
 * The final code is from Ira Woodring's gitHub repository.
 *
 * @author Rosa Fleming
 * @author Gionata Bonazzi
 * @author Alex Woods
 * 
 * @version 4/13/17
 ***********************************************************************/
public class LinkedList<T> {
	
	/** The first element of the List **/
	private Node<T> head;
	
	/** The last element of the list **/
	private Node<T> tail;
	
	/** The size of the list **/
	private int size;

	/********************************************************************
	 * Constructor for an empty LinkedList.
	 *******************************************************************/
	public LinkedList(){
		head = null;
		tail = null;
		size = 0;
	}

	/********************************************************************
	 * Private class for a Node object.
	 * Each node is an element in the LinkedList.
	 *******************************************************************/
	private class Node<T>{
		
		/** The object inside this Node **/
		protected T element;
		
		/** The next Node **/
		protected Node<T> next;
		
		/****************************************************************
		 * Create a Node that contains the element.
		 * @param element the object inside the Node.
		 ***************************************************************/
		protected Node(T element){
			this.element = element;
		}
	}

	/********************************************************************
	 * Add an element at the end of the list.
	 * @param element element to be added.
	 *******************************************************************/
	public void enQ(T element){
		Node<T> t = new Node<T>(element);
		if(size==0){
			head = t;
			head.next = t;
			tail = t;
		} else {
			tail.next = t;
			tail = t;
		}
		size++;
	}

	/********************************************************************
	 * Takes the first element of the list out.
	 * @return the first element of the list.
	 * @throws EmptyQException if the list is empty
	 ********************************************************************/
	public T deQ() throws EmptyQException{
		if(size == 0){
			throw new EmptyQException("The Q is empty");
		}
		Node<T> temp = head;
		head = head.next;
		size--;
		return temp.element;
	}
	
	/********************************************************************
	 * @return the list's size.
	 *******************************************************************/
	public int size(){
		return size;
	}

}
