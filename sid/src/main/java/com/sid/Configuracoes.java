package com.sid;

import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.basic.BasicSliderUI;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

// Classe customizada para criar uma UI personalizada para o JSlider
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
        int thumbY = (slider.getHeight() - thumbIcon.getIconHeight()) / 2;
        thumbIcon.paintIcon(slider, g, thumbRect.x, thumbY);
    }

    @Override
    public void paintTrack(Graphics g) {
        int trackHeight = trackIcon.getIconHeight();
        int trackY = (slider.getHeight() - trackHeight) / 2;
        trackIcon.paintIcon(slider, g, 0, trackY); // Ajusta a posição X conforme necessário
    }

    @Override
    protected Dimension getThumbSize() {
        return new Dimension(thumbIcon.getIconWidth(), thumbIcon.getIconHeight());
    }

    @Override
    public void paintTicks(Graphics g) {
        // Não utilizado
    }

    @Override
    public void paintLabels(Graphics g) {
        // Não utilizado
    }

    @Override
    public void paintFocus(Graphics g) {
        // Não utilizado
    }
}

// Classe Configuracoes que representa a tela de configurações
public class Configuracoes extends JPanel {
    private JFrame MenuConfiguracoes;
    private Image imagemDeFundo;
    private Image blocoConfiguracoes;
    private JSlider volumeSlider;
    private String email;

    // Construtor da classe Configuracoes
    public Configuracoes(JFrame menuConfiguracoes, String email) {
        this.MenuConfiguracoes = menuConfiguracoes;
        this.email = email;  // Certifique-se de inicializar a variável email

        setLayout(new GridBagLayout());
        carregarImagens();
        configurarComponentes();
    }

    // Carrega as imagens necessárias
    private void carregarImagens() {
        try {
            imagemDeFundo = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
            blocoConfiguracoes = new ImageIcon(getClass().getResource("/images/blococonfiguracoes.png")).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Configura os componentes da tela
    private void configurarComponentes() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        // Adiciona ícone de música
        ImageIcon iconMusica = new ImageIcon(getClass().getResource("/images/iconemusica.png"));
        JLabel labelMusica = new JLabel(iconMusica);
        gbc.insets = new Insets(155, 10, 5, 350);
        gbc.gridy = 0;
        gbc.gridx = 0;
        add(labelMusica, gbc);

        // Configuração do JSlider para volume
        ImageIcon iconeScroll = new ImageIcon(getClass().getResource("/images/slider.png"));
        ImageIcon iconeTrack = new ImageIcon(getClass().getResource("/images/Barra.png"));
        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        volumeSlider.setOpaque(false);
        volumeSlider.setUI(new ImageSliderUI(volumeSlider, iconeScroll, iconeTrack));
        
        // Adicionando ChangeListener para o JSlider
        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                float volume = source.getValue() / 100.0f;
                TocarMusica.getInstance().setVolume(volume);
            }
        });

        gbc.insets = new Insets(155, 100, 5, 100); 
        gbc.gridy = 0;
        gbc.gridx = 1;
        add(volumeSlider, gbc);

        // Botão de voltar
        ImageIcon imagemBotao = new ImageIcon(getClass().getResource("/images/botaovoltar.png"));
        JButton botaoVoltar = new JButton("", imagemBotao);
        botaoVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoVoltar.setBorderPainted(false);
        botaoVoltar.setContentAreaFilled(false);
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.setOpaque(false);
        gbc.insets = new Insets(60, 10, 100, 350);
        gbc.gridy = 2;
        botaoVoltar.addActionListener(e -> voltarAoMenuCorrespondente());
        add(botaoVoltar, gbc);
    }

    // Método que decide qual menu mostrar com base no tipo de usuário
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        int x = (getWidth() - blocoConfiguracoes.getWidth(null)) / 2;
        int y = (getHeight() - blocoConfiguracoes.getHeight(null)) / 2;
        g.drawImage(blocoConfiguracoes, x, y, blocoConfiguracoes.getWidth(null), blocoConfiguracoes.getHeight(null), this);
    }

    private void mostrarMenuAluno() {
        MenuConfiguracoes.dispose();
        JFrame frameMenu = criarFrameMenu();
        MenuAluno menuAluno = new MenuAluno(frameMenu, email);
        frameMenu.add(menuAluno);
        frameMenu.setVisible(true);
    }

    private void mostrarMenuProfessor() {
        MenuConfiguracoes.dispose();
        JFrame frameMenu = criarFrameMenu();
        MenuProfessor menuProfessor = new MenuProfessor(frameMenu);
        frameMenu.add(menuProfessor);
        frameMenu.setVisible(true);
    }

    private void mostrarMenuAdministrador() {
        MenuConfiguracoes.dispose();
        JFrame frameMenu = criarFrameMenu();
        MenuAdministrador menuAdmin = new MenuAdministrador(frameMenu);
        frameMenu.add(menuAdmin);
        frameMenu.setVisible(true);
    }

    private JFrame criarFrameMenu() {
        JFrame frameMenu = new JFrame("Menu");
        frameMenu.setSize(1280, 720);
        frameMenu.setMinimumSize(new Dimension(1280, 720));
        frameMenu.setMaximumSize(new Dimension(1920, 1080));
        frameMenu.setLocationRelativeTo(null);
        frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMenu.setResizable(true);
        return frameMenu;
    }
}
