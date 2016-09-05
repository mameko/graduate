package assign2;

import java.util.LinkedList;
/**
 * 
 * @author Shuwen Pan
 * This is the AST entry class
 * 
 */
public class AST {

	String segName;
	LinkedList<Job2> Joblist;
	int pageNum;
	int location;// mbt slot.
	
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public String getSegName() {
		return segName;
	}
	public void setSegName(String segName) {
		this.segName = segName;
	}
	public LinkedList<Job2> getJoblist() {
		return Joblist;
	}
	public void setJoblist(LinkedList<Job2> joblist) {
		Joblist = joblist;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	
}
