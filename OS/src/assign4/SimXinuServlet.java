package assign4;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.*;
/**
 * Servlet implementation class SimXinuServlet
 */
@WebServlet("/SimXinuServlet")
public class SimXinuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SimXinuServlet() {
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
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		JSONObject meg = null;

		String op = request.getParameter("op");
		SysProcSim sys = (SysProcSim) request.getSession().getAttribute("sys");
		if (op.equals("next")) {
			// start or next			
			// if not been created, create the simulation system and initialise it
			if (sys == null) {
				sys = new SysProcSim();
				sys.boot();
			}
			
			// execute one call
			sys.executeOneSysCall();
			
			// get process table, ready list, current system call and whether it is a bad call
			int badcall = sys.getBadCall();
			String curExec = sys.getCurrentExec();
			ProcessTableEntry[] processTable = sys.getProtab();
			LinkedList<Integer> readylist = sys.getReadyList();
			
			// assemble the result after this execution to json format
			JSONArray jsprotab = new JSONArray();
			JSONArray jsreadylist = new JSONArray();
			JSONObject json = new JSONObject();

//			meg = "{"+"badcall"+""+"}";
			
			if (badcall == -1) {
				json.put("badcall", true);
			}else{
				json.put("badcall", false);
			}
			
			json.put("curExec", curExec);
			
			for (int i = 0; i < readylist.size(); i++) {
				jsreadylist.put(readylist.get(i));
			}
			
			json.put("readylist", jsreadylist);
			
			for (int i = 0; i <  processTable.length; i++) {
				
				JSONObject process = new JSONObject();
				process.put("state", processTable[i].pstate);
				process.put("name", processTable[i].pname);
				process.put("priority", processTable[i].pprio);				
				
				jsprotab.put(process);
			}
			
			json.put("protab", jsprotab);
			
			meg = json;
			
			// after every operation set the sys object to the session.
			request.getSession().setAttribute("sys", sys);
		}else{
			//stop
			sys.halt();
			request.getSession().removeAttribute("sys");
		}
		
		out.print(meg);
		out.flush();
		out.close();
	}

}
