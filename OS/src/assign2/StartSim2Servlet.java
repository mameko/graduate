package assign2;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StartSim2Servlet
 */
@WebServlet("/StartSim2Servlet")
public class StartSim2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StartSim2Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String reset = request.getParameter("reset");//get the reset parameter.
		if (reset!=null) {
			this.finishInit = false;
			this.finish = false;
			response.sendRedirect("/CS6721/segWithPageSetUp.jsp");// and redirect to the simulation set up page.
		}else{
			if (finishInit==false) {
				ms = new MemSysSegPage();
				int pageS = (Integer) request.getSession().getAttribute("pageSize");
				int memS = (Integer) request.getSession().getAttribute("memSize");
				int cycle = (Integer) request.getSession().getAttribute("cyc");
				int jobN = (Integer) request.getSession().getAttribute("jobNumber");
				ms.setMemSize(memS);
				ms.setPageSize(pageS);
				ms.setJobNumber(jobN);
				ms.setCycles(cycle);
				
				ArrayList<Job2> jobL = new ArrayList<Job2>();
				for (int i = 0; i < jobN; i++) {//Initialise jobs in the system. Using the user input data.
					int Ji = Integer.parseInt(request.getParameter("J"+i));
					Job2 j = new Job2();
					j.setSegTableSize(Ji);
					j.setJobName("J"+i);
					j.initJob();
					SMT[] smt= j.getMySmts();
					for (int k = 0; k < Ji; k++) {// segments
						int Pi = Integer.parseInt(request.getParameter("J"+i+"S"+k));
						int[] myPMT = new int[Pi];
						smt[k] = new SMT();
						smt[k].setMyPMT(myPMT);	
						smt[k].setSegNum(k);
						smt[k].setPMTsize(Pi);
						String name = request.getParameter("J"+i+"NS"+k);
						boolean s = false;
						if(request.getParameterValues("J"+i+"CS"+k)!=null){
							s = true;
						}			
						smt[k].initSMT(name, s);					
					}						
					jobL.add(j);				
				}
				
				ms.setJobList(jobL);
				ms.initMBT();
				ms.initSystemTrace();
				
				finishInit = true;
				
				finish = ms.execOneCycle();	
				
				request.setAttribute("mbt", ms.getMyMBT());				
				request.setAttribute("joblist", ms.getJobList());
				request.setAttribute("ast", ms.getMyAST());
			}else {
				if (finish==false) {// if there still are some page trace, get the current tables of the system
					finish = ms.execOneCycle();				
					request.setAttribute("mbt", ms.getMyMBT());				
					request.setAttribute("joblist", ms.getJobList());
					request.setAttribute("ast", ms.getMyAST());
				}
				
				request.setAttribute("pageSize", ms.getPageSize());			
				request.setAttribute("memSize", ms.getMemSize());
				request.setAttribute("swap", ms.swap);				
				request.setAttribute("pageFault", ms.getPagefault());
				
			}
			request.setAttribute("finish", finish);			
			request.setAttribute("curCycle", ms.getCurT());		
			// forward the request to the simulation result page to display the result after one cycle of execution
			RequestDispatcher dispatcher = request.getRequestDispatcher("/simResult2.jsp");
			dispatcher.forward(request, response);
		}
	}
	boolean finishInit = false;// record whether the simulation class is initialise.
	boolean finish = false;// record whether the simulation is finished.
	MemSysSegPage ms;// The reference of the simulation class
}
