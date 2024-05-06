package com.sid;

import javax.swing.*;
import java.awt.*;
import java.sql.*;


public class Jogo extends JPanel{
    private Image imagemDeFundo;
    private Image blocoJogo;
    private Image retanguloOpaco;
    private JFrame jogo;
    private JLabel labelPergunta;
    private JLabel labelAlternativa1;
    private JLabel labelAlternativa2;
    private JLabel labelAlternativa3;
    private JLabel labelAlternativa4;
    private JLabel labelJustificativa;
    private int ultimoIdPerguntaExibido = 0;
    private ConnectionFactory connectionFactory; 
    


    public Jogo(JFrame frameJogo) {
        this.jogo = frameJogo;
        setLayout(new GridBagLayout());
        carregarImagens();
        configurarComponentes();
        this.connectionFactory = new ConnectionFactory();
        exibirPergunta(); 
    }

    public void carregarImagens(){
        imagemDeFundo = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
        blocoJogo = new ImageIcon(getClass().getResource("/images/blocoquiz.png")).getImage();
        retanguloOpaco = new ImageIcon(getClass().getResource("/images/retanguloopaco.png")).getImage();


    }

    public void configurarComponentes(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Faz com que o componente ocupe toda a linha
        gbc.fill = GridBagConstraints.HORIZONTAL; // Faz com que o componente expanda horizontalmente
        gbc.insets = new Insets(10, 180, 10, 180);
        
        labelPergunta = new JLabel();
        labelPergunta.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER; // Ancora a pergunta no centro
        add(labelPergunta, gbc);
        
        labelAlternativa1 = new JLabel();
        labelAlternativa1.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx=1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 50, 0); // Espaçamento de 20px entre a alternativa 1 e 3
        gbc.anchor = GridBagConstraints.CENTER; // Ancora as alternativas no centro
        add(labelAlternativa1, gbc);
        
        labelAlternativa2 = new JLabel();
        labelAlternativa2.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx=2;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 700, 10, 0); // Espaçamento de 360px entre a alternativa 1 e 2
        add(labelAlternativa2, gbc);
        
        labelAlternativa3 = new JLabel();
        labelAlternativa3.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx=1;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 50, 0); // Espaçamento de 20px entre a alternativa 3 e 1
        add(labelAlternativa3, gbc);
        
        labelAlternativa4 = new JLabel();
        labelAlternativa4.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx=2;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 700, 10, 0); // Espaçamento de 360px entre a alternativa 3 e 4
        add(labelAlternativa4, gbc);
    }
    
    
    public void exibirPergunta() {
        try (Connection connection = connectionFactory.obtemConexao()) {
            // Query para selecionar a próxima pergunta com base no último ID de pergunta exibido
            String query = "SELECT * FROM perguntas WHERE id_perguntas > ? ORDER BY id_perguntas LIMIT 1";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // Defina o ID da última pergunta exibida + 1 para obter a próxima pergunta
                statement.setInt(1, ultimoIdPerguntaExibido + 1);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // Obtenha os dados da pergunta e suas respostas
                        String pergunta = resultSet.getString("pergunta");
                        String respostaCerta = resultSet.getString("resposta_certa");
                        String respostaErrada1 = resultSet.getString("resposta_errada1");
                        String respostaErrada2 = resultSet.getString("resposta_errada2");
                        String respostaErrada3 = resultSet.getString("resposta_errada3");
                        String justificativa = resultSet.getString("justificativa");
    
                        // Exiba a pergunta e as respostas na interface
                        labelPergunta.setText(pergunta);
                        // Suponha que 'labelAlternativa1' até 'labelAlternativa4' sejam JLabels onde você deseja exibir as respostas
                        labelAlternativa1.setText("a)"+respostaCerta);
                        labelAlternativa2.setText("b)"+respostaErrada1);
                        labelAlternativa3.setText("c)"+respostaErrada2);
                        labelAlternativa4.setText("d)"+respostaErrada3);
                        // Suponha que 'labelJustificativa' seja um JLabel onde você deseja exibir a justificativa
                        //labelJustificativa.setText(justificativa);
    
                        // Atualize o ID da última pergunta exibida
                        ultimoIdPerguntaExibido = resultSet.getInt("id_perguntas");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        int x = (getWidth() - blocoJogo.getWidth(null)) / 2;
        int y = (getHeight() - blocoJogo.getHeight(null)) / 2;
        
       
        g.drawImage(blocoJogo, x, y, blocoJogo.getWidth(null), blocoJogo.getHeight(null), this);
        g.drawImage(retanguloOpaco, x+22, y+130, retanguloOpaco.getWidth(null),retanguloOpaco.getHeight(null),this );
        g.drawImage(retanguloOpaco, x+710, y+130, retanguloOpaco.getWidth(null),retanguloOpaco.getHeight(null),this );
        g.drawImage(retanguloOpaco, x+22, y+230, retanguloOpaco.getWidth(null),retanguloOpaco.getHeight(null),this );
        g.drawImage(retanguloOpaco, x+710, y+230, retanguloOpaco.getWidth(null),retanguloOpaco.getHeight(null),this );
    }
}
