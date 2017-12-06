package os_assignment5;

import java.util.concurrent.Semaphore;
import ThreadClasses.CPUScheduler;
import ThreadClasses.IOSystem;
import ThreadClasses.ReadFile;

public class Prog {
	
	public static Printer printer;
	public static DoubleLinkedList ioQueue;
	public static DoubleLinkedList readyQueue;
	
	public static Semaphore sem1;
	public static Semaphore sem2;
	
	public static Semaphore mutex1;
	public static Semaphore mutex2;
	
	public static int file_read_done;
	public static int cpu_sch_done;
	public static int io_sys_done;
	
	public static int procNum = 0;
	public static int quantum;
	public static String algorithm;
	public static int allDone = 0;
	
	public static long totalWaitingTime = 0;
	public static int totalTurnaround = 0;
	public static int totalUtilization = 0;
	
	public static void main(String[] args) {
		
		int quantumFlag = 0;
		
		printer = new Printer();
		ioQueue = new DoubleLinkedList();
		readyQueue = new DoubleLinkedList();
		
		sem1 = new Semaphore(cpu_sch_done);
		sem2 = new Semaphore(cpu_sch_done);
		mutex1 = new Semaphore(1);
		mutex2 = new Semaphore(1);
		
		file_read_done = 0;
		cpu_sch_done = 0;
		io_sys_done = 0;
		
		long startTime;
		long endTime;
		long totalTime = 0;
		
		long cpuUtilization = 0;
		double throughput = 0.0;
		double avgTurnaroundTime = 0.0;
		double avgWaitingTime = 0.0;
		
		String file;
		
		//Take care of arguments
		if(args[2].equals("-quantum")){
			algorithm = args[1];
			quantum = Integer.parseInt(args[3]);
			file = args[5];
			quantumFlag = 1;
		}
		else{
			algorithm = args[1];
			file = args[3];
		}
		
		//error if quantum but no RR
		if(quantumFlag == 1 && !algorithm.equals("RR") ){
			System.out.println("Error. Cannot have quantum with algorithm " + algorithm + ".");
			return;
		}
		//or RR but no quantum
		if(quantumFlag == 0 && algorithm.equals("RR")){
			System.out.println("Error. Round Robin must have quantum.");
			return;
		}
			
		
		//Set up threads
		ReadFile reader = new ReadFile(file);
		Thread t1 = new Thread(reader);
		
		CPUScheduler cpu = new CPUScheduler();
		Thread t2 = new Thread(cpu);
		
		IOSystem io = new IOSystem();
		Thread t3 = new Thread(io);
		
		startTime = System.currentTimeMillis();
		try {
			//start the threads
			t1.start();
			t1.join();
			t2.start();
			t3.start();
		
			//join all the threads
			t1.join();
			t2.join();
			t3.join();
			
		} catch (InterruptedException e) {e.printStackTrace();}
		
		//get total time
		endTime = System.currentTimeMillis();
		totalTime = endTime - startTime;
		
		//get CPU Utilization
		cpuUtilization = totalTime - totalUtilization;
		
		//get Throughput
		throughput = (double)procNum/totalTime;
		
		//get Avg. Turnaround Time
		avgTurnaroundTime = (double)totalTurnaround/procNum;
	
		//get Avg. Waiting Time
		avgWaitingTime = (double)totalWaitingTime/procNum;

		printer.printStats(file, algorithm, cpuUtilization, throughput, avgTurnaroundTime, avgWaitingTime);
	}
}
