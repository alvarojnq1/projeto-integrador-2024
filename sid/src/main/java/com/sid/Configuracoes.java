package com.sid;

import javax.swing.event.ChangeEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.sql.*;

class ImageSliderUI extends BasicSliderUI {
    private ImageIcon thumbIcon;
    private ImageIcon trackIcon; // Imagem para a trilha

    
    public ImageSliderUI(JSlider slider, ImageIcon thumbIcon, ImageIcon trackIcon) {
        super(slider);
        this.thumbIcon = thumbIcon;
        this.trackIcon = trackIcon;
    }

    @Override
    public void paintThumb(Graphics g) {
        // Calcula a posição Y para que o thumb esteja centralizado verticalmente com o track
        int thumbY = (slider.getHeight() - thumbIcon.getIconHeight()) / 2;
        thumbIcon.paintIcon(slider, g, thumbRect.x, thumbY);
    }

    @Override
    public void paintTrack(Graphics g) {
        // Calcula a posição Y para centralizar a trilha verticalmente
        int trackHeight = trackIcon.getIconHeight();
        int trackY = (slider.getHeight() - trackHeight) / 2;
        int trackX = (slider.getWidth() - trackIcon.getIconWidth()) / 2;

        trackIcon.paintIcon(slider, g, trackX, trackY);
    }

    @Override
    protected Dimension getThumbSize() {
        return new Dimension(thumbIcon.getIconWidth(), thumbIcon.getIconHeight());
    }

    @Override
    public void paintTicks(Graphics g) {
        // Opcional: Implemente se quiser mostrar ticks
    }

    @Override
    public void paintLabels(Graphics g) {
        // Opcional: Implemente se quiser mostrar labels
    }
    @Override
    public void paintFocus(Graphics g) {
        //Não desenhar o foco
    }
}

public class Configuracoes extends JPanel {
    private JFrame MenuConfiguracoes;
    private Image imagemDeFundo;
    private Image blocoConfiguracoes;
    private JSlider volumeSlider;  
    private ConnectionFactory connectionFactory; 

    

    public Configuracoes(JFrame menuConfiguracoes){
        this.MenuConfiguracoes = menuConfiguracoes;
        this.connectionFactory = new ConnectionFactory();
        setLayout(new GridBagLayout());
        carregarImagens();
        configurarComponentes();
    }

    private void carregarImagens() {
        try {
            imagemDeFundo = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
            blocoConfiguracoes = new ImageIcon(getClass().getResource("/images/blococonfiguracoes.png")).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 

    private void configurarComponentes(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        // Adicionando um ícone de música
        ImageIcon iconMusica = new ImageIcon(getClass().getResource("/images/iconemusica.png"));
        JLabel labelMusica = new JLabel(iconMusica);
        gbc.insets = new Insets(155, 10, 5, 350);
        gbc.gridy = 0;
        add(labelMusica, gbc);

        // Configuração do JSlider para volume
        ImageIcon iconeScroll = new ImageIcon(getClass().getResource("/images/slider.png"));
        ImageIcon iconeTrack = new ImageIcon(getClass().getResource("/images/Barra.png"));
        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        volumeSlider.setOpaque(false);
        volumeSlider.setMajorTickSpacing(10);
        volumeSlider.setMinorTickSpacing(1);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.setUI(new ImageSliderUI(volumeSlider, iconeScroll,iconeTrack));
        
        gbc.gridx = 0;  // Pode usar 0 para começar no início
        gbc.gridy = 0;  // Fica na primeira linha
        gbc.gridwidth = GridBagConstraints.REMAINDER;  // Ocupa o restante da linha
        gbc.anchor = GridBagConstraints.CENTER;  // Centraliza o componente
        gbc.insets = new Insets(155, 100, 5, 100);  // Ajusta os espaçamentos
        volumeSlider.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                float volume = source.getValue() / 100.0f;
                TocarMusica.getInstance().setVolume(volume);
            }
        });

       
        add(volumeSlider, gbc);

        
        

        //botao voltar
        ImageIcon imagemBotao = new ImageIcon(getClass().getResource("/images/botaovoltar.png"));
        JButton botaoVoltar = new JButton("", imagemBotao);
        botaoVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoVoltar.setBorderPainted(false);
        botaoVoltar.setContentAreaFilled(false);
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.setOpaque(false);
        gbc.insets = new Insets(60, 10, 100, 350);
        gbc.gridy = 1;
        botaoVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                voltarAoMenuCorrespondente();
            }
        });
        
        add(botaoVoltar, gbc);

    }
    private void voltarAoMenuCorrespondente() {
        switch (TelaLogin.tipoUsuario) {
            case "aluno":
                mostrarMenuAluno();
                break;
            case "adm":
                mostrarMenuAdministrador();
                break;
            case "professor":
                mostrarMenuProfessor();
                break;
            default:
                System.err.println("Tipo de usuário desconhecido: " + TelaLogin.tipoUsuario);
                break;
        }
    }

    private void ajustarVolume(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            float volume = source.getValue() / 100.0f;
            TocarMusica.getInstance().setVolume(volume);
            source.setValue((int) (volume * 100)); // Define a posição do slider para refletir o novo valor do volume
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        int x = (getWidth() - blocoConfiguracoes.getWidth(null)) / 2;
        int y = (getHeight() - blocoConfiguracoes.getHeight(null)) / 2;
        g.drawImage(blocoConfiguracoes, x, y, blocoConfiguracoes.getWidth(null), blocoConfiguracoes.getHeight(null), this);
    }

    private void mostrarMenuAluno(){
        MenuConfiguracoes.dispose();

        JFrame frameMenu = new JFrame("Menu");
        frameMenu.setSize(1280, 720);
        frameMenu.setMinimumSize(new Dimension(1280, 720));
        frameMenu.setMaximumSize(new Dimension(1920, 1080));
        frameMenu.setLocationRelativeTo(null);
        frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMenu.setResizable(true);
        MenuAluno menuAluno = new MenuAluno(frameMenu, login.getText());
        frameMenu.add(menuAluno);
        frameMenu.setVisible(true);
    }

    private void mostrarMenuProfessor(){
        MenuConfiguracoes.dispose();

        JFrame frameMenu = new JFrame("Menu");
        frameMenu.setSize(1280, 720);
        frameMenu.setMinimumSize(new Dimension(1280, 720));
        frameMenu.setMaximumSize(new Dimension(1920, 1080));
        frameMenu.setLocationRelativeTo(null);
        frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMenu.setResizable(true);
        MenuProfessor menuProfessor = new MenuProfessor(frameMenu);
        frameMenu.add(menuProfessor);
        frameMenu.setVisible(true);
    }

    private void mostrarMenuAdministrador(){
        MenuConfiguracoes.dispose();

        JFrame frameMenu = new JFrame("Menu");
        frameMenu.setSize(1280, 720);
        frameMenu.setMinimumSize(new Dimension(1280, 720));
        frameMenu.setMaximumSize(new Dimension(1920, 1080));
        frameMenu.setLocationRelativeTo(null);
        frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMenu.setResizable(true);
        MenuAdministrador menuAdmin = new MenuAdministrador(frameMenu);
        frameMenu.add(menuAdmin);
        frameMenu.setVisible(true);
    }
}
