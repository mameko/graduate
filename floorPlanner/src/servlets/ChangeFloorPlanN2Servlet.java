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
 * Servlet implementation class ChangeFloorPlanN2Servlet
 */
@WebServlet("/ChangeFloorPlanN2Servlet")
public class ChangeFloorPlanN2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeFloorPlanN2Servlet() {
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
		String newName = request.getParameter("newName");
		String meg = "ab";
		if (un != null) {
			String path = this.getServletContext().getInitParameter("dataLocation")+ un +"\\";
			File f = new File(path+ filename);
			
			if(new File(path+ newName).exists()){
				meg = "a file with this name is already exist, please choose another name";
			} else {
				boolean renamesucess = f.renameTo(new File(path + newName));
				if (renamesucess) {
					meg = "success";
				} else {
					meg = "fail to rename the file";
//					System.out.println(path+newName);
				}

			}
			
		}else{
			meg = "error";			
		}
	
		out.print(meg);
		out.flush();
		out.close();
		
	}

}
