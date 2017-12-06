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
		//System.out.println("Hello");
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
			System.out.println("READ:\tREAD DONE");
			
			buffReader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void analyzeLine(String []line){
		if(line[0].equals("proc")){
			System.out.println("READ:\tproc detected");
			numberOfProccesses++;
			proc(line);
			
		}
		else if(line[0].equals("sleep")){
			System.out.println("READ:\tsleep detected");
			sleep(line);
		}
		else if(line[0].equals("stop")){
			System.out.println("READ:\tstop detected");
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
		
		//try {
			//mutex1.acquire();
			readyQueue.push(element);
			//mutex1.release();
			sem1.release();
		//} catch (InterruptedException e) {e.printStackTrace();}
		
		System.out.println("READ:\tSem1 released\tsem1 size: " + sem1.availablePermits());
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
