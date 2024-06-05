package com.sid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuProfessor extends JPanel {
    private JFrame menuProfessor;
    private Image imagemDeFundo;
    private Image blocoMenu;

    public MenuProfessor(JFrame menuProfessor) {
        this.menuProfessor = menuProfessor;
        setLayout(new GridBagLayout());
        carregarImagens();
        configurarComponentes();
    }

    private void carregarImagens() {
        try {
            imagemDeFundo = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
            blocoMenu = new ImageIcon(getClass().getResource("/images/menu.png")).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configurarComponentes() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Botão "Administrar Perguntas"
        JButton botaoAdministrar = criarBotao("ADMINISTRAR PERGUNTAS", "/images/botao.png", e -> mostrarTelaPerguntas());
        gbc.gridy = 0;
        add(botaoAdministrar, gbc);

        // Botão "Ranking"
        JButton botaoRanking = criarBotao("RANKING", "/images/botao.png", e -> mostrarRanking());
        gbc.gridy = 1;
        add(botaoRanking, gbc);

        // Botão "Configurações"
        JButton botaoConfiguracoes = criarBotao("CONFIGURAÇÕES", "/images/botao.png", e -> mostrarTelaConfiguracoes());
        gbc.gridy = 2;
        add(botaoConfiguracoes, gbc);
    }

    private JButton criarBotao(String texto, String caminhoImagem, ActionListener acao) {
        ImageIcon imagemBotao = new ImageIcon(getClass().getResource(caminhoImagem));
        JButton botao = new JButton(texto, imagemBotao);
        botao.setFont(new Font("Open Sans", Font.BOLD, 22));
        botao.setForeground(Color.WHITE);
        botao.setHorizontalTextPosition(SwingConstants.CENTER);
        botao.setVerticalTextPosition(SwingConstants.CENTER);
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botao.setBorderPainted(false);
        botao.setContentAreaFilled(false);
        botao.setFocusPainted(false);
        botao.setOpaque(false);
        botao.addActionListener(acao);
        return botao;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        int x = (getWidth() - blocoMenu.getWidth(null)) / 2;
        int y = (getHeight() - blocoMenu.getHeight(null)) / 2;
        g.drawImage(blocoMenu, x, y, blocoMenu.getWidth(null), blocoMenu.getHeight(null), this);
    }

    public void mostrarRanking() {
        menuProfessor.dispose();

        JFrame frameRanking = new JFrame("Ranking");
        frameRanking.setSize(1280, 720);
        frameRanking.setMinimumSize(new Dimension(1280, 720));
        frameRanking.setMaximumSize(new Dimension(1920, 1080));
        frameRanking.setLocationRelativeTo(null);
        frameRanking.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameRanking.setResizable(true);

        Ranking ranking = new Ranking(frameRanking, "");
        frameRanking.add(ranking);
        frameRanking.setVisible(true);
    }

    public void mostrarTelaPerguntas() {
        menuProfessor.dispose();

        JFrame frameMenu = new JFrame("Administrar Perguntas");
        frameMenu.setSize(1280, 720);
        frameMenu.setMinimumSize(new Dimension(1920, 1080));
        frameMenu.setMaximumSize(new Dimension(1920, 1080));
        frameMenu.setLocationRelativeTo(null);
        frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMenu.setResizable(true);
        AdminPerguntas adminPerguntas = new AdminPerguntas(frameMenu);
        frameMenu.add(adminPerguntas);
        frameMenu.setVisible(true);
}

    public void mostrarTelaConfiguracoes() {
        menuProfessor.dispose();

        JFrame frameConfiguracoes = new JFrame("Configurações");
        frameConfiguracoes.setSize(1280, 720);
        frameConfiguracoes.setMinimumSize(new Dimension(1280, 720));
        frameConfiguracoes.setMaximumSize(new Dimension(1920, 1080));
        frameConfiguracoes.setLocationRelativeTo(null);
        frameConfiguracoes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameConfiguracoes.setResizable(true);

        Configuracoes menuConfiguracoes = new Configuracoes(frameConfiguracoes, "");
        frameConfiguracoes.add(menuConfiguracoes);
        frameConfiguracoes.setVisible(true);
    }
}
