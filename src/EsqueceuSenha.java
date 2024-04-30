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

        JLabel textoExibicao = new JLabel(
                "<html><div style='text-align: center;'>Digite o seu e-mail no campo abaixo e lhe enviaremos<br>um código para redefinir sua senha</div></html>");
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

        ImageIcon botao2 = new ImageIcon(getClass().getResource("/images/botaogerartoken.png"));
        JButton botaoGerarToken = new JButton("", botao2);
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
                String token = gerarToken();
                EnviarEmail sender = new EnviarEmail();
                sender.enviarTokenPorEmail(emailDigitado, token);
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
                voltar();
            }
        });
        add(botaoVoltar, gbc);
    }

    private String gerarToken() {
        Random random = new Random();
        int token = 100000 + random.nextInt(900000);
        return String.valueOf(token);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        int x = (getWidth() - blocoEsqueciSenha.getWidth(null)) / 2;
        int y = (getHeight() - blocoEsqueciSenha.getHeight(null)) / 2;
        g.drawImage(blocoEsqueciSenha, x, y, blocoEsqueciSenha.getWidth(null), blocoEsqueciSenha.getHeight(null),
                this);
    }

    private void voltar() {
        //método para voltar a tela anterior
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
}

class Credentials {
    String email;
    String senha;

    public Credentials(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }
}

class EnviarEmail {

    public Credentials getCredentials() {
        String query = "SELECT loginUsuario, senhaUsuario FROM usuarios WHERE idUsuario = ?"; // Exemplo genérico
        try (Connection conn = new ConnectionFactory().obtemConexao();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new Credentials(rs.getString("loginUsuario"), rs.getString("senhaUsuario"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //public void enviarTokenPorEmail(String destinatarioEmail, String token) {
        // Credentials credentials = getCredentials();

        /*
         * if (credentials == null) {
         * System.out.println("Falha ao obter credenciais");
         * return;
         * }
         */

        //Properties props = new Properties();
        //Session session = Session.getDefaultInstance(props, null);

        //try {
            //Message msg = new MimeMessage(session);
            //msg.setFrom(new InternetAddress("alvarojnq111@gmail.com", "Example.com Admin"));
            //msg.addRecipient(Message.RecipientType.TO,
                    //new InternetAddress("apolityyt@gmail.com", "Mr. User"));
            //msg.setSubject("Your Example.com account has been activated");
            //msg.setText("This is a test");
            //Transport.send(msg);
        //} catch (AddressException e) {
            // ...
        //} catch (MessagingException e) {
            // ...
        //} catch (UnsupportedEncodingException e) {
            // ...
        //}
    //} 
}
