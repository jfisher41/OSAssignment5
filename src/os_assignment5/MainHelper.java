package os_assignment5;

public class MainHelper {
	
	//public String[] readFile();
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

}
