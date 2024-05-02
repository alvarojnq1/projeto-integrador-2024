package com.sid;
import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setSize(1280, 720);
        frame.setMinimumSize(new Dimension(1280, 720));
        frame.setMaximumSize(new Dimension(1920,1080));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        TelaLogin telaLogin = new TelaLogin(frame);
        frame.add(telaLogin);
        frame.setVisible(true);
    }
}
