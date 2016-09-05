package assign1;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MemSimServlet
 */
@WebServlet("/MemSimServlet")
public class MemSimInitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemSimInitServlet() {
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
		int pageSize = Integer.parseInt(request.getParameter("pageS"));
		int memSize = Integer.parseInt(request.getParameter("memS"));
		int jobNumber = Integer.parseInt(request.getParameter("jobN"));
		
		//set system's initial value
		request.getSession().setAttribute("pageSize", pageSize);
		request.getSession().setAttribute("memSize", memSize);
		request.getSession().setAttribute("jobNumber", jobNumber);
		
		
		//create the second page for entering the job's size
		request.setAttribute("jn", jobNumber);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/initJob.jsp");
		dispatcher.forward(request, response);
	}

}
