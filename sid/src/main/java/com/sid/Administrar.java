package com.sid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;

public class Administrar extends JPanel {
    private JFrame Administrar;
    private Image imagemDeFundo;
    private Image blocoAdministrar;

    public Administrar(JFrame administrar){
        this.Administrar = administrar;
        setLayout(new GridBagLayout());
        carregarImagens();
        configurarComponentes();
    }

    public void carregarImagens(){
        imagemDeFundo = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
        blocoAdministrar = new ImageIcon(getClass().getResource("/images/blocoadministrar.png")).getImage();
    }
    public void configurarComponentes(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Faz com que o componente ocupe toda a linha
        gbc.fill = GridBagConstraints.HORIZONTAL; // Faz com que o componente expanda horizontalmente
        gbc.insets = new Insets(5, 0, 5, 0); // Espaçamento padrão

        // Botão "professor"
        ImageIcon imagemBotao = new ImageIcon(getClass().getResource("/images/botao.png")); // carrega a imagem pro  botao
        JButton botaoProfessor = new JButton("PROFESSOR", imagemBotao); 
        botaoProfessor.setFont(new Font("Arial", Font.BOLD,22));
        botaoProfessor.setForeground(Color.WHITE);
        botaoProfessor.setHorizontalTextPosition(SwingConstants.CENTER); // Centraliza texto horizontalmente sobre a imagem
        botaoProfessor.setVerticalTextPosition(SwingConstants.CENTER); // Centraliza texto verticalmente sobre a imagem
        botaoProfessor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // adiciona o efeito de "mouse sobre o objeto
        botaoProfessor.setBorderPainted(false); // Não pinta a borda
        botaoProfessor.setContentAreaFilled(false); // Não preenche a área do conteúdo
        botaoProfessor.setFocusPainted(false); // Não pinta o foco do botão
        botaoProfessor.setOpaque(false); // Define opacidade como falsa
        gbc.insets = new Insets(50, 0, 5, 0);
        gbc.gridy = 0;
        botaoProfessor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarAdministrarProfessor();
            }
        });
        add(botaoProfessor, gbc);

        // Botão "aluno"
        JButton botaoAluno = new JButton("ALUNO", imagemBotao); 
        botaoAluno.setFont(new Font("Arial", Font.BOLD,22));
        botaoAluno.setForeground(Color.WHITE);
        botaoAluno.setHorizontalTextPosition(SwingConstants.CENTER); // Centraliza texto horizontalmente sobre a imagem
        botaoAluno.setVerticalTextPosition(SwingConstants.CENTER); // Centraliza texto verticalmente sobre a imagem
        botaoAluno.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // adiciona o efeito de "mouse sobre o objeto
        botaoAluno.setBorderPainted(false); // Não pinta a borda
        botaoAluno.setContentAreaFilled(false); // Não preenche a área do conteúdo
        botaoAluno.setFocusPainted(false); // Não pinta o foco do botão
        botaoAluno.setOpaque(false); // Define opacidade como falsa
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.gridy = 1;
        botaoAluno.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarAdministrarAluno();
            }
        });
        add(botaoAluno, gbc);

        ImageIcon imagemBotao2 = new ImageIcon(getClass().getResource("/images/botaovoltar.png"));
        JButton botaoVoltar = new JButton(imagemBotao2);
        botaoVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoVoltar.setBorderPainted(false);
        botaoVoltar.setContentAreaFilled(false);
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.setOpaque(false);
        gbc.insets = new Insets(10, 10, 5, 350);
        gbc.gridy = 6;
        botaoVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarMenuAdministrador();
            }
        });
        add(botaoVoltar, gbc);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        int x = (getWidth() - blocoAdministrar.getWidth(null)) / 2;
        int y = (getHeight() - blocoAdministrar.getHeight(null)) / 2;
        g.drawImage(blocoAdministrar, x, y, blocoAdministrar.getWidth(null), blocoAdministrar.getHeight(null),this);
    }

    public void mostrarAdministrarAluno(){
        Administrar.dispose();

        JFrame frameMenu = new JFrame("Administrar Aluno");
        frameMenu.setSize(1280, 720);
        frameMenu.setMinimumSize(new Dimension(1280, 720));
        frameMenu.setMaximumSize(new Dimension(1920, 1080));
        frameMenu.setLocationRelativeTo(null);
        frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMenu.setResizable(true);
        AdministrarAluno menuAdmin = new AdministrarAluno(frameMenu);
        frameMenu.add(menuAdmin);
        frameMenu.setVisible(true);
    }

    public void mostrarAdministrarProfessor(){
        Administrar.dispose();

        JFrame frameMenu = new JFrame("Administrar Professor");
        frameMenu.setSize(1280, 720);
        frameMenu.setMinimumSize(new Dimension(1280, 720));
        frameMenu.setMaximumSize(new Dimension(1920, 1080));
        frameMenu.setLocationRelativeTo(null);
        frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMenu.setResizable(true);
        AdministrarProfessor menuAdmin = new AdministrarProfessor(frameMenu);
        frameMenu.add(menuAdmin);
        frameMenu.setVisible(true);
    }

    public void mostrarMenuAdministrador(){
        Administrar.dispose();

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


