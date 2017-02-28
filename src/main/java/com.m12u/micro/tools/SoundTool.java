package com.m12u.micro.tools;

import javax.sound.sampled.AudioFormat;

public class SoundTool {

    /**
     * @param audioData byte[]
     * @return int
     */
    public static int calculateRMSLevel(byte[] audioData)
    {
        // audioData might be buffered data read from a data line
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

    /**
     *
     * @return
     */
    public static AudioFormat getAudioFormat() {
        float sampleRate = 8000.0F;
        byte sampleSizeInBits = 16;
        byte channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }
}
