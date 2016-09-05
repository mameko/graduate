package assign2;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class segPageInitServlet
 */
@WebServlet("/segPageInitServlet")
public class SegPageInitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SegPageInitServlet() {
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
		int cycles = Integer.parseInt(request.getParameter("cyc"));
		
		//set system's initial value
		request.getSession().setAttribute("pageSize", pageSize);
		request.getSession().setAttribute("memSize", memSize);
		request.getSession().setAttribute("jobNumber", jobNumber);
		request.getSession().setAttribute("cyc", cycles);
		
//		System.out.println(pageSize +"   " +memSize +"   " +jobNumber +"   " +cycles);
		
		//create the second page for entering the job's size
		request.setAttribute("jn", jobNumber);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/initJob2.jsp");
		dispatcher.forward(request, response);
		
	}

}
