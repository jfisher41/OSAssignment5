package os_assignment5;

public class MainHelper {
	//DoubleLinkedList list = new DoubleLinkedList();
	
	//Prints the output for the program
	public void printStats(String fileName, String alg){
		System.out.printf("---------------------------------------\n"
				+ "Input File Name\t\t\t: %s\n"
				+ "CPU Scheduling Alg\t\t: %s\n"
				+ "CPU Utilization\t\t\t: ....\n"
				+ "Throughput\t\t\t: ....\n"
				+ "Avg. Turaround Time\t\t: ....\n"
				+ "Avg. Waiting Time in R Queue\t: ....\n"
				+ "---------------------------------------",
				fileName, alg);
	}
	
	/**
	//determines what type of line is in the file (proc/sleep/stop)
	public void analyzeLine(String []line){
		if(line[0].equals("proc")){
			proc(line);
			System.out.println("proc detected");
		}
		else if(line[0].equals("sleep")){
			System.out.println("sleep detected");
			sleep(line);
		}
		else if(line[0].equals("stop")){
			System.out.println("stop detected");
			throw new RuntimeException();
		}
		else
			System.out.println("Invalid keyword");
	}
	
	//handles a proc line
	//creates and assigns values to a PCB
	//adds the new PCB to the linked list
	private void proc(String line[]){
		PCB element = new PCB();
		element.priority = Integer.parseInt(line[1]);
		int burstNum = Integer.parseInt(line[2]);
		element.numCPUBurst = (burstNum/2) + 1;
		element.numIOBurst = burstNum/2;
		element.CPUBurst = getBurst(element.numCPUBurst, line, "CPU");
		element.IOBurst = getBurst(element.numIOBurst, line, "IO");
		
		list.push(element);
	}
	
	private void sleep(String line[]){
		try {
			Thread.sleep(Long.parseLong(line[1]));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//Returns the array of numbers for the cpu or io bursts
	private int[] getBurst(int num, String[] line, String type){
		int result[] = new int[num];
		int switcher = 0;
		int index = 0;
		int start = 0;
		
		if(type.equals("CPU"))
			start = 3;
		else if (type.equals("IO"))
			start = 4;
		
		for(int i = start; i < line.length; i++){
			if(switcher%2 == 0){
				result[index] = Integer.parseInt(line[i]);
				index++;
			}
			switcher++;
		}
		return result;
	}
	**/
	
	//returns the list of the PCBs
	//public DoubleLinkedList getList(){
	//	return list;
	//}
	
	//printer methods
	public void printStringArray(String arr[]){
		for(String str: arr){
			System.out.print(str + " ");
		}
	}
	public void printIntArray(int arr[]){
		for(int num: arr){
			System.out.print(num + " ");
		}
	}
	public void printLinkedList(DoubleLinkedList list){
		PCB tmp;
		while(!list.isEmpty()){
			tmp = list.pop();
			System.out.println(tmp.priority);
		}
	}
	//public void printLinkedList(){
	//	PCB tmp;
	//	while(!list.isEmpty()){
	//		tmp = list.pop();
	//		System.out.println(tmp.CPUBurst[0]);
	//	}
	//}
}
