package com.sid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AlterarPerguntas extends JPanel {
    private JFrame frameAlterarPerguntas;
    private Image imagemDeFundo;
    private Image blocoAlterarPerguntas;
    private ConnectionFactory connectionFactory;
    private String perguntaOriginal;
    private JTextField campoPergunta;
    private JTextField campoRespostaErrada1;
    private JTextField campoRespostaErrada2;
    private JTextField campoRespostaErrada3;
    private JTextField campoRespostaCerta;
    private JTextArea campoJustificativa;
    private JButton botaoAlterarPergunta;

    public AlterarPerguntas(JFrame frame, String perguntaOriginal) {
        this.frameAlterarPerguntas = frame;
        this.perguntaOriginal = perguntaOriginal;
        connectionFactory = new ConnectionFactory();
        setLayout(null); // Mantendo o layout absoluto
        carregarImagens();
        configurarComponentes();
        carregarPergunta(perguntaOriginal);
    }

    private void carregarImagens() {
        // Utiliza as mesmas imagens do código 1
        imagemDeFundo = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
        blocoAlterarPerguntas = new ImageIcon(getClass().getResource("/images/blocoadminperguntas.png")).getImage();
    }

    private void configurarComponentes() {
        // Adiciona a imagem do bloco
        JLabel blocoLabel = new JLabel(new ImageIcon(blocoAlterarPerguntas));
        blocoLabel.setBounds(300, 150, blocoAlterarPerguntas.getWidth(null), blocoAlterarPerguntas.getHeight(null));
        add(blocoLabel);

        JLabel labelPergunta = new JLabel("Pergunta:");
        labelPergunta.setBounds(50, 100, 150, 30);
        blocoLabel.add(labelPergunta);

        campoPergunta = new JTextField();
        campoPergunta.setBounds(200, 100, 600, 30);
        blocoLabel.add(campoPergunta);

        JLabel labelRespostaErrada1 = new JLabel("Resposta Errada 1:");
        labelRespostaErrada1.setBounds(50, 150, 150, 30);
        blocoLabel.add(labelRespostaErrada1);

        campoRespostaErrada1 = new JTextField();
        campoRespostaErrada1.setBounds(200, 150, 600, 30);
        blocoLabel.add(campoRespostaErrada1);

        JLabel labelRespostaErrada2 = new JLabel("Resposta Errada 2:");
        labelRespostaErrada2.setBounds(50, 200, 150, 30);
        blocoLabel.add(labelRespostaErrada2);

        campoRespostaErrada2 = new JTextField();
        campoRespostaErrada2.setBounds(200, 200, 600, 30);
        blocoLabel.add(campoRespostaErrada2);

        JLabel labelRespostaErrada3 = new JLabel("Resposta Errada 3:");
        labelRespostaErrada3.setBounds(50, 250, 150, 30);
        blocoLabel.add(labelRespostaErrada3);

        campoRespostaErrada3 = new JTextField();
        campoRespostaErrada3.setBounds(200, 250, 600, 30);
        blocoLabel.add(campoRespostaErrada3);

        JLabel labelRespostaCerta = new JLabel("Resposta Certa:");
        labelRespostaCerta.setBounds(50, 300, 150, 30);
        blocoLabel.add(labelRespostaCerta);

        campoRespostaCerta = new JTextField();
        campoRespostaCerta.setBounds(200, 300, 600, 30);
        blocoLabel.add(campoRespostaCerta);

        JLabel labelJustificativa = new JLabel("Justificativa:");
        labelJustificativa.setBounds(50, 350, 150, 30);
        blocoLabel.add(labelJustificativa);

        campoJustificativa = new JTextArea();
        campoJustificativa.setBounds(200, 350, 600, 100);
        blocoLabel.add(campoJustificativa);

        botaoAlterarPergunta = new JButton("Alterar Pergunta");
        botaoAlterarPergunta.setBackground(Color.GREEN);
        botaoAlterarPergunta.setForeground(Color.WHITE);
        botaoAlterarPergunta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoAlterarPergunta.setBounds(700, 500, 150, 40);
        botaoAlterarPergunta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterarPergunta();
            }
        });
        blocoLabel.add(botaoAlterarPergunta);

        ImageIcon imagemBotao = new ImageIcon(getClass().getResource("/images/botaovoltar.png"));
        JButton botaoVoltar = new JButton("", imagemBotao);
        botaoVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoVoltar.setBorderPainted(false);
        botaoVoltar.setContentAreaFilled(false);
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.setOpaque(false);
        botaoVoltar.setBounds(50, 500, 50, 40);
        botaoVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltarParaAdminPerguntas();
            }
        });
        blocoLabel.add(botaoVoltar);
    }

    private void carregarPergunta(String pergunta) {
        try (Connection conn = connectionFactory.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM perguntas WHERE pergunta = ?")) {
            stmt.setString(1, pergunta);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    campoPergunta.setText(rs.getString("pergunta"));
                    campoRespostaErrada1.setText(rs.getString("resposta_errada1"));
                    campoRespostaErrada2.setText(rs.getString("resposta_errada2"));
                    campoRespostaErrada3.setText(rs.getString("resposta_errada3"));
                    campoRespostaCerta.setText(rs.getString("resposta_certa"));
                    campoJustificativa.setText(rs.getString("justificativa"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void alterarPergunta() {
        String pergunta = campoPergunta.getText();
        String respostaErrada1 = campoRespostaErrada1.getText();
        String respostaErrada2 = campoRespostaErrada2.getText();
        String respostaErrada3 = campoRespostaErrada3.getText();
        String respostaCerta = campoRespostaCerta.getText();
        String justificativa = campoJustificativa.getText();

        try (Connection conn = connectionFactory.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement("UPDATE perguntas SET pergunta = ?, resposta_errada1 = ?, resposta_errada2 = ?, resposta_errada3 = ?, resposta_certa = ?, justificativa = ? WHERE pergunta = ?")) {
            stmt.setString(1, pergunta);
            stmt.setString(2, respostaErrada1);
            stmt.setString(3, respostaErrada2);
            stmt.setString(4, respostaErrada3);
            stmt.setString(5, respostaCerta);
            stmt.setString(6, justificativa);
            stmt.setString(7, perguntaOriginal);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Pergunta alterada com sucesso!");
            voltarParaAdminPerguntas();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao alterar a pergunta.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void voltarParaAdminPerguntas() {
        frameAlterarPerguntas.dispose();
        JFrame frameAdminPerguntas = new JFrame("Administrar Perguntas");
        frameAdminPerguntas.setSize(1920, 1080);
        frameAdminPerguntas.setMinimumSize(new Dimension(1920, 1080));
        frameAdminPerguntas.setMaximumSize(new Dimension(1920, 1080));
        frameAdminPerguntas.setLocationRelativeTo(null);
        frameAdminPerguntas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameAdminPerguntas.setResizable(true);
        AdminPerguntas telaAdminPerguntas = new AdminPerguntas(frameAdminPerguntas);
        frameAdminPerguntas.add(telaAdminPerguntas);
        frameAdminPerguntas.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
    }
}
