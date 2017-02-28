package com.m12u.micro;

import com.darkprograms.speech.microphone.MicrophoneAnalyzer;
import com.darkprograms.speech.recognizer.GoogleResponse;
import com.darkprograms.speech.recognizer.Recognizer;
import com.m12u.micro.model.MicroDetectorListenerInterface;
import javaFlacEncoder.FLACFileWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.lang.Thread;

public class JarvisMicroDetector extends Thread {

    // un seul objet pour tous les types d'écouteurs
    private final
    Collection<MicroDetectorListenerInterface>
            microDetectorListenerInterfaces = new ArrayList<MicroDetectorListenerInterface>();

    /**
     *
     */
    private volatile boolean running = true;

    /**
     *
     */
    private MicrophoneAnalyzer mic;

    /**
     *
     */
    private Recognizer recognizer;

    /**
     *
     */
    private int THRESHOLD = 10;

    /**
     * @param apiKey
     * @param apiLangue
     */
    public JarvisMicroDetector(String apiKey, String apiLangue) {
        this.mic = new MicrophoneAnalyzer(FLACFileWriter.FLAC);
        this.recognizer = new Recognizer(apiLangue, apiKey); //Specify your language here.
    }

    /**
     * @param microDetectorListenerInterface
     */
    public void addGoogleResponseListener(MicroDetectorListenerInterface microDetectorListenerInterface) {
        microDetectorListenerInterfaces.add(microDetectorListenerInterface);
    }

    /**
     * @param microDetectorListenerInterface
     */
    public void addMicroLevelListener(MicroDetectorListenerInterface microDetectorListenerInterface) {
        microDetectorListenerInterfaces.add(microDetectorListenerInterface);
    }

    /**
     * @param microDetectorListenerInterface
     */
    public void addRecordingListener(MicroDetectorListenerInterface microDetectorListenerInterface) {
        microDetectorListenerInterfaces.add(microDetectorListenerInterface);
    }

    /**
     *
     * @param THRESHOLD
     */
    public void setThreshold(int THRESHOLD) {
        this.THRESHOLD = THRESHOLD;
    }

    /**
     *
     */
    public void closeIt() {
        this.running = false;
    }

    @Override
    public void run() {
        startJarvis();
    }

    protected void startJarvis()
    {
        int n = 0;
        int max = 0;
        System.out.println("Check sound in progress");
        mic.open();
        while (n < 100) {
            int volume = mic.getAudioVolume();
            microLevelListener(volume);
            if (volume > max) {
                max = volume;
                log("Check sound max : " + max);
            }
            n++;
        }
        mic.close();
        THRESHOLD = max;
        System.out.println("Check sound finish");
        log("Check sound finish(THRESHOLD = "+THRESHOLD+")");
        ambientListenin();
    }

    /**
     * Ecoute et déclanche l'enregistrement
     */
    protected void ambientListenin() {
        mic.setAudioFile(new File("AudioTestNow.flac"));
        while(true){
            mic.open();

            final int THRESHOLD = 10;
            int volume = mic.getAudioVolume();
            microLevelListener(volume);
            boolean isSpeaking = (volume > THRESHOLD);
            if(isSpeaking){
                createRecording(THRESHOLD);
            }
        }
    }

    /**
     *
     */
    protected void createRecording(int THRESHOLD)
    {
        try {
            System.out.println("RECORDING...");
            recordingListener(true);
            mic.captureAudioToFile(mic.getAudioFile());//Saves audio to file.
            do{
                Thread.sleep(1000);//Updates every second
            }
            while(mic.getAudioVolume() > THRESHOLD);
            System.out.println("Recording Complete, Recognizing...");
            File fileForSend = mic.getAudioFile();
            sendRequestGoogle(fileForSend);
            recordingListener(false);
            log("Looping back(Restarts loops)");//Restarts loops
        } catch (Exception e) {
            e.printStackTrace();
            log("Error Occured");
        }
        finally{
            //Makes sure microphone closes on exit.
            mic.close();
        }
    }

    public void sendRequestGoogle(File file) {
        try {
            int maxNumOfResponses = 4;
            GoogleResponse response = recognizer.getRecognizedDataForFlac(
                    file,
                    maxNumOfResponses,
                    (int) mic.getAudioFormat().getSampleRate()
            );
            responseGoogle(response);
        } catch (java.lang.NullPointerException ex) {
            log("google not work with null");
        } catch (Exception ex) {
            System.out.println("ERROR: Google cannot be contacted");
            ex.printStackTrace();
        }
    }

    /**
     * @param response
     */
    protected void responseGoogle(GoogleResponse response) {
        for (MicroDetectorListenerInterface listener : microDetectorListenerInterfaces) {
            listener.responseGoogle(response);
        }
    }

    protected void microLevelListener(int level) {
        for (MicroDetectorListenerInterface listener : microDetectorListenerInterfaces) {
            listener.microLevelListener(level);
        }
    }

    protected void recordingListener(boolean onRecording) {
        for (MicroDetectorListenerInterface listener : microDetectorListenerInterfaces) {
            listener.recordingListener(onRecording);
        }
    }

    protected void log(String message)
    {
        if (true) {
            //System.out.println(message);
        }
    }
}
