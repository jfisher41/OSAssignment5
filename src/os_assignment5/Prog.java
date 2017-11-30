package os_assignment5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import ThreadClasses.ReadFile;



public class Prog {
	
	public static void main(String[] args) {
		
		MainHelper helper = new MainHelper();
		DoubleLinkedList ioQueue = new DoubleLinkedList();
		PCB readyQueue[] = null;
		
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
		String commandArgs[] = {"prog", "-alg", "FIFO", "-quantum", "3", "-input", "input.txt"};
		
		
		//read file in thread 1
		ReadFile reader = new ReadFile(commandArgs[6], helper);
		Thread t1 = new Thread(reader);
		t1.start();
		
		//for(int i = 0; i < 100; i++)
		//System.out.println((i+1) + "One-thousand");
		
		try {
			t1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Update the ioQueue
		ioQueue = helper.getList();

		helper.printStats(commandArgs[6], commandArgs[2]);
	}

}
