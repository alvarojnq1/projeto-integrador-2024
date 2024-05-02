package com.sid;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;



public class MenuAluno extends JPanel {
    private JFrame MenuAluno;
    private Image imagemDeFundo;
    private Image blocoMenu;

    public MenuAluno(JFrame menuAluno){
        this.MenuAluno = menuAluno;
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

    private void configurarComponentes(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Faz com que o componente ocupe toda a linha
        gbc.fill = GridBagConstraints.HORIZONTAL; // Faz com que o componente expanda horizontalmente
        gbc.insets = new Insets(5, 0, 5, 0); // Espaçamento padrão

        // Botão "jogar"
        ImageIcon imagemBotao = new ImageIcon(getClass().getResource("/images/botao.png")); // carrega a imagem pro  botao
        JButton botaoJogar = new JButton("JOGAR", imagemBotao); 
        botaoJogar.setFont(new Font("Open Sans", Font.BOLD,22));
        botaoJogar.setForeground(Color.WHITE);
        botaoJogar.setHorizontalTextPosition(SwingConstants.CENTER); // Centraliza texto horizontalmente sobre a imagem
        botaoJogar.setVerticalTextPosition(SwingConstants.CENTER); // Centraliza texto verticalmente sobre a imagem
        botaoJogar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // adiciona o efeito de "mouse sobre o objeto
        botaoJogar.setBorderPainted(false); // Não pinta a borda
        botaoJogar.setContentAreaFilled(false); // Não preenche a área do conteúdo
        botaoJogar.setFocusPainted(false); // Não pinta o foco do botão
        botaoJogar.setOpaque(false); // Define opacidade como falsa
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridy = 0;
        botaoJogar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarTelaJogar();
            }
        });
        add(botaoJogar, gbc);

        // Botão "ranking"
        JButton botaoRanking = new JButton("RANKING", imagemBotao); 
        botaoRanking.setFont(new Font("Open Sans", Font.BOLD,22));
        botaoRanking.setForeground(Color.WHITE);
        botaoRanking.setHorizontalTextPosition(SwingConstants.CENTER); // Centraliza texto horizontalmente sobre a imagem
        botaoRanking.setVerticalTextPosition(SwingConstants.CENTER); // Centraliza texto verticalmente sobre a imagem
        botaoRanking.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // adiciona o efeito de "mouse sobre o objeto
        botaoRanking.setBorderPainted(false); // Não pinta a borda
        botaoRanking.setContentAreaFilled(false); // Não preenche a área do conteúdo
        botaoRanking.setFocusPainted(false); // Não pinta o foco do botão
        botaoRanking.setOpaque(false); // Define opacidade como falsa
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.gridy = 1;
        botaoRanking.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarRanking();
            }
        });
        add(botaoRanking, gbc);

        // Botão "configs"
        JButton botaoConfiguracoes = new JButton("CONFIGURAÇÕES", imagemBotao); 
        botaoConfiguracoes.setFont(new Font("Open Sans", Font.BOLD,22));
        botaoConfiguracoes.setForeground(Color.WHITE);
        botaoConfiguracoes.setHorizontalTextPosition(SwingConstants.CENTER); // Centraliza texto horizontalmente sobre a imagem
        botaoConfiguracoes.setVerticalTextPosition(SwingConstants.CENTER); // Centraliza texto verticalmente sobre a imagem
        botaoConfiguracoes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // adiciona o efeito de "mouse sobre o objeto
        botaoConfiguracoes.setBorderPainted(false); // Não pinta a borda
        botaoConfiguracoes.setContentAreaFilled(false); // Não preenche a área do conteúdo
        botaoConfiguracoes.setFocusPainted(false); // Não pinta o foco do botão
        botaoConfiguracoes.setOpaque(false); // Define opacidade como falsa
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.gridy = 3;
        botaoConfiguracoes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarTelaConfiguracoes();
            }
        });
        add(botaoConfiguracoes, gbc);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        int x = (getWidth() - blocoMenu.getWidth(null)) / 2;
        int y = (getHeight() - blocoMenu.getHeight(null)) / 2;
        g.drawImage(blocoMenu, x, y, blocoMenu.getWidth(null), blocoMenu.getHeight(null), this);
    }

    public void mostrarRanking(){
        //futuramente adicionar o método para mostrar a tela Ranking
    }

    public void mostrarTelaJogar(){
        //futuramente adicionar o método para mostrar a tela Jogar
    }

    public void mostrarTelaConfiguracoes(){
        //futuramente adicionar o método para mostrar a tela de configs
    }
}
