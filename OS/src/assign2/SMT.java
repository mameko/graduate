package assign2;
/**
 * 
 * @author Shuwen Pan
 * 
 * This is the SMT entry class
 *
 */
public class SMT {

	int PMTsize;
	int segNum;
	int[] myPMT;// PMT is implemented as an integer array

	String segName;
	boolean shared;//0 = not shared, 1 = shared.
	boolean exception = true;// false = in memory(one page in is in), true = not in memory(no page in is not in). 
	
	private void initPMT(){
		for (int i = 0; i < myPMT.length; i++) {			
			myPMT[i] = -1;// initial to all -1, because no page is in memory					
		}
	}
	
	public void initSMT(String name, boolean s){
		initPMT();
		segName = name;
		shared = s;
	}
	
	
	public int getSegNum() {
		return segNum;
	}
	public void setSegNum(int segNum) {
		this.segNum = segNum;
	}
	
	public int getPMTsize() {
		return PMTsize;
	}

	public void setPMTsize(int pMTsize) {
		PMTsize = pMTsize;
	}

	public int[] getMyPMT() {
		return myPMT;
	}

	public void setMyPMT(int[] myPMT) {
		this.myPMT = myPMT;
	}

	public boolean isException() {
		return exception;
	}

	public void setException(boolean exception) {
		this.exception = exception;
	}

	public String getSegName() {
		return segName;
	}
	public void setSegName(String segName) {
		this.segName = segName;
	}
	public boolean isShared() {
		return shared;
	}
	public void setShared(boolean shared) {
		this.shared = shared;
	}
	
	
}
