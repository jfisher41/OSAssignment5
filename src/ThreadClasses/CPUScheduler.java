package ThreadClasses;

import os_assignment5.PCB;
import os_assignment5.Prog;

public class CPUScheduler extends Prog implements Runnable{
	String alg;
	PCB element;
	int counter = 0;
	public CPUScheduler(){
		
		
		alg = arguments[2];
		element = null;
	}
	
	private void scheduler(){
		while(true){
			System.out.println("sch entr");
			System.out.println("CPU:\tIOQ size: " + ioQueue.size());
			System.out.println("CPU:\tRQ size: " + readyQueue.size());
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
		/**
		sem1.acquire();
		System.out.println("CPU:\tSem1 acquired\tsem1 size: " + sem1.availablePermits());
		element = readyQueue.pop();
		
		try{
		Thread.sleep(element.CPUBurst[element.cpuIndex]);
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("CPU: Out of bounds thrown");
			System.out.println("CPU: CPUindex: " + element.cpuIndex);
		}
		element.cpuIndex++;
		
		//add one because index starts at 0
		if(!(element.cpuIndex+1 >= element.numCPUBurst )){
			mutex2.acquire();
			ioQueue.push(element);
			mutex2.release();
			sem2.release();
			//System.out.println("CPU:\tSem2 released\tsem2 size: " + sem2.availablePermits());
		}
		//System.out.println("CPU:\tEnd loop");
		 * 
		 */
		
	}
	private void sjf() throws InterruptedException{
		System.out.println("start");
		
			mutex1.acquire();
	         sem1.acquire();
	         element = readyQueue.getShortest();
	    mutex1.release();
		
		System.out.println("CPU:\tSem1 acquired\tsem1 size: " + sem1.availablePermits());
		
		
		System.out.println("CPU: Pulled from RQ");
		try{
		Thread.sleep(element.CPUBurst[element.cpuIndex]);
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("CPU: Out of bounds thrown");
			System.out.println("CPU: CPUindex: " + element.cpuIndex);
		}
		
		
		//add one because index starts at 0
		if(!(element.cpuIndex+1 >= element.numCPUBurst )){
			mutex2.acquire();
			ioQueue.push(element);
			mutex2.release();
			sem2.release();
			
			System.out.println("CPU:\tSem2 released\tsem2 size: " + sem2.availablePermits());
			
		}
		else{
			counter++;
			System.out.println("Last CPUBurst\tcounter: " + counter);
		}
		
		element.cpuIndex++;
		System.out.println("CPU:\tEnd loop");
		
	}
	private void pr(){
		synchronized (this) {
	         try {
	        	//mutex1.acquire();
				sem1.acquire();
				element = readyQueue.getPriority();
				//mutex1.release();
				System.out.println(element.priority);
				
	         //System.out.println("burst: " + element.numCPUBurst + "\tindex: " + element.cpuIndex+1);
				if(element.numCPUBurst < element.cpuIndex+1){
					
				
					//mutex2.acquire();
					ioQueue.push(element);
					sem2.release();
					//mutex2.release();
				
					
				}
				element.cpuIndex++;
				
				
				
				
				
	         } catch (InterruptedException e) {}
	   } 
	
	}
	private void rr(){
	
	}

	public void run() {
		scheduler();
		
	}

}
