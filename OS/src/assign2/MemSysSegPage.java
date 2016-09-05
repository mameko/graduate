package assign2;

import java.util.ArrayList;
import java.util.LinkedList;
/**
 * 
 * @author Shuwen Pan
 * 
 * This is the simulation class.
 *
 */
public class MemSysSegPage {

	MBTitemSegPage[] myMBT;
	ArrayList<Job2> jobList=new ArrayList<Job2>();
	ArrayList<TraceItemSegPage> systemTrace = new ArrayList<TraceItemSegPage>();//the job trace of this system
	ArrayList<AST> myAST = new ArrayList<AST>();
	
	int pageSize;
	int memSize;
	int pagefault = 0;
	int swap = 0;
	int curT = 0;
	int jobNumber;// total jobs in the system
	int cycles;//total cycles for execution
	int shareSegNum;// number of shared segments in the system
	
	public boolean initMBT() {// initialise the memory block table
		if (pageSize != 0) {
			myMBT = new MBTitemSegPage[(int) Math.ceil(memSize/pageSize)];// The size of the MBT is the same as the number of page in the system.
			for (int i = 0; i < myMBT.length; i++) {
				myMBT[i] = new MBTitemSegPage();
			}
			return true;
		} else {
			return false;
		}
	}
	
	public void initSystemTrace(){// genereate system seg_page trace randomly
		for (int g = 0; g < cycles; g=g+jobList.size()) {			
			for (int i = 0; i < jobList.size(); i++) {//because round robin scheduling, every job one cycle. (begin from job 0)
				Job2 j = jobList.get(i);
				SMT[] jsmt = j.getMySmts();
				
				int segN = (int) (Math.random() * jsmt.length);//random segment in the job
				int pmtsize = jsmt[segN].getPMTsize();
				int pageN = (int) (Math.random() * pmtsize);// random page in that segment
				
				TraceItemSegPage t = new TraceItemSegPage();
				t.setJ(j);
				t.setPageNum(pageN);
				t.setSegNum(segN);			
				
				systemTrace.add(t);
			}
		}
	}
	
	public boolean execOneCycle(){
		if(curT<cycles){// not finish all the cycles
			TraceItemSegPage curTrace = systemTrace.get(curT);
			Job2 currentJ = curTrace.getJ();
			int segNrequest = curTrace.getSegNum();
			int pageNrequest = curTrace.getPageNum();
			
			SMT currentSMTEntry = currentJ.getMySmts()[segNrequest];
			if (!currentSMTEntry.isException()) {//segment in memory
				int memLocation = currentSMTEntry.getMyPMT()[pageNrequest];
				if (memLocation!= -1) {//page in memory, reset counter
					myMBT[memLocation].resetCounter();
					increaseOthers(memLocation);
				}else {// page not in memory, see if it is a share segment.
					if (currentSMTEntry.shared) {
					// if this segment is shared, check AST see if others loaded this segment in memory
						String segNameR = currentJ.getMySmts()[segNrequest].getSegName();
						int inAst = inAST(segNameR, pageNrequest);
						if (inAst != -1) {
							// in AST, update SMT, PMT, add this job to AST joblist
							myAST.get(inAst).getJoblist().add(currentJ);
							currentJ.getMySmts()[segNrequest].setException(false);
							memLocation = myAST.get(inAst).getLocation();							
							currentJ.getMySmts()[segNrequest].getMyPMT()[pageNrequest] = memLocation;
							
							myMBT[memLocation].setJ2(currentJ);
							myMBT[memLocation].setPageNum(pageNrequest);
							myMBT[memLocation].setSegName(segNameR);
							myMBT[memLocation].setSegNumber(segNrequest);
							myMBT[memLocation].resetCounter();
							increaseOthers(memLocation);
						}else {
							pageExceptionHandle(curTrace);
						}
					} else {
						pageExceptionHandle(curTrace);
					}
				}
			}else {// requested segment not in the memory or requested page not in memory
				if (currentSMTEntry.shared) {
					// if this segment is shared, check AST see if others loaded this segment in memory
						String segNameR = currentJ.getMySmts()[segNrequest].getSegName();
						int inAst = inAST(segNameR, pageNrequest);
						if (inAst != -1) {
							// in AST, update SMT, PMT, add this job to AST joblist
							myAST.get(inAst).getJoblist().add(currentJ);
							int ind= jobList.indexOf(currentJ);
							jobList.get(ind).getMySmts()[segNrequest].setException(false);
							int memLocation = myAST.get(inAst).getLocation();
							jobList.get(ind).getMySmts()[segNrequest].getMyPMT()[pageNrequest] = memLocation;

							myMBT[memLocation].setJ2(currentJ);
							myMBT[memLocation].setPageNum(pageNrequest);
							myMBT[memLocation].setSegName(segNameR);
							myMBT[memLocation].setSegNumber(segNrequest);							
							myMBT[memLocation].resetCounter();
							increaseOthers(memLocation);
						}else {// not in AST
							pageExceptionHandle(curTrace);
						}
					} else {// not a shared segment
						pageExceptionHandle(curTrace);
					}
			}	
			curT++;
			return false;
		}else{
			// finish execution
			return true;
		}
	}
	
	public MBTitemSegPage[] getMyMBT() {
		return myMBT;
	}

	public void setMyMBT(MBTitemSegPage[] myMBT) {
		this.myMBT = myMBT;
	}

	public ArrayList<Job2> getJobList() {
		return jobList;
	}

	public void setJobList(ArrayList<Job2> jobList) {
		this.jobList = jobList;
	}

	public ArrayList<TraceItemSegPage> getSystemTrace() {
		return systemTrace;
	}

	public void setSystemTrace(ArrayList<TraceItemSegPage> systemTrace) {
		this.systemTrace = systemTrace;
	}

	public ArrayList<AST> getMyAST() {
		return myAST;
	}

	public void setMyAST(ArrayList<AST> myAST) {
		this.myAST = myAST;
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

	public int getPagefault() {
		return pagefault;
	}

	public void setPagefault(int pagefault) {
		this.pagefault = pagefault;
	}

	public int getSwap() {
		return swap;
	}

	public void setSwap(int swap) {
		this.swap = swap;
	}

	public int getCurT() {
		return curT;
	}

	public void setCurT(int curT) {
		this.curT = curT;
	}

	public int getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(int jobNumber) {
		this.jobNumber = jobNumber;
	}

	public int getCycles() {
		return cycles;
	}

	public void setCycles(int cycles) {
		this.cycles = cycles;
	}

	public int getShareSegNum() {
		return shareSegNum;
	}

	public void setShareSegNum(int shareSegNum) {
		this.shareSegNum = shareSegNum;
	}

	private void pageExceptionHandle(TraceItemSegPage curTrace){
		// page fault
		pagefault++;
		Job2 currentJ = curTrace.getJ();
		int segNrequest = curTrace.getSegNum();
		int pageNrequest = curTrace.getPageNum();
		SMT currentSMTEntry = currentJ.getMySmts()[segNrequest];
		
		int placeAvail = findEmptyInMBT();
		if (placeAvail != -1) {
			// have empty place in the memory, reset counter for load in page increase other's counter.
			// load page in, set SMT, PMT, if shared set AST too
			myMBT[placeAvail].setJ2(currentJ);
			myMBT[placeAvail].setPageNum(pageNrequest);
			myMBT[placeAvail].setSegName(currentSMTEntry.getSegName());
			myMBT[placeAvail].setSegNumber(segNrequest);
			myMBT[placeAvail].resetCounter();
			increaseOthers(placeAvail);

			int ind = jobList.indexOf(currentJ);
			jobList.get(ind).getMySmts()[segNrequest].setException(false);
			jobList.get(ind).getMySmts()[segNrequest].getMyPMT()[pageNrequest] = placeAvail;
			
			if (currentSMTEntry.shared) {// if it is a share segment, add this job to AST.
				AST ast = new AST();
				ast.segName = currentSMTEntry.getSegName();	
				LinkedList<Job2> jList= new LinkedList<Job2>();
				jList.add(currentJ);
				ast.setJoblist(jList);
				ast.setLocation(placeAvail);
				ast.setPageNum(pageNrequest);
	
				myAST.add(ast);
			}		
			
		} else {// no place in memory, need swap, LRU
			swap++;
			
			int replacedPositon = LRU();
			
			//update old page's smt, pmt, if shared, ast also need to update
			MBTitemSegPage itemOld = myMBT[replacedPositon];
			Job2 oldJob2 = itemOld.getJ2();
			int ind = jobList.indexOf(oldJob2);
			jobList.get(ind).getMySmts()[itemOld.getSegNumber()].getMyPMT()[itemOld.getPageNum()] = -1;
			SMT oldSmt = oldJob2.getMySmts()[itemOld.getSegNumber()];
			int[] pmt = oldSmt.getMyPMT();
			pmt[itemOld.getPageNum()] = -1;
			
			// see if this is the last page in that segment in the memory
			boolean segIn = true;
			for (int i = 0; i < pmt.length; i++) {
				if (pmt[i]!= -1) {
					segIn = false;
				}
			}
			jobList.get(ind).getMySmts()[itemOld.getSegNumber()].setException(segIn);
			
			if (oldSmt.shared) {
				// if shared, remove this page of this segment in AST
				// update all the jobs which shared this page of this segment
				String segName = oldSmt.getSegName();
				
				int indexOfSeg = inAST(segName,itemOld.getPageNum());
				LinkedList<Job2> jobl= myAST.get(indexOfSeg).getJoblist();
				
				for (int i = 0; i < jobl.size(); i++) {
					Job2 otherJob=jobl.get(i);
					int indo = jobList.indexOf(otherJob);					
					SMT[] mySmts = otherJob.getMySmts();
					for (int j = 0; j < mySmts.length; j++) {
						SMT mySmt = mySmts[j];
						int[] pmtO = mySmt.getMyPMT();
						if (mySmt.getSegName().equals(segName)) {
							pmtO[itemOld.getPageNum()] = -1;
							jobList.get(indo).getMySmts()[mySmt.getSegNum()].getMyPMT()[itemOld.getPageNum()] = -1;
						}
						
						boolean segOIn = true;
						for (int k = 0; k < pmt.length; k++) {
							if (pmtO[i]!= -1) {
								segOIn = false;
							}
						}
						jobList.get(indo).getMySmts()[mySmt.getSegNum()].setException(segOIn);
					}				
				}
				
				myAST.remove(indexOfSeg);
				
			}
			
			//update new page's smt, pmt, if shared, ast also need to update
			int idx = jobList.indexOf(currentJ);
			jobList.get(idx).getMySmts()[segNrequest].getMyPMT()[pageNrequest] = replacedPositon;
			jobList.get(idx).getMySmts()[segNrequest].setException(false);
			
			if (currentSMTEntry.shared) {// if it is a share segment, add this job to AST.
				AST ast = new AST();
				ast.segName = currentSMTEntry.getSegName();	
				LinkedList<Job2> jList= new LinkedList<Job2>();
				jList.add(currentJ);
				ast.setJoblist(jList);
				ast.setLocation(replacedPositon);
				ast.setPageNum(pageNrequest);
	
				myAST.add(ast);
				
			}
			myMBT[replacedPositon].setJ2(currentJ);
			myMBT[replacedPositon].setPageNum(pageNrequest);
			myMBT[replacedPositon].setSegName(currentSMTEntry.getSegName());
			myMBT[replacedPositon].setSegNumber(segNrequest);
			myMBT[replacedPositon].resetCounter();
			increaseOthers(replacedPositon);
		}
	}
	
	private int LRU(){
		int replacedPositon = -1;
		int max = 0;
		
		for (int i = 0; i < myMBT.length; i++) {
			int count = myMBT[i].getCounter();
			if (count>=max) {
				max = count;
				replacedPositon = i;
			}
		}
		return replacedPositon;
	}
	
	private int inAST(String segNameR, int pageN){
		for (int i = 0; i < myAST.size(); i++) {
			String segName = myAST.get(i).getSegName();
			if (segName!="") {
				if (segNameR.equals(segName)) {
					if (pageN == myAST.get(i).getPageNum()) {
						//in AST
						return i;
					}
				}
			}
		}
		
		return -1;
	}
	
	private int findEmptyInMBT(){
		for (int i = 0; i < myMBT.length; i++) {
			if (myMBT[i].j == null) {//have empty slot
				return i;
			}
		}
		return -1;
	}
	
	private void increaseOthers(int inMbt){
		// increase others's counter, except for the one in parameter
		for (int i = 0; i < myMBT.length; i++) {
			if (i!=inMbt) {
				if (myMBT[i].getJ2()!=null) {// do not need to increase empty slot's counter
					myMBT[i].increaseC();
				}
				
			}
		}
		
	}
	
}
