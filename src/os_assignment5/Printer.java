package os_assignment5;

import java.text.DecimalFormat;

public class Printer extends Prog{
	
	//Prints the output for the program
	public void printStats(String fileName, String algorithm, long utilization, double throughput, double turnaround, double waiting){
		DecimalFormat numberFormat = new DecimalFormat("0.000");
		double doubles [] = new double[3];
		String printout[] = new String[7];
		
		printout[0] = fileName;
		printout[1] = algorithm;
		printout[3] = Long.toString(utilization);
		
		int index = 4;
		
		doubles[0] = throughput;
		doubles[1] = turnaround;
		doubles[2] = waiting;
		
		for(double stat : doubles){
			printout[index] = numberFormat.format(stat);
			index++;
		}
		
		if(quantum != 0)
			printout[2] = "(" + Integer.toString(quantum) + ")";
		else
			printout[2] = "";
		
		System.out.printf("---------------------------------------\n"
				+ "Input File Name\t\t\t: %s\n"
				+ "CPU Scheduling Alg\t\t: %s %s\n"
				+ "CPU Utilization\t\t\t: %s\n"
				+ "Throughput\t\t\t: %s\n"
				+ "Avg. Turaround Time\t\t: %s\n"
				+ "Avg. Waiting Time in R Queue\t: %s\n"
				+ "---------------------------------------",
				printout[0], printout[1], printout[2], printout[3], printout[4], printout[5], printout[6]);
	}
}
