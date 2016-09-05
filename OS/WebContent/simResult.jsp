<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	import="assign1.*" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Result</title>
<script type="text/javascript" src="jquery.js"></script>
<script type="text/javascript">
	$(window).load(repeatGet);
	var to;
	function stop() {
		alert("stop");
		clearTimeout(to);
	}
	function repeatGet() {// geting the result from the server every second.
		var stop = document.getElementById('re');
		to = setTimeout("getData()", 1000);

		if (stop.value == "true") {
			clearTimeout(to);
			document.getElementById('stop').setAttribute("disabled", "disabled");
			document.getElementById('resume').setAttribute("disabled", "disabled");
			document.getElementById('recordResult').removeAttribute("disabled");
		}
	}

	function getData() {
		window.location.href = "/CS6721/startSim";
	}

	function go() {
		window.location.href = "/CS6721/startSim?reset=true";
	}
	
	function recordResult(){
		var memS = document.getElementById("memSize");
		var pageS = document.getElementById("pageSize");
		var pageF = document.getElementById("pageFault");
		var swap = document.getElementById("swap");
		
		$.post('/CS6721/record',{memSize:memS.value,pageSize:pageS.value,pageFault:pageF.value,swap:swap.value});
		document.getElementById('recordResult').setAttribute("disabled", "disabled");
	}
</script>
</head>
<body>
	<%
		boolean f = (Boolean) request.getAttribute("finish");
		if (f==true) {// if the simulation is finished, display the results.
	%>
	<h1>The simulation is finished.</h1>
	<div>
		Memory Size:
		<%=(Integer)request.getAttribute("memSize")%></div><input style="visibility: hidden;position: absolute;left: 400px"  id = 'memSize' value = '<%=(Integer)request.getAttribute("memSize")%>' />
	<div >
		Page Size:
		<%=(Integer)request.getAttribute("pageSize")%></div><input style="visibility: hidden;position: absolute;left: 400px"  id = 'pageSize' value = '<%=(Integer)request.getAttribute("pageSize")%>' />
	<div>
		Page Fault:
		<%=(Integer)request.getAttribute("pageFault")%></div><input style="visibility: hidden;position: absolute;left: 400px"  id = 'pageFault' value = '<%=(Integer)request.getAttribute("pageFault")%>' />
	<div >
		Swap:
		<%=(Integer)request.getAttribute("swap")%></div><input style="visibility: hidden;position: absolute;left: 400px"  id = 'swap' value = '<%=(Integer)request.getAttribute("swap")%>' />
	<a href='#' onclick="go()"> go to the start page.</a>
	<%
		} //else {
			
			//Generate the talbes content no matter the simlation is finished.
			MBTitem[] mbt = (MBTitem[]) request.getAttribute("mbt");
			ArrayList<Job> jobL = (ArrayList<Job>) request.getAttribute("joblist");
	%>
	<h1>
		cycle:
		<%=(Integer)request.getAttribute("curCycle")%></h1>
		<button id = 'stop' onclick='stop()'>stop</button>
		<button id = 'resume' onclick='repeatGet()'>resume</button>
		<button id = 'recordResult' disabled="disabled" onclick='recordResult()'>record this simulation</button>
	<h3>MBT :</h3>
	<table border="1">
		<tr>
			<td>Number</td>
			<td>Job name</td>
			<td>Page number</td>
			<td>count</td>
		</tr>
		<%
			for (int i = 0; i < mbt.length; i++) {
					if (mbt[i].getJ() != null) {
		%>
		<tr>
			<td><%=i%></td>
			<td><%=mbt[i].getJ().getJobName()%></td>
			<td><%=mbt[i].getPageNum()%></td>
			<td><%=mbt[i].getCounter()%></td>
		</tr>
		<%
			} else {
		%>
		<tr>
			<td>empty</td>
		</tr>
		<%
			}
				}
		%>
	</table>

	<%
			for (int j = 0; j < jobL.size(); j++) {
				Job jb = jobL.get(j);
				int [] mypmt = jb.getPMT();
					
	%>
	<h3>PMT :<%=jb.getJobName() %></h3>
	<table border="1">
		<tr>
			<td>Number</td>
			<td>Block Number</td>
			<td>In Memory</td>
		</tr>
		<%					
			for (int i = 0; i < mypmt.length; i++) {
					if(mypmt[i] != -1){
		%>
		<tr>
			<td><%=i%></td>
			<td><%=mypmt[i]%></td>
			<td>Yes</td>
		</tr>
		<%
			}else{
		%>
		<tr>
			<td><%=i%></td>
			<td>---</td>
			<td>No</td>
		</tr>

		<%
			}
			
			}
		
		%>
	</table>
	<%
			
			}
		
		%>

	

	<%
		
//	}
	%>

	<input id='re' style="visibility: hidden;" value='<%=f%>'></input>
</body>
</html>