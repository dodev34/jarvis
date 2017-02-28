package com.m12u.window;

import com.m12u.Jarvis;
import com.darkprograms.speech.recognizer.GoogleResponse;
import com.m12u.micro.JarvisMicroDetector;
import com.m12u.micro.adapter.MicroDetectorAdapter;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class MainController extends JFrame {
    private JPanel rootPanel;
    private JTextArea textArea1;
    private JProgressBar progressBar1;
    private JLabel probability;
    private JLabel fiabilityPurcent;
    private JLabel volumeLabel;
    private JLabel onRecordLabel;

    private JarvisMicroDetector jarvisMicroDetector;

    public MainController() {
        super("Main controller");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initJarvis();
        setVisible(true);
    }

    private void initJarvis() {
        // start micro on thread
        jarvisMicroDetector = new JarvisMicroDetector(Jarvis.API_KEY, Jarvis.API_LANGUAGE);

        // add listener
        jarvisMicroDetector.addGoogleResponseListener(new MicroDetectorAdapter() {
            @Override
            public void responseGoogle(GoogleResponse response) {
                String message = response.getResponse();
                System.out.println(message);
                textArea1.append(message + "\n");
                fiabilityPurcent.setText((Double.parseDouble(response.getConfidence()) * 100) + "");
                //System.out.println(response.getResponse());
                //System.out.println("Google is " + Double.parseDouble(response.getConfidence())*100 + "%");
                //System.out.println("Other Possible responses are: ");
                //for(String s: response.getOtherPossibleResponses()){
                //    System.out.println("\t" + s);
                //}
            }
        });

        jarvisMicroDetector.addMicroLevelListener(new MicroDetectorAdapter() {
            @Override
            public void microLevelListener(int level) {
                progressBar1.setValue(level);
                volumeLabel.setText(String.valueOf(level));
            }
        });

        jarvisMicroDetector.addMicroLevelListener(new MicroDetectorAdapter() {
            @Override
            public void recordingListener(boolean onRecording) {
                if (onRecording) {
                    onRecordLabel.setText("Enregistrement en cours");
                } else {
                    onRecordLabel.setText("");
                }
            }
        });

        jarvisMicroDetector.start();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 2, new Insets(15, 15, 15, 15), -1, -1));
        textArea1 = new JTextArea();
        rootPanel.add(textArea1, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        progressBar1 = new JProgressBar();
        rootPanel.add(progressBar1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        probability = new JLabel();
        probability.setText("Fiabilite");
        rootPanel.add(probability, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fiabilityPurcent = new JLabel();
        fiabilityPurcent.setText("...%");
        rootPanel.add(fiabilityPurcent, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Volume");
        rootPanel.add(label1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        volumeLabel = new JLabel();
        volumeLabel.setText("");
        rootPanel.add(volumeLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        onRecordLabel = new JLabel();
        onRecordLabel.setText("");
        rootPanel.add(onRecordLabel, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}
