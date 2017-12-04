package ThreadClasses;

import os_assignment5.PCB;
import os_assignment5.Prog;

public class IOSystem extends Prog implements Runnable {
	
	private void ioSystem(){
		PCB element = null;
		
		while(true){
			
			//System.out.println("IO:\tIOQ size: " + ioQueue.size());
			//System.out.println("IO:\tRQ size: " + readyQueue.size());
			
			if(readyQueue.isEmpty() && ioQueue.isEmpty() && file_read_done == 1){
				break;
			}
			
			try {
				sem2.acquire();
				System.out.println("IO:\tSem2 acquired\tsem2 size: " + sem2.availablePermits());
				element = ioQueue.pop();
				//System.out.println("IO: first CPU: " + element.CPUBurst[0] + "\tRQ Size: " + readyQueue.size() + "\tioQ size: " + ioQueue.size() + "\tfile read: " + file_read_done + "\tpriority: " + element.priority);
				Thread.sleep(element.IOBurst[element.ioIndex]);
				element.ioIndex++;
				
				mutex1.acquire();
				readyQueue.push(element);
				mutex1.release();
				sem1.release();
				System.out.println("IO:\tSem1 released\tsem1 size: " + sem1.availablePermits());
			} //catch (InterruptedException e) {e.printStackTrace();}
			catch (Exception e) {System.out.println("ERROR\npriority: " + element.priority + "index: " + element.ioIndex);}
		}
		
	io_sys_done = 1;
	System.out.println("IO:\tIO DONE");
	}

	public void run() {
		ioSystem();
		
	}

}
