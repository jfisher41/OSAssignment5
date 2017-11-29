package os_assignment5;

public class PCB {
	int numCPUBurst;
	int numIOBurst;
	int cpuIndex;
	int ioIndex;
	PCB prev;
	PCB next;
	
	public int getNumCPUBurst() {
		return numCPUBurst;
	}
	public void setNumCPUBurst(int numCPUBurst) {
		this.numCPUBurst = numCPUBurst;
	}
	public int getNumIOBurst() {
		return numIOBurst;
	}
	public void setNumIOBurst(int numIOBurst) {
		this.numIOBurst = numIOBurst;
	}
	public int getCpuIndex() {
		return cpuIndex;
	}
	public void setCpuIndex(int cpuIndex) {
		this.cpuIndex = cpuIndex;
	}
	public int getIoIndex() {
		return ioIndex;
	}
	public void setIoIndex(int ioIndex) {
		this.ioIndex = ioIndex;
	}
	public PCB getPrev() {
		return prev;
	}
	public void setPrev(PCB prev) {
		this.prev = prev;
	}
	public PCB getNext() {
		return next;
	}
	public void setNext(PCB next) {
		this.next = next;
	}
	
	

}
