package os_assignment5;

public class MainHelper {
	DoubleLinkedList list = new DoubleLinkedList();
	
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
	
	public void analyzeLine(String []line){
		if(line[0].equals("proc")){
			System.out.println("proc detected");
		}
		else if(line[0].equals("sleep")){
			System.out.println("sleep detected");
		}
		else if(line[0].equals("stop")){
			System.out.println("stop detected");
		}
		else
			System.out.println("Invalid keyword");
	}
	
	private void proc(String line[]){
		PCB element = new PCB();
		element.priority = Integer.parseInt(line[1]);
		int burstNum = Integer.parseInt(line[2]);
		element.numCPUBurst = (burstNum/2) + 1;
		element.numIOBurst = burstNum/2;
		element.CPUBurst = getCPUBurst(element.numCPUBurst, line);
		element.IOBurst = getIOBurst(element.numIOBurst, line);
	}
	
	private int[] getCPUBurst(int num, String[] line){
		int result[] = new int[num];
		int switcher = 0;
		int index = 0;
		for(int i = 3; i < line.length; i++){
			if(switcher%2 == 0){
				result[index] = Integer.parseInt(line[i]);
			}
			switcher++;
			index++;
		}
		
		
		return null;
	}

}
