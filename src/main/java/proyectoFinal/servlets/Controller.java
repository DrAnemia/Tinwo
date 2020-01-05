
package proyectoFinal.servlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.LineUnavailableException;


import proyectoFinal.dominio.Palabra;
import proyectoFinal.services.Traductor;
import proyectoFinal.dao.CloudantPalabraStore;

import proyectoFinal.services.Dictator;

/**
 * Servlet implementation class Controller
 */
@WebServlet(urlPatterns = {"/listar", "/insertar", "/hablar", "/parar"})
public class Controller extends HttpServlet  {
	private static final long serialVersionUID = 1L;

	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();		
		CloudantPalabraStore store = new CloudantPalabraStore();
		System.out.println(request.getServletPath());
		switch(request.getServletPath())
		{
			case "/listar":
				if(store.getDB() == null)
					  System.out.println("No hay DB");
				else {					
					response.setContentType("text/plain");
					response.getWriter().write(store.getAll().toString());
					response.getWriter().close();
					System.out.println("Palabras en la BD uuu:<br />" + store.getAll());
				}
				break;
				
			case "/insertar":
				Palabra palabra = new Palabra();				
				String texto = request.getParameter("text_content");
				String filename = request.getParameter("filename");
								
				if(texto==null)
				{
					System.out.println("Siempre se debe enviar algo");
				}
				else
				{
					if(store.getDB() == null) 
					{
						System.out.println(String.format("Palabra: %s", palabra));
					}
					else
					{	
						System.out.println(texto);
						palabra.setName(texto+"$$");
						palabra.setFileName(filename+"$%");
						
						store.persist(palabra);					    			    	  
					}
				}
				break;
			case "/hablar":
						
				Dictator.convertToText();

				break;
			case "/parar":
				Dictator.closeMicro();
				break;
			case "/traducir":
				String content = request.getParameter("text_content");
				String answer="";
				System.out.println(content);
				String[] sentences=content.split(",|\\.");
				for (String sentence : sentences) {
					String traduccion=Traductor.translate(sentence, "es", "en", false);
					answer=answer + traduccion;					
				}
				response.setContentType("text/plain");
				response.getWriter().write(answer);
				response.getWriter().close();
				break;
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
