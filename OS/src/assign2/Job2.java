package assign2;
/**
 * 
 * @author Shuwen Pan
 * 
 * This class is the job class of the system.
 * 
 */
public class Job2 {

	int segTableSize;
	SMT[] mySmts;
	String jobName;
	
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public void initJob(){
		mySmts = new SMT[segTableSize];
	}
	
	public int getSegTableSize() {
		return segTableSize;
	}
	public void setSegTableSize(int segTableSize) {
		this.segTableSize = segTableSize;
	}
	public SMT[] getMySmts() {
		return mySmts;
	}
	public void setMySmts(SMT[] mySmts) {
		this.mySmts = mySmts;
	}
	
	
}
