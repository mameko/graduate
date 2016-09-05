package servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.xpath.internal.compiler.OpCodes;

/**
 * Servlet implementation class PrepareAdminServlet
 */
@WebServlet("/PrepareAdminServlet")
public class AdminDelPreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminDelPreServlet() {
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
		String op = request.getParameter("op");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();		

			String meg = "";
			
		if(un.equals("admin")){
			BufferedReader rd = new BufferedReader(new FileReader(this.getServletContext().getInitParameter("userInfo")));
			String line = rd.readLine();		
			while ((line = rd.readLine()) != null) {
				String[] info= line.split(",");
//				System.out.println(line);
				if (op.equals("del")) {
					meg = meg + "<a href = '#' onclick = delU('"+info[0]+"')>"+info[0] + "</a><br/>";
					
				} else {
					meg = meg + "<a href = '#' onclick = enableUpload('"+info[0]+"')>"+info[0] + "</a><br/>";
				}
			}
			
			rd.close();		
		}else {
			meg = "error";
		}
		
		out.print(meg);
		out.flush();
		out.close();
	}

}
