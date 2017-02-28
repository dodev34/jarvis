package com.m12u;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import java.io.ByteArrayOutputStream;

public class MicroRealTime {

    public static void main(String[] args) {
        TargetDataLine line = null;
        AudioFormat format = new AudioFormat(16000, 16, 1, true, true);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("Line is not supported");
        }

        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open();
        } catch (LineUnavailableException e) {
            System.out.println("Failed to get line");
            System.exit(-1);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int numBytesRead;
        byte[] data = new byte[line.getBufferSize() / 5];

        // Begin audio capture.
        line.start();

        // Here, stopped is a global boolean set by another thread.
        while (true) {
            // Read the next chunk of data from the TargetDataLine.
            numBytesRead = line.read(data, 0, data.length);
            // Save this chunk of data.
            out.write(data, 0, numBytesRead);
            int level = MicroRealTime.calculateRMSLevel(data);
            if (level > 15) {
                System.out.println("Pret avec un volume de : " + MicroRealTime.calculateRMSLevel(data));
                // todo : récupérer la phrase ici :)
            }
        }
    }

    public static int calculateRMSLevel(byte[] audioData)
    { // audioData might be buffered data read from a data line
        long lSum = 0;
        for(int i=0; i<audioData.length; i++)
            lSum = lSum + audioData[i];

        double dAvg = lSum / audioData.length;

        double sumMeanSquare = 0d;
        for(int j=0; j<audioData.length; j++)
            sumMeanSquare = sumMeanSquare + Math.pow(audioData[j] - dAvg, 2d);

        double averageMeanSquare = sumMeanSquare / audioData.length;
        return (int)(Math.pow(averageMeanSquare,0.5d) + 0.5);
    }
}
