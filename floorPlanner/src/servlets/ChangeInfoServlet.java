package servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ChangInfo
 */
@WebServlet("/ChangInfo")
public class ChangeInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String meg = "";
		String operator = (String)request.getSession().getAttribute("un");
		
		if(operator!=null){	
			
			
			String un  = request.getParameter("username");
			String pw  = request.getParameter("password1");
			String key  = request.getParameter("key");
			if (operator.equals("admin")) {
				operator = request.getParameter("uc");
			}
			if (un.equals("") || pw.equals("")) {
				meg = "User name or password can not be empty.Please enter again.";
			} else {
				if (!isDigit(pw)) {
					meg = "Password contains less than 6 digits. Please try again.";
				} else {
					String path= this.getServletContext().getInitParameter("dataLocation");
					File dir = new File(path+ un);
					String[] dirs = dir.list();
					if (dirs == null) {
					
						//change the name of user directory
						File f=new File(path + operator);
						f.renameTo(new File(path +un));
						//record user information in userinfo.txt
				        LinkedList<String> uinfo = new LinkedList<String>(); 
						
						BufferedReader rd = new BufferedReader(new FileReader(this.getServletContext().getInitParameter("userInfo")));
						String line = null;
						while ((line = rd.readLine()) != null) {
							String[] info= line.split(",");
							if(!info[0].equals(operator)){
								uinfo.add(line);			
							}else {
								uinfo.add(un+","+pw+"," + key);
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
						meg = "success";
						
					} else {
						meg = "user with this name is already existed, Please choose another user name";
					}
				}
			}
		}else{
			meg = "you are not allow to do this.";
		}
		out.print(meg);
		out.flush();
		out.close();
	}
	
	boolean isDigit(String s){
		boolean result = Pattern.matches("^[0-9]{6,}",s);
		return result; 
	}
}
