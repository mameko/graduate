package servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DelFloorPlan2Servlet
 */
@WebServlet("/DelFloorPlan2Servlet")
public class DelFloorPlan2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DelFloorPlan2Servlet() {
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
		
		String un = (String) request.getSession().getAttribute("un");
		if(un.equals("admin")){
			un = request.getParameter("cu");
		}
		String filename = request.getParameter("filename");
		String meg = "ab";
		if (un != null) {
			File f = new File(this.getServletContext().getInitParameter("dataLocation")+ un +"\\"+ filename);
			
		
			boolean delsucess = f.delete();
			if (delsucess) {
				meg = "success";
				System.out.println("deleted file: "+this.getServletContext().getInitParameter("dataLocation")+ un +"\\"+ filename);
			}else{
				meg = "fail to delete a the file";
			}
			
		}
		out.print(meg);
		out.flush();
		out.close();
	}

}
