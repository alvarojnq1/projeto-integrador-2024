package com.sid;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.util.Arrays;
import java.sql.*;


class ImageBackgroundTextField extends JTextField {
    private Image backgroundImage;
    private Icon iconeEmail;

    public ImageBackgroundTextField(String imagePath, String iconPath, int columns) {
        super(columns);
        setOpaque(false);
        try {
            backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
            iconeEmail = new ImageIcon(getClass().getResource(iconPath));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

class ImageBackgroundPasswordField extends JPasswordField {
    private Image backgroundImage;
    private Icon iconeSenha;
    private JButton verSenha;

    public ImageBackgroundPasswordField(String imagePath, String iconPath, String buttonIconPath, int columns) {
        super(columns);
        setOpaque(false); // Torna o componente transparente
        try {
            backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
            iconeSenha = new ImageIcon(getClass().getResource(iconPath));

            verSenha = new JButton(new ImageIcon(getClass().getResource(buttonIconPath)));
            verSenha.setBorderPainted(false);
            verSenha.setContentAreaFilled(false);
            verSenha.setFocusPainted(false);
            verSenha.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            verSenha.addActionListener(e -> verSenha());
            setLayout(new BorderLayout()); // Usando BorderLayout
            add(verSenha, BorderLayout.EAST); // Posiciona o botão à direita*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void verSenha() {
        setEchoChar(getEchoChar() == '\u0000' ? '•' : '\u0000'); // Alterna entre mostrar e esconder a senha
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        super.paintComponent(g); // Agora desenha o componente de senha sobre a imagem de fundo.
        if (iconeSenha != null) {
            int iconHeight = iconeSenha.getIconHeight();
            int x = 20;
            int y = (getHeight() - iconHeight) / 2;
            iconeSenha.paintIcon(this, g, x, y);
        }
    }
}

public class TelaLogin extends JPanel {
    private Image imagemDeFundo;
    private Image blocoLogin;
    private JTextField login;
    private JPasswordField senha;
    private JFrame frameLogin;
    public static String tipoUsuario;
    private ConnectionFactory connectionFactory;
    

    public TelaLogin(JFrame frameLogin) {
        this.frameLogin = frameLogin;
        connectionFactory = new ConnectionFactory();
        setLayout(new GridBagLayout());
        carregarImagens();
        configurarComponentes();
    }

    private void carregarImagens() {
        try {
            imagemDeFundo = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
            blocoLogin = new ImageIcon(getClass().getResource("/images/blocologin.png")).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configurarComponentes() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Faz com que o componente ocupe toda a linha
        gbc.fill = GridBagConstraints.HORIZONTAL; // Faz com que o componente expanda horizontalmente
        gbc.insets = new Insets(5, 0, 5, 0); // Espaçamento padrão

        login = new ImageBackgroundTextField("/images/campoemail.png", "/images/iconeemail.png", 0);
        login.setPreferredSize(new Dimension(400, 60));
        login.setBorder(null);
        login.setHorizontalAlignment(JTextField.CENTER);
        login.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx = 0;  // Pode usar 0 para começar no início
        gbc.gridy = 1;  // Fica na primeira linha
        gbc.gridwidth = GridBagConstraints.REMAINDER;  // Ocupa o restante da linha
        gbc.anchor = GridBagConstraints.CENTER;  // Centraliza o componente
        gbc.insets = new Insets(50, 100, 5, 100);  // Ajusta os espaçamentos
        add(login, gbc);

        senha = new ImageBackgroundPasswordField("/images/campoemail.png", "/images/iconesenha.png","/images/versenha.png", 0);
        senha.setPreferredSize(new Dimension(400, 60));
        senha.setBorder(null);
        senha.setHorizontalAlignment(JTextField.CENTER);
        senha.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx = 0;  // Pode usar 0 para começar no início
        gbc.gridy = 2;  // Fica na primeira linha
        gbc.gridwidth = GridBagConstraints.REMAINDER;  // Ocupa o restante da linha
        gbc.anchor = GridBagConstraints.CENTER;  // Centraliza o componente
        gbc.insets = new Insets(20, 100, 5, 100);  // Ajusta os espaçamentos

        add(senha, gbc);

        senha.addActionListener(e -> processarLogin());
        login.addActionListener(e -> processarLogin());

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
        // Ajustes de posição para o JLabel
        gbc.insets = new Insets(5, 330, 5, 0); // Centraliza o componente
        gbc.gridy = 3; // Coloca na terceira linha do layout
        add(esqueceuSenha, gbc);

        // Botão "Entrar"
        ImageIcon botao = new ImageIcon(getClass().getResource("/images/botao.png")); // carrega a imagem pro  botao
        JButton botaoEntrar = new JButton("ENTRAR", botao); 
        botaoEntrar.setFont(new Font("Open Sans", Font.BOLD,22));
        botaoEntrar.setForeground(Color.WHITE);
        botaoEntrar.setHorizontalTextPosition(SwingConstants.CENTER); // Centraliza texto horizontalmente sobre a imagem
        botaoEntrar.setVerticalTextPosition(SwingConstants.CENTER); // Centraliza texto verticalmente sobre a imagem
        botaoEntrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // adiciona o efeito de "mouse sobre o objeto
        botaoEntrar.setBorderPainted(false); // Não pinta a borda
        botaoEntrar.setContentAreaFilled(false); // Não preenche a área do conteúdo
        botaoEntrar.setFocusPainted(false); // Não pinta o foco do botão
        botaoEntrar.setOpaque(false); // Define opacidade como falsa
        gbc.gridx = 0; 
        gbc.gridy = 4; 
        gbc.gridwidth = GridBagConstraints.REMAINDER;  // Ocupa o restante da linha
        gbc.anchor = GridBagConstraints.CENTER;  // Centraliza o componente
        gbc.insets = new Insets(5, 0, 10, 0);  // Ajusta os espaçamentos
        botaoEntrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                processarLogin();
            }
        });
        add(botaoEntrar, gbc);
    }

    private void abrirEsqueceuSenha() {
        frameLogin.dispose();
        // colocar aqui a logica para abrir a tela esqueceu senha
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

    private void mostrarMenuAluno(){
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

    private void mostrarMenuProfessor(){
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

    private void mostrarMenuAdministrador(){
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
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        int x = (getWidth() - blocoLogin.getWidth(null)) / 2;
        int y = (getHeight() - blocoLogin.getHeight(null)) / 2;
        g.drawImage(blocoLogin, x, y, blocoLogin.getWidth(null), blocoLogin.getHeight(null), this);
    }

}