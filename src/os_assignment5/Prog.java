package os_assignment5;

import java.text.DecimalFormat;
import java.util.concurrent.Semaphore;
import ThreadClasses.CPUScheduler;
import ThreadClasses.IOSystem;
import ThreadClasses.ReadFile;

public class Prog {
	
	public static MainHelper helper;
	public static String[] arguments;
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
	
	public static long totalWaitingTime = 0;
	public static int totalTurnaround = 0;
	public static int totalUtilization = 0;

	
	public static void main(String[] args) {
		
		
		helper = new MainHelper();
		ioQueue = new DoubleLinkedList();
		readyQueue = new DoubleLinkedList();
		
		sem1 = new Semaphore(cpu_sch_done);
		sem2 = new Semaphore(cpu_sch_done);
		mutex1 = new Semaphore(1);
		mutex2 = new Semaphore(1);
		
		file_read_done = 0;
		cpu_sch_done = 0;
		io_sys_done = 0;
		
		double startTime;
		double endTime;
		double totalTime = 0;
		
		double cpuUtilization = 0.0;
		double throughput = 0.0;
		double avgTurnaroundTime = 0.0;
		double avgWaitingTime = 0.0;
		
		
		DecimalFormat numberFormat = new DecimalFormat("#.00");
		/**
		//Get argv from the console
		Scanner scan = new Scanner(System.in);
		String commandInput[];
		System.out.println("Enter commands:");
		String params = scan.nextLine();
		commandInput = params.split(" ");
		
		for(String param: commandInput){
			System.out.println(param);
		}
		**/
		
		//temp command line simulator
		String commandArgs[] = {"prog", "-alg", "FIFO", "-quantum", "30", "-input", "input.txt"};
		arguments = commandArgs;
		
		
		//Set up threads
		ReadFile reader = new ReadFile(commandArgs[6]);
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
		
			t1.join();
			System.out.println("1 joined");
			t2.join();
			System.out.println("2 joined");
			t3.join();
			System.out.println("3 joined");
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
		

		helper.printStats(commandArgs[6], arguments[2], cpuUtilization, throughput, avgTurnaroundTime, avgWaitingTime);
	}
}
