<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Initial job assign2</title>
<script type="text/javascript" src="jquery.js"></script>
<script type="text/javascript">
function createSeg(ji){
	var segNum = document.getElementById(ji).value;
 	for(var i = 0; i<parseInt(segNum);i++){
 		var input = document.createElement('input');
 		input.setAttribute("name", ji+"S"+i);
 		input.setAttribute("id", ji+"S"+i);
 		input.setAttribute("size", 3);
 		input.setAttribute("type", "text");	
 		input.setAttribute("value", 2);
 		var text = document.createTextNode("pages");
 		var br = document.createElement('br');
 		var whithSpace = document.createTextNode(" ");
 		
 		var input2 = document.createElement('input');
 		input2.setAttribute("name", ji+"NS"+i);
 		input2.setAttribute("id", ji+"NS"+i);
 		input2.setAttribute("size", 5);
 		input2.setAttribute("type", "text");	
 		input2.setAttribute("value", ji+"NS"+i);
 		var text2 = document.createTextNode("   name");
 		
 		var checkbox = document.createElement('input');
 		checkbox.setAttribute("type", "checkbox");
 		checkbox.setAttribute("id", ji+"CS"+i);
 		checkbox.setAttribute("name",ji+"CS"+i);
 		var text3 = document.createTextNode("shared");
 		
 		var div = document.getElementById('Job'+ji[1]);
 		div.appendChild(input);
 		div.appendChild(text);
 		div.appendChild(whithSpace);
 		div.appendChild(text2);
 		div.appendChild(input2);
 		div.appendChild(whithSpace);
 		div.appendChild(text3);
 		div.appendChild(checkbox); 		
 		div.appendChild(br);
 	}
}
</script>
</head>
<body>
<h1>Please initialise the jobs</h1>
<form action = 'startSim2' method = 'post' id = 'jobForm'>
<%
	int jobN = (Integer)request.getAttribute("jn");
	for(int i= 0;i<jobN;i++){
%>

Job <%=i%> : <input type="text" size = '3' id = 'J<%=i%>' name = 'J<%=i%>' value = '2'/> segments
 &nbsp;&nbsp;&nbsp;
 <div id = "Job<%=i %>"><a href = "#" onclick = "createSeg('J<%=i%>')">--></a><br/></div>


<%
	}
%>
<br/>
 <button type="submit">submit</button>	
	<button type = "reset">reset</button>
</form>
</body>
</html>