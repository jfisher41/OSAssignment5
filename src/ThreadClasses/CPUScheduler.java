package ThreadClasses;

import os_assignment5.PCB;
import os_assignment5.Prog;

public class CPUScheduler extends Prog implements Runnable{
	String alg;
	
	public CPUScheduler(){
		alg = arguments[2];
	}
	
	private void scheduler(){
		while(true){
			
			if(readyQueue.isEmpty() && ioQueue.isEmpty() && file_read_done == 1)
				break;
			try {
				if(alg.equals("FIFO")){
					fifo();
				}
				else if(alg.equals("SJF")){
					sjf();
				}
				else if(alg.equals("PR")){
					pr();
				}
				else if(alg.equals("RR")){
					rr();
				}
			} catch (InterruptedException e) {e.printStackTrace();}
		}
		cpu_sch_done = 1;
	}
	private void fifo() throws InterruptedException{
		PCB element;
		sem1.acquire();
		System.out.println("Sem1 acquired");
		element = readyQueue.pop();
		System.out.println(element.CPUBurst[element.cpuIndex] + "Accessed");
		Thread.sleep(element.CPUBurst[element.cpuIndex]);
		element.cpuIndex++;
		
		if(!(element.cpuIndex >= element.numCPUBurst )){
			mutex2.acquire();
			ioQueue.push(element);
			mutex2.release();
			sem2.release();
		}
		
	}
	private void sjf() throws InterruptedException{
		//PCB element;
		//sem1.acquire();
		//element = readyQueue.pop();		
		//Thread.sleep(element.CPUBurst[element.cpuIndex]);
		//element.cpuIndex++;
		
		//if(!(element.numCPUBurst >= element.cpuIndex)){
		//	mutex2.acquire();
		//	ioQueue.push(element);
		//	mutex2.release();
		//	sem2.release();
		//}
	}
	private void pr(){
	
	}
	private void rr(){
	
	}

	@Override
	public void run() {
		scheduler();
		
	}

}
