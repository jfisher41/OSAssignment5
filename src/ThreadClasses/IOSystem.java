package ThreadClasses;

import os_assignment5.PCB;
import os_assignment5.Prog;

public class IOSystem extends Prog implements Runnable {
	
	private void ioSystem(){
		PCB element = null;
		
		while(true){
			System.out.println("---------------IO ENTERED---");
			System.out.println("IO:\tIOQ size: " + ioQueue.size());
			System.out.println("IO:\tRQ size: " + readyQueue.size());
			
			if(cpu_sch_done == 1 || (readyQueue.isEmpty() && ioQueue.isEmpty() && file_read_done == 1)){
				System.out.println("---------------IO JAILBREAK---");
				break;
			}
			
			try {
				
				//mutex2.acquire();
				sem2.acquire();
				element = ioQueue.pop();
				//mutex2.release();

				System.out.println("IO:\tSem2 acquired\tsem2 size: " + sem2.availablePermits());
				
				Thread.sleep(element.IOBurst[element.ioIndex]);
				element.ioIndex++;
				
				
				mutex1.acquire();
				readyQueue.push(element);
				sem1.release();
				mutex1.release();

				System.out.println("IO:\tSem1 released\tsem1 size: " + sem1.availablePermits());
			} //catch (InterruptedException e) {e.printStackTrace();}
			catch (Exception e) {System.out.println("ERROR\npriority: " + element.priority + "index: " + element.ioIndex);}
			System.out.println("---------------IO ENDED---");
		}
		
	io_sys_done = 1;
	System.out.println("IO:\tIO DONE");
	}

	public void run() {
		ioSystem();
		
	}

}
