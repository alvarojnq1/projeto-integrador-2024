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
    private int idAluno;
    private String respostaCerta; 

    public Jogo(JFrame frameJogo) {
        this.jogo = frameJogo;
        alternativas = new ArrayList<>();
        perguntasExibidas = new ArrayList<>();
        this.idAluno = idAluno;
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
        JPanel perguntaPanel = new JPanel(new BorderLayout()); // Layout BorderLayout para a pergunta
        perguntaPanel.setOpaque(false); // Torna o painel transparente
        perguntaPanel.setPreferredSize(new Dimension(500, 100)); // Tamanho do painel da pergunta
    
        labelPergunta = new JLabel();
        labelPergunta.setFont(new Font("Arial", Font.BOLD, 14)); // Fonte da pergunta
        labelPergunta.setHorizontalAlignment(JLabel.CENTER); // Centraliza o texto da pergunta
        labelPergunta.setVerticalAlignment(JLabel.CENTER); // Centraliza verticalmente o texto da pergunta
        labelPergunta.setPreferredSize(new Dimension(500, 100)); // Tamanho máximo da pergunta
        labelPergunta.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adiciona margem
        perguntaPanel.add(labelPergunta, BorderLayout.CENTER);
    
        // Painel para as alternativas
        JPanel alternativasPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // Layout GridLayout para as alternativas
        alternativasPanel.setOpaque(false); // Torna o painel transparente
        gbc.insets = new Insets(10, 10, 10, 10);
    
        // Criação dos JLabels para alternativas
        labelAlternativa1 = new JLabel();
        labelAlternativa2 = new JLabel();
        labelAlternativa3 = new JLabel();
        labelAlternativa4 = new JLabel();
    
        // Configura os JLabels
        configurarLabel(labelAlternativa1);
        configurarLabel(labelAlternativa2);
        configurarLabel(labelAlternativa3);
        configurarLabel(labelAlternativa4);
    
        // Adiciona as alternativas ao painel de alternativas
        alternativasPanel.add(labelAlternativa1);
        alternativasPanel.add(labelAlternativa2);
        alternativasPanel.add(labelAlternativa3);
        alternativasPanel.add(labelAlternativa4);
    
        // Adiciona os painéis ao bloco do jogo
        add(perguntaPanel, gbc);
        gbc.gridy++; // Move para a próxima linha
        add(alternativasPanel, gbc);
    }
    

    // Método para configurar JLabel
    private void configurarLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 14)); // Fonte das alternativas
        label.setPreferredSize(new Dimension(500, 80)); // Tamanho máximo das alternativas
        label.setVerticalAlignment(JLabel.CENTER); // Centraliza verticalmente o texto
        label.setHorizontalAlignment(JLabel.CENTER); // Centraliza horizontalmente o texto
        label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY)); // Adiciona borda
    }

    private void exibirTextoComQuebraDeLinha(JLabel label, String texto) {
        StringBuilder textoComQuebra = new StringBuilder("<html>");

        // Tamanho máximo de caracteres por linha
        int maxCaracteresPorLinha = 50;
        int startIndex = 0;
        int endIndex = Math.min(maxCaracteresPorLinha, texto.length());

        while (startIndex < texto.length()) {
            // Adiciona parte do texto com quebra de linha
            textoComQuebra.append(texto.substring(startIndex, endIndex)).append("<br>");

            // Atualiza os índices para a próxima parte do texto
            startIndex = endIndex;
            endIndex = Math.min(startIndex + maxCaracteresPorLinha, texto.length());
        }

        // Fecha a tag HTML
        textoComQuebra.append("</html>");

        // Define o texto com quebra de linha no JLabel
        label.setText(textoComQuebra.toString());
    }

    private void configurarOuvintesMouse() {
        if (!alternativas.isEmpty()) {
            labelAlternativa1.addMouseListener(new AlternativaMouseListener(labelAlternativa1.getText(), connectionFactory, this));
            labelAlternativa2.addMouseListener(new AlternativaMouseListener(labelAlternativa2.getText(), connectionFactory, this));
            labelAlternativa3.addMouseListener(new AlternativaMouseListener(labelAlternativa3.getText(), connectionFactory, this));
            labelAlternativa4.addMouseListener(new AlternativaMouseListener(labelAlternativa4.getText(), connectionFactory, this));

        }
    }

    public int getIdPerguntaAtual() {
        return idPerguntaAtual;
    }

    public void exibirPergunta() {
        try (Connection connection = connectionFactory.obtemConexao()) {
            // Query para selecionar a próxima pergunta aleatoriamente que não foi exibida
            // ainda
            String query = "SELECT id_perguntas, pergunta, resposta_certa, resposta_errada1, resposta_errada2, resposta_errada3, justificativa FROM perguntas WHERE id_perguntas NOT IN (?) ORDER BY RAND() LIMIT 1";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1,
                        perguntasExibidas.stream().map(Object::toString).collect(Collectors.joining(", ")));
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // Exibir a pergunta apenas se houver resultados na consulta
                        idPerguntaAtual = resultSet.getInt("id_perguntas");
                        String pergunta = resultSet.getString("pergunta");
                        String respostaCerta = resultSet.getString("resposta_certa");
                        String respostaErrada1 = resultSet.getString("resposta_errada1");
                        String respostaErrada2 = resultSet.getString("resposta_errada2");
                        String respostaErrada3 = resultSet.getString("resposta_errada3");
                        String justificativa = resultSet.getString("justificativa");
    
                        // Imprime as alternativas para verificar se estão sendo atualizadas corretamente
                        System.out.println("Resposta correta: " + respostaCerta);
                        System.out.println("Resposta errada 1: " + respostaErrada1);
                        System.out.println("Resposta errada 2: " + respostaErrada2);
                        System.out.println("Resposta errada 3: " + respostaErrada3);
    
                        alternativas = Arrays.asList(respostaCerta, respostaErrada1, respostaErrada2, respostaErrada3);
                        Collections.shuffle(alternativas);
    
                        exibirTextoComQuebraDeLinha(labelPergunta, pergunta);
                        exibirTextoComQuebraDeLinha(labelAlternativa1, "a) " + alternativas.get(0));
                        exibirTextoComQuebraDeLinha(labelAlternativa2, "b) " + alternativas.get(1));
                        exibirTextoComQuebraDeLinha(labelAlternativa3, "c) " + alternativas.get(2));
                        exibirTextoComQuebraDeLinha(labelAlternativa4, "d) " + alternativas.get(3));
    
                        perguntasExibidas.add(idPerguntaAtual); // Adiciona o ID da pergunta exibida à lista
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        // Verifica se o número de perguntas exibidas é igual ao total de perguntas
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
        // Reinicializa as variáveis ​​de controle do jogo
        idPerguntaAtual = 0;
        score = 0;
        perguntasExibidas.clear(); // Limpa a lista de perguntas exibidas

        // Limpa os componentes de interface gráfica
        labelPergunta.setText("");
        labelAlternativa1.setText("");
        labelAlternativa2.setText("");
        labelAlternativa3.setText("");
        labelAlternativa4.setText("");

        // Exibe a primeira pergunta
        exibirPergunta();
    }

    public int atualizarEObterScore(int idAluno) {
        score += 5;
    
        // Atualiza a pontuação no banco de dados
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

    private void mostrarMenuAluno() {
        jogo.dispose();

        JFrame frameMenu = new JFrame("Menu");
        frameMenu.setSize(1280, 720);
        frameMenu.setMinimumSize(new Dimension(1280, 720));
        frameMenu.setMaximumSize(new Dimension(1920, 1080));
        frameMenu.setLocationRelativeTo(null);
        frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMenu.setResizable(true);
        MenuAluno menuAluno = new MenuAluno(frameMenu);
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
    private int idAluno;

    public AlternativaMouseListener(String alternativaCompleta, ConnectionFactory connectionFactory, Jogo jogo) {
        this.alternativaCompleta = alternativaCompleta;
        this.connectionFactory = connectionFactory;
        this.jogo = jogo;
        this.idAluno = idAluno;
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
                        // Verificando a alternativa selecionada pelo usuário
                        if (alternativaCompleta.equalsIgnoreCase(respostaCerta)) {
                            JOptionPane.showMessageDialog(jogo,
                                    "Parabéns! Você acertou. Seu score atual é: " + jogo.atualizarEObterScore(idAluno),
                                    "Resposta Correta", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            System.out.println(alternativaCompleta);
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
    // Método para remover tags HTML
    private String removerTagsHTML(String htmlString) {
        // Expressão regular para encontrar tags HTML
        String regex = "\\<.*?\\>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(htmlString);
        
        // Substitui todas as tags HTML por uma string vazia
        return matcher.replaceAll("");
    }
}