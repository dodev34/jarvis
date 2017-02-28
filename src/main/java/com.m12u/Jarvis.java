package com.m12u;

import com.m12u.micro.JarvisMicroDetector;
import com.m12u.window.MainController;

/**
 * Created by m12u on 19/02/2017.
 */
public class Jarvis {


    static public final String API_KEY = "your api key";
    static public final String API_LANGUAGE = "fr";

    /**
     * com.m12u.micro.JarvisMicroDetector
     */
    protected JarvisMicroDetector jarvisMicroDetector;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Jarvis jarvis = new Jarvis();
    }

    /**
     *
     */
    public Jarvis() {
        MainController mainController = new MainController();
    }
}
