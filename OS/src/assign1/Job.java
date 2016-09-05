package assign1;
/**
 * This is the job class.
 * @author Shuwen Pan
 *
 */
public class Job {
	int size;//number of page
	int duration;//cycle to complete
	String jobName;//the job's name. eg. j1,j2,j3...
	
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	int[] myPMT;	// every job has it's own PMT
	int[] myTrace;  // job generates it's own page trace. (randomly)
					//This will be used in the initial system to build the system's page trace.
	
	public void initPMT(){// initialze PMT. The index of the PMT is the page number, the content of the PMT is the block number.
		// if the page is not in memory, the content of the page will be -1.
		myPMT = new int[size];
		for (int i = 0; i < size; i++) {// initially, no page is in the memory
			myPMT[i]= -1;
		}
	}
	
	public boolean setPMT(int pageNum, int blockNum){//set the content of PMT of this job.
		if (pageNum<size) {
			myPMT[pageNum] = blockNum;
			return true;
		}else {
			return false;
		}		
	}
	
	public int[] getPMT() {
		return this.myPMT;
	}
	
	public void initTrace(){// use random number generator to generate the page trace for this job
		System.out.println("page trace for "+jobName);
			myTrace = new int[duration];
		for (int i = 0; i < duration; i++) {
			myTrace[i] = (int) (Math.random()*size);
			System.out.println(myTrace[i]);
		}	
	}	

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
	
}
