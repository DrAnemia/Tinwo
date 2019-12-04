package proyectoFinal.dominio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class Microfono
{
	int sampleRate;
	AudioFormat format;
	DataLine.Info info;
	TargetDataLine line;
	AudioInputStream audio;

	public Microfono(int sampleRate)
	{
		// Signed PCM AudioFormat with 16kHz, 16 bit sample size, mono
		this.sampleRate = sampleRate;
		this.format = new AudioFormat(sampleRate, 16, 1, true, false);
		this.info = new DataLine.Info(TargetDataLine.class, this.format);

		if (!AudioSystem.isLineSupported(this.info)) {
		  System.out.println("Line not supported");
		  System.exit(0);
		}

		this.line=null;
		
		try {
			this.line = (TargetDataLine) AudioSystem.getLine(this.info);
			this.line.open(this.format);
			this.line.start();			
		} catch (LineUnavailableException e) {e.printStackTrace();}		
		
		this.audio = new AudioInputStream(this.line);
	}

	public AudioInputStream getAudio()
	{
		return this.audio;
	}
}