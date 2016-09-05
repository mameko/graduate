package assign1;
/**
 * This class is the MBT item.
 * 
 * @author Administrator
 *
 */
public class MBTitem {
	Job j;
	int pageNum;
	int counter = 0;// work as the reference bit. It will be reset to 0 if being referenced or being brought in the memory
	
	public Job getJ() {
		return j;
	}
	public void setJ(Job j) {
		this.j = j;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getCounter() {
		return counter;
	}
	public void resetCounter() {
		this.counter = 0;
	}
	
	public void increaseC(){
		this.counter++;
	}
	
	
}
