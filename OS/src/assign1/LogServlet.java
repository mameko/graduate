package assign1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class LogServlet
 */
@WebServlet("/LogServlet")
public class LogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogServlet() {
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
		
		String path= this.getServletContext().getInitParameter("logLocation");
		File f = new File(path);
		if (!f.exists()) {
			f.createNewFile();
			System.out.println("file not exist");
		}
		
		String memSize  = request.getParameter("memSize");
		String pageSize  = request.getParameter("pageSize");
		String pageFault  = request.getParameter("pageFault");
		String swap  = request.getParameter("swap");
		
		FileWriter writer = new FileWriter(f,true);
		String line = memSize + ','+pageSize+ ','+pageFault+ ','+swap;
		
		System.out.println(line);
		
		writer.write(line);
		writer.write("\r\n");		
		
		writer.close();  
		
	}

}
