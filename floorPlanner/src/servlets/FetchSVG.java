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
 * Servlet implementation class FetchSVG
 */
@WebServlet("/FetchSVG")
public class FetchSVG extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchSVG() {
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
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out = response.getWriter();
	
		String path  = request.getParameter("path");
		
		File svg = new File(this.getServletContext().getInitParameter("dataLocation")+path);	
		BufferedReader br = new BufferedReader(new FileReader(svg));
		String line1 = "";
		String line2 = "";
		String line3 = "";
		
		line1=br.readLine();
		line2=br.readLine();
		line3=br.readLine();
	   
	    br.close();
	    
	    String r = "<mydata>"+"<svg>"+line1+"</svg>"+"<hw>"+line2+"</hw>"+"<vw>"+line3+"</vw>"+"</mydata>";  
	   
	    out.print(r);
		out.flush();
		out.close();
		
	}

}
