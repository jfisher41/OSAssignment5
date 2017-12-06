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
	
	public boolean isEmpty2(){
		if(head == null){
			return true;
		}
		else
			return false;
	}
	
	public void push(PCB element){
		element.prev = null;
		element.next = null;

		if(tail != null){
			tail.next = element;
			element.prev = tail;
			tail = element;
			tail.next = null;
		}
		if(head == null){
			head = element;
			tail = element;
		}
		size++;	
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

		return vipPCB;
	}
	
	public PCB getShortest(){
		PCB temp = head;
		int index = 1;
		int minIndex = 1;
		PCB minPCB = temp;
		int minCPU = temp.CPUBurst[temp.cpuIndex];
		while(temp != null){
			int current = temp.CPUBurst[temp.cpuIndex];
			if(current < minCPU){
				minIndex = index;
				minCPU = current;
				minPCB = temp;
			}
			index++;
			temp = temp.next;
		}
		removeElement(minIndex);
		
		return minPCB;
	}
	
	public void removeElement(int index){
		PCB temp;
		int found =0;
		for(int i = 0; i < index; i++){
			
			temp = pop();
			if((i+1) != index){
				push(temp);
			}
			else{
				found = 1;
			}
		}
	}
	
	public void removeElement(PCB element){
		int length = size;
		PCB temp;
		for(int i = 0; i < length; i++){
			temp = pop();
			if(temp != element && temp != null){
				push(temp);
			}
		}
	}
	
	public void printList(String name){
		PCB tmp = head;
		int counter = 1;
		String prev = "null";
		String current = "null";
		String next = "null";
			
		System.out.println("~~~~~~~~~~" + name + " LIST PRINTER~~~~~~~~~~");
		while(tmp != null){
			
			prev = "null";
			current = "null";
			next = "null";
			try{
				prev = Integer.toString(tmp.prev.id);
			}catch(Exception e){}
			
			try{
				current = Integer.toString(tmp.id);
			}catch(Exception e){}
			
			try{
				next = Integer.toString(tmp.next.id);
			}catch(Exception e){}
			
			System.out.println(counter + ":\t\tprev: " + prev + "\t\tcur: " + current + "\t\tnext: " + next);
			tmp = tmp.next;
			counter++;
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
}
