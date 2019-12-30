<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Proyecto ASR new...</title>
</head>
<body>
<h1>Proyecto de ASR con Cloudant, DevOps y microservicio de traducción</h1>
<hr />
<p>Listado de Opciones <b>CLOUDANT o Traducción</b>:</p>
<ul>
<li><a href="listar">Listar</a></li>
<li>Introduzca la palabra a traducir <input type="text" name="palabra" id="text_word"> <button type="button"  onclick="location.href='insertar?palabra='+document.getElementById('text_word').value">Traducir</button></li>
<li><a href="hablar">Dictar</a></li>
<li><a href="parar">Parar</a></li>
 <div id="login-button-div" >
        <a href="ProtectedServlet">
            <button class="button">Login</button>
        </a>
    </div>
</ul>
</body>
</html>