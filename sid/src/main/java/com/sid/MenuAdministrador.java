package com.sid;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;

public class MenuAdministrador extends JPanel {
    private JFrame MenuAdministrador;
    private Image imagemDeFundo;
    private Image blocoMenu;
   

    public MenuAdministrador(JFrame menuAdministrador) {
        this.MenuAdministrador = menuAdministrador;
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
        JButton botaoCadastrar = new JButton("CADASTRAR USUÁRIO", imagemBotao);
        botaoCadastrar.setFont(new Font("Open Sans", Font.BOLD, 22));
        botaoCadastrar.setForeground(Color.WHITE);
        botaoCadastrar.setHorizontalTextPosition(SwingConstants.CENTER); // Centraliza texto horizontalmente sobre a imagem
        botaoCadastrar.setVerticalTextPosition(SwingConstants.CENTER); // Centraliza texto verticalmente sobre a imagem
        botaoCadastrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // adiciona o efeito de "mouse sobre o objeto
        botaoCadastrar.setBorderPainted(false); // Não pinta a borda
        botaoCadastrar.setContentAreaFilled(false); // Não preenche a área do conteúdo
        botaoCadastrar.setFocusPainted(false); // Não pinta o foco do botão
        botaoCadastrar.setOpaque(false); // Define opacidade como falsa
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridy = 0;
        botaoCadastrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarCadastrar();
            }
        });
        add(botaoCadastrar, gbc);

        // Botão "ranking"
        JButton botaoAdministrar = new JButton("ADMINISTRAR USUÁRIO", imagemBotao);
        botaoAdministrar.setFont(new Font("Open Sans", Font.BOLD, 22));
        botaoAdministrar.setForeground(Color.WHITE);
        botaoAdministrar.setHorizontalTextPosition(SwingConstants.CENTER); // Centraliza texto horizontalmente sobre a imagem
        botaoAdministrar.setVerticalTextPosition(SwingConstants.CENTER); // Centraliza texto verticalmente sobre a imagem
        botaoAdministrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // adiciona o efeito de "mouse sobre o objeto
        botaoAdministrar.setBorderPainted(false); // Não pinta a borda
        botaoAdministrar.setContentAreaFilled(false); // Não preenche a área do conteúdo
        botaoAdministrar.setFocusPainted(false); // Não pinta o foco do botão
        botaoAdministrar.setOpaque(false); // Define opacidade como falsa
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.gridy = 1;
        botaoAdministrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarAdministrar();
            }
        });
        add(botaoAdministrar, gbc);

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

    public void mostrarCadastrar() {
        MenuAdministrador.dispose();

        JFrame frameMenu = new JFrame("Cadastrar");
        frameMenu.setSize(1280, 720);
        frameMenu.setMinimumSize(new Dimension(1280, 720));
        frameMenu.setMaximumSize(new Dimension(1920, 1080));
        frameMenu.setLocationRelativeTo(null);
        frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMenu.setResizable(true);
        Cadastrar cadastrar = new Cadastrar(frameMenu);
        frameMenu.add(cadastrar);
        frameMenu.setVisible(true);    }

    public void mostrarAdministrar() {
        MenuAdministrador.dispose();

        JFrame frameConfiguracoes = new JFrame("Administrar");
        frameConfiguracoes.setSize(1280, 720);
        frameConfiguracoes.setMinimumSize(new Dimension(1280, 720));
        frameConfiguracoes.setMaximumSize(new Dimension(1920, 1080));
        frameConfiguracoes.setLocationRelativeTo(null);
        frameConfiguracoes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameConfiguracoes.setResizable(true);
        
        Administrar administrar = new Administrar(frameConfiguracoes);
        frameConfiguracoes.add(administrar);
        frameConfiguracoes.setVisible(true);    }

    public void mostrarTelaConfiguracoes(){
        MenuAdministrador.dispose();

        JFrame frameConfiguracoes = new JFrame("Configurações");
        frameConfiguracoes.setSize(1280, 720);
        frameConfiguracoes.setMinimumSize(new Dimension(1280, 720));
        frameConfiguracoes.setMaximumSize(new Dimension(1920, 1080));
        frameConfiguracoes.setLocationRelativeTo(null);
        frameConfiguracoes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameConfiguracoes.setResizable(true);
        
        Configuracoes menuConfiguracoes = new Configuracoes(frameConfiguracoes);
        frameConfiguracoes.add(menuConfiguracoes);
        frameConfiguracoes.setVisible(true);
    }
}
