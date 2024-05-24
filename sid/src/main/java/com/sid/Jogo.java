    package com.sid;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.MouseAdapter;
    import java.awt.event.MouseEvent;
    import java.sql.*;
    import java.util.Arrays;
    import java.util.Collections;
    import java.util.List;
    import java.util.ArrayList;



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
    private List<Integer> perguntasExibidas; // Lista para armazenar IDs das perguntas exibidas

    public Jogo(JFrame frameJogo) {
        this.jogo = frameJogo;
        perguntasExibidas = new ArrayList<>();
        setLayout(new GridBagLayout());
        connectionFactory = new ConnectionFactory();
        carregarImagens();
        configurarComponentes();
        exibirPergunta();
    }

        public void carregarImagens() {
            imagemDeFundo = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
            blocoJogo = new ImageIcon(getClass().getResource("/images/blocoquiz.png")).getImage();
        }

        public void configurarComponentes() {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 20, 100, 20);

            // Configurando labelPergunta
            labelPergunta = new JLabel();
            labelPergunta.setFont(new Font("Arial", Font.BOLD, 15));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.fill= GridBagConstraints.NORTH; // Ancora a pergunta no topo
            add(labelPergunta, gbc);

            // Configurando labelAlternativa1
            labelAlternativa1 = new JLabel();
            labelAlternativa1.setFont(new Font("Arial", Font.BOLD, 15));
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.fill= GridBagConstraints.WEST; // Ancora a pergunta no topo
            gbc.insets = new Insets(1, 20, 50, 700);
            labelAlternativa1.addMouseListener(new AlternativaMouseListener(alternativas.get(0), connectionFactory, this));

            add(labelAlternativa1, gbc);

            // Configurando labelAlternativa2
            labelAlternativa2 = new JLabel();
            labelAlternativa2.setFont(new Font("Arial", Font.BOLD, 15));
            gbc.gridx = 2;
            gbc.gridy = 1;
            gbc.fill= GridBagConstraints.EAST; // Ancora a pergunta no topo
            gbc.insets = new Insets(1, 700, 50, 30);
            labelAlternativa1.addMouseListener(new AlternativaMouseListener(alternativas.get(1), connectionFactory, this));

            add(labelAlternativa2, gbc);

            // Configurando labelAlternativa3
            labelAlternativa3 = new JLabel();
            labelAlternativa3.setFont(new Font("Arial", Font.BOLD, 15));
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.fill= GridBagConstraints.WEST; // Ancora a pergunta no topo
            gbc.insets = new Insets(10, 20, 60, 700);
            labelAlternativa1.addMouseListener(new AlternativaMouseListener(alternativas.get(2), connectionFactory, this));

            add(labelAlternativa3, gbc);

            // Configurando labelAlternativa4
            labelAlternativa4 = new JLabel();
            labelAlternativa4.setFont(new Font("Arial", Font.BOLD, 15));
            gbc.gridx = 2;
            gbc.gridy = 2;
            gbc.fill= GridBagConstraints.EAST; // Ancora a pergunta no topo
            gbc.insets = new Insets(10, 700, 60, 30);
            labelAlternativa1.addMouseListener(new AlternativaMouseListener(alternativas.get(3), connectionFactory, this));

            add(labelAlternativa4, gbc);
        }

        public int getIdPerguntaAtual() {
            return idPerguntaAtual;
        }

        public void exibirPergunta() {
            try (Connection connection = connectionFactory.obtemConexao()) {
                // Query para selecionar a próxima pergunta aleatoriamente que não foi exibida ainda
                String query = "SELECT id_perguntas, pergunta, resposta_certa, resposta_errada1, resposta_errada2, resposta_errada3, justificativa FROM perguntas WHERE id_perguntas NOT IN (?) ORDER BY RAND() LIMIT 1";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, perguntasExibidas.toString());
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            idPerguntaAtual = resultSet.getInt("id_perguntas");
                            String pergunta = resultSet.getString("pergunta");
                            String respostaCerta = resultSet.getString("resposta_certa");
                            String respostaErrada1 = resultSet.getString("resposta_errada1");
                            String respostaErrada2 = resultSet.getString("resposta_errada2");
                            String respostaErrada3 = resultSet.getString("resposta_errada3");
                            String justificativa = resultSet.getString("justificativa");
                        
                            alternativas = Arrays.asList(respostaCerta, respostaErrada1, respostaErrada2, respostaErrada3);
                            Collections.shuffle(alternativas);
    
                            labelPergunta.setText(pergunta);
                            labelAlternativa1.setText("a) " + alternativas.get(0));
                            labelAlternativa2.setText("b) " + alternativas.get(1));
                            labelAlternativa3.setText("c) " + alternativas.get(2));
                            labelAlternativa4.setText("d) " + alternativas.get(3));
    
                            perguntasExibidas.add(idPerguntaAtual); // Adiciona o ID da pergunta exibida à lista
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    
            // Verifica se o usuário respondeu todas as perguntas
            if (perguntasExibidas.size() >= getTotalPerguntas()) {
                exibirMensagemFinal();
            }
        }
        public int getTotalPerguntas() {
            // Aqui você deve retornar o número total de perguntas disponíveis
            // Por exemplo, você pode implementar uma lógica para contar o número de perguntas no banco de dados
            return 10; // Exemplo: 10 perguntas
        }
    
        public void exibirMensagemFinal() {
            JOptionPane.showMessageDialog(jogo, "Parabéns! Você respondeu todas as perguntas. Seu score final é: " + score, "Fim do Jogo", JOptionPane.INFORMATION_MESSAGE);
            int resposta = JOptionPane.showConfirmDialog(jogo, "Deseja jogar novamente?", "Jogar Novamente", JOptionPane.YES_NO_OPTION);
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

        public int atualizarEObterScore() {
            score += 5;
            return score;
        }

        private void mostrarMenuAluno(){
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
    
        public AlternativaMouseListener(String alternativaCompleta, ConnectionFactory connectionFactory, Jogo jogo) {
            this.alternativaCompleta = alternativaCompleta;
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
                            String respostaCerta = resultSet.getString("resposta_certa");
                            String justificativa = resultSet.getString("justificativa");
    
                            // Verificando a alternativa selecionada pelo usuário
                            if (alternativaCompleta.equalsIgnoreCase(respostaCerta)) {
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