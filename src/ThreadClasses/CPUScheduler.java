package ThreadClasses;

import os_assignment5.PCB;
import os_assignment5.Prog;

public class CPUScheduler extends Prog implements Runnable{
	String alg;
	PCB element;
	int counter;
	int cpuBurstTime;
	
	//for cpuUtilization
	long beforeSleep;
	long afterSleep;
	
	public CPUScheduler(){
		
		counter = 0;
		alg = arguments[2];
		element = null;
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
		System.out.println("CPU:\tCPU DONE");
	}
	
	private void fifo() throws InterruptedException{
			sem1.acquire();
			element = readyQueue.pop();
			performCalculations(element);	
	}
	
	private void sjf() throws InterruptedException{
			sem1.acquire();
			element = readyQueue.getShortest();
			performCalculations(element);	   
	}
	
	private void pr() throws InterruptedException{
	
		sem1.acquire();
		element = readyQueue.getPriority();
		performCalculations(element);
	}
	
	private void rr() throws InterruptedException{
		int quantum = Integer.parseInt(arguments[4]);
		int cpuBurstTime;
		
		sem1.acquire();
		element = readyQueue.pop();
		cpuBurstTime = element.CPUBurst[element.cpuIndex];
		
		if(cpuBurstTime < quantum){
			Thread.sleep(cpuBurstTime);
			element.cpuIndex++;		
		}
		else{
			Thread.sleep(quantum);
			cpuBurstTime -= quantum;
			element.CPUBurst[element.cpuIndex] = cpuBurstTime;	
		}

		if(element.numCPUBurst > element.cpuIndex + 1){
			mutex2.acquire();
			ioQueue.push(element);
			sem2.release();
			mutex2.release();
		}
	}
	
	private void performCalculations(PCB element) throws InterruptedException{
		//calculate time element spent in readyQueue
		element.totalWaitingTime += System.currentTimeMillis() - element.rQueueInputTime;
		
		//getBurstime based on cpuBurst idex
		cpuBurstTime = element.CPUBurst[element.cpuIndex];
		
		beforeSleep = System.currentTimeMillis();
		Thread.sleep(cpuBurstTime);
		afterSleep = System.currentTimeMillis();
		
		//calculate the amount of time the thread slept
		element.totalUtilization += afterSleep - beforeSleep;
		
		//update index
		element.cpuIndex++;
		
		//if last cpuBurst
		if(element.numCPUBurst > element.cpuIndex+1){
			mutex2.acquire();
			ioQueue.push(element);
			sem2.release();
			
			//update stats before disgarding the element
			totalUtilization += element.totalUtilization;
			totalTurnaround += (System.currentTimeMillis() - element.creationTime);
			totalWaitingTime += element.totalWaitingTime;
			
			mutex2.release();
		}
	}

	public void run() {
		scheduler();
	}
}
