package com.sid;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ranking extends JPanel {
    private JFrame ranking;
    private Image imagemDeFundo;
    private Image blocoRanking;
    private String email;
    private JTable tabela;
    private ConnectionFactory connectionFactory;

    public Ranking(JFrame ranking, String email) {
        this.email = email;
        this.ranking = ranking;
        connectionFactory = new ConnectionFactory();
        setLayout(new GridBagLayout());
        carregarImagens();
        configurarComponentes();
        popularTabelaDoBancoDeDados();
    }

    public void carregarImagens() {
        imagemDeFundo = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
        blocoRanking = new ImageIcon(getClass().getResource("/images/blocoranking.png")).getImage();
    }

    public void configurarComponentes() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Faz com que o componente ocupe toda a linha
        gbc.fill = GridBagConstraints.HORIZONTAL; // Faz com que o componente expanda horizontalmente
        gbc.insets = new Insets(5, 0, 5, 0); // Espaçamento padrão

        String[] columnNames = {"LUGAR", "NOME", "PONTUAÇÃO"};
        DefaultTableModel modeloTabela = new DefaultTableModel(columnNames, 0);
        tabela = new JTable(modeloTabela);
        tabela.setFont(new Font("Arial", Font.BOLD, 20));
        tabela.setRowHeight(40);

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(40, 100, 5, 100);
        add(scrollPane, gbc);

        // Botão "Voltar"
        ImageIcon imagemBotaoVoltar = new ImageIcon(getClass().getResource("/images/botaovoltar.png"));
        JButton botaoVoltar = new JButton(imagemBotaoVoltar);
        botaoVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoVoltar.setBorderPainted(false);
        botaoVoltar.setContentAreaFilled(false);
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.setOpaque(false);
        gbc.insets = new Insets(5, 10, 0, 750);
        gbc.gridy = 1;
        botaoVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                voltarAoMenuCorrespondente();
            }
        });
        add(botaoVoltar, gbc);
    }

    private void popularTabelaDoBancoDeDados() {
        DefaultTableModel modeloTabela = (DefaultTableModel) tabela.getModel();
        modeloTabela.setRowCount(0); // Limpar dados existentes
    
        try {
            // Estabelecer a conexão com o banco de dados
            Connection conn = connectionFactory.obtemConexao();
            
            // Consulta SQL para obter os dados do ranking com o nome do aluno e a posição correta
            String sql = "SELECT " +
                         "ROW_NUMBER() OVER (ORDER BY r.pontuacao DESC) AS lugar, " +
                         "a.nome_aluno AS nome, " +
                         "r.pontuacao " +
                         "FROM ranking r " +
                         "INNER JOIN aluno a ON r.id_aluno_popula = a.id_aluno " +
                         "ORDER BY r.pontuacao DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
    
            // Iterar sobre os resultados e adicionar à tabela
            while (rs.next()) {
                String lugar = rs.getString("lugar");
                String nome = rs.getString("nome");
                String pontuacao = rs.getString("pontuacao");
    
                modeloTabela.addRow(new Object[]{lugar, nome, pontuacao});
            }
    
            // Fechar recursos
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    private void voltarAoMenuCorrespondente() {
        switch (TelaLogin.tipoUsuario) {
            case "aluno":
                mostrarMenuAluno();
                break;
            case "professor":
                mostrarMenuProfessor();
                break;
            default:
                System.err.println("Tipo de usuário desconhecido: " + TelaLogin.tipoUsuario);
                break;
        }
    }

    private void mostrarMenuAluno() {
        ranking.dispose();
        JFrame frameMenu = new JFrame("Menu Aluno");
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

    private void mostrarMenuProfessor() {
        ranking.dispose();
        JFrame frameMenu = new JFrame("Menu Professor");
        frameMenu.setSize(1280, 720);
        frameMenu.setMinimumSize(new Dimension(1280, 720));
        frameMenu.setMaximumSize(new Dimension(1920, 1080));
        frameMenu.setLocationRelativeTo(null);
        frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMenu.setResizable(true);
        MenuProfessor menuProfessor = new MenuProfessor(frameMenu);
        frameMenu.add(menuProfessor);
        frameMenu.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        int x = (getWidth() - blocoRanking.getWidth(null)) / 2;
        int y = (getHeight() - blocoRanking.getHeight(null)) / 2;
        g.drawImage(blocoRanking, x, y, blocoRanking.getWidth(null), blocoRanking.getHeight(null), this);
    }
}
