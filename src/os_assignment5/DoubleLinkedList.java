package os_assignment5;

public class DoubleLinkedList {
	PCB head;
	PCB tail;
	int size;
	MainHelper helper = new MainHelper();
	
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
		
		if(element == null)
			return;
		if(tail != null){
			tail.next = element;
			element.prev = tail;
			tail = element;
			tail.next = null;
		}
		if(head == null){
			head = tail = element;
		}
		//System.out.println("----------PUSHED: " + element.CPUBurst[0] + " index is: " + element.cpuIndex + " ----------");
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
		else
			head = null;

		size--;
		//System.out.println("----------POPPED: " + temp.CPUBurst[0] + " index is: " + temp.cpuIndex + " ----------");
		return temp;
	}
	
	public PCB peek(){
		return head;
	}
	public PCB getShortest(){
		PCB temp = head;
		int index = 1;
		int minIndex = 1;
		PCB minPCB = temp;
		int minCPU = temp.CPUBurst[temp.cpuIndex];
		System.out.println("START!!!!!!!!!!!!!!!!!!");
		while(temp != null){
			int current = temp.CPUBurst[temp.cpuIndex];
			System.out.println(index + ":\tCompare " + current + " with " + minCPU);
			if(current < minCPU){
				minIndex = index;
				minCPU = current;
				minPCB = temp;
			}
			//System.out.println();
			index++;
			System.out.println("Min is: " + minCPU);
			System.out.println("Min index is: "+ minIndex);
			temp = temp.next;
		}
		removeElement(minIndex);
		//removeElement(minPCB);
		//if(minPCB == null)
			System.out.println("Tried to remove: " + minPCB + " head is: " + head);
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
				System.out.println("Examine: " + temp + " with " + element + " out of " + (size+1));
				System.out.println("temp pr: " + temp.CPUBurst[0] + " element pr: " + element.CPUBurst[0]);
				push(temp);
			}
			else if(temp == element){
				found = 1;
				System.out.println("Element removed: " + element);
			}
		}
		if(found != 1)
			System.out.println("Element not found: " + element);
		
	}
	public void removeElement(int index){
		printList();
		System.out.println("THE SIZE: " + size);
		PCB temp;
		int counter = 1;
		int savedSize = size;
		int found =0;
		for(int i = 0; i < index; i++){
			System.out.println("Iteration " + (i+1));
			
			temp = pop();
			if((i+1) != index){
				push(temp);
			}
			else{
				found = 1;
				System.out.println("removed \tindex: " + index + "\telement: " + temp);
			}
		}
		if(found != 1)
			System.out.println("element not found at index: " + index);
				
		
		
	}
	
	public void printList(){
		PCB tmp = head;
		int counter = 1;
		System.out.println("~~~~~~~~~~LIST PRINTER~~~~~~~~~~");
		while(tmp != null){
			System.out.println(counter + ":\t" + tmp.CPUBurst[0]);
			tmp = tmp.next;
			counter++;
		}
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	
	
}
