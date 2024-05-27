package com.sid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Jogo extends JPanel {
    private Image imagemDeFundo;
    private Image blocoJogo;
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
    private List<Integer> perguntasExibidas;
    private String email;

    public Jogo(JFrame frameJogo, String email) {
        this.jogo = frameJogo;
        alternativas = new ArrayList<>();
        perguntasExibidas = new ArrayList<>();
        this.email = email;
        setLayout(new GridBagLayout());
        connectionFactory = new ConnectionFactory();
        carregarImagens();
        configurarComponentes();
        exibirPergunta();
        configurarOuvintesMouse();
    }

    public void carregarImagens() {
        imagemDeFundo = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
        blocoJogo = new ImageIcon(getClass().getResource("/images/blocoquiz.png")).getImage();
    }

    public void configurarComponentes() {
        // Define o layout para GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Painel para a pergunta
        JPanel perguntaPanel = new JPanel(new BorderLayout());
        perguntaPanel.setOpaque(false);
        perguntaPanel.setPreferredSize(new Dimension(500, 100));

        labelPergunta = new JLabel();
        labelPergunta.setFont(new Font("Arial", Font.BOLD, 14));
        labelPergunta.setHorizontalAlignment(JLabel.CENTER);
        labelPergunta.setVerticalAlignment(JLabel.CENTER);
        labelPergunta.setPreferredSize(new Dimension(500, 100));
        labelPergunta.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        perguntaPanel.add(labelPergunta, BorderLayout.CENTER);

        // Painel para as alternativas
        JPanel alternativasPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        alternativasPanel.setOpaque(false);
        gbc.insets = new Insets(10, 10, 10, 10);

        labelAlternativa1 = new JLabel();
        labelAlternativa2 = new JLabel();
        labelAlternativa3 = new JLabel();
        labelAlternativa4 = new JLabel();

        configurarLabel(labelAlternativa1);
        configurarLabel(labelAlternativa2);
        configurarLabel(labelAlternativa3);
        configurarLabel(labelAlternativa4);

        alternativasPanel.add(labelAlternativa1);
        alternativasPanel.add(labelAlternativa2);
        alternativasPanel.add(labelAlternativa3);
        alternativasPanel.add(labelAlternativa4);

        add(perguntaPanel, gbc);
        gbc.gridy++;
        add(alternativasPanel, gbc);
    }

    private void configurarLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setPreferredSize(new Dimension(500, 80));
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    }

    private void exibirTextoComQuebraDeLinha(JLabel label, String texto) {
        StringBuilder textoComQuebra = new StringBuilder("<html>");

        int maxCaracteresPorLinha = 50;
        int startIndex = 0;
        int endIndex = Math.min(maxCaracteresPorLinha, texto.length());

        while (startIndex < texto.length()) {
            textoComQuebra.append(texto.substring(startIndex, endIndex)).append("<br>");

            startIndex = endIndex;
            endIndex = Math.min(startIndex + maxCaracteresPorLinha, texto.length());
        }

        textoComQuebra.append("</html>");

        label.setText(textoComQuebra.toString());
    }

    private void configurarOuvintesMouse() {
        if (!alternativas.isEmpty()) {
            labelAlternativa1.addMouseListener(new AlternativaMouseListener(labelAlternativa1.getText(), connectionFactory, this, email));
            labelAlternativa2.addMouseListener(new AlternativaMouseListener(labelAlternativa2.getText(), connectionFactory, this, email));
            labelAlternativa3.addMouseListener(new AlternativaMouseListener(labelAlternativa3.getText(), connectionFactory, this, email));
            labelAlternativa4.addMouseListener(new AlternativaMouseListener(labelAlternativa4.getText(), connectionFactory, this, email));
        }
    }

    public int getIdPerguntaAtual() {
        return idPerguntaAtual;
    }

    public void exibirPergunta() {
        try (Connection connection = connectionFactory.obtemConexao()) {
            String query = "SELECT id_perguntas, pergunta, resposta_certa, resposta_errada1, resposta_errada2, resposta_errada3, justificativa FROM perguntas WHERE id_perguntas NOT IN (?) ORDER BY RAND() LIMIT 1";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1,
                        perguntasExibidas.stream().map(Object::toString).collect(Collectors.joining(", ")));
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        idPerguntaAtual = resultSet.getInt("id_perguntas");
                        String pergunta = resultSet.getString("pergunta");
                        String respostaCerta = resultSet.getString("resposta_certa");
                        String respostaErrada1 = resultSet.getString("resposta_errada1");
                        String respostaErrada2 = resultSet.getString("resposta_errada2");
                        String respostaErrada3 = resultSet.getString("resposta_errada3");

                        alternativas = Arrays.asList(respostaCerta, respostaErrada1, respostaErrada2, respostaErrada3);
                        Collections.shuffle(alternativas);

                        exibirTextoComQuebraDeLinha(labelPergunta, pergunta);
                        exibirTextoComQuebraDeLinha(labelAlternativa1, "a) " + alternativas.get(0));
                        exibirTextoComQuebraDeLinha(labelAlternativa2, "b) " + alternativas.get(1));
                        exibirTextoComQuebraDeLinha(labelAlternativa3, "c) " + alternativas.get(2));
                        exibirTextoComQuebraDeLinha(labelAlternativa4, "d) " + alternativas.get(3));

                        perguntasExibidas.add(idPerguntaAtual);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (perguntasExibidas.size() >= getTotalPerguntas()) {
            exibirMensagemFinal();
        }
    }

    public int getTotalPerguntas() {
        int totalPerguntas = 0;
        try (Connection connection = connectionFactory.obtemConexao()) {
            String query = "SELECT COUNT(*) AS total FROM perguntas";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        totalPerguntas = resultSet.getInt("total");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalPerguntas;
    }

    public void exibirMensagemFinal() {
        JOptionPane.showMessageDialog(jogo, "Parabéns! Você respondeu todas as perguntas. Seu score final é: " + score,
                "Fim do Jogo", JOptionPane.INFORMATION_MESSAGE);
        int resposta = JOptionPane.showConfirmDialog(jogo, "Deseja jogar novamente?", "Jogar Novamente",
                JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            reiniciarJogo();
        } else {
            mostrarMenuAluno();
        }
    }

    public void reiniciarJogo() {
        idPerguntaAtual = 0;
        score = 0;
        perguntasExibidas.clear();

        labelPergunta.setText("");
        labelAlternativa1.setText("");
        labelAlternativa2.setText("");
        labelAlternativa3.setText("");
        labelAlternativa4.setText("");

        exibirPergunta();
    }

    public int atualizarEObterScore(String email) {
        score += 5;
        int idAluno = getIdAlunoPorEmail(email);
        try (Connection connection = connectionFactory.obtemConexao()) {
            String query = "UPDATE ranking SET pontuacao = ? WHERE id_aluno_popula = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, score);
                statement.setInt(2, idAluno);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return score;
    }

    private int getIdAlunoPorEmail(String email) {
        int idAluno = 0;
        try (Connection connection = connectionFactory.obtemConexao()) {
            String query = "SELECT id_aluno FROM aluno WHERE email_aluno = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        idAluno = resultSet.getInt("id_aluno");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idAluno;
    }

    private void mostrarMenuAluno() {
        jogo.dispose();

        JFrame frameMenu = new JFrame("Menu");
        frameMenu.setSize(1280, 720);
        frameMenu.setMinimumSize(new Dimension(1280, 720));
        frameMenu.setMaximumSize(new Dimension(1920, 1080));
        frameMenu.setLocationRelativeTo(null);
        frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMenu.setResizable(true);
        MenuAluno menuAluno = new MenuAluno(frameMenu, email);
        frameMenu.add(menuAluno);
        frameMenu.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        int x = (getWidth() - blocoJogo.getWidth(null)) / 2;
        int y = (getHeight() - blocoJogo.getHeight(null)) / 2;

        g.drawImage(blocoJogo, x, y, blocoJogo.getWidth(null), blocoJogo.getHeight(null), this);
    }

}

class AlternativaMouseListener extends MouseAdapter {
    private String alternativaCompleta;
    private ConnectionFactory connectionFactory;
    private Jogo jogo;
    private String email;

    public AlternativaMouseListener(String alternativaCompleta, ConnectionFactory connectionFactory, Jogo jogo, String email) {
        this.alternativaCompleta = alternativaCompleta;
        this.connectionFactory = connectionFactory;
        this.jogo = jogo;
        this.email = email;
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
                        String respostaCerta = resultSet.getString("resposta_certa");
                        String justificativa = resultSet.getString("justificativa");
                        alternativaCompleta = removerTagsHTML(alternativaCompleta).substring(3);
                        if (alternativaCompleta.equalsIgnoreCase(respostaCerta)) {
                            JOptionPane.showMessageDialog(jogo,
                                    "Parabéns! Você acertou. Seu score atual é: " + jogo.atualizarEObterScore(email),
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

    private String removerTagsHTML(String htmlString) {
        String regex = "\\<.*?\\>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(htmlString);
        return matcher.replaceAll("");
    }
}
