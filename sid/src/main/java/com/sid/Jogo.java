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

// Classe principal do jogo de perguntas
public class Jogo extends JPanel {
    private Image imagemDeFundo;  // Imagem de fundo do jogo
    private Image blocoJogo;  // Imagem do bloco do jogo
    private JFrame jogo;  // Referência ao JFrame do jogo
    private JLabel labelPergunta;  // Label para mostrar a pergunta
    private JLabel labelAlternativa1;  // Label para mostrar a alternativa 1
    private JLabel labelAlternativa2;  // Label para mostrar a alternativa 2
    private JLabel labelAlternativa3;  // Label para mostrar a alternativa 3
    private JLabel labelAlternativa4;  // Label para mostrar a alternativa 4
    private ConnectionFactory connectionFactory;  // Objeto para gerenciar conexões com o banco de dados
    private int idPerguntaAtual = 0;  // ID da pergunta atual
    private int score = 0;  // Pontuação do jogador
    private List<String> alternativas;  // Lista de alternativas para a pergunta atual
    private List<Integer> perguntasExibidas;  // Lista de IDs de perguntas já exibidas
    private String email;  // Email do jogador

    // Construtor da classe Jogo
    public Jogo(JFrame frameJogo, String email) {
        this.jogo = frameJogo;
        alternativas = new ArrayList<>();
        perguntasExibidas = new ArrayList<>();
        this.email = email;
        setLayout(new GridBagLayout());  // Define o layout do painel
        connectionFactory = new ConnectionFactory();  // Inicializa o gerenciador de conexões
        carregarImagens();  // Carrega as imagens necessárias
        configurarComponentes();  // Configura os componentes da interface
        exibirPergunta();  // Exibe a primeira pergunta
        configurarOuvintesMouse();  // Configura os ouvintes de eventos de mouse
    }

    // Método para carregar as imagens do jogo
    public void carregarImagens() {
        imagemDeFundo = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
        blocoJogo = new ImageIcon(getClass().getResource("/images/blocoquiz.png")).getImage();
    }

    // Método para configurar os componentes da interface
    public void configurarComponentes() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel perguntaPanel = new JPanel(new BorderLayout());
        perguntaPanel.setOpaque(false);  // Define o painel como transparente
        perguntaPanel.setPreferredSize(new Dimension(500, 100));

        labelPergunta = new JLabel();
        configurarLabelPergunta(labelPergunta);  // Configura o label da pergunta
        perguntaPanel.add(labelPergunta, BorderLayout.CENTER);

        JPanel alternativasPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        alternativasPanel.setOpaque(false);

        labelAlternativa1 = new JLabel();
        labelAlternativa2 = new JLabel();
        labelAlternativa3 = new JLabel();
        labelAlternativa4 = new JLabel();

        configurarLabel(labelAlternativa1);  // Configura o label da alternativa 1
        configurarLabel(labelAlternativa2);  // Configura o label da alternativa 2
        configurarLabel(labelAlternativa3);  // Configura o label da alternativa 3
        configurarLabel(labelAlternativa4);  // Configura o label da alternativa 4

        alternativasPanel.add(labelAlternativa1);
        alternativasPanel.add(labelAlternativa2);
        alternativasPanel.add(labelAlternativa3);
        alternativasPanel.add(labelAlternativa4);

        add(perguntaPanel, gbc);
        gbc.gridy++;
        add(alternativasPanel, gbc);
    }

    // Método para configurar o estilo do label da pergunta
    private void configurarLabelPergunta(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(500, 100));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    // Método para configurar o estilo dos labels das alternativas
    private void configurarLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setPreferredSize(new Dimension(500, 80));
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    }

    // Método para exibir texto com quebras de linha em um JLabel
    private void exibirTextoComQuebraDeLinha(JLabel label, String texto) {
        StringBuilder textoComQuebra = new StringBuilder("<html>");

        int maxCaracteresPorLinha = 50;  // Número máximo de caracteres por linha
        int startIndex = 0;
        int endIndex = Math.min(maxCaracteresPorLinha, texto.length());

        while (startIndex < texto.length()) {
            textoComQuebra.append(texto, startIndex, endIndex).append("<br>");
            startIndex = endIndex;
            endIndex = Math.min(startIndex + maxCaracteresPorLinha, texto.length());
        }

        textoComQuebra.append("</html>");
        label.setText(textoComQuebra.toString());
    }

    // Método para configurar os ouvintes de eventos de mouse nas alternativas
    private void configurarOuvintesMouse() {
        labelAlternativa1.addMouseListener(new AlternativaMouseListener(labelAlternativa1, connectionFactory, this, email));
        labelAlternativa2.addMouseListener(new AlternativaMouseListener(labelAlternativa2, connectionFactory, this, email));
        labelAlternativa3.addMouseListener(new AlternativaMouseListener(labelAlternativa3, connectionFactory, this, email));
        labelAlternativa4.addMouseListener(new AlternativaMouseListener(labelAlternativa4, connectionFactory, this, email));
    }

    // Método para obter o ID da pergunta atual
    public int getIdPerguntaAtual() {
        return idPerguntaAtual;
    }

    // Método para exibir uma nova pergunta
    public void exibirPergunta() {
        try (Connection connection = connectionFactory.obtemConexao()) {
            // Query para selecionar uma nova pergunta que ainda não foi exibida
            String query = "SELECT id_perguntas, pergunta, resposta_certa, resposta_errada1, resposta_errada2, resposta_errada3 FROM perguntas WHERE id_perguntas NOT IN (?) ORDER BY RAND() LIMIT 1";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, perguntasExibidas.stream().map(Object::toString).collect(Collectors.joining(", ")));
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        idPerguntaAtual = resultSet.getInt("id_perguntas");
                        String pergunta = resultSet.getString("pergunta");
                        String respostaCerta = resultSet.getString("resposta_certa");
                        String respostaErrada1 = resultSet.getString("resposta_errada1");
                        String respostaErrada2 = resultSet.getString("resposta_errada2");
                        String respostaErrada3 = resultSet.getString("resposta_errada3");

                        alternativas = Arrays.asList(respostaCerta, respostaErrada1, respostaErrada2, respostaErrada3);
                        Collections.shuffle(alternativas);  // Embaralha as alternativas

                        exibirTextoComQuebraDeLinha(labelPergunta, pergunta);
                        exibirTextoComQuebraDeLinha(labelAlternativa1, "a) " + alternativas.get(0));
                        exibirTextoComQuebraDeLinha(labelAlternativa2, "b) " + alternativas.get(1));
                        exibirTextoComQuebraDeLinha(labelAlternativa3, "c) " + alternativas.get(2));
                        exibirTextoComQuebraDeLinha(labelAlternativa4, "d) " + alternativas.get(3));

                        perguntasExibidas.add(idPerguntaAtual);  // Adiciona a pergunta atual à lista de exibidas
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Se todas as perguntas foram exibidas, exibe a mensagem final
        if (perguntasExibidas.size() >= getTotalPerguntas()) {
            exibirMensagemFinal();
        }
    }

    // Método para obter o total de perguntas no banco de dados
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

    // Método para exibir a mensagem final do jogo
    private void exibirMensagemFinal() {
        JOptionPane.showMessageDialog(jogo, "Parabéns! Você completou o quiz com uma pontuação de: " + score, "Quiz Completo", JOptionPane.INFORMATION_MESSAGE);
        mostrarMenuAluno();  // Volta ao menu do aluno
    }

    // Método para atualizar e obter a pontuação do jogador
    public int atualizarEObterScore(String email) {
        score += 5;  // Incrementa a pontuação
    
        int idAluno = getIdAlunoPorEmail(email);
    
        try (Connection connection = connectionFactory.obtemConexao()) {
            String queryVerifica = "SELECT pontuacao FROM ranking WHERE id_aluno_popula = ?";
            try (PreparedStatement stmtVerifica = connection.prepareStatement(queryVerifica)) {
                stmtVerifica.setInt(1, idAluno);
                try (ResultSet resultSet = stmtVerifica.executeQuery()) {
                    if (resultSet.next()) {
                        int pontuacaoAtual = resultSet.getInt("pontuacao");
                        if (score > pontuacaoAtual) {
                            String queryUpdate = "UPDATE ranking SET pontuacao = ? WHERE id_aluno_popula = ?";
                            try (PreparedStatement stmtUpdate = connection.prepareStatement(queryUpdate)) {
                                stmtUpdate.setInt(1, score);
                                stmtUpdate.setInt(2, idAluno);
                                stmtUpdate.executeUpdate();
                            }
                        }
                    } else {
                        String queryInsert = "INSERT INTO ranking (id_aluno_popula, pontuacao) VALUES (?, ?)";
                        try (PreparedStatement stmtInsert = connection.prepareStatement(queryInsert)) {
                            stmtInsert.setInt(1, idAluno);
                            stmtInsert.setInt(2, score);
                            stmtInsert.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return score;
    }
    

    // Método para obter o ID do aluno a partir do email
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

    // Método para mostrar o menu do aluno
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

    // Método para desenhar os componentes gráficos
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        int x = (getWidth() - blocoJogo.getWidth(null)) / 2;
        int y = (getHeight() - blocoJogo.getHeight(null)) / 2;
        g.drawImage(blocoJogo, x, y, blocoJogo.getWidth(null), blocoJogo.getHeight(null), this);
    }
}

// Classe que lida com eventos de mouse nas alternativas
class AlternativaMouseListener extends MouseAdapter {
    private JLabel labelAlternativa;
    private ConnectionFactory connectionFactory;
    private Jogo jogo;
    private String email;

    // Construtor da classe AlternativaMouseListener
    public AlternativaMouseListener(JLabel labelAlternativa, ConnectionFactory connectionFactory, Jogo jogo, String email) {
        this.labelAlternativa = labelAlternativa;
        this.connectionFactory = connectionFactory;
        this.jogo = jogo;
        this.email = email;
    }

    // Método chamado quando o mouse clica em uma alternativa
    @Override
    public void mouseClicked(MouseEvent e) {
        String alternativaClicada = removerTagsHTML(labelAlternativa.getText()).substring(3).trim();
        int idPerguntaAtual = jogo.getIdPerguntaAtual();

        try (Connection connection = connectionFactory.obtemConexao()) {
            String query = "SELECT resposta_certa, justificativa FROM perguntas WHERE id_perguntas = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, idPerguntaAtual);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String respostaCerta = resultSet.getString("resposta_certa");
                        String justificativa = resultSet.getString("justificativa");

                        if (alternativaClicada.equalsIgnoreCase(respostaCerta)) {
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

        jogo.exibirPergunta();  // Exibe uma nova pergunta
    }

    // Método chamado quando o mouse entra em uma alternativa
    @Override
    public void mouseEntered(MouseEvent e) {
        labelAlternativa.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Muda o cursor para indicar que é clicável
    }

    // Método chamado quando o mouse sai de uma alternativa
    @Override
    public void mouseExited(MouseEvent e) {
        labelAlternativa.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));  // Restaura o cursor padrão
    }

    // Método para remover tags HTML de um texto
    private String removerTagsHTML(String htmlString) {
        String regex = "\\<.*?\\>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(htmlString);
        return matcher.replaceAll("");
    }
}
