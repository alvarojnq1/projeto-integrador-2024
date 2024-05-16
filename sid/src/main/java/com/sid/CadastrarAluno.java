package com.sid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.sql.*;


public class CadastrarAluno extends JPanel {
    private JFrame CadastrarAluno;
    private Image imagemDeFundo;
    private Image blocoCadastroAluno;
    private ConnectionFactory connectionFactory;
    private JTextField nome;
    private JTextField email;
    private JPasswordField senha;


    public CadastrarAluno(JFrame CadastrarAluno){
        this.CadastrarAluno = CadastrarAluno;
        connectionFactory = new ConnectionFactory();
        setLayout(new GridBagLayout());
        carregarImagens();
        configurarComponentes();
    }

    public void carregarImagens(){
        imagemDeFundo = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
        blocoCadastroAluno = new ImageIcon(getClass().getResource("/images/blococadastroaluno.png")).getImage();
    }

    public void configurarComponentes(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Faz com que o componente ocupe toda a linha
        gbc.fill = GridBagConstraints.HORIZONTAL; // Faz com que o componente expanda horizontalmente
        gbc.insets = new Insets(5, 0, 5, 0); // Espaçamento padrão

        nome = new ImageBackgroundTextField("/images/campoemail.png", "/images/iconenome.png", 0);
        nome.setPreferredSize(new Dimension(400, 60));
        nome.setBorder(null);
        nome.setHorizontalAlignment(JTextField.CENTER);
        nome.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx = 0;  
        gbc.gridy = 1;  
        gbc.gridwidth = GridBagConstraints.REMAINDER;  // Ocupa o restante da linha
        gbc.anchor = GridBagConstraints.CENTER;  // Centraliza o componente
        gbc.insets = new Insets(40, 100, 5, 100);  // Ajusta os espaçamentos
        add(nome, gbc);

        email = new ImageBackgroundTextField("/images/campoemail.png", "/images/iconeemail.png", 0);
        email.setPreferredSize(new Dimension(400, 60));
        email.setBorder(null);
        email.setHorizontalAlignment(JTextField.CENTER);
        email.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx = 0;  
        gbc.gridy = 2;  
        gbc.gridwidth = GridBagConstraints.REMAINDER;  // Ocupa o restante da linha
        gbc.anchor = GridBagConstraints.CENTER;  // Centraliza o componente
        gbc.insets = new Insets(20, 100, 5, 100);  // Ajusta os espaçamentos
        add(email, gbc);

        senha = new ImageBackgroundPasswordField("/images/campoemail.png", "/images/iconesenha.png","/images/versenha.png", 0);
        senha.setPreferredSize(new Dimension(400, 60));
        senha.setBorder(null);
        senha.setHorizontalAlignment(JTextField.CENTER);
        senha.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx = 0;  
        gbc.gridy = 3;  
        gbc.gridwidth = GridBagConstraints.REMAINDER;  // Ocupa o restante da linha
        gbc.anchor = GridBagConstraints.CENTER;  // Centraliza o componente
        gbc.insets = new Insets(20, 100, 5, 100);  // Ajusta os espaçamentos
        add(senha, gbc);

        // Botão "Cadastrar"
        ImageIcon botao = new ImageIcon(getClass().getResource("/images/botao.png")); // carrega a imagem pro  botao
        JButton botaoEntrar = new JButton("CADASTRAR", botao); 
        botaoEntrar.setFont(new Font("Arial", Font.BOLD,22));
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
        gbc.insets = new Insets(20, 0, 10, 0);  // Ajusta os espaçamentos
        botaoEntrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cadastrar();
            }
        });
        add(botaoEntrar, gbc);

        ImageIcon imagemBotao2 = new ImageIcon(getClass().getResource("/images/botaovoltar.png"));
        JButton botaoVoltar = new JButton(imagemBotao2);
        botaoVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoVoltar.setBorderPainted(false);
        botaoVoltar.setContentAreaFilled(false);
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.setOpaque(false);
        gbc.insets = new Insets(5, 10, 0, 350);
        gbc.gridy = 6;
        botaoVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarMenuCadastrar();
            }
        });
        add(botaoVoltar, gbc);
    }

    public void mostrarMenuCadastrar(){
        CadastrarAluno.dispose();
        JFrame frameMenu = new JFrame("Cadastro");
        frameMenu.setSize(1280, 720);
        frameMenu.setMinimumSize(new Dimension(1280, 720));
        frameMenu.setMaximumSize(new Dimension(1920, 1080));
        frameMenu.setLocationRelativeTo(null);
        frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMenu.setResizable(true);
        Cadastrar cadastrar = new Cadastrar(frameMenu);
        frameMenu.add(cadastrar);
        frameMenu.setVisible(true);
    }

    public void cadastrar() {
        // Recuperar os valores digitados
        String nomeAluno = nome.getText();
        String emailAluno = email.getText();
        String senhaAluno = new String(senha.getPassword()); // Recuperando a senha como uma string
    
        // Validar o email
        if (!ValidaEmail.validar(emailAluno)) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um email válido.");
            return; // Não continua com o cadastro se o email não for válido
        }
    
        // Verificar se o email já existe no banco de dados
        try {
            // Verificar se o email já existe no banco de dados
            if (emailJaCadastrado(emailAluno)) {
                JOptionPane.showMessageDialog(this, "Este email já está cadastrado.");
                return; // Não continua com o cadastro se o email já estiver cadastrado
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao verificar o email no banco de dados: " + ex.getMessage());
            ex.printStackTrace();
            return; // Não continua com o cadastro se ocorrer um erro ao verificar o email no banco de dados
        }
    
        // Estabelecer conexão com o banco de dados
        try (Connection conn = connectionFactory.obtemConexao()) {
            // Preparar a instrução SQL para inserção
            String sql = "INSERT INTO aluno (nome_aluno, email_aluno, senha_aluno) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Definir os parâmetros da instrução SQL
                stmt.setString(1, nomeAluno);
                stmt.setString(2, emailAluno);
                stmt.setString(3, senhaAluno);
    
                // Executar a instrução SQL de inserção
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(this, "Falha ao cadastrar aluno.");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar aluno: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private boolean emailJaCadastrado(String email) throws SQLException {
        // Estabelecer conexão com o banco de dados
        try (Connection conn = connectionFactory.obtemConexao()) {
            // Preparar a instrução SQL para verificar se o email já está cadastrado
            String sql = "SELECT COUNT(*) FROM aluno WHERE email_aluno = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email);
                // Executar a consulta SQL
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        return count > 0; // Retorna true se o email já estiver cadastrado
                    }
                }
            }
        }
        return false; // Retorna false se ocorrer algum erro na consulta
    }
    
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        int x = (getWidth() - blocoCadastroAluno.getWidth(null)) / 2;
        int y = (getHeight() - blocoCadastroAluno.getHeight(null)) / 2;
        g.drawImage(blocoCadastroAluno, x, y, blocoCadastroAluno.getWidth(null), blocoCadastroAluno.getHeight(null),this);
    }
}

class ValidaEmail {
    public static boolean validar(String email) {
        // Verifica se o email é nulo ou vazio
        if (email == null || email.isEmpty()) {
            return false;
        }

        // Verifica se o email contém '@' e '.'
        int atIndex = email.indexOf('@');
        int dotIndex = email.lastIndexOf('.');

        // Verifica se '@' e '.' estão presentes e se '@' ocorre antes de '.'
        if (atIndex < 0 || dotIndex < 0 || atIndex >= dotIndex) {
            return false;
        }

        // Verifica se há caracteres antes e depois de '@' e '.'
        if (atIndex == 0 || dotIndex == email.length() - 1) {
            return false;
        }

        // Verifica se há mais de um '.' após o '@'
        String domain = email.substring(atIndex + 1);
        if (domain.indexOf('.') == -1) {
            return false;
        }

        return true;
    }
}
