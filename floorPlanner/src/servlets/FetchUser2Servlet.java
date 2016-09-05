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

/**
 * Servlet implementation class FetchUser2Servlet
 */
@WebServlet("/FetchUser2Servlet")
public class FetchUser2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FetchUser2Servlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String un = (String) request.getSession().getAttribute("un");

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		String meg = "";
		String dul = request.getParameter("dul");
		if (un.equals("admin")) {
			BufferedReader rd = new BufferedReader(new FileReader(this
					.getServletContext().getInitParameter("userInfo")));
			String line = rd.readLine();
			while ((line = rd.readLine()) != null) {
				String[] info = line.split(",");
				switch (Integer.parseInt(dul)) {// equal to 1 is duplicate,0 is
												// delete,2 is rename
				case 0:
					meg = meg + "<a href = '#' onclick = delF('" + info[0]
							+ "')>" + info[0] + "</a><br/>";
					break;
				case 1:
					meg = meg + "<a href = '#' onclick = dulF('" + info[0]
							+ "')>" + info[0] + "</a><br/>";
					break;
				case 2:
					meg = meg + "<a href = '#' onclick = renameF('" + info[0]
							+ "')>" + info[0] + "</a><br/>";
					break;
				}

			}
			rd.close();
		} else {
			meg = "error";
		}

		out.print(meg);
		out.flush();
		out.close();
	}
}
