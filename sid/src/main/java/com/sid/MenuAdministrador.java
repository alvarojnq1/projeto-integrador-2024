package com.sid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuAdministrador extends JPanel {
    private JFrame menuAdministrador;
    private Image imagemDeFundo;
    private Image blocoMenu;

    public MenuAdministrador(JFrame menuAdministrador) {
        this.menuAdministrador = menuAdministrador;
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

        // Botão "cadastrar usuário"
        JButton botaoCadastrar = criarBotao("CADASTRAR USUÁRIO", "/images/botao.png", e -> mostrarCadastrar());
        gbc.gridy = 0;
        add(botaoCadastrar, gbc);

        // Botão "administrar usuário"
        JButton botaoAdministrar = criarBotao("ADMINISTRAR USUÁRIO", "/images/botao.png", e -> mostrarAdministrar());
        gbc.gridy = 1;
        add(botaoAdministrar, gbc);

        // Botão "configurações"
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

    public void mostrarCadastrar() {
        menuAdministrador.dispose();

        JFrame frameCadastrar = new JFrame("Cadastrar");
        frameCadastrar.setSize(1280, 720);
        frameCadastrar.setMinimumSize(new Dimension(1280, 720));
        frameCadastrar.setMaximumSize(new Dimension(1920, 1080));
        frameCadastrar.setLocationRelativeTo(null);
        frameCadastrar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameCadastrar.setResizable(true);
        Cadastrar cadastrar = new Cadastrar(frameCadastrar);
        frameCadastrar.add(cadastrar);
        frameCadastrar.setVisible(true);
    }

    public void mostrarAdministrar() {
        menuAdministrador.dispose();

        JFrame frameAdministrar = new JFrame("Administrar");
        frameAdministrar.setSize(1280, 720);
        frameAdministrar.setMinimumSize(new Dimension(1280, 720));
        frameAdministrar.setMaximumSize(new Dimension(1920, 1080));
        frameAdministrar.setLocationRelativeTo(null);
        frameAdministrar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameAdministrar.setResizable(true);
        Administrar administrar = new Administrar(frameAdministrar);
        frameAdministrar.add(administrar);
        frameAdministrar.setVisible(true);
    }

    public void mostrarTelaConfiguracoes() {
        menuAdministrador.dispose();

        JFrame frameConfiguracoes = new JFrame("Configurações");
        frameConfiguracoes.setSize(1280, 720);
        frameConfiguracoes.setMinimumSize(new Dimension(1280, 720));
        frameConfiguracoes.setMaximumSize(new Dimension(1920, 1080));
        frameConfiguracoes.setLocationRelativeTo(null);
        frameConfiguracoes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameConfiguracoes.setResizable(true);
        Configuracoes configuracoes = new Configuracoes(frameConfiguracoes, ""); // Pass email if needed
        frameConfiguracoes.add(configuracoes);
        frameConfiguracoes.setVisible(true);
    }
}
