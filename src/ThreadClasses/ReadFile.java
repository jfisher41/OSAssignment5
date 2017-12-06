package ThreadClasses;

import java.io.BufferedReader;
import java.io.FileReader;

import os_assignment5.DoubleLinkedList;
import os_assignment5.MainHelper;
import os_assignment5.PCB;
import os_assignment5.Prog;


public class ReadFile extends Prog implements Runnable {
	String fileName;
	DoubleLinkedList list;
	PCB queue[];
	int numberOfProccesses;
	
	public ReadFile(String fileName){
		this.fileName = fileName;
		numberOfProccesses = 0;

	}
	
	public void read(){
		String fileContents[];
		String line = null;
		try {
			
			BufferedReader buffReader = new BufferedReader(new FileReader(fileName));
			
			while((line = buffReader.readLine()) != null){
				fileContents = line.split(" +");
				
				//Send the line off to be analyzed
				try{
					analyzeLine(fileContents);
				}catch (RuntimeException e){break;} 
			}
			file_read_done = 1;
			procNum = numberOfProccesses;
			
			buffReader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void analyzeLine(String []line){
		if(line[0].equals("proc")){
			numberOfProccesses++;
			proc(line);
			
		}
		else if(line[0].equals("sleep")){;
			sleep(line);
		}
		else if(line[0].equals("stop")){
			throw new RuntimeException();
		}
		else
			System.out.println("READ:\tInvalid keyword \"" + line[0] + "\"");
	}
	
	//handles a proc line
	//creates and assigns values to a PCB
	//adds the new PCB to the linked list
	private void proc(String line[]){
		PCB element = new PCB();
		element.cpuIndex = 0;
		element.ioIndex = 0;
		element.priority = Integer.parseInt(line[1]);
		int burstNum = Integer.parseInt(line[2]);
		element.numCPUBurst = (burstNum/2) + 1;
		element.numIOBurst = burstNum/2;
		element.CPUBurst = getBurst(element.numCPUBurst, line, "CPU");
		element.IOBurst = getBurst(element.numIOBurst, line, "IO");
		
		element.creationTime = System.currentTimeMillis();
		element.rQueueInputTime = System.currentTimeMillis();
		
		element.id = numberOfProccesses;
		
		element.note = "in READ";
		
		readyQueue.push(element);
		System.out.println("READ:\tpushed " + element.id);
		//readyQueue.printList("READ ReadyQueue");
		//ioQueue.printList("READ IOQueue");
		sem1.release();
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

	public void run() {	
		read();
	}
}
