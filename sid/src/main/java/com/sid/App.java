package com.sid;

import javax.swing.*;
import java.awt.*;
import javax.sound.sampled.*;
import java.io.File;



public class App {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Login");
        frame.setSize(1280, 720);
        frame.setMinimumSize(new Dimension(1280, 720));
        frame.setMaximumSize(new Dimension(1920,1080));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        
        // Usando o caminho absoluto para o arquivo de áudio
        String audioFilePath = "C:/Users/USER/Downloads/PI/sid/src/main/resources/musicadefundo.wav";
        TocarMusica tocador = new TocarMusica(); // Criar instância de TocarMusica
        tocador.tocaMusica(audioFilePath, 0.1f); // Inicia com o volume definido como 0.1

        Jogo telaLogin = new Jogo(frame);
        frame.add(telaLogin);
        frame.setVisible(true);
    }
}

class TocarMusica {
    private Clip audioClip;  // Armazena o Clip para acesso global dentro da classe
    private static TocarMusica instance;

    public TocarMusica() {}

    // Método público estático para obter a instância
    public static synchronized TocarMusica getInstance() {
        if (instance == null) {
            instance = new TocarMusica();
        }
        return instance;
    }

    public void tocaMusica(String filePath, float volumeInicial) {
        new Thread(() -> {
            try {
                File audioFile = new File(filePath);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                AudioFormat format = audioStream.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, format);
                audioClip = (Clip) AudioSystem.getLine(info);

                audioClip.open(audioStream);
                setVolume(volumeInicial);  // Define o volume inicial
                audioClip.loop(Clip.LOOP_CONTINUOUSLY);
                audioClip.start();

                Thread.sleep(audioClip.getMicrosecondLength());
            } catch (Exception ex) {
                System.out.println("Falha ao carregar musica");
                ex.printStackTrace();
            }
        }).start();
    }

    public void setVolume(float volume) {
        if (audioClip != null && audioClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }
}
