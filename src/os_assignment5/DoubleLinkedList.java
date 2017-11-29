package os_assignment5;

public class DoubleLinkedList {
	PCB head;
	PCB tail;
	int size;
	
	public DoubleLinkedList(){
		head = null;
		tail = null;
		size = 0;
	}
	
	public int size(){
		return size;
	}
	
	public boolean isEmpty(){
		return size == 0;
	}
	
	public void push(PCB element){
		if(tail != null){
			tail.next = element;
			element.prev = tail;
			tail = element;
		}
		if(head == null){
			head = tail = element;
		}
		size++;
	}
	
	public PCB pop(){
		if(size == 0){
			System.out.println("No more elements");
		}
		PCB temp = tail;
		tail = tail.prev;
		tail.next = null;
		size--;
		return temp;
	}
	
	
	

}
