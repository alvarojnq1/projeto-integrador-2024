import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.util.Arrays;
import java.sql.*;

class ConnectionFactory {
    private String usuario = "root";
    private String senha = "Pkloi135!";
    private String host = "localhost";
    private String porta = "3306";
    private String bd = "usuarios";

    public Connection obtemConexao() {
        try {
            Connection c = DriverManager.getConnection("jdbc:mysql://" + host + ":" + porta + "/" + bd,usuario,senha);
            return c;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

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

    public TelaLogin(JFrame frameLogin) {
        this.frameLogin = frameLogin;
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

        login = new ImageBackgroundTextField("./images/campoemail.png", "./images/iconeemail.png", 0);
        login.setPreferredSize(new Dimension(400, 60));
        login.setBorder(null);
        login.setHorizontalAlignment(JTextField.CENTER);
        login.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.insets = new Insets(30, 10, 5, 10);
        gbc.gridy = 1;
        add(login, gbc);

        senha = new ImageBackgroundPasswordField("./images/campoemail.png", "./images/iconesenha.png","./images/versenha.png", 0);
        senha.setPreferredSize(new Dimension(400, 60));
        senha.setBorder(null);
        senha.setHorizontalAlignment(JTextField.CENTER);
        senha.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.insets = new Insets(7, 10, 5, 10);
        gbc.gridy = 2;

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
        gbc.insets = new Insets(5, 260, 5, 0); // Centraliza o componente
        gbc.gridy = 3; // Coloca na terceira linha do layout
        add(esqueceuSenha, gbc);

        // Botão "Entrar"
        ImageIcon botao = new ImageIcon(getClass().getResource("/images/botaoentrar.png")); // carrega a imagem pro  botao
        JButton botaoEntrar = new JButton("", botao); // empty string para o botao assumir a forma da imagem
        botaoEntrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // adiciona o efeito de "mouse sobre o objeto
        botaoEntrar.setBorderPainted(false); // Não pinta a borda
        botaoEntrar.setContentAreaFilled(false); // Não preenche a área do conteúdo
        botaoEntrar.setFocusPainted(false); // Não pinta o foco do botão
        botaoEntrar.setOpaque(false); // Define opacidade como falsa
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridy = 4;
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

    private void processarLogin() {
        String username = login.getText();
        char[] password = senha.getPassword();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = new ConnectionFactory().obtemConexao();
            String sql = "SELECT * FROM usuarios WHERE loginUsuario = ? AND senhaUsuario = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, new String(password));
            rs = stmt.executeQuery();
    
            if (rs.next()) {
                System.out.println("Login successful!");
            } else {
                JOptionPane.showMessageDialog(frameLogin, "Email ou senha incorretos. Tente novamente.");
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        int x = (getWidth() - blocoLogin.getWidth(null)) / 2;
        int y = (getHeight() - blocoLogin.getHeight(null)) / 2;
        g.drawImage(blocoLogin, x, y, blocoLogin.getWidth(null), blocoLogin.getHeight(null), this);
    }

}
