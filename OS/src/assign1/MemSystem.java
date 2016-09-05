package assign1;
/*
 * This class is the main class of the simulation. 
 */
import java.util.ArrayList;

public class MemSystem {
	MBTitem[] myMBT;// MBT
	ArrayList<Job> joblist = new ArrayList<Job>();// jobs in the system
	ArrayList<SystemTraceItem> systemTrace = new ArrayList<SystemTraceItem>();//the job trace of this system

	int pageSize;// page size of the system
	int memSize;// memory size of the system
	int pageFault = 0;//counter for page fault
	int swap = 0;// counter for page being swap out 
	int curT = 0;// the current trace
	int jobNumber;//the job's number, eg. 0,1,2,3...

	public int getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(int jobNumber) {
		this.jobNumber = jobNumber;
	}

	public ArrayList<Job> getJoblist() {
		return joblist;
	}

	public void setJoblist(ArrayList<Job> joblist) {
		this.joblist = joblist;
	}

	int maxTraceSize() {// finding the max page trace number in the system
		int mt = 0;
		for (int i = 0; i < joblist.size(); i++) {
			if (mt <= joblist.get(i).myTrace.length) {
				mt = joblist.get(i).myTrace.length;
			}
		}
		return mt;
	}

	public void initSystemTrace() {// assemble the job's traces in side the system  
		
		int mtr = maxTraceSize();
		System.out.println("----------");
		for (int k = 0; k < mtr; k++) {
			for (int i = 0; i < joblist.size(); i++) {
				SystemTraceItem item = new SystemTraceItem();
				Job job = joblist.get(i);
				item.j = job;
				if (k < job.myTrace.length) {
					item.page = job.myTrace[k];
					System.out.println(job.getJobName());
					System.out.println(item.page);
					systemTrace.add(item);
				}
			}
		}
	}

	public boolean initMBT() {// initialise the memory block table
		if (pageSize != 0) {
			myMBT = new MBTitem[(int) Math.ceil(memSize/pageSize)];// The size of the MBT is the same as the number of page in the system.
			for (int i = 0; i < myMBT.length; i++) {
				myMBT[i] = new MBTitem();
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean execOneCycle() {// execute one cycle, consume one page trace.
		if (curT<systemTrace.size()) {// if current page trace is less than the total page trace size (i.e not finished),continue the demand paging process
			Job job = systemTrace.get(curT).j;
			int localT = systemTrace.get(curT).getPage();
			int in = inMBT(job, localT);// check if the page is resided in the memory.
			if (in != -1) {// in MBT, in memory
				myMBT[in].counter = 0;// reset counter for LRU.
				job.setPMT(localT,in);// renew the job's PMT
				increaseOthers(in);// increase other page's counter for LRU
			} else {// not in memory
				pageFault++;// generate a page fault

				int avail = findMBT();// now a new page will be brought in, so check the MBT see if the memory 
				                      // get place for this page.

				if (avail != -1) {// MBT have place, no need to swap
					System.out.println("avail  " + avail);
					myMBT[avail].resetCounter();// reset the counter for LRU
					myMBT[avail].setJ(job);//set the job of the trace to this slot
					myMBT[avail].setPageNum(localT);// set the job's page of the trace in this slot.
					job.setPMT(localT,avail);//renew the job's PMT. 
					increaseOthers(avail);//increase other job's counter for LRU
				} else { // not available block in the memory, use LRU to replace pages.
					int max = 0;
					int maxPos = 0;//the page of the job that will be swap out 
					for (int i = 0; i < myMBT.length; i++) {// find the page with the max count (the page will be replaced)
						if (myMBT[i].j != null) {			
							if (myMBT[i].counter >= max) {
								maxPos = i;
								max = myMBT[i].counter;
							}
						}
					}
					//set job's pmt which being swapped out
					Job jo= myMBT[maxPos].j;
					int p= myMBT[maxPos].pageNum;
					jo.setPMT(p, -1 );
					
					// set the current page trace record to the MBT.
					myMBT[maxPos].resetCounter();
					myMBT[maxPos].setJ(job);
					myMBT[maxPos].setPageNum(localT);
					job.setPMT(localT,maxPos);// set PMT of the current job (page trace's job)
					increaseOthers(maxPos);// increase other pages counter.
					swap++;// increase swap
				}
			}

			curT++;// increase the current trace number. Move to next page trace. 
			return false;//not finished
		}else {// all the page trace in the system is consumed. finish the simulation.
			return true;//finished
		}
			
		
	}

	private void increaseOthers(int excep) {// increase other page's counter except for the parameter page
		// TODO Auto-generated method stub
		for (int i = 0; i < myMBT.length; i++) {
			if (i != excep) {
				if (myMBT[i].j!= null) {
					myMBT[i].increaseC();
				}
			}
		}
	}

	private int findMBT() {
		//search the MBT for empty place
		//if there is an empty slot, return the place (i.e. the index)
		//if not, return -1
		// TODO Auto-generated method stub
		for (int i = 0; i < myMBT.length; i++) {
			if (myMBT[i].j== null) {
				return i;
			}
		}

		return -1;
	}

	private int inMBT(Job curJ, int LT) {
		// search the MBT for the job, if the job is in the mem, return the
		// block number(i.e. the index of the array)
		// if the job is not in mem, return -1
		// TODO Auto-generated method stub

		for (int i = 0; i < myMBT.length; i++) {
			if (myMBT[i].j!=null){
				if (myMBT[i].j.equals(curJ)) {
					if (myMBT[i].pageNum == LT) {
						return i;
					}
				}
			}
			
		}

		return -1;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getMemSize() {
		return memSize;
	}

	public void setMemSize(int memSize) {
		this.memSize = memSize;
	}

}
