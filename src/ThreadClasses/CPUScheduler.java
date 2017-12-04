package ThreadClasses;

import os_assignment5.PCB;
import os_assignment5.Prog;

public class CPUScheduler extends Prog implements Runnable{
	String alg;
	PCB element;
	int counter;
	int cpuBurstTime;
	public CPUScheduler(){
		
		counter = 0;
		alg = arguments[2];
		element = null;
	}
	
	private void scheduler(){
		while(true){
			System.out.println("sch entr");
			System.out.println("CPU:\tIOQ size: " + ioQueue.size());
			System.out.println("CPU:\t--------RQ size: " + readyQueue.size() + "--------");
			System.out.println("CPU:\tioDone: " + io_sys_done);
			if(readyQueue.isEmpty() && ioQueue.isEmpty() && file_read_done == 1)
				break;
			//if(file_read_done == 1 && counter == procNum){
			///	System.out.println("proc: " + procNum);
			//	break;
			//}
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
				System.out.println(element.cpuIndex);
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
	private void sjf() throws InterruptedException{
		 try {
				
			 	sem1.acquire();
			 	mutex1.acquire();
			 	System.out.println("Mutex1 acquired by CPU!");
				element = readyQueue.getShortest();
				System.out.println("Mutex1 released by CPU!");
				mutex1.release();
				
				
				cpuBurstTime = element.CPUBurst[element.cpuIndex];
				Thread.sleep(cpuBurstTime);
				element.cpuIndex++;

				if(element.numCPUBurst > element.cpuIndex+1){
					
					System.out.println("Before: " + ioQueue.size());
					mutex2.acquire();
					ioQueue.push(element);
					sem2.release();
					mutex2.release();
					System.out.println("After: " + ioQueue.size());
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
				
				System.out.println("Before: " + ioQueue.size());
				mutex2.acquire();
				ioQueue.push(element);
				sem2.release();
				mutex2.release();
				System.out.println("After: " + ioQueue.size());
			}
         } catch (InterruptedException e) {}
	}

	public void run() {
		scheduler();
		
	}

}
