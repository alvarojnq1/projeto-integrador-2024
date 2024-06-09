package com.sid;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


class ImageBackgroundPasswordField2 extends JPasswordField {
    private Image backgroundImage;
    private Icon iconeSenha;
    private JButton verSenha;

    public ImageBackgroundPasswordField2(String imagePath, String iconPath, String buttonIconPath, int columns) {
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

public class RedefinirSenha extends JPanel{
    private Image imagemDeFundo;
    private Image blocoRedefinirSenha;
    private Image retanguloOpaco;
    private Image iconeOk;
    private JTextField tokenD;
    private JPasswordField senha;
    private JPasswordField senhaConfirmada;
    private JFrame redefinirSenha;
    private String emailDigitado; 
    private ConnectionFactory connectionFactory;
    private String email;
    private String token;
    

    

    public RedefinirSenha(JFrame redefinirSenha, String email, String token) {
        this.token= token;
        this.emailDigitado = emailDigitado;
        this.email = email;
        this.redefinirSenha = redefinirSenha;
        connectionFactory = new ConnectionFactory();
        setLayout(new GridBagLayout());
        carregarImagens();
        configurarComponentes();

    }
    private void carregarImagens() {
        try {
            iconeOk = new ImageIcon(getClass().getResource("/images/okicon.png")).getImage();
            retanguloOpaco = new ImageIcon(getClass().getResource("/images/retanguloopaco.png")).getImage();
            imagemDeFundo = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
            blocoRedefinirSenha = new ImageIcon(getClass().getResource("/images/blocoredefinirsenha.png")).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void configurarComponentes(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        JLabel textoExibicao = new JLabel("<html><div style='text-align: center;'>Enviamos um token para o e-mail cadastrado informado.<br>Insria o código para redefinir sua senha.</div></html>");
        textoExibicao.setFont(new Font("Arial", Font.BOLD, 15));
        textoExibicao.setHorizontalAlignment(SwingConstants.CENTER);
        textoExibicao.setVerticalAlignment(SwingConstants.CENTER);
        gbc.insets = new Insets(70, 0, 25, 0);
        gbc.gridy = 0;
        add(textoExibicao, gbc);

        JLabel textoExibicao2 = new JLabel("<html><div style='text-align: center;'>Por favor, insira no campo abaixo o código de ativação que<br> você recebeu por e-mail, e redefina uma nova senha</div></html>");
        textoExibicao2.setFont(new Font("Arial", Font.BOLD, 15));
        textoExibicao2.setHorizontalAlignment(SwingConstants.CENTER);
        textoExibicao2.setVerticalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;  // Pode usar 0 para começar no início
        gbc.gridy = 1;  // Fica na primeira linha
        gbc.gridwidth = GridBagConstraints.REMAINDER;  // Ocupa o restante da linha
        gbc.anchor = GridBagConstraints.CENTER;  // Centraliza o componente
        gbc.insets = new Insets(20, 100, 5, 100);  // Ajusta os espaçamentos
        add(textoExibicao2, gbc);

        tokenD = new ImageBackgroundTextField("/images/campoemail.png", "/images/iconetoken.png", 0);
        tokenD.setPreferredSize(new Dimension(400, 60));
        tokenD.setBorder(null);
        tokenD.setHorizontalAlignment(JTextField.CENTER);
        tokenD.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx = 0;  // Pode usar 0 para começar no início
        gbc.gridy = 2;  // Fica na primeira linha
        gbc.gridwidth = GridBagConstraints.REMAINDER;  // Ocupa o restante da linha
        gbc.anchor = GridBagConstraints.CENTER;  // Centraliza o componente
        gbc.insets = new Insets(20, 300, 5, 300);  // Ajusta os espaçamentos
        add(tokenD, gbc);

        senha = new ImageBackgroundPasswordField("/images/campoemail.png", "/images/iconesenha.png","/images/versenha.png", 0);
        senha.setPreferredSize(new Dimension(400, 60));
        senha.setBorder(null);
        senha.setHorizontalAlignment(JTextField.CENTER);
        senha.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx = 0;  // Pode usar 0 para começar no início
        gbc.gridy = 3;  // Fica na primeira linha
        gbc.gridwidth = GridBagConstraints.REMAINDER;  // Ocupa o restante da linha
        gbc.anchor = GridBagConstraints.CENTER;  // Centraliza o componente
        gbc.insets = new Insets(20, 300, 5, 300);  // Ajusta os espaçamentos

        add(senha, gbc);

        senhaConfirmada = new ImageBackgroundPasswordField("/images/campoemail.png", "/images/iconeconfirmarsenha.png","/images/versenha.png", 0);
        senhaConfirmada.setPreferredSize(new Dimension(400, 60));
        senhaConfirmada.setBorder(null);
        senhaConfirmada.setHorizontalAlignment(JTextField.CENTER);
        senhaConfirmada.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx = 0;  // Pode usar 0 para começar no início
        gbc.gridy = 4;  // Fica na primeira linha
        gbc.gridwidth = GridBagConstraints.REMAINDER;  // Ocupa o restante da linha
        gbc.anchor = GridBagConstraints.CENTER;  // Centraliza o componente
        gbc.insets = new Insets(20, 300, 5, 300);  // Ajusta os espaçamentos

        add(senhaConfirmada, gbc);

        ImageIcon imagemBotaoRedefinir = new ImageIcon(getClass().getResource("/images/botao.png")); // carrega a imagem pro  botao
        JButton botaoRedefinir = new JButton("REDEFINIR SENHA", imagemBotaoRedefinir);
        botaoRedefinir.setFont(new Font("Open Sans", Font.BOLD,22));
        botaoRedefinir.setForeground(Color.WHITE);
        botaoRedefinir.setHorizontalTextPosition(SwingConstants.CENTER); // Centraliza texto horizontalmente sobre a imagem
        botaoRedefinir.setVerticalTextPosition(SwingConstants.CENTER); // Centraliza texto verticalmente sobre a imagem
        botaoRedefinir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // adiciona o efeito de "mouse sobre o objeto
        botaoRedefinir.setBorderPainted(false); // Não pinta a borda
        botaoRedefinir.setContentAreaFilled(false); // Não preenche a área do conteúdo
        botaoRedefinir.setFocusPainted(false); // Não pinta o foco do botão
        botaoRedefinir.setOpaque(false); // Define opacidade como falsa
        gbc.insets = new Insets(20, 0, 5, 0);
        gbc.gridy = 5;
        botaoRedefinir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String tokenDigitado = tokenD.getText();

                // Verificação do token
                if (tokenDigitado.isEmpty()) {
                    JOptionPane.showMessageDialog(redefinirSenha, "Por favor, insira o token.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return; // Encerra a execução do método se o campo estiver vazio
                }
                boolean tokenValido = verificarToken(tokenDigitado);
        
                // Verificação da igualdade das senhas
                String senha1 = new String(senha.getPassword());
                String senha2 = new String(senhaConfirmada.getPassword());
                boolean senhasIguais = senha1.equals(senha2);
                
                // Verificações combinadas e ação de atualização
                if (tokenValido && senhasIguais) {

                    boolean sucesso = redefinirSenha(email, senha1);
                    if (sucesso) {
                        JOptionPane.showMessageDialog(redefinirSenha, "Senha atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        mostrarTelaLogin(); // Fechar a janela de redefinição após o sucesso
                    } else {
                        JOptionPane.showMessageDialog(redefinirSenha, "Erro ao atualizar a senha.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    if (!tokenValido) {
                        JOptionPane.showMessageDialog(redefinirSenha, "Token inválido ou expirado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(redefinirSenha, "As senhas não correspondem.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        add(botaoRedefinir, gbc);

        ImageIcon imagemBotao = new ImageIcon(getClass().getResource("/images/botaovoltar.png"));
        JButton botaoVoltar = new JButton(imagemBotao);
        botaoVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoVoltar.setBorderPainted(false);
        botaoVoltar.setContentAreaFilled(false);
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.setOpaque(false);
        gbc.insets = new Insets(10, 10, 5, 400);
        gbc.gridy = 6;
        botaoVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarTelaEsqueceuSenha();
            }
        });
        add(botaoVoltar, gbc);

    }

    public boolean verificarToken(String tokenDigitado) {
        if (token == null) {
            return false;
        }
        return token.trim().equalsIgnoreCase(tokenDigitado.trim());
    }

    private void mostrarTelaLogin() {
        // método para voltar a tela anterior
        redefinirSenha.dispose(); // Fecha a janela atual

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
    
    private void mostrarTelaEsqueceuSenha(){
        redefinirSenha.dispose(); // Fecha a janela atual

        // Criando um novo JFrame para a tela de Esqueceu Senha
        JFrame frameTelaEsqueceuSenha = new JFrame("Esqueci a senha");
        frameTelaEsqueceuSenha.setSize(1280, 720);
        frameTelaEsqueceuSenha.setMinimumSize(new Dimension(1280, 720));
        frameTelaEsqueceuSenha.setMaximumSize(new Dimension(1920, 1080));
        frameTelaEsqueceuSenha.setLocationRelativeTo(null);
        frameTelaEsqueceuSenha.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameTelaEsqueceuSenha.setResizable(true);

        // Criando a tela de esqueceu senha e configurando como o conteúdo do JFrame
        EsqueceuSenha telaEsqueciASenha = new EsqueceuSenha(frameTelaEsqueceuSenha);
        frameTelaEsqueceuSenha.setContentPane(telaEsqueciASenha);
        frameTelaEsqueceuSenha.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        int x = (getWidth() - blocoRedefinirSenha.getWidth(null)) / 2;
        int y = (getHeight() - blocoRedefinirSenha.getHeight(null)) / 2;
        g.drawImage(blocoRedefinirSenha, x, y, blocoRedefinirSenha.getWidth(null), blocoRedefinirSenha.getHeight(null),this);
        g.drawImage(retanguloOpaco, x+22, y+70, retanguloOpaco.getWidth(null),retanguloOpaco.getHeight(null),this );
        g.drawImage(iconeOk, x+32, y+100, iconeOk.getWidth(null), iconeOk.getHeight(null),this);
    }

    public boolean redefinirSenha(String email, String novaSenha) {
        String queryAluno = "UPDATE aluno SET senha_aluno = ? WHERE email_aluno = ?";
        String queryAdm = "UPDATE adm SET senha_adm = ? WHERE email_adm = ?";
        String queryProfessor = "UPDATE professores SET senha_professor = ? WHERE email_professor = ?";

        try (Connection conn = new ConnectionFactory().obtemConexao()) {
            try (PreparedStatement stmtAluno = conn.prepareStatement(queryAluno);
                 PreparedStatement stmtAdm = conn.prepareStatement(queryAdm);
                 PreparedStatement stmtProfessor = conn.prepareStatement(queryProfessor)) {

                conn.setAutoCommit(false);

                // Update senha for aluno
                stmtAluno.setString(1, novaSenha);
                stmtAluno.setString(2, email);
                int rowsUpdatedAluno = stmtAluno.executeUpdate();

                // Update senha for adm
                stmtAdm.setString(1, novaSenha);
                stmtAdm.setString(2, email);
                int rowsUpdatedAdm = stmtAdm.executeUpdate();

                // Update senha for professor
                stmtProfessor.setString(1, novaSenha);
                stmtProfessor.setString(2, email);
                int rowsUpdatedProfessor = stmtProfessor.executeUpdate();

                conn.commit();
                
                // Return true if any rows were updated
                return (rowsUpdatedAluno > 0 || rowsUpdatedAdm > 0 || rowsUpdatedProfessor > 0);

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

