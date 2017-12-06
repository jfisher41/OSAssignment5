package os_assignment5;

public class PCB {
	public int numCPUBurst;
	public int numIOBurst;
	public int cpuIndex;
	public int ioIndex;
	public int priority;
	public int CPUBurst[];
	public int IOBurst[];
	public PCB prev;
	public PCB next;
	
	public long rQueueInputTime;
	public long totalWaitingTime = 0;
	
	public long totalUtilization = 0;
	
	
	

}
