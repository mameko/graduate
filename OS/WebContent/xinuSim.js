$(window).load(init);

/**
 * initialise the page when loaded.
 */

function init(){
	$("#start").click(startHandler);
	$("#pause").click(pauseHandler);
	$("#resume").click(resumeHandler);
	$("#stop").click(stopHandler);

	$("#pause").attr("disabled","disabled");
	$("#resume").attr("disabled","disabled");
	$("#stop").attr("disabled","disabled");	
}

var t;
/**
 * handle the start button click event.
 */
function startHandler(){
	t = setTimeout("nextRound()",2000);
	$("#start").attr("disabled","disabled");
	$("#pause").removeAttr("disabled");
	$("#stop").removeAttr("disabled");
	
	// clear all the divs
	document.getElementById("console").innerHTML = "";
	var oldt=document.getElementById("proctab");
	if(oldt.firstChild){
		oldt.removeChild(oldt.firstChild);
	}		
	$("#readylist").html("");
}
/**
 * request for next execution of the system and get the result.
 */
function nextRound(){
	$.ajax({
		dataType : "json",
		type: "POST",
		url: "xinu",
		data: {
			op: "next"
		},
		success: showResult
	});
}

/**
 * handle pause event, clear the time out
 */

function pauseHandler(){
	clearTimeout(t);
	$("#resume").removeAttr("disabled");
	$("#pause").attr("disabled","disabled");
}

/**
 * handle resume event, reset time out and start the repeatedly request process.
 */
function resumeHandler(){
	t = setTimeout("nextRound()",2000);
	$("#pause").removeAttr("disabled");
	$("#resume").attr("disabled","disabled");
}

/**
 * handle stop event, clear the time out and send a request to the sever to stop the simulation
 */
function stopHandler(){
	clearTimeout(t);
	$.ajax({
		type: "POST",
		url: "xinu",
		data: {
			op: "stop"
		},
		success: complete
	});
}

/**
 * call back function of nextRound. Get data from server, assemble them to the correct format,
 *  generate the corresponding elements and place the generated elements in the right place on
 *  the page
 *  
 * @param myJsonData
 */
function showResult(myJsonData){
	// disassemble the data and put it to the right place
	var badcall = myJsonData.badcall;
	var curExec = myJsonData.curExec;
	
	var curExec = document.createTextNode(curExec);
	badcall = document.createTextNode("badcall : "+badcall);
//	var br = document.createElement("br");
	
	$("#console").append(curExec);
	$("#console").append("<br/>");
	$("#console").append(badcall);
	$("#console").append("<br/>");
	$("#console").append("<br/>");
	var obj = document.getElementById("console");
	obj.scrollTop = obj.scrollHeight;
	
	var readylist = myJsonData.readylist;
	
	var list = "";
	for(var i in readylist){
		list = list + readylist[i] + ',';
	}
	
	$("#readylist").html(list);
	
	var oldt=document.getElementById("proctab");
	if(oldt.firstChild){
		oldt.removeChild(oldt.firstChild);
	}		
	var protab = myJsonData.protab;

	var r = document.createElement("tr"); 
	var c0 = document.createElement("td");  
	var t0 = document.createTextNode("pid");
	c0.appendChild(t0);
	r.appendChild(c0);
	
	var c1 = document.createElement("td");  
	var t1 = document.createTextNode("state");
	c1.appendChild(t1);
	r.appendChild(c1);
	var c2 = document.createElement("td");
	var t2 = document.createTextNode("name");
	c2.appendChild(t2);
	r.appendChild(c2);
	var c3 = document.createElement("td");
	var t3 = document.createTextNode("priority");
	c3.appendChild(t3);
	r.appendChild(c3);
	$("#proctab").append(r);
	
	for(var j in protab){		
		var row = document.createElement("tr"); 
		
		var cell0 = document.createElement("td");  
		var content0 = document.createTextNode(j);
		cell0.appendChild(content0);
		row.appendChild(cell0);
		
		var cell1 = document.createElement("td");  
		var text;
		switch(protab[j].state){
		case 1:
			text = "PRCURR";
			break;
		case 2:
			text = "PRFREE";
			break;
		case 3:
			text = "PRREADY";
			break;
		case 6:
			text = "PRSUSP";
			break;
		default:
			text = "ERR";			
		}
		var content1 = document.createTextNode(text);
		cell1.appendChild(content1);
		row.appendChild(cell1);
		var cell2 = document.createElement("td");  
		var content2 = document.createTextNode(protab[j].name);
		cell2.appendChild(content2);
		row.appendChild(cell2);
		var cell3 = document.createElement("td");  
		var content3 = document.createTextNode(protab[j].priority);
		cell3.appendChild(content3);
		row.appendChild(cell3);
		
		$("#proctab").append(row);
	
	}
	t = setTimeout("nextRound()",2000);
}

/**
 * call back function of stopHandler.
 */

function complete(){
	alert("halt");
	$("#start").removeAttr("disabled");
	$("#pause").attr("disabled","disabled");
	$("#resume").attr("disabled","disabled");
	$("#stop").attr("disabled","disabled");
}