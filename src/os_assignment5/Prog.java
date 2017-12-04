package os_assignment5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
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
	
	public static void main(String[] args) {
		
		
		helper = new MainHelper();
		ioQueue = new DoubleLinkedList();
		readyQueue = new DoubleLinkedList();
		
		sem1 = new Semaphore(cpu_sch_done);
		sem2 = new Semaphore(cpu_sch_done);
		mutex1 = new Semaphore(cpu_sch_done);
		mutex2 = new Semaphore(cpu_sch_done);
		
		file_read_done = 0;
		cpu_sch_done = 0;
		io_sys_done = 0;
		
		
		
		
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
		String commandArgs[] = {"prog", "-alg", "PR", "-quantum", "3", "-input", "input.txt"};
		arguments = commandArgs;
		
		
		//Set up threads
		ReadFile reader = new ReadFile(commandArgs[6]);
		Thread t1 = new Thread(reader);
		
		CPUScheduler cpu = new CPUScheduler();
		Thread t2 = new Thread(cpu);
		
		IOSystem io = new IOSystem();
		Thread t3 = new Thread(io);
		try {
		//start the threads
		t1.start();
		t1.join();
		t2.start();
		t3.start();
		
		//for(int i = 0; i < 100; i++)
		//System.out.println((i+1) + "One-thousand");
		
		
			t1.join();
			System.out.println("1 joined");
			t2.join();
			System.out.println("2 joined");
			t3.join();
			System.out.println("3 joined");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Update the ioQueue
		//ioQueue = helper.getList();


		helper.printStats(commandArgs[6], arguments[2]);
	}

}
