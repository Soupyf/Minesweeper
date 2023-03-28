import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music extends Thread {
    private final String fileName;

    public Music(String wavFile) {
        this.fileName = wavFile;
    }

    public void run() {
        File soundFile = new File(fileName);
        if(!soundFile.exists()) {
            System.err.println("Wave file not found:" + fileName);
            return;
        }
        while(true) {
            AudioInputStream audioInputStream;
            try {
                audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            } catch(UnsupportedAudioFileException | IOException e1) {
                e1.printStackTrace();
                return;
            }
            AudioFormat format = audioInputStream.getFormat();
            SourceDataLine auline;
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            try {
                auline = (SourceDataLine) AudioSystem.getLine(info);
                auline.open(format);
            } catch(Exception e) {
                e.printStackTrace();
                return;
            }
            if(auline.isControlSupported(FloatControl.Type.PAN)) {
                FloatControl pan = (FloatControl) auline.getControl(FloatControl.Type.PAN);
            }
            auline.start();
            int nBytesRead = 0;
            int EXTERNAL_BUFFER_SIZE = 524288;
            byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
            try {
                while(nBytesRead != -1) {
                    nBytesRead = audioInputStream.read(abData, 0, abData.length);
                    if(nBytesRead >= 0)
                        auline.write(abData, 0, nBytesRead);
                }
            } catch(IOException e) {
                e.printStackTrace();
                return;
            } finally {
                auline.drain();
            }
        }
    }
}
