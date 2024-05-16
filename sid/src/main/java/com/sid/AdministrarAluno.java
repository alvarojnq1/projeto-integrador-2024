package com.sid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.sql.*;

public class AdministrarAluno extends JPanel{
    private JFrame AdministrarAluno;
    private Image imagemDeFundo;
    private Image blocoAdministrarAluno;
    private ConnectionFactory connectionFactory;
    private JTextField nome;
    private JTextField email;

    public AdministrarAluno(JFrame AdministrarAluno){
        this.AdministrarAluno = AdministrarAluno;
        connectionFactory = new ConnectionFactory();
        setLayout(new GridBagLayout());
        carregarImagens();
        configurarComponentes();
    }

    public void carregarImagens(){
        imagemDeFundo = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
        blocoAdministrarAluno = new ImageIcon(getClass().getResource("/images/blocoadministraraluno.png")).getImage();
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

        // Botão "Deletar"
        ImageIcon botao = new ImageIcon(getClass().getResource("/images/botao.png")); // carrega a imagem pro  botao
        JButton botaoDeletar = new JButton("DELETAR", botao); 
        botaoDeletar.setFont(new Font("Arial", Font.BOLD,22));
        botaoDeletar.setForeground(Color.WHITE);
        botaoDeletar.setHorizontalTextPosition(SwingConstants.CENTER); // Centraliza texto horizontalmente sobre a imagem
        botaoDeletar.setVerticalTextPosition(SwingConstants.CENTER); // Centraliza texto verticalmente sobre a imagem
        botaoDeletar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // adiciona o efeito de "mouse sobre o objeto
        botaoDeletar.setBorderPainted(false); // Não pinta a borda
        botaoDeletar.setContentAreaFilled(false); // Não preenche a área do conteúdo
        botaoDeletar.setFocusPainted(false); // Não pinta o foco do botão
        botaoDeletar.setOpaque(false); // Define opacidade como falsa
        gbc.gridx = 0; 
        gbc.gridy = 4; 
        gbc.gridwidth = GridBagConstraints.REMAINDER;  // Ocupa o restante da linha
        gbc.anchor = GridBagConstraints.CENTER;  // Centraliza o componente
        gbc.insets = new Insets(30, 0, 10, 0);  // Ajusta os espaçamentos
        botaoDeletar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Deletar();
            }
        });
        add(botaoDeletar, gbc);

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
                mostrarMenuAdministrar();
            }
        });
        add(botaoVoltar, gbc);
    }

    public void Deletar() {
        // Recuperar os valores digitados
        String nomeAluno = nome.getText().trim();
        String emailAluno = email.getText().trim();
    
        // Validar se ambos os campos estão preenchidos
        if (nomeAluno.isEmpty() || emailAluno.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
            return;
        }
    
        // Converter para uppercase ou lowercase, se necessário
        nomeAluno = nomeAluno.toUpperCase(); // Convertendo para uppercase
        emailAluno = emailAluno.toUpperCase(); // Convertendo para lowercase
    
        // Estabelecer conexão com o banco de dados
        try (Connection conn = connectionFactory.obtemConexao()) {
            // Preparar a instrução SQL para exclusão
            String sql = "DELETE FROM aluno WHERE UPPER(nome_aluno) = ? AND UPPER(email_aluno) = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Definir os parâmetros da instrução SQL
                stmt.setString(1, nomeAluno); 
                stmt.setString(2, emailAluno);
    
                // Executar a instrução SQL de exclusão
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Aluno deletado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(this, "Nenhum aluno encontrado com o nome e email fornecidos.");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao deletar aluno: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    

    public void mostrarMenuAdministrar(){
        AdministrarAluno.dispose();
        JFrame frameMenu = new JFrame("Administrar");
        frameMenu.setSize(1280, 720);
        frameMenu.setMinimumSize(new Dimension(1280, 720));
        frameMenu.setMaximumSize(new Dimension(1920, 1080));
        frameMenu.setLocationRelativeTo(null);
        frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMenu.setResizable(true);
        Administrar administrar = new Administrar(frameMenu);
        frameMenu.add(administrar);
        frameMenu.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        int x = (getWidth() - blocoAdministrarAluno.getWidth(null)) / 2;
        int y = (getHeight() - blocoAdministrarAluno.getHeight(null)) / 2;
        g.drawImage(blocoAdministrarAluno, x, y, blocoAdministrarAluno.getWidth(null), blocoAdministrarAluno.getHeight(null),this);
    }
     
}
