package assign4;

public class ProcessTableEntry {
	int pstate;
	int pprio;
	int[] preg;
	int psem;
	int pmsg;
	int phasmsg;
	int pbase;
	int pstklen;
	int plimit;
	String pname;
	
	//getter & setter
	public int getPstate() {
		return pstate;
	}
	public void setPstate(int pstate) {
		this.pstate = pstate;
	}
	public int getPprio() {
		return pprio;
	}
	public void setPprio(int pprio) {
		this.pprio = pprio;
	}
	public int[] getPreg() {
		return preg;
	}
	public void setPreg(int[] preg) {
		this.preg = preg;
	}
	public int getPsem() {
		return psem;
	}
	public void setPsem(int psem) {
		this.psem = psem;
	}
	public int getPmsg() {
		return pmsg;
	}
	public void setPmsg(int pmsg) {
		this.pmsg = pmsg;
	}
	public int getPhasmsg() {
		return phasmsg;
	}
	public void setPhasmsg(int phasmsg) {
		this.phasmsg = phasmsg;
	}
	public int getPbase() {
		return pbase;
	}
	public void setPbase(int pbase) {
		this.pbase = pbase;
	}
	public int getPstklen() {
		return pstklen;
	}
	public void setPstklen(int pstklen) {
		this.pstklen = pstklen;
	}
	public int getPlimit() {
		return plimit;
	}
	public void setPlimit(int plimit) {
		this.plimit = plimit;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	
	
}
