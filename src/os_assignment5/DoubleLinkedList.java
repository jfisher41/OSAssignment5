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
			tail.next = null;
		}
		if(head == null){
			head = tail = element;
		}
		size++;
		//System.out.println("pushed: " + size);
	}
	
	public PCB pop(){
		PCB temp = head;
		if(head == null)
			return null;
		head = head.next;
		
		if(head != null)
			head.prev = null;

		size--;

		return temp;
	}
	
	public PCB peek(){
		return head;
	}
	public PCB getShortest(){
		PCB result = head;
		PCB minPCB = head;
		int minCPU = head.CPUBurst[head.cpuIndex];
		int length = size;
		int current;
		//System.out.println("Head:\t" + head);
		//System.out.println("Result:\t" + result);
		
		
		while(result != null){
			current = result.CPUBurst[result.cpuIndex];
			if(current < minCPU){
				minCPU = current;
				minPCB = result;
			}
			result = result.next;	
		}
		//System.out.println("Min PCB:\t" + minPCB);
		removeElement(minPCB);
		return minPCB;
	}
	
	public void removeElement(PCB element){
		int length = size;
		int found = 0;
		PCB temp;
		for(int i = 0; i < length; i++){
			temp = pop();
			if(temp != element){
				push(temp);
			}
			else{
				found = 1;
				System.out.println("Element removed");
			}
		}
		if(found != 1)
			System.out.println("Element not found");
		
	}
	
	
}
