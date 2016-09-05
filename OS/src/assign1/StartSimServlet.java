package assign1;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StarSimServlet
 */
@WebServlet("/StarSimServlet")
public class StartSimServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StartSimServlet() {
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
		//If the simulation is over and the user choose to do another simulation, a 'reset' parameter will be sent with the request
		
		if (reset!=null) {// if 'reset' is set, reset the system variables.
			this.finishInit = false;
			this.finish = false;
			response.sendRedirect("/CS6721/memSimInit.jsp");// and redirect to the simulation set up page.
			
		}else {// if not a reset request.
			
		
		
		if(finishInit==false){// check whether the system is initialised. if not, initialise the simulation class.	
			//init ms
			ms = new MemSystem();
			pageS = (Integer) request.getSession().getAttribute("pageSize");
			memS = (Integer) request.getSession().getAttribute("memSize");
			int jobN = (Integer) request.getSession().getAttribute("jobNumber");
			ms.setMemSize(memS);
			ms.setPageSize(pageS);
			ms.setJobNumber(jobN);
			
			ArrayList<Job> jobL = new ArrayList<Job>();
			
			for (int i = 0; i < jobN; i++) {//Initialise jobs in the system. Using the user input data.
				int Ci = Integer.parseInt(request.getParameter("C"+i));
				int Pi = Integer.parseInt(request.getParameter("P"+i));
				
				Job j = new Job();
				j.setDuration(Ci);
				j.setSize(Pi);
				j.initPMT();
				j.initTrace();
				j.setJobName("J"+i);
				
				jobL.add(j);				
			}
			
			ms.setJoblist(jobL);
			ms.initMBT();
			ms.initSystemTrace();
			
			finishInit = true;
			
			finish = ms.execOneCycle();	
//			System.out.println("finish :"+finish);
			request.setAttribute("mbt", ms.myMBT);				
			request.setAttribute("joblist", ms.joblist);
			
		}else {	// if it is initialised before, continue execute the page trace.	
			if (finish==false) {// if there still are some page trace, get the current tables of the system
				finish = ms.execOneCycle();				
				request.setAttribute("mbt", ms.myMBT);				
				request.setAttribute("joblist", ms.joblist);
			}
				// set other attributes which will be display on the web.
				request.setAttribute("pageSize", pageS);			
				request.setAttribute("memSize", memS);
				request.setAttribute("swap", ms.swap);				
				request.setAttribute("pageFault", ms.pageFault);
				
						
		}
		request.setAttribute("finish", finish);			
		request.setAttribute("curCycle", ms.curT);		
		// forward the request to the simulation result page to display the result after one cycle of execution
		RequestDispatcher dispatcher = request.getRequestDispatcher("/simResult.jsp");
		dispatcher.forward(request, response);
		}
	}

	// since all the request during the simulation process is handled here, all these variables are global
	boolean finishInit = false;// record whether the simulation class is initialise.
	boolean finish = false;// record whether the simulation is finished.
	MemSystem ms;// The reference of the simulation class
	int pageS;//page size of the system
	int memS;// memory size of the system
}
