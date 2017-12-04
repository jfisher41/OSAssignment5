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
		int current;

		while(result != null){
			current = result.CPUBurst[result.cpuIndex];
			if(current < minCPU){
				minCPU = current;
				minPCB = result;
			}
			result = result.next;	
		}
		removeElement(minPCB);
		return minPCB;
	}
	
	public PCB getPriority(){
		PCB result = head;
		PCB vipPCB = head;
		int max = head.priority;
		int current;

		while(result != null){
			current = result.priority;
			if(current > max){
				max = current;
				vipPCB = result;
			}
			result = result.next;	
		}
		removeElement(vipPCB);
		//System.out.println(vipPCB.priority);
		return vipPCB;
	}
	
	public void removeElement(PCB element){
		int length = size;
		int found = 0;
		PCB temp;
		for(int i = 0; i < length; i++){
			temp = pop();
			if(temp != element && temp != null){
				push(temp);
			}
			else if(temp == element){
				found = 1;
				System.out.println("Element removed: " + element);
			}
		}
		if(found != 1)
			System.out.println("Element not found");
		
	}
	
	
}
