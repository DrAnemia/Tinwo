package proyectoFinal.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import com.ibm.watson.speech_to_text.v1.SpeechToText;
import com.ibm.watson.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.speech_to_text.v1.model.SpeechRecognitionResults;
import com.ibm.cloud.sdk.core.http.HttpMediaType;
import com.ibm.cloud.sdk.core.http.Response;
import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.speech_to_text.v1.websocket.BaseRecognizeCallback;
import com.ibm.watson.speech_to_text.v1.websocket.RecognizeCallback;
import com.ibm.watson.speech_to_text.v1.model.AcousticModel;
import com.ibm.watson.speech_to_text.v1.model.CreateAcousticModelOptions;


public class Dictator{

	public static String dictate() throws FileNotFoundException, LineUnavailableException, InterruptedException{
		
		Authenticator authenticator = new IamAuthenticator("n4HN674f2uy9oWO7IvLo2VEMHZxm_IldDL1RbEbi4kdL");
		SpeechToText speechToText = new SpeechToText(authenticator);
		
		speechToText.setServiceUrl("https://gateway-lon.watsonplatform.net/speech-to-text/api");
		
		/* Signed PCM AudioFormat with 16kHz, 16 bit sample size, mono


		
		int sampleRate = 16000;
		AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, false);
		TargetDataLine line = AudioSystem.getTargetDataLine(format);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

		if (!AudioSystem.isLineSupported(info)) {
		  System.out.println("Line not supported");
		  System.exit(0);
		}
		System.out.println(info);
		//TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
		line.open(format);
		line.start();

		AudioInputStream audio = new AudioInputStream(line);

		RecognizeOptions options = new RecognizeOptions.Builder()
		  .audio(audio)
		  .interimResults(true)
		  .timestamps(true)
		  .wordConfidence(true)
		  //.inactivityTimeout(5) // use this to stop listening when the speaker pauses, i.e. for 5s
		  .contentType(HttpMediaType.AUDIO_RAW + "; rate=" + sampleRate)
		  .build();
		
		BaseRecognizeCallback baseRecognizeCallback =
			    new BaseRecognizeCallback() {
			      public void onTranscription
			        (SpeechRecognitionResults speechRecognitionResults) {
			          System.out.println(speechRecognitionResults);
			      }

			      public void onDisconnected() {
			        System.exit(0);
			      }

			    };

	    speechToText.recognizeUsingWebSocket(options,  baseRecognizeCallback);

		System.out.println("Listening to your voice for the next 30s...");
		Thread.sleep(3 * 1000);

		// closing the WebSockets underlying InputStream will close the WebSocket itself.
		line.stop();
		line.close();

		System.out.println("Fin.");
		//String transcriptFirstOption = transcriptions.get(0).getAsJsonObject().get("transcript").getAsString();
		
		return "hola";
		*/
		
		
		speechToText.setServiceUrl("https://gateway-lon.watsonplatform.net/speech-to-text/api");
		
		// Signed PCM AudioFormat with 16kHz, 16 bit sample size, mono
		int sampleRate = 16000;
		AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, false);

		TargetDataLine line;
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format); // format is an AudioFormat object
		if (!AudioSystem.isLineSupported(info)) {
			  System.out.println("Line not supported");
			  System.exit(0);
		}
		// Obtain and open the line.
		try {
		    line = (TargetDataLine) AudioSystem.getLine(info);
		    line.open(format);
		    
		    
		    ByteArrayOutputStream out  = new ByteArrayOutputStream();
			int numBytesRead;
			byte[] data = new byte[line.getBufferSize() / 5];

			// Begin audio capture.
			line.start();
			
			AudioInputStream audio = new AudioInputStream(line);
			
		
			Thread.sleep(3 * 1000);

			// closing the WebSockets underlying InputStream will close the WebSocket itself.
			line.stop();
			line.close();
			
			RecognizeOptions options = new RecognizeOptions.Builder()
					  .audio(audio)
					  .interimResults(true)
					  .timestamps(true)
					  .wordConfidence(true)
					  //.inactivityTimeout(5) // use this to stop listening when the speaker pauses, i.e. for 5s
					  .contentType(HttpMediaType.AUDIO_RAW + "; rate=" + sampleRate)
					  .build();
			
			BaseRecognizeCallback baseRecognizeCallback =
				    new BaseRecognizeCallback() {
				      public void onTranscription
				        (SpeechRecognitionResults speechRecognitionResults) {
				          System.out.println(speechRecognitionResults);
				      }

				      public void onDisconnected() {
				        System.exit(0);
				      }

				    };

		    System.out.println(speechToText.recognizeUsingWebSocket(options,  baseRecognizeCallback));
			
			//speechToText.recognize(options);
		    
			     
		} catch (LineUnavailableException ex) {
		    System.out.println(ex);
		    System.exit(0);
		}
		// Assume that the TargetDataLine, line, has already
		// been obtained and opened.
		return "hola";
	}
}