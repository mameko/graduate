package servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadFileServlet
 */
@WebServlet("/DownloadFileServlet")
public class DownloadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadFileServlet() {
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
		response.setContentType("application/svg;charset=utf-8");
		PrintWriter out = response.getWriter();
		String un = (String) request.getSession().getAttribute("un");
		if(un!=null){
			String user;
			String path = this.getServletContext().getInitParameter("dataLocation");
			String name = request.getParameter("name");
			
			if(un.equals("admin")){
				user = request.getParameter("u");
				path = path + user + "\\" + name;
			}else{
				path = path + un + "\\" + name;
			}
			String lineO = "";
			String line;
			File file = new File(path);
			BufferedReader rd = new BufferedReader( new FileReader( file ));
			while( (line=rd.readLine()) != null ) {				
				lineO = lineO + line + "\n";
	        }
			out.print(lineO);
	        rd.close();
		}

		out.flush();
		out.close();
	}

}
