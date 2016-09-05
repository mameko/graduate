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
 * Servlet implementation class FetchFloorPlanServlet
 */
@WebServlet("/FetchFloorPlanServlet")
public class FetchFloorPlanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchFloorPlanServlet() {
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
		String un = (String)request.getSession().getAttribute("un");
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();		

		String meg = "";
		String dul = request.getParameter("dul");
		String user = request.getParameter("un");
		if(un.equals("admin")){
			
		File dir = new File(this.getServletContext().getInitParameter("dataLocation")+ user);
		String[] dirs = dir.list();
			
		for(int i = 1;i<dirs.length;i++) {
			
			switch (Integer.parseInt(dul)) {//equal to 1 is duplicate,0 is delete
			case 0:
				meg = meg + "<a href = '#' onclick = delF2('"+dirs[i]+"')>"+dirs[i] + "</a><br/>";
				break;
			case 1:
				meg = meg + "<a href = '#' onclick = dulF2('"+dirs[i]+"')>"+dirs[i] + "</a><br/>";
				break;
			case 2:
				meg = meg + "<a href = '#' onclick = renameF2('"+dirs[i]+"')>"+dirs[i] + "</a><br/>";
				break;
				
			}
			
			
		}
		
			
	}else {
		meg = "error";
	}
	
	out.print(meg);
	out.flush();
	out.close();
	}
	}


