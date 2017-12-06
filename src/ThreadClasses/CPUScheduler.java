package ThreadClasses;

import os_assignment5.PCB;
import os_assignment5.Prog;

public class CPUScheduler extends Prog implements Runnable{
	String alg;
	
	int counter;
	int cpuBurstTime;
	
	//for cpuUtilization
	long beforeSleep;
	long afterSleep;
	
	public CPUScheduler(){
		counter = 0;
		alg = algorithm;
		
	}
	
	//Runs infinite loop that executes the code associated with the set algorithm 
	private void scheduler(){
		while(true){
			
			if(readyQueue.isEmpty() && ioQueue.isEmpty2() && file_read_done == 1){
				System.out.println("one broke");
				break;
			}
			if(allDone == procNum){
				System.out.println("two broke");
				break;
			}
			
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
	
	//If algorithm is FIFO
	private void fifo() throws InterruptedException{
			PCB element;
			sem1.acquire();
			element = readyQueue.pop();
			performCalculations(element);	
	}
	
	//If algorithm is SJF
	private void sjf() throws InterruptedException{
		PCB element;
		sem1.acquire();		
		element = readyQueue.getShortest2();
		performCalculations(element);	
		}
	
	//If algorithm is PR
	private void pr() throws InterruptedException{
		PCB element;
		sem1.acquire();
		element = readyQueue.getPriority();
		performCalculations(element);
	}
	
	//If algorithm is RR
	private void rr() throws InterruptedException{
		PCB element;
		sem1.acquire();
		element = readyQueue.pop();
		System.out.println("CPU:\trecieved " + element.id);
		
		//calculate time element spent in readyQueue
		element.totalWaitingTime += System.currentTimeMillis() - element.rQueueInputTime;
		
		cpuBurstTime = element.CPUBurst[element.cpuIndex];
		
		if(cpuBurstTime < quantum){
			beforeSleep = System.currentTimeMillis();
			Thread.sleep(cpuBurstTime);
			afterSleep = System.currentTimeMillis();
			
			//calculate the amount of time the thread slept
			element.totalUtilization += afterSleep - beforeSleep;
			
			element.cpuIndex++;		
		}
		else{
			beforeSleep = System.currentTimeMillis();
			Thread.sleep(quantum);
			afterSleep = System.currentTimeMillis();
			
			//calculate the amount of time the thread slept
			element.totalUtilization += afterSleep - beforeSleep;
			
			System.out.println("before: " + element.CPUBurst[element.cpuIndex]);
			cpuBurstTime -= quantum;
			element.CPUBurst[element.cpuIndex] = cpuBurstTime;	
			System.out.println("after: " + element.CPUBurst[element.cpuIndex] + " " + element.cpuIndex + " " + element.id);
		}

		if(element.cpuIndex >= element.numCPUBurst){
			System.out.println("Marked " + element.id + " as done.");
			element.done = 1;
			allDone++;
			
			//update stats before disgarding the element
			totalUtilization += element.totalUtilization;
			totalTurnaround += (System.currentTimeMillis() - element.creationTime);
			totalWaitingTime += element.totalWaitingTime;
			
		}
		else{

			mutex2.acquire();
			ioQueue.push(element);
			System.out.println("CPU:\tpushed " + element.id);
			sem2.release();
			mutex2.release();
		}
	}
	
	//Code used for FIFO, SJF, and PR
	private void performCalculations(PCB element) throws InterruptedException{
			
		//calculate time element spent in readyQueue
		element.totalWaitingTime += System.currentTimeMillis() - element.rQueueInputTime;
		
		//getBurstime based on cpuBurst index
		cpuBurstTime = element.CPUBurst[element.cpuIndex];
		
		beforeSleep = System.currentTimeMillis();
		Thread.sleep(cpuBurstTime);
		afterSleep = System.currentTimeMillis();
		System.out.println("Slept " + cpuBurstTime);
		
		//calculate the amount of time the thread slept
		element.totalUtilization += afterSleep - beforeSleep;
		
		//update index
		element.cpuIndex = element.cpuIndex+1;
		
		//if last cpuBurst
		
		if(element.cpuIndex >= element.numCPUBurst){
			System.out.println("Marked " + element.id + " as done.");
			element.done = 1;
			allDone++;
			
			//update stats before disgarding the element
			totalUtilization += element.totalUtilization;
			totalTurnaround += (System.currentTimeMillis() - element.creationTime);
			totalWaitingTime += element.totalWaitingTime;
			
		}
		else{

			mutex2.acquire();
			ioQueue.push(element);
			sem2.release();
			mutex2.release();
		}
	}

	public void run() {
		scheduler();
	}
}
