package com.m12u.micro.model;

import com.darkprograms.speech.recognizer.GoogleResponse;
import java.util.EventListener;

public interface MicroDetectorListenerInterface extends EventListener {
    void responseGoogle(GoogleResponse googleResponse);

    void microLevelListener(int level);

    void recordingListener(boolean onRecording);
}
