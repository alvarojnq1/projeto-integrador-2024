import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.sql.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

class ConnectionFactory {
    private String usuario = "root";
    private String senha = "Pkloi135!";
    private String host = "localhost";
    private String porta = "3306";
    private String bd = "usuarios";

    public Connection obtemConexao() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + host + ":" + porta + "/" + bd + "?useSSL=false",
                    usuario, senha);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

class ArmazenarToken {
    private static ArmazenarToken instance;
    private String tokenGerado;

    public ArmazenarToken() {}

    public static synchronized ArmazenarToken getInstance() {
        if (instance == null) {
            instance = new ArmazenarToken();
        }
        return instance;
    }

    public String gerarToken() {
        Random random = new Random();
        int token = 100000 + random.nextInt(900000);
        tokenGerado = String.valueOf(token);
        return tokenGerado;
    }

    public boolean verificarToken(String tokenDigitado) {
        if (tokenGerado == null) {
            return false;
        }
        return tokenGerado.equals(tokenDigitado);
    }
}

public class EsqueceuSenha extends JPanel {
    private Image imagemDeFundo;
    private Image blocoEsqueciSenha;
    private JTextField email;
    private JFrame esqueceuSenha;

    public EsqueceuSenha(JFrame esqueceuSenha) {
        this.esqueceuSenha = esqueceuSenha;
        setLayout(new GridBagLayout());
        carregarImagens();
        configurarComponentes();
    }

    private void carregarImagens() {
        try {
            imagemDeFundo = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
            blocoEsqueciSenha = new ImageIcon(getClass().getResource("/images/blocoesquecisenha.png")).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configurarComponentes() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        JLabel textoExibicao = new JLabel("<html><div style='text-align: center;'>Digite o seu e-mail no campo abaixo e lhe enviaremos<br>um código para redefinir sua senha</div></html>");
        textoExibicao.setFont(new Font("Arial", Font.BOLD, 15));
        textoExibicao.setHorizontalAlignment(SwingConstants.CENTER);
        textoExibicao.setVerticalAlignment(SwingConstants.CENTER);
        gbc.insets = new Insets(10, 0, 25, 0);
        gbc.gridy = 0;
        add(textoExibicao, gbc);

        email = new ImageBackgroundTextField("./images/campoemail.png", "./images/iconeemail.png", 0);
        email.setPreferredSize(new Dimension(461, 60));
        email.setBorder(null);
        email.setHorizontalAlignment(JTextField.CENTER);
        email.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.insets = new Insets(10, 11, 10, 9);
        gbc.gridy = 1;
        add(email, gbc);

        ImageIcon botao2 = new ImageIcon(getClass().getResource("/images/botao.png"));
        JButton botaoGerarToken = new JButton("GERAR TOKEN", botao2);
        botaoGerarToken.setFont(new Font("Open Sans", Font.BOLD,22));
        botaoGerarToken.setForeground(Color.WHITE);
        botaoGerarToken.setHorizontalTextPosition(SwingConstants.CENTER); // Centraliza texto horizontalmente sobre a imagem
        botaoGerarToken.setVerticalTextPosition(SwingConstants.CENTER); // Centraliza texto verticalmente sobre a imagem
        botaoGerarToken.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoGerarToken.setBorderPainted(false);
        botaoGerarToken.setContentAreaFilled(false);
        botaoGerarToken.setFocusPainted(false);
        botaoGerarToken.setOpaque(false);
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridy = 2;
        botaoGerarToken.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String emailDigitado = email.getText();
                if (emailCadastrado(emailDigitado)) {
                    ArmazenarToken armazenarToken = new ArmazenarToken();  // Cria uma instância da nova classe
                    String token = armazenarToken.gerarToken();  // Chama o método para gerar o token
                    enviarEmailComToken(emailDigitado, token);
                } else {
                    JOptionPane.showMessageDialog(esqueceuSenha, "Email não cadastrado!", "Erro",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(botaoGerarToken, gbc);

        ImageIcon botao3 = new ImageIcon(getClass().getResource("./images/botaovoltar.png"));
        JButton botaoVoltar = new JButton("", botao3);
        botaoVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoVoltar.setBorderPainted(false);
        botaoVoltar.setContentAreaFilled(false);
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.setOpaque(false);
        gbc.insets = new Insets(5, 10, 5, 400);
        gbc.gridy = 3;
        botaoVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarTelaLogin();
            }
        });
        add(botaoVoltar, gbc);

    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        int x = (getWidth() - blocoEsqueciSenha.getWidth(null)) / 2;
        int y = (getHeight() - blocoEsqueciSenha.getHeight(null)) / 2;
        g.drawImage(blocoEsqueciSenha, x, y, blocoEsqueciSenha.getWidth(null), blocoEsqueciSenha.getHeight(null),this);
    }

    private void mostrarTelaLogin() {
        // método para voltar a tela anterior
        esqueceuSenha.dispose(); // Fecha a janela atual

        // Criando um novo JFrame para a tela de login
        JFrame frameTelaLogin = new JFrame("Login");
        frameTelaLogin.setSize(1280, 720);
        frameTelaLogin.setMinimumSize(new Dimension(1280, 720));
        frameTelaLogin.setMaximumSize(new Dimension(1920, 1080));
        frameTelaLogin.setLocationRelativeTo(null);
        frameTelaLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameTelaLogin.setResizable(true);

        // Criando a tela de login e configurando-a como o conteúdo do JFrame
        TelaLogin telaLogin = new TelaLogin(frameTelaLogin);
        frameTelaLogin.setContentPane(telaLogin);
        frameTelaLogin.setVisible(true);
    }

    public boolean emailCadastrado(String email) {
        String query = "SELECT COUNT(*) FROM usuarios WHERE loginUsuario = ?";
        try (Connection conn = new ConnectionFactory().obtemConexao();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Retorna true se o email está cadastrado
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false se não encontrou o email ou houve erro
    }

    public void enviarEmailComToken(String emailDestinatario, String token) {
        Session session = criarSessionEmail();
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("alvarojnq111@gmail.com", "No-Reply"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestinatario));
            message.setSubject("Redefinição de senha");
            message.setText("Seu código para redefinição de senha é: " + token);
    
            Transport.send(message);
            JOptionPane.showMessageDialog(esqueceuSenha, "Email enviado com sucesso!", "Sucesso!",JOptionPane.INFORMATION_MESSAGE);
            mostrarTelaRedefinirSenha();

        } catch (MessagingException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(esqueceuSenha, "Falha ao enviar email.", "Erro",JOptionPane.ERROR_MESSAGE);
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(esqueceuSenha, "Insira um email válido.", "Erro",JOptionPane.ERROR_MESSAGE);

        }
    }

    public Session criarSessionEmail() {
        // Define as propriedades para a sessão SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
        props.put("mail.smtp.socketFactory.port", "465"); // Porta SSL
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true"); // Ativa autenticação
        props.put("mail.smtp.port", "465"); // Porta do servidor SMTP
    
        // Retorna uma sessão com autenticação configurada
        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("alvarojnq111@gmail.com", "nhfo yukr pffu pmqy");
            }
        });
    }

    private void mostrarTelaRedefinirSenha() {
        esqueceuSenha.dispose(); // Fecha a janela atual

        // Criando um novo JFrame para a tela de login
        JFrame frameRedefinirSenha = new JFrame("Redefinir Senha");
        frameRedefinirSenha.setSize(1280, 720);
        frameRedefinirSenha.setMinimumSize(new Dimension(1920, 1080));
        frameRedefinirSenha.setMaximumSize(new Dimension(1920, 1080));
        frameRedefinirSenha.setLocationRelativeTo(null);
        frameRedefinirSenha.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameRedefinirSenha.setResizable(true);

        // Criando a tela de login e configurando-a como o conteúdo do JFrame
        RedefinirSenha redefinirSenha = new RedefinirSenha(frameRedefinirSenha);
        frameRedefinirSenha.setContentPane(redefinirSenha);
        frameRedefinirSenha.setVisible(true);
    }
}