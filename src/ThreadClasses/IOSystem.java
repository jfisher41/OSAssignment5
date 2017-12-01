package ThreadClasses;

import os_assignment5.PCB;
import os_assignment5.Prog;

public class IOSystem extends Prog implements Runnable {
	
	private void ioSystem(){
		PCB element;
		
		while(true){
			
			if(readyQueue.isEmpty() && ioQueue.isEmpty() && file_read_done == 1)
				break;
			
			try {
				sem2.acquire();
				element = ioQueue.pop();
				System.out.println(element.CPUBurst[1] + "Size: " + readyQueue.size() + "io size: " + ioQueue.size() + "file read: " + file_read_done);
				Thread.sleep(element.IOBurst[element.ioIndex]);
				element.ioIndex++;
				
				mutex1.acquire();
				readyQueue.push(element);
				mutex1.release();
				sem1.release();
			} catch (InterruptedException e) {e.printStackTrace();}
		}
		
	io_sys_done = 1;
	}

	@Override
	public void run() {
		ioSystem();
		
	}

}
