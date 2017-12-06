package os_assignment5;

import java.util.concurrent.Semaphore;

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
		PCB temp;
		if(element == null)
			return;
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
		
		//printList("poped " + temp.id);
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
	public PCB getShortest(Semaphore sem1){
		if(head == null){
			sem1.release();
			return null;
	}
		
		if(head.done == 1){
			pop();
		}
		PCB temp = head;
		int index = 1;
		int minIndex = 1;
		int current;
		PCB minPCB = temp;
		if (temp == null)
			return null;
		int minCPU = temp.CPUBurst[temp.cpuIndex];
		while(temp != null){
			current = temp.CPUBurst[temp.cpuIndex];
			//System.out.println("Compare " + current + " with " + minCPU);
			if(current < minCPU){
				minIndex = index;
				minCPU = current;
				minPCB = temp;
			}
			//System.out.println(minCPU + " is smaller");
			index++;
			temp = temp.next;
		}
		//System.out.println("END SESSION\n");
		//System.out.println(minIndex + " " + (index-1));
		removeElement(minIndex, (index-1));
		
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
			}
		}
		//if(found != 1)
			//System.out.println("Element not found: " + element);
		
	}
	public void removeElement(int minIndex, int index){
		PCB temp = head;
		PCB prior = temp;
		int found =0;
		//System.out.println("START");
		for(int i = 0; i < index; i++){
			
			//temp = pop();
			if((i+1) != minIndex){
				prior = temp;
				temp = temp.next;
				//printList("during return");
			}
			else{
				if(temp == head)
					pop();
				else if(temp == tail)
					tail.prev.next = null;
				else{
					prior.next = temp.next;
					temp.next.prev = prior;
				}
				//System.out.println("Returned " + temp.id);
				found = 1;
			}
		}
		//printList("After return");
	}
	
	public PCB getShortest2(){
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
		removeElement2(minIndex);
		
		return minPCB;
	}
	
	public void removeElement2(int index){
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
