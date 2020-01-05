var micro=document.getElementById("micro-icon");
var stop_recording=document.getElementById("stop_recording_icon");
var save_button=document.getElementById("save-button");
var translate=document.getElementById("translate-button");

$(document).ready(function() {
	var url=window.location.href;
	if(url.includes("?")){
		var urlquery=url.split("?")[1];
		var parameters=urlquery.split("&text=");
		var title=parameters[0].replace("title=","");
		var text=parameters[1];
		document.getElementById("filename").value=decodeURI(title);
		document.getElementById("wordarea").value=decodeURI(text);
	}
	
});

micro.onclick= function(){
	console.log("Has pinchado en el micro");
	document.getElementById("wordarea").focus();
	$.ajax({
	    "type": "POST",
	    "url": "/GetStartedJava/hablar",
	    "success": function (data) {
	        console.log("Se ha parado el micro");
	    }
	});
}

stop_recording.onclick= function(){
	console.log("Has pinchado en el stop");
	document.getElementById("wordarea").focus();
	$.ajax({
	    "type": "POST",
	    "url": "/GetStartedJava/parar",
	    "success": function (data) {
	        console.log("Se ha parado el micro");
	    }
	});
}

translate.onclick= function(){
	console.log("Has pinchado en el traducir");
	var content=document.getElementById("wordarea").value;
	
	$.ajax({
	    "type": "POST",
	    "url": "/GetStartedJava/traducir?text_content="+content,	   
	    "success": function (data) {
	        console.log(data);
	        document.getElementById("wordarea").value=data;
	    }
	});
}

save_button.onclick= function(){
	console.log("Has pinchado en el boton de guardar");
	var filename=document.getElementById("filename").value;
	var content=document.getElementById("wordarea").value;
	
	$.ajax({
	    "type": "POST",
	    "url": "/GetStartedJava/insertar?text_content="+content+"&filename="+filename,	   
	    "success": function (data) {	        
	        createFile(filename);
	    }
	});
	
}


function createFile(filename) { 
	var content=document.getElementById("wordarea").value;
	var blob = new Blob([content], {type: "text/plain;charset=utf-8"});
	saveAs(blob, filename+".txt");
}

