<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>initialise job</title>
</head>
<body>
<h1>Please specify job size and cycles needs to complete the job</h1>
<form action = 'startSim' method = 'post' id = 'jobForm'>
<%
	int jobN = (Integer)request.getAttribute("jn");
	for(int i= 0;i<jobN;i++){
%>

Job <%=i%> : <input type="text" size = '3' id = 'P<%=i%>' name = 'P<%=i%>' value = '5'/> pages
 &nbsp;&nbsp;&nbsp;
			 need<input type="text" size = '3' id = 'C<%=i%>' name = 'C<%=i%>' value = '3'/> cycles<br/>


<%
	}
%>
	<button type="submit">submit</button>
	<button type = "reset">reset</button>
</form>
</body>
</html>