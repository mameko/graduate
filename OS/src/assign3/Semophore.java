package assign3;

import java.util.LinkedList;
/**
 * This is the semaphore class, including the methods: signal, wait and screate
 * @author Shuwen Pan
 *
 */
public class Semophore {
	int waitingPNo;
	LinkedList<Integer> waitingProcess = new LinkedList<Integer>();
	
	//-------------------------------------------syn methods-----------------------------------	
	
	public Semophore(int semNumber){// screate();
		waitingPNo = semNumber;
	}
	
	public int signal() {		
		waitingPNo++;
		int pid = 100;
		if (waitingProcess.size()>0) {
			pid = waitingProcess.poll();
		}
		return pid;
	}

	public void wait(int pid) {
		waitingPNo--;
		if (waitingPNo < 0) {
			waitingProcess.add(pid);
		}
	}
	
	public boolean needToWait(int pid) {
		if (waitingProcess.contains(pid)||waitingPNo<0) {
			return true;
		} else {
			return false;
		}
	}
}
