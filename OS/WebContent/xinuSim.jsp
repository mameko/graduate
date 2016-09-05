<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>xinu system call simulation</title>
<script type="text/javascript" src= "jquery.js"></script>
<script type="text/javascript" src= "xinuSim.js"></script>
</head>
<body>
<h1> Click on buttons below to start the simulation.</h1>
<button id = "start">start</button>
<button id = "pause" >pause</button>
<button id = "resume">resume</button>
<button id = "stop">stop</button>
<br/>
<br/>
<h3>protab</h3>
<table id = "proctab" border="1">
</table>

<div id = "console" style="position: absolute; top: 150px;left: 550px;overflow-y: scroll; width: 350px; height: 400px;"></div>

<h3>ready list</h3>
<div id = "readylist"></div>
</body>
</html>