package servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DulFloorPlan2Servlet
 */
@WebServlet("/DulFloorPlan2Servlet")
public class DulFloorPlan2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DulFloorPlan2Servlet() {
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
		String filename = request.getParameter("filename");
		String meg = "error";
		if (un != null) {
			if(un.equals("admin")){
				un = request.getParameter("cu");
			}
			String path = this.getServletContext().getInitParameter(
					"dataLocation")
					+ un + "\\" + filename;
			File f = new File(path + "_copy");
			if (f.exists()) {
				meg = "file already exist.";
			} else {
				boolean re = f.createNewFile();
				if (re) {
					LinkedList<String> uinfo = new LinkedList<String>();

					BufferedReader rd = new BufferedReader(new FileReader(path));
					String line = null;
					while ((line = rd.readLine()) != null) {
						uinfo.add(line);
					}
					rd.close();

					FileWriter writer = new FileWriter(path + "_copy");
					while (!uinfo.isEmpty()) {
						writer.write(uinfo.get(0));
						uinfo.remove(0);
						writer.write("\r\n");
					}
					writer.close();
					meg = "success";
				}else {
					meg = "fail to copy file";
				}
			}
		}

		out.print(meg);
		out.flush();
		out.close();
		
	}

}
