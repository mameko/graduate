<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Segmented Demand Paged Memory Management Set Up</title>
</head>
<body>

<h1>Set Up system parameter</h1>
<form id = 'form1' action="segPageInit" METHOD="POST">
	<table id = 'para'>
	<tr>
		<td>Memory size: </td>
		<td><input type="text" id="memS" name="memS" size="4" value = '100'/></td>
	</tr>
	<tr>
		<td>Page size: </td>
		<td><input type="text" id="pageS" name="pageS" size="4" value = '10'/></td>
	</tr>
	<tr>
		<td>Job number: </td>
		<td><input type="text" id="jobN" name="jobN" size="3" value = '3'/></td>
	</tr>
	<tr>
		<td>Cycle of execution: </td>
		<td><input type="text" id="cyc" name="cyc" size="15" value = '15'/></td>
	</tr>
	<tr>
		<td><button type="submit">submit</button></td>
		<td><button type="reset">reset</button></td>
	</tr>
	</table>


</form>

</body>
</html>