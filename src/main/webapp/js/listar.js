$(document).ready(function() {	
	$.ajax({
	    "type": "POST",
	    "url": "/GetStartedJava/listar",
	    "success": function (data) {
	    	console.log(data);
	    	var files=data.split("$$,");
	    	
	    	for (index = 0; index < files.length; index++){
	        	var info=files[index].split("$%");
	        	var title=info[0].replace("$$","").replace("[","");
	        	var text=info[1].replace("$%","").replace("]","").replace("$$","");	
	        	console.log(info);
	        	document.getElementById("link-container").innerHTML += "<a href='/GetStartedJava/Escribir.jsp?title="+title+"&text="+ text +"' style='color: white'>"+title+"</a><br>";
	        } 
	    }
	});
});