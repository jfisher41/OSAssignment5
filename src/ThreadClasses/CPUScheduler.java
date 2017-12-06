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
		
		 try {
				sem1.acquire();
				element = readyQueue.pop();
				
				//for waiting time
				element.totalWaitingTime += System.currentTimeMillis() - element.rQueueInputTime;
				
				cpuBurstTime = element.CPUBurst[element.cpuIndex];
				
				beforeSleep = System.currentTimeMillis();
				Thread.sleep(cpuBurstTime);
				afterSleep = System.currentTimeMillis();
				element.totalUtilization += afterSleep - beforeSleep;
				
				element.cpuIndex++;
				
				if(element.numCPUBurst > element.cpuIndex+1){
					mutex2.acquire();
					ioQueue.push(element);
					sem2.release();
					
					//for waiting time
					totalUtilization += element.totalUtilization;
					totalWaitingTime += element.totalWaitingTime;
					mutex2.release();
				}
	         } catch (InterruptedException e) {}
	   
	}
	private void sjf() throws InterruptedException{
		 try {
			 	sem1.acquire();
			 	mutex1.acquire();
				element = readyQueue.getShortest();
				
				mutex1.release();
				
				cpuBurstTime = element.CPUBurst[element.cpuIndex];
				Thread.sleep(cpuBurstTime);
				element.cpuIndex++;

				if(element.numCPUBurst > element.cpuIndex+1){
					
					mutex2.acquire();
					ioQueue.push(element);
					sem2.release();
					mutex2.release();
				}
	         } catch (InterruptedException e) {}
	   
	}
	private void pr(){
		
		try {
			sem1.acquire();
			element = readyQueue.getPriority();

			cpuBurstTime = element.CPUBurst[element.cpuIndex];
			Thread.sleep(cpuBurstTime);
			element.cpuIndex++;

			if(element.numCPUBurst > element.cpuIndex+1){
				mutex2.acquire();
				ioQueue.push(element);
				sem2.release();
				mutex2.release();
			}
		} catch (InterruptedException e) {}
	   
	
	}
	private void rr(){
		int quantum = Integer.parseInt(arguments[4]);
		int cpuBurstTime;
		
		try {
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
         } catch (InterruptedException e) {}
	}

	public void run() {
		scheduler();
	}

}
