package assign2;

import assign1.MBTitem;
/**
 * 
 * @author Shuwen Pan
 * 
 * This is the MBT entry class.
 *
 */
public class MBTitemSegPage extends MBTitem {

	int segNumber;
	Job2 j;	
	String segName;

	public Job2 getJ2() {
		// TODO Auto-generated method stub
		return this.j;
	}

	public void setJ2(Job2 j) {
		// TODO Auto-generated method stub
		this.j=j;
	}

	public String getSegName() {
		return segName;
	}

	public void setSegName(String segName) {
		this.segName = segName;
	}

	public int getSegNumber() {
		return segNumber;
	}

	public void setSegNumber(int segNumber) {
		this.segNumber = segNumber;
	}
}
