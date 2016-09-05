package servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminDelServlet
 */
@WebServlet("/AdminDelServlet")
public class AdminDelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminDelServlet() {
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
		String un = request.getParameter("un");

		String meg = "success";
		
		File f=new File(this.getServletContext().getInitParameter("dataLocation") + un);
		f.delete();
		
		LinkedList<String> uinfo = new LinkedList<String>(); 
		
		BufferedReader rd = new BufferedReader(new FileReader(this.getServletContext().getInitParameter("userInfo")));
		String line = null;
		while ((line = rd.readLine()) != null) {
			String[] info= line.split(",");
			if(!info[0].equals(un)){
				uinfo.add(line);			
			}
			
		}
		rd.close();		
		
		FileWriter writer = new FileWriter(this.getServletContext().getInitParameter("userInfo"));
		while(!uinfo.isEmpty()) {
			writer.write(uinfo.get(0));	
			uinfo.remove(0);		
			writer.write("\r\n");			
		}
		writer.close();  
		
		out.print(meg);
		out.flush();
		out.close();
	}

}
