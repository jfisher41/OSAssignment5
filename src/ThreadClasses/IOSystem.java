package ThreadClasses;

import os_assignment5.PCB;
import os_assignment5.Prog;

public class IOSystem extends Prog implements Runnable {
	
	private void ioSystem(){
		PCB element = null;
		
		while(true){
			if(cpu_sch_done == 1){
				break;
			}
			try {
				while(!sem2.tryAcquire() && cpu_sch_done != 1);

				element = ioQueue.pop();

				if(element.done != 1){
					if(element.numIOBurst > element.ioIndex){
						Thread.sleep(element.IOBurst[element.ioIndex]);
						element.ioIndex++;
					}
					
					mutex1.acquire();
					element.rQueueInputTime = System.currentTimeMillis();
					readyQueue.push(element);
					sem1.release();
					mutex1.release();
				}
				else{
					sem2.release();
				}

			} catch(Exception e) {}
		}
		io_sys_done = 1;
	}

	public void run() {
		ioSystem();
	}
}
