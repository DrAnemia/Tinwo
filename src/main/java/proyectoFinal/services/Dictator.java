package proyectoFinal.services;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.io.PrintWriter;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.cloud.sdk.core.http.HttpMediaType;
import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.speech_to_text.v1.SpeechToText;
import com.ibm.watson.speech_to_text.v1.model.CreateLanguageModelOptions;
import com.ibm.watson.speech_to_text.v1.model.LanguageModel;
import com.ibm.watson.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.speech_to_text.v1.model.SpeechRecognitionResults;
import com.ibm.watson.speech_to_text.v1.websocket.BaseRecognizeCallback;

import proyectoFinal.servlets.Controller;
import proyectoFinal.services.Traductor;

public class Dictator {
	
	static TargetDataLine line;
	
	static {

        System.setProperty("java.awt.headless", "false");
	}
	
	public static void closeMicro() {
		line.stop();
		line.close();
	}
	
	public static void convertToText(){
		
		Authenticator authenticator = new IamAuthenticator("5jTth1nrjH9gP-aHVOyUvhmW_VZc83Pf2sWBzgw2rGF8");
		SpeechToText speechToText = new SpeechToText(authenticator);
		speechToText.setServiceUrl("https://gateway-lon.watsonplatform.net/speech-to-text/api");
		String prueba;
		// Signed PCM AudioFormat with 16kHz, 16 bit sample size, mono
		int sampleRate = 16000;
		AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, false);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		System.out.println("---");
		
		if (!AudioSystem.isLineSupported(info)) {
		  System.out.println("Line not supported");
		  System.exit(0);
		}	
		
		try {
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format);
			line.start();
			
		} catch (LineUnavailableException e) {	
			e.printStackTrace();
		}		

		
		AudioInputStream audio = new AudioInputStream(line);
		RecognizeOptions options = new RecognizeOptions.Builder()
		  .model("es-ES_BroadbandModel")
		  .interimResults(true)
		  .audio(audio)
		  .contentType(HttpMediaType.AUDIO_RAW + "; rate=" + sampleRate)
		  .build();	
		
	
		 
		 speechToText.recognizeUsingWebSocket(options, new BaseRecognizeCallback() {
		  @Override
		  public void onTranscription(SpeechRecognitionResults speechResults) {	
			  try {
				  Robot r=null;
				  try {
						r = new Robot();
				  } catch (AWTException e) {e.printStackTrace();}
			  
		    
				  String speechJSON = speechResults.toString();
				  JsonObject rootObj = JsonParser.parseString(speechJSON).getAsJsonObject();
				  JsonArray resultados = rootObj.getAsJsonArray("results");
				  System.out.println(resultados.get(0).getAsJsonObject());
		   
				  String res=resultados.get(0).getAsJsonObject().getAsJsonArray("alternatives").get(0).getAsJsonObject().get("transcript").getAsString();
				  Boolean acabado=resultados.get(0).getAsJsonObject().get("final").getAsBoolean();
				  if(res.contains(" ") && res!=" " && acabado==false){
					  String[] res_array=res.split(" ");
					  res=res_array[res_array.length-1]+" ";
					  if(res=="coma") {
						  res=",";						  
					  }else if(res=="punto"){
						  res=".";
					  }
					  for(char ch : res.toCharArray()) {
				  		  try {
				  			  int command = KeyEvent.getExtendedKeyCodeForChar(ch);
				  			  System.out.println(command);
							  r.keyPress(command);
							  r.keyRelease(command); 
				  		  }catch(Exception e) {
				  			  System.out.println("No se identifica el simbolo");
				  		  }
						  
					  }
					  res="";
				  }
			  }catch (Exception  e) {
				  e.printStackTrace();
			  }
		  }
		});	
		
		 

	}
}