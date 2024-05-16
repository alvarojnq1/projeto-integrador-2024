package com.sid;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;

public class Cadastrar extends JPanel {
    private JFrame Cadastrar;
    private Image imagemDeFundo;
    private Image blocoCadastrar;


    public Cadastrar(JFrame cadastrar){
        this.Cadastrar = cadastrar;
        setLayout(new GridBagLayout());
        carregarImagens();
        configurarComponentes();
    }

    public void carregarImagens(){
        imagemDeFundo = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
        blocoCadastrar = new ImageIcon(getClass().getResource("/images/blococadastrar.png")).getImage();
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
                mostrarCadastrarProfessor();
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
                mostrarCadastrarAluno();
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
        int x = (getWidth() - blocoCadastrar.getWidth(null)) / 2;
        int y = (getHeight() - blocoCadastrar.getHeight(null)) / 2;
        g.drawImage(blocoCadastrar, x, y, blocoCadastrar.getWidth(null), blocoCadastrar.getHeight(null),this);
    }

    public void mostrarCadastrarAluno(){
        Cadastrar.dispose();

        JFrame frameMenu = new JFrame("Cadastrar Aluno");
        frameMenu.setSize(1280, 720);
        frameMenu.setMinimumSize(new Dimension(1280, 720));
        frameMenu.setMaximumSize(new Dimension(1920, 1080));
        frameMenu.setLocationRelativeTo(null);
        frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMenu.setResizable(true);
        CadastrarAluno cadastrarAluno = new CadastrarAluno(frameMenu);
        frameMenu.add(cadastrarAluno);
        frameMenu.setVisible(true);
    }

    public void mostrarCadastrarProfessor(){
        Cadastrar.dispose();

        JFrame frameMenu = new JFrame("Cadastrar Professor");
        frameMenu.setSize(1280, 720);
        frameMenu.setMinimumSize(new Dimension(1280, 720));
        frameMenu.setMaximumSize(new Dimension(1920, 1080));
        frameMenu.setLocationRelativeTo(null);
        frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMenu.setResizable(true);
        CadastrarProfessor cadastrarProfessor = new CadastrarProfessor(frameMenu);
        frameMenu.add(cadastrarProfessor);
        frameMenu.setVisible(true);
    }

    public void mostrarMenuAdministrador(){
        Cadastrar.dispose();

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
