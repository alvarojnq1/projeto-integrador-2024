package com.sid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Jogo extends JPanel {
    private Image imagemDeFundo;
    private Image blocoJogo;
    private Image retanguloOpaco;
    private JFrame jogo;
    private JLabel labelPergunta;
    private JLabel labelAlternativa1;
    private JLabel labelAlternativa2;
    private JLabel labelAlternativa3;
    private JLabel labelAlternativa4;
    private ConnectionFactory connectionFactory;
    private int idPerguntaAtual = 0;
    private int score = 0;
    private List<String> alternativas;


    public Jogo(JFrame frameJogo) {
        this.jogo = frameJogo;
        setLayout(new GridBagLayout());
        connectionFactory = new ConnectionFactory();
        carregarImagens();
        configurarComponentes();
        exibirPergunta();
    }

    public void carregarImagens() {
        imagemDeFundo = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
        blocoJogo = new ImageIcon(getClass().getResource("/images/blocoquiz.png")).getImage();
        retanguloOpaco = new ImageIcon(getClass().getResource("/images/retanguloopaco.png")).getImage();
    }

    public void configurarComponentes() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);

        // Configurando labelPergunta
        labelPergunta = new JLabel();
        labelPergunta.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH; // Ancora a pergunta no topo
        add(labelPergunta, gbc);

        // Configurando labelAlternativa1
        labelAlternativa1 = new JLabel();
        labelAlternativa1.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST; // Ancora a alternativa1 totalmente à esquerda
        gbc.insets = new Insets(10, 20, 10, 200);
        labelAlternativa1.addMouseListener(new AlternativaMouseListener("a", alternativas,connectionFactory, this));

        add(labelAlternativa1, gbc);

        // Configurando labelAlternativa2
        labelAlternativa2 = new JLabel();
        labelAlternativa2.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST; // Ancora a alternativa2 totalmente à direita
        gbc.insets = new Insets(10, 500, 10, 30);
        labelAlternativa2.addMouseListener(new AlternativaMouseListener("b", alternativas, connectionFactory, this));

        add(labelAlternativa2, gbc);

        // Configurando labelAlternativa3
        labelAlternativa3 = new JLabel();
        labelAlternativa3.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST; // Ancora a alternativa3 totalmente à esquerda
        gbc.insets = new Insets(10, 20, 10, 200);
        labelAlternativa3.addMouseListener(new AlternativaMouseListener("c", alternativas,connectionFactory, this));

        add(labelAlternativa3, gbc);

        // Configurando labelAlternativa4
        labelAlternativa4 = new JLabel();
        labelAlternativa4.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST; // Ancora a alternativa4 totalmente à direita
        gbc.insets = new Insets(10, 500, 10, 30);
        labelAlternativa4.addMouseListener(new AlternativaMouseListener("d",alternativas, connectionFactory, this));

        add(labelAlternativa4, gbc);
    }

    public int getIdPerguntaAtual() {
        return idPerguntaAtual;
    }

    public void exibirPergunta() {
        try (Connection connection = connectionFactory.obtemConexao()) {
            // Query para selecionar a próxima pergunta aleatoriamente
            String query = "SELECT pergunta, resposta_certa, resposta_errada1, resposta_errada2, resposta_errada3, justificativa FROM perguntas ORDER BY RAND() LIMIT 1";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // Obtenha os dados da pergunta e suas respostas
                        String pergunta = resultSet.getString("pergunta");
                        String respostaCerta = resultSet.getString("resposta_certa");
                        String respostaErrada1 = resultSet.getString("resposta_errada1");
                        String respostaErrada2 = resultSet.getString("resposta_errada2");
                        String respostaErrada3 = resultSet.getString("resposta_errada3");
                        String justificativa = resultSet.getString("justificativa");
                    
                        // Embaralhe as respostas
                        alternativas = Arrays.asList(respostaCerta, respostaErrada1, respostaErrada2, respostaErrada3);
                        Collections.shuffle(alternativas);
                    
                        // Exiba a pergunta e as respostas na interface
                        labelPergunta.setText(pergunta);
                        labelAlternativa1.setText("a) " + alternativas.get(0));
                        labelAlternativa2.setText("b) " + alternativas.get(1));
                        labelAlternativa3.setText("c) " + alternativas.get(2));
                        labelAlternativa4.setText("d) " + alternativas.get(3));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int atualizarEObterScore() {
        score += 5;
        return score;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        int x = (getWidth() - blocoJogo.getWidth(null)) / 2;
        int y = (getHeight() - blocoJogo.getHeight(null)) / 2;

        g.drawImage(blocoJogo, x, y, blocoJogo.getWidth(null), blocoJogo.getHeight(null), this);
        g.drawImage(retanguloOpaco, x + 22, y + 130, retanguloOpaco.getWidth(null), retanguloOpaco.getHeight(null),
                this);
        g.drawImage(retanguloOpaco, x + 710, y + 130, retanguloOpaco.getWidth(null), retanguloOpaco.getHeight(null),
                this);
        g.drawImage(retanguloOpaco, x + 22, y + 230, retanguloOpaco.getWidth(null), retanguloOpaco.getHeight(null),
                this);
        g.drawImage(retanguloOpaco, x + 710, y + 230, retanguloOpaco.getWidth(null), retanguloOpaco.getHeight(null),
                this);
    }

}

class AlternativaMouseListener extends MouseAdapter {
    private String alternativa;
    private ConnectionFactory connectionFactory;
    private Jogo jogo;
    private List<String> alternativas;


    public AlternativaMouseListener(String alternativa, List<String> alternativas,ConnectionFactory connectionFactory, Jogo jogo) {
        this.alternativa = alternativa;
        this.connectionFactory = connectionFactory;
        this.jogo = jogo;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int idPerguntaAtual = jogo.getIdPerguntaAtual();
    
        try (Connection connection = connectionFactory.obtemConexao()) {
            String query = "SELECT resposta_certa, resposta_errada1, resposta_errada2, resposta_errada3, justificativa FROM perguntas WHERE id_perguntas = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, idPerguntaAtual);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String respostaCerta = resultSet.getString("resposta_certa").trim();
                        String justificativa = resultSet.getString("justificativa");
                        
                        // Verificando a alternativa selecionada pelo usuário
                        if (alternativa.equalsIgnoreCase(respostaCerta)) {
                            JOptionPane.showMessageDialog(jogo,
                                    "Parabéns! Você acertou. Seu score atual é: " + jogo.atualizarEObterScore(),
                                    "Resposta Correta", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(jogo, "Ops! Você errou. A justificativa é: " + justificativa,
                                    "Resposta Incorreta", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    jogo.exibirPergunta();
}

    @Override
    public void mouseEntered(MouseEvent e) {
        JLabel label = (JLabel) e.getSource();
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JLabel label = (JLabel) e.getSource();
        label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

}