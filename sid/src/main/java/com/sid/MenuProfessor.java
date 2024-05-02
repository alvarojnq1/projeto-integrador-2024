package com.sid;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;

public class MenuProfessor extends JPanel{
    private JFrame MenuProfessor;
    private Image imagemDeFundo;
    private Image blocoMenu;

    public MenuProfessor(JFrame menuProfessor) {
        this.MenuProfessor = menuProfessor;
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
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Faz com que o componente ocupe toda a linha
        gbc.fill = GridBagConstraints.HORIZONTAL; // Faz com que o componente expanda horizontalmente
        gbc.insets = new Insets(5, 0, 5, 0); // Espaçamento padrão

        // Botão "administrar"
        ImageIcon imagemBotao = new ImageIcon(getClass().getResource("/images/botao.png")); // carrega a imagem pro botao
        JButton botaoAdministrar = new JButton("ADMINISTRAR PERGUNTAS", imagemBotao);
        botaoAdministrar.setFont(new Font("Open Sans", Font.BOLD, 22));
        botaoAdministrar.setForeground(Color.WHITE);
        botaoAdministrar.setHorizontalTextPosition(SwingConstants.CENTER); // Centraliza texto horizontalmente sobre a imagem
        botaoAdministrar.setVerticalTextPosition(SwingConstants.CENTER); // Centraliza texto verticalmente sobre a imagem
        botaoAdministrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // adiciona o efeito de "mouse sobre o objeto
        botaoAdministrar.setBorderPainted(false); // Não pinta a borda
        botaoAdministrar.setContentAreaFilled(false); // Não preenche a área do conteúdo
        botaoAdministrar.setFocusPainted(false); // Não pinta o foco do botão
        botaoAdministrar.setOpaque(false); // Define opacidade como falsa
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridy = 0;
        botaoAdministrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarTelaAdmin();
            }
        });
        add(botaoAdministrar, gbc);

        // Botão "ranking"
        JButton botaoRanking = new JButton("RANKING", imagemBotao);
        botaoRanking.setFont(new Font("Open Sans", Font.BOLD, 22));
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
        botaoConfiguracoes.setFont(new Font("Open Sans", Font.BOLD, 22));
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

    public void mostrarRanking() {
        // futuramente adicionar o método para mostrar a tela Ranking
    }

    public void mostrarTelaAdmin() {
        // futuramente adicionar o método para mostrar a tela Jogar
    }

    public void mostrarTelaConfiguracoes() {
        // futuramente adicionar o método para mostrar a tela de configs
    }
}
