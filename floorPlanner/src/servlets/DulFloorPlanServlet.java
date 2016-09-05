package servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DulFloorPlanServlet
 */
@WebServlet("/DulFloorPlanServlet")
public class DulFloorPlanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DulFloorPlanServlet() {
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
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String meg = "error";
		
		String un = (String) request.getSession().getAttribute("un");
		
			if (un != null) {

				File dir = new File(this.getServletContext().getInitParameter(
						"dataLocation")
						+ un);
				String[] dirs = dir.list();
				
				request.setAttribute("floorPlanList", dirs);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/dulFloorPlan.jsp");			
				dispatcher.forward(request, response);
			}else{
				response.sendRedirect("/floor_planner/pages/login.html");
			}
		
		out.print(meg);
		out.flush();
		out.close();
	}

}
