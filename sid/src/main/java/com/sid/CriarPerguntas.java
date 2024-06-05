package com.sid;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CriarPerguntas extends JPanel {
    private JFrame criarPerguntas;
    private Image imagemDeFundo;
    private Image blocoCriarPerguntas;
    private JTextField pergunta;
    private JTextField respostaCerta;
    private JTextField respostaErrada1;
    private JTextField respostaErrada2;
    private JTextField respostaErrada3;
    private JTextField justificativa;
    private JButton criar;
    private ConnectionFactory connectionFactory;

    public CriarPerguntas(JFrame criarPerguntas) {
        this.criarPerguntas = criarPerguntas;
        connectionFactory = new ConnectionFactory();
        setLayout(new BorderLayout());
        carregarImagens();
        configurarComponentes();
    }

    private void carregarImagens() {
        imagemDeFundo = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
        blocoCriarPerguntas = new ImageIcon(getClass().getResource("/images/blococriarperguntas.png")).getImage();
    }

    private void configurarComponentes() {
        JPanel painelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Desenha as imagens de fundo
                g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
                int x = (getWidth() - blocoCriarPerguntas.getWidth(null)) / 2;
                int y = (getHeight() - blocoCriarPerguntas.getHeight(null)) / 2;
                g.drawImage(blocoCriarPerguntas, x, y, blocoCriarPerguntas.getWidth(null),
                        blocoCriarPerguntas.getHeight(null), this);
            }
        };
        painelPrincipal.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Configurar JLabel para a pergunta
        JLabel labelPergunta = new JLabel("Pergunta:");
        painelPrincipal.add(labelPergunta, gbc);

        // Configurar JTextField para a pergunta
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        pergunta = new JTextField(20);
        pergunta.setPreferredSize(new Dimension(500, 60));
        painelPrincipal.add(pergunta, gbc);

        // Configurar JLabel para a primeira resposta errada
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        JLabel labelRespostaErrada1 = new JLabel("Resposta Errada 1:");
        painelPrincipal.add(labelRespostaErrada1, gbc);

        // Configurar JTextField para a primeira resposta errada
        gbc.gridx = 2;
        gbc.gridy = 1;
        respostaErrada1 = new JTextField(20);
        respostaErrada1.setPreferredSize(new Dimension(500, 60));
        painelPrincipal.add(respostaErrada1, gbc);

        // Configurar JLabel para a segunda resposta errada
        gbc.gridx = 3;
        gbc.gridy = 0;
        JLabel labelRespostaErrada2 = new JLabel("Resposta Errada 2:");
        painelPrincipal.add(labelRespostaErrada2, gbc);

        // Configurar JTextField para a segunda resposta errada
        gbc.gridx = 3;
        gbc.gridy = 1;
        respostaErrada2 = new JTextField(20);
        respostaErrada2.setPreferredSize(new Dimension(500, 60));
        painelPrincipal.add(respostaErrada2, gbc);

        // Configurar JLabel para a terceira resposta errada
        gbc.gridx = 4;
        gbc.gridy = 0;
        JLabel labelRespostaErrada3 = new JLabel("Resposta Errada 3:");
        painelPrincipal.add(labelRespostaErrada3, gbc);

        // Configurar JTextField para a terceira resposta errada
        gbc.gridx = 4;
        gbc.gridy = 1;
        respostaErrada3 = new JTextField(20);
        respostaErrada3.setPreferredSize(new Dimension(500, 60));
        painelPrincipal.add(respostaErrada3, gbc);

        // Configurar JLabel para a resposta certa
        gbc.gridx = 5;
        gbc.gridy = 0;
        JLabel labelRespostaCerta = new JLabel("Resposta Certa:");
        painelPrincipal.add(labelRespostaCerta, gbc);

        // Configurar JTextField para a resposta certa
        gbc.gridx = 5;
        gbc.gridy = 1;
        respostaCerta = new JTextField(20);
        respostaCerta.setPreferredSize(new Dimension(500, 60));
        painelPrincipal.add(respostaCerta, gbc);

        // Configurar JLabel para a justificativa
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 6;
        JLabel labelJustificativa = new JLabel("Justificativa:");
        painelPrincipal.add(labelJustificativa, gbc);

        // Configurar JTextField para a justificativa
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 6;
        justificativa = new JTextField(20);
        justificativa.setPreferredSize(new Dimension(500, 60));
        painelPrincipal.add(justificativa, gbc);

        // Configurar botão de criar pergunta
        criar = new JButton("+ Criar pergunta");
        criar.setBackground(Color.GREEN);
        criar.setForeground(Color.WHITE);
        criar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        criar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        criar.setOpaque(true);
        criar.setFocusPainted(false);
        gbc.gridx = 5;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(20, 20, 20, 20);
        painelPrincipal.add(criar, gbc);

        criar.addActionListener(e -> criarPergunta());

        // Botão de voltar
        ImageIcon imagemBotao = new ImageIcon(getClass().getResource("/images/botaovoltar.png"));
        JButton botaoVoltar = new JButton("", imagemBotao);
        botaoVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoVoltar.setBorderPainted(false);
        botaoVoltar.setContentAreaFilled(false);
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.setOpaque(false);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(20, 20, 20, 20);
        painelPrincipal.add(botaoVoltar, gbc);
        botaoVoltar.addActionListener(e -> mostrarAdminPerguntas());

        add(painelPrincipal, BorderLayout.CENTER);
    }

    private void mostrarAdminPerguntas() {
        criarPerguntas.dispose();

        JFrame frameAdministrar = new JFrame("Administrar Perguntas");
        frameAdministrar.setSize(1920, 1080);
        frameAdministrar.setMinimumSize(new Dimension(1920, 1080));
        frameAdministrar.setMaximumSize(new Dimension(1920, 1080));
        frameAdministrar.setLocationRelativeTo(null);
        frameAdministrar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameAdministrar.setResizable(true);
        AdminPerguntas administrarPerguntas = new AdminPerguntas(frameAdministrar);
        frameAdministrar.add(administrarPerguntas);
        frameAdministrar.setVisible(true);
    }

    private void criarPergunta() {
        String perguntaText = pergunta.getText();
        String respostaCertaText = respostaCerta.getText();
        String respostaErrada1Text = respostaErrada1.getText();
        String respostaErrada2Text = respostaErrada2.getText();
        String respostaErrada3Text = respostaErrada3.getText();
        String justificativaText = justificativa.getText();

        try (Connection conn = connectionFactory.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO perguntas (pergunta, resposta_certa, resposta_errada1, resposta_errada2, resposta_errada3, justificativa) VALUES (?, ?, ?, ?, ?, ?)")) {

            stmt.setString(1, perguntaText);
            stmt.setString(2, respostaCertaText);
            stmt.setString(3, respostaErrada1Text);
            stmt.setString(4, respostaErrada2Text);
            stmt.setString(5, respostaErrada3Text);
            stmt.setString(6, justificativaText);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Pergunta criada com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao criar pergunta.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao criar pergunta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        pergunta.setText("");
        respostaCerta.setText("");
        respostaErrada1.setText("");
        respostaErrada2.setText("");
        respostaErrada3.setText("");
        justificativa.setText("");
    }
}
