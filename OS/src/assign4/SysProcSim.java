package assign4;

import java.util.LinkedList;
import java.util.Random;
/**
 * 
 * @author Shuwen Pan
 * 
 * this is the simulation class
 *
 */
public class SysProcSim {
	LinkedList<Integer> readyList;
	int nproc;
	int numberproc;
	ProcessTableEntry[] protab;
	int pregs;
	int pnmlen;
	int currpid;
//	int nextProc;
	
	int badCall = 0;
	String currentExec = "";
	
	// only generate 4 kinds of system call.
	// create(),resume(),suspend() and kill().
	// in these calls system call such as ready(),resched() and ctxsw() will be involked
	Random call = new Random();
	
	/**
	 * boot method, initialise the system
	 * 
	 */
	public void boot(){
		readyList = new LinkedList<Integer>();// key = priority, value = pid
		nproc = 10;// number of process this system can hold
		protab = new ProcessTableEntry[nproc];// create the process table
		
		// create the null process	
		ProcessTableEntry nullp = new ProcessTableEntry();
		nullp.setPname("nullProc");
		nullp.setPprio(0);
		nullp.setPstate(3);//in ready state
		
		protab[0] = nullp;// put the null process into the first slot of protab		
		numberproc = 0;// although one process is alive (the null process), the numberproc doesn't count the null proc
		
		// put the null process in the readylist 
		// because it has the lowest priority, so put it in the head
		readyList.add(0);
		pregs = 9;// r0--r5,pc,ps,sp
		
		// initialise other protab slot to Free state.
		for (int i = 1; i < protab.length; i++) {
			ProcessTableEntry emptyP = new ProcessTableEntry();
			emptyP.setPstate(2);// in free state
			protab[i] = emptyP;
		}
		
		currpid = 0;//only null process exist when the system is created	
	}
	
	private int getNextCall(){
		return call.nextInt(5);
	}
	
	/**
	 * executeOneSysCall method, generate the required arguments randomly for the system call
	 * and execute that system call. 
	 */
	public void executeOneSysCall(){
		int pid = (int)(Math.random()*13);
		// get the next call
		if (badCall==-1) {			
			badCall = 0;
		}
		currentExec = "";
		int nc= getNextCall();
		switch (nc) {
		case 0:
			// invoke create()
			// randomly generate the parameters for the create method
			int nargs = (int)(Math.random()*10);
			int procaddr = (int)(Math.random()*10);
			int ssize = (int)(Math.random()*10);
			int priority = (int)(Math.random()*30);
			badCall = create(procaddr, ssize, priority, "proc"+procaddr, nargs, new Object[nargs]);
			currentExec = "create("+procaddr+","+ ssize+","+priority+","+"proc"+procaddr+","+nargs+", arguments)";
			break;
		case 1:
			// invoke resume()			
			badCall = resume(pid);
			currentExec = "resume("+pid+")";
			break;
		case 2:
			// invoke suspend()
			badCall = suspend(pid);
			currentExec ="suspend("+pid+")";
			break;
		case 3:
			// invoke kill()
			badCall = kill(pid);
			currentExec ="kill("+pid+")";
			break;
		case 4:
			// current process resched
			resched();
			badCall = 0;
			currentExec = "resched(" + currpid + ")";
			break;

		default:
			break;
		}
		
		System.out.println(currentExec);
	}
	
	private boolean isBadId(int pid){
		if (pid<=0 || pid>=nproc) {
			return true;
		}
		return false;
	}
	
	/**
	 *  halt the system and clear the variables
	 */
	public void halt(){
		readyList = null;
		nproc = 0;
		protab = null;
		pregs = 0;
		pnmlen = 0;
		currpid = 0;
	}

	//------------------------------getters and setters------------------------
	public int getNproc() {
		return nproc;
	}

	public void setNproc(int nproc) {
		this.nproc = nproc;
	}

	public ProcessTableEntry[] getProtab() {
		return protab;
	}

	public void setProtab(ProcessTableEntry[] protab) {
		this.protab = protab;
	}

	public int getPregs() {
		return pregs;
	}

	public void setPregs(int pregs) {
		this.pregs = pregs;
	}

	public int getPnmlen() {
		return pnmlen;
	}

	public void setPnmlen(int pnmlen) {
		this.pnmlen = pnmlen;
	}

	public int getCurrpid() {
		return currpid;
	}

	public void setCurrpid(int currpid) {
		this.currpid = currpid;
	}
	public LinkedList<Integer> getReadyList() {
		return readyList;
	}

	public void setReadyList(LinkedList<Integer> readyList) {
		this.readyList = readyList;
	}

	public int getBadCall() {
		return badCall;
	}

	public void setBadCall(int badCall) {
		this.badCall = badCall;
	}

	public String getCurrentExec() {
		return currentExec;
	}

	public void setCurrentExec(String currentExec) {
		this.currentExec = currentExec;
	}

	//-------------------------------------system calls-------------------------------------
	
	/**
	 * create method
	 * use for creating process in the system
	 * 
	 * @param procaddr, the address of the code for creating this process
	 * @param ssize, the stack size of the process
	 * @param priotity, the priority of this process
	 * @param nargs, number of arguments needed to create this process
	 * @param args, the values of the arguments
	 * 
	 * @return pid if the process created successfully
	 *         -1 (SYSERR)if things go wrong.
	 */
	
	private int create(int procaddr, int ssize, int priority, String name, int nargs, Object[] args){
		disablePS();
		
		// find the next place for this newly created process in protab
		int pid = findFree();// if no free space pid will = -1
			
		// if no empty space in the proctab||pid is bad || creating null process
		if (pid == 0 || isBadId(pid)||priority<1) {
			restorePS();
			System.out.println("in craate, syserr : bad id");
			return -1;//SYSERR
		}
		
		allocateMem();
		// increase active process number
		numberproc ++;
		
		// create process and set the corresponding attributes in the proctab
		ProcessTableEntry pe = new ProcessTableEntry();
		pe.setPname(name);
		pe.setPprio(priority);
		pe.setPsem(0);
		pe.setPstate(6);
		pe.setPstklen(ssize);
		
		// update protab[pid] field
		protab[pid] = pe;
		
		// push arguments and the INTRET address in stack
		System.out.println("pushing arguments");
		
		restorePS();
		return pid;
	}
	
	/**
	 * find a free space in protab
	 * @return int number specify the free slot place if there is one, 
	 *         -1 if no free space
	 */
	private int findFree(){
		for (int i = 0; i < protab.length; i++) {
			if (protab[i].pstate==2) {
				return i;
			}
		}
		return -1;// no free space in protab
	}
	
	/**
	 * resume method, use for resuming process in the system
	 * 
	 * @param pid
	 * @return priority of the resumed process
	 *         -1 (SYSERR) if things go wrong
	 */
	
	private int resume(int pid){
		int prio = -1;		
		ProcessTableEntry pptr;
		
		if(isBadId(pid)){
			restorePS();
			System.out.println("in resume, syserr : bad id");
			return -1;//SYSERR
		}else {
			pptr = protab[pid];
		
			if (pptr.pstate != 6) {
				restorePS();
				System.out.println("in resume, syserr : resume pid.state !=sus");
				return -1;// SYSERR
			}
		}
		
		prio = pptr.pprio;
		
		int re=ready(pid,true);
		
		if (re == -1) {
			System.out.println("in resume, syserr : ready error");
			return -1;//SYSERR
		}
		
		restorePS();
		return prio;
	}
	
	/**
	 * ready method, make a process "ready"
	 * @param pid
	 * @param reschedule
	 * @return 1 (SYSOK) if executed successfully
	 *         -1 (SYSERR) if things go wrong
	 */
	
	private int ready(int pid, boolean reschedule){
		if(isBadId(pid)){
			System.out.println("in ready, syserr: bad id");
			return -1;//SYSERR
		}
		ProcessTableEntry pptr = protab[pid];
		pptr.pstate = 3;// PRREADY
		
		// insert the process into the ready list
		insert(pid);
		
		if (reschedule) {
			resched();
		}
		
		return 1;//SYSOK
	}
	
	/**
	 * resched method,
	 * use for reschedules the process
	 * 
	 * @return 1 (SYSOK)
	 */
	
	private int resched(){		
		ProcessTableEntry optr = protab[currpid];
		
		disablePS();
		
		// no switch needed if the current process has the highest priority		
		if (readyList.peekLast()!=null) {
			int id = readyList.peekLast();
			if (optr.pstate == 1 && (optr.pprio > protab[id].pprio)) {
				restorePS();
				return 1;// SYSOK
			}
		}else {
			return 1;//SYSOK, no need to reschedule
		}
		
		
		// force context switching, the current process doesn't have the highest priority
		if (optr.pstate==1) {
			optr.pstate = 3;//PRREADY
			insert(currpid);
		}
		
		// set it to the current running process
		// and remove the highest priority process from ready list (the last one)
		currpid = readyList.pollLast();			
		protab[currpid].pstate = 1;
		
		//context switch
		ctxsw();
		
		restorePS();
		return 1;//SYSOK
	}
	
	/**
	 * suspend method, suspend a process
	 * @param pid
	 * @return priority of the suspended process
	 *         -1 (SYSERR) if things go wrong
	 */
	
	private int suspend(int pid){
		int prio = -1;
		ProcessTableEntry pptr;
		disablePS();
		
		if (isBadId(pid)){
			restorePS();
			System.out.println("in suspend, syserr : bad id");
			return -1;//SYSERR			
		}else{
			pptr = protab[pid];
		
			if(pid == 0||(pptr.pstate != 1&&pptr.pstate != 3)) {
				restorePS();
				System.out.println("in suspend, syserr : suspend pstate !=1 or 3");
				return -1;//SYSERR
			}
		}
		
		if (pptr.pstate==3){
			// take out the process from the ready list
			readyList.remove((Integer)pid);
			protab[pid].pstate = 6;// PRSUSP
		}else {// if it is not PRREADY it must be PRCURR
			protab[pid].pstate = 6;// PRSUSP
			resched();
		}
		
		prio = pptr.pprio;
		restorePS();
		return prio;
	}
	
	/**
	 * kill method, use to kill a process in the system
	 * @param pid
	 * @return 1 (SYSOK) if executed successfully
	 *         -1 (SYSERR) if things go wrong
	 */
	
	private int kill(int pid){
		disablePS();
		ProcessTableEntry pptr;
		
		if (isBadId(pid)){	
			restorePS();
			System.out.println("in kill, syserr : bad id");
			return -1;//SYSERR
		}else{
			pptr = protab[pid];
			if(pptr.pstate == 2) {
				restorePS();
				System.out.println("in kill, syserr: kill(pid), pid is already free");
				return -1;//SYSERR
			}
		}
		
		// kill one process, decrease the active process number
		numberproc = numberproc - 1;
		
		if (numberproc == 0) {
			System.out.println("kill all the process, only null proc left");
		}
		
		System.out.println("free the spaces");
		
		switch (pptr.pstate) {
		case 1:// current process suicide
			protab[pid].pstate = 2;// PRFREE
			resched();
			break;
		case 3:// kill process in the ready list 
			readyList.remove((Integer)pid);// dequeue the process from ready list
			protab[pid].pstate = 2;// PRFREE
			break;

		default:
			protab[pid].pstate = 2;// PRFREE
			break;
		}
		
		restorePS();
		return 1;//SYSOK
	}
	
	
	/**
	 * disablePS, mask the interruption handling
	 */
	private void disablePS(){
		System.out.println("disable ps");
	}
	
	/**
	 * restorePS, remove the mask and enable interruption handling
	 */
	private void restorePS(){
		System.out.println("restore ps");
	}
	
	/**
	 * allocateMem, allocating stack space for the process
	 */
	private void allocateMem(){
		System.out.println("allocating memory");
	}
	
	/**
	 * ctxsw, handle context switching.
	 */
	private void ctxsw(){
		System.out.println("context switching");
	}
	
	/**
	 * insert the process in the proper place of the readylist
	 * 
	 * @param pid
	 * @param prio
	 */
	private void insert(int pid){
		int prio = protab[pid].pprio;
		int position = -1;
		for (int i = 0; i < readyList.size(); i++) {
			int id = readyList.get(i);
			int pri = protab[id].pprio;
			if (pri>prio) {
				position = i;
				break;
			}
		}
		
		if (position == -1) {// the priority of all the processes in the list is bigger than this one
			readyList.add(pid);
		} else {
			readyList.add(position, pid);
		}
	}
}
