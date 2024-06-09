package com.sid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.util.Arrays;
import java.sql.*;

// Classe para criar um campo de texto com uma imagem de fundo
class ImageBackgroundTextField extends JTextField {
    private Image backgroundImage;
    private Icon iconeEmail;

    // Construtor que recebe o caminho da imagem de fundo, ícone e a quantidade de colunas
    public ImageBackgroundTextField(String imagePath, String iconPath, int columns) {
        super(columns);
        setOpaque(false); // Torna o campo de texto transparente
        try {
            backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
            iconeEmail = new ImageIcon(getClass().getResource(iconPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para desenhar o componente com a imagem de fundo e ícone
    @Override
    protected void paintComponent(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        super.paintComponent(g);
        if (iconeEmail != null) {
            int iconHeight = iconeEmail.getIconHeight();
            int x = 20; // Espaço entre a borda do campo e o ícone
            int y = (this.getHeight() - iconHeight) / 2; // Centraliza o ícone verticalmente
            iconeEmail.paintIcon(this, g, x, y);
        }
    }
}

// Classe para criar um campo de senha com uma imagem de fundo
class ImageBackgroundPasswordField extends JPasswordField {
    private Image backgroundImage;
    private Icon iconeSenha;
    private JButton verSenha;

    // Construtor que recebe o caminho da imagem de fundo, ícone, ícone do botão e a quantidade de colunas
    public ImageBackgroundPasswordField(String imagePath, String iconPath, String buttonIconPath, int columns) {
        super(columns);
        setOpaque(false); // Torna o campo de senha transparente
        try {
            backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
            iconeSenha = new ImageIcon(getClass().getResource(iconPath));
            verSenha = new JButton(new ImageIcon(getClass().getResource(buttonIconPath)));
            verSenha.setBorderPainted(false);
            verSenha.setContentAreaFilled(false);
            verSenha.setFocusPainted(false);
            verSenha.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            verSenha.addActionListener(e -> verSenha());
            setLayout(new BorderLayout());
            add(verSenha, BorderLayout.EAST); // Posiciona o botão à direita
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para alternar entre mostrar e esconder a senha
    private void verSenha() {
        setEchoChar(getEchoChar() == '\u0000' ? '•' : '\u0000');
    }

    // Método para desenhar o componente com a imagem de fundo e ícone
    @Override
    protected void paintComponent(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        super.paintComponent(g);
        if (iconeSenha != null) {
            int iconHeight = iconeSenha.getIconHeight();
            int x = 20;
            int y = (getHeight() - iconHeight) / 2;
            iconeSenha.paintIcon(this, g, x, y);
        }
    }
}

// Classe principal que representa a tela de login
public class TelaLogin extends JPanel {
    private Image imagemDeFundo;
    private Image blocoLogin;
    private JTextField login;
    private JPasswordField senha;
    private JFrame frameLogin;
    public static String tipoUsuario;
    private ConnectionFactory connectionFactory;

    // Construtor que inicializa o frame e configura a tela de login
    public TelaLogin(JFrame frameLogin) {
        this.frameLogin = frameLogin;
        connectionFactory = new ConnectionFactory();
        setLayout(new GridBagLayout());
        carregarImagens();
        configurarComponentes();
    }

    // Método para carregar as imagens de fundo
    private void carregarImagens() {
        try {
            imagemDeFundo = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
            blocoLogin = new ImageIcon(getClass().getResource("/images/blocologin.png")).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para configurar os componentes da tela
    private void configurarComponentes() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Faz com que o componente ocupe toda a linha
        gbc.fill = GridBagConstraints.HORIZONTAL; // Faz com que o componente expanda horizontalmente
        gbc.insets = new Insets(5, 0, 5, 0); // Espaçamento padrão

        // Campo de texto para login
        login = new ImageBackgroundTextField("/images/campoemail.png", "/images/iconeemail.png", 0);
        login.setPreferredSize(new Dimension(400, 60));
        login.setBorder(null);
        login.setHorizontalAlignment(JTextField.CENTER);
        login.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(50, 100, 5, 100);
        add(login, gbc);

        // Campo de texto para senha
        senha = new ImageBackgroundPasswordField("/images/campoemail.png", "/images/iconesenha.png", "/images/versenha.png", 0);
        senha.setPreferredSize(new Dimension(400, 60));
        senha.setBorder(null);
        senha.setHorizontalAlignment(JTextField.CENTER);
        senha.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 100, 5, 100);
        add(senha, gbc);

        // Adiciona listeners para processar o login quando pressionar Enter
        senha.addActionListener(e -> processarLogin());
        login.addActionListener(e -> processarLogin());

        // Label "Esqueceu sua senha?"
        JLabel esqueceuSenha = new JLabel("Esqueceu sua senha?");
        esqueceuSenha.setFont(new Font("Arial", Font.BOLD, 15));
        esqueceuSenha.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Muda o cursor para indicar que é clicável
        esqueceuSenha.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirEsqueceuSenha();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                esqueceuSenha.setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                esqueceuSenha.setForeground(Color.BLACK);
            }
        });
        gbc.insets = new Insets(5, 330, 5, 0); // Centraliza o componente
        gbc.gridy = 3;
        add(esqueceuSenha, gbc);

        // Botão "Entrar"
        ImageIcon botao = new ImageIcon(getClass().getResource("/images/botao.png")); // Carrega a imagem para o botão
        JButton botaoEntrar = new JButton("ENTRAR", botao);
        botaoEntrar.setFont(new Font("Open Sans", Font.BOLD, 22));
        botaoEntrar.setForeground(Color.WHITE);
        botaoEntrar.setHorizontalTextPosition(SwingConstants.CENTER); // Centraliza o texto horizontalmente sobre a imagem
        botaoEntrar.setVerticalTextPosition(SwingConstants.CENTER); // Centraliza o texto verticalmente sobre a imagem
        botaoEntrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Adiciona o efeito de "mouse sobre o objeto"
        botaoEntrar.setBorderPainted(false); // Não pinta a borda
        botaoEntrar.setContentAreaFilled(false); // Não preenche a área do conteúdo
        botaoEntrar.setFocusPainted(false); // Não pinta o foco do botão
        botaoEntrar.setOpaque(false); // Define opacidade como falsa
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 0, 10, 0);
        botaoEntrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                processarLogin();
            }
        });
        add(botaoEntrar, gbc);
    }

    // Método para abrir a tela de "Esqueceu sua senha?"
    private void abrirEsqueceuSenha() {
        frameLogin.dispose();
        // Lógica para abrir a tela de "Esqueceu sua senha?"
        JFrame frameEsqueceuSenha = new JFrame("Esqueci a senha");
        frameEsqueceuSenha.setSize(1280, 720);
        frameEsqueceuSenha.setMinimumSize(new Dimension(1280, 720));
        frameEsqueceuSenha.setMaximumSize(new Dimension(1920, 1080));
        frameEsqueceuSenha.setLocationRelativeTo(null);
        frameEsqueceuSenha.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameEsqueceuSenha.setResizable(true);
        EsqueceuSenha telaEsqueceuSenha = new EsqueceuSenha(frameEsqueceuSenha);
        frameEsqueceuSenha.add(telaEsqueceuSenha);
        frameEsqueceuSenha.setVisible(true);
    }

    // Método para abrir o menu do aluno
    private void mostrarMenuAluno() {
        frameLogin.dispose();
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

    // Método para abrir o menu do professor
    private void mostrarMenuProfessor() {
        frameLogin.dispose();
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

    // Método para abrir o menu do administrador
    private void mostrarMenuAdministrador() {
        frameLogin.dispose();
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

    // Método para processar o login
    private void processarLogin() {
        String username = login.getText();
        char[] password = senha.getPassword();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = new ConnectionFactory().obtemConexao();

            // Verifica na tabela de alunos
            if (verificarUsuario(connection, "SELECT * FROM aluno WHERE email_aluno = ? AND senha_aluno = ?", username, new String(password))) {
                tipoUsuario = "aluno";
                mostrarMenuAluno();
                return;
            }

            // Verifica na tabela de administradores
            if (verificarUsuario(connection, "SELECT * FROM adm WHERE email_adm = ? AND senha_adm = ?", username, new String(password))) {
                tipoUsuario = "adm";
                mostrarMenuAdministrador();
                return;
            }

            // Verifica na tabela de professores
            if (verificarUsuario(connection, "SELECT * FROM professores WHERE email_professor = ? AND senha_professor = ?", username, new String(password))) {
                tipoUsuario = "professor";
                mostrarMenuProfessor();
                return;
            }

            // Se nenhuma das tabelas retornou true, usuário/senha estão incorretos
            JOptionPane.showMessageDialog(frameLogin, "Email ou senha incorretos. Tente novamente.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frameLogin, "Erro ao conectar ao banco de dados.", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Arrays.fill(password, '0'); // Boa prática para segurança
    }

    // Método para verificar se o usuário e senha estão corretos
    private boolean verificarUsuario(Connection connection, String sql, String username, String password) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            return rs.next();
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
    }

    // Método para desenhar a tela com a imagem de fundo e o bloco de login
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        int x = (getWidth() - blocoLogin.getWidth(null)) / 2;
        int y = (getHeight() - blocoLogin.getHeight(null)) / 2;
        g.drawImage(blocoLogin, x, y, blocoLogin.getWidth(null), blocoLogin.getHeight(null), this);
    }
}
