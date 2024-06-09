package com.sid;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class AdminPerguntas extends JPanel {
    private JFrame adminPerguntas;
    private ConnectionFactory connectionFactory;
    private Image imagemDeFundo;
    private Image blocoAdminPerguntas;
    private ImageIcon editarIcon;
    private ImageIcon deletarIcon;
    private ImageIcon iconeScroll;
    private ArrayList<String> listaPerguntas;
    private DefaultTableModel tableModel;
    private JTable perguntasTable;
    private JButton criarPerguntaButton;

    public AdminPerguntas(JFrame adminperguntas) {
        this.adminPerguntas = adminperguntas;
        connectionFactory = new ConnectionFactory();
        setLayout(new BorderLayout());
        carregarImagens();
        configurarComponentes();
        carregarPerguntas();
    }

    private void carregarImagens() {
        imagemDeFundo = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
        blocoAdminPerguntas = new ImageIcon(getClass().getResource("/images/blocoadminperguntas.png")).getImage();
        editarIcon = new ImageIcon(getClass().getResource("/images/Editar.png"));
        deletarIcon = new ImageIcon(getClass().getResource("/images/Lixo.png"));
        iconeScroll = new ImageIcon(getClass().getResource("/images/slider.png"));
    }

    private void configurarComponentes() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1200, 400));

        JLabel blocoLabel = new JLabel(new ImageIcon(blocoAdminPerguntas));
        blocoLabel.setBounds(50, 50, blocoAdminPerguntas.getWidth(null), blocoAdminPerguntas.getHeight(null));
        layeredPane.add(blocoLabel, JLayeredPane.DEFAULT_LAYER);

        tableModel = new DefaultTableModel(new Object[] { "Pergunta", "Editar", "Deletar" }, 0);
        perguntasTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };

        perguntasTable.getColumnModel().getColumn(1).setCellRenderer(new ButtonRenderer(editarIcon));
        perguntasTable.getColumnModel().getColumn(1).setCellEditor(new ButtonEditor(new JCheckBox(), editarIcon, "Editar", this));
        perguntasTable.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer(deletarIcon));
        perguntasTable.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JCheckBox(), deletarIcon, "Deletar", this));

        perguntasTable.setRowHeight(40);

        JScrollPane scrollPane = new JScrollPane(perguntasTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(1200, 400));

        scrollPane.setBounds(110, 150, 1200, 400);

        // Personaliza a aparência da barra de rolagem vertical
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI(iconeScroll));

        layeredPane.add(scrollPane, JLayeredPane.PALETTE_LAYER);

        criarPerguntaButton = new JButton("+ Criar pergunta");
        criarPerguntaButton.setBackground(Color.GREEN);
        criarPerguntaButton.setForeground(Color.WHITE);
        criarPerguntaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        criarPerguntaButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        criarPerguntaButton.setOpaque(true);
        criarPerguntaButton.setFocusPainted(false);
        criarPerguntaButton.setBounds(1150, 600, 150, 40);
        criarPerguntaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                telaCriarPergunta();
            }
        });
        layeredPane.add(criarPerguntaButton, JLayeredPane.PALETTE_LAYER);

        add(layeredPane, BorderLayout.CENTER);

        // Botão de voltar
        ImageIcon imagemBotao = new ImageIcon(getClass().getResource("/images/botaovoltar.png"));
        JButton botaoVoltar = new JButton("", imagemBotao);
        botaoVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoVoltar.setBorderPainted(false);
        botaoVoltar.setContentAreaFilled(false);
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.setOpaque(false);
        botaoVoltar.setBounds(100, 600, 50, 50);
        botaoVoltar.addActionListener(e -> mostrarMenuProfessor());
        layeredPane.add(botaoVoltar, JLayeredPane.PALETTE_LAYER);

        add(layeredPane, BorderLayout.CENTER);
    }

    public void editarPergunta() {
        int selectedRow = perguntasTable.getSelectedRow();
        if (selectedRow != -1 && selectedRow < tableModel.getRowCount()) {
            String perguntaSelecionada = listaPerguntas.get(selectedRow);
            
            JFrame frameAlterarPerguntas = new JFrame("Alterar Perguntas");
            frameAlterarPerguntas.setSize(1920, 1080);
            frameAlterarPerguntas.setMinimumSize(new Dimension(1920, 1080));
            frameAlterarPerguntas.setMaximumSize(new Dimension(1920, 1080));
            frameAlterarPerguntas.setLocationRelativeTo(null);
            frameAlterarPerguntas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameAlterarPerguntas.setResizable(true);
            AlterarPerguntas telaAlterarPerguntas = new AlterarPerguntas(frameAlterarPerguntas, perguntaSelecionada);
            frameAlterarPerguntas.add(telaAlterarPerguntas);
            frameAlterarPerguntas.setVisible(true);
            adminPerguntas.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma pergunta válida para editar.");
        }
    }

    private void carregarPerguntas() {
        listaPerguntas = new ArrayList<>();
        try (Connection conn = connectionFactory.obtemConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT pergunta FROM perguntas")) {

            while (rs.next()) {
                String pergunta = rs.getString("pergunta");
                listaPerguntas.add(pergunta);
                tableModel.addRow(new Object[] { pergunta, editarIcon, deletarIcon });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarMenuProfessor(){
        adminPerguntas.dispose();

        JFrame frameMenu = new JFrame("Menu");
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
    }
    private void telaCriarPergunta(){
        adminPerguntas.dispose();
        // colocar aqui a logica para abrir a tela esqueceu senha
        JFrame frameCriarPerguntas = new JFrame("Criar Perguntas");
        frameCriarPerguntas.setSize(1920, 1080);
        frameCriarPerguntas.setMinimumSize(new Dimension(1920, 1080));
        frameCriarPerguntas.setMaximumSize(new Dimension(1920, 1080));
        frameCriarPerguntas.setLocationRelativeTo(null);
        frameCriarPerguntas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameCriarPerguntas.setResizable(true);
        CriarPerguntas telaCriarPerguntas = new CriarPerguntas(frameCriarPerguntas);
        frameCriarPerguntas.add(telaCriarPerguntas);
        frameCriarPerguntas.setVisible(true);
    }
    
    public void deletarPergunta() {
        int selectedRow = perguntasTable.getSelectedRow();
        if (selectedRow != -1 && selectedRow < tableModel.getRowCount()) {
            // Pergunta selecionada
            String perguntaRemovida = listaPerguntas.get(selectedRow);
            
            // Confirmação para excluir a pergunta
            int option = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir esta pergunta?", "Confirmação", JOptionPane.YES_NO_OPTION);
            
            if (option == JOptionPane.YES_OPTION) {
                // Remove a pergunta da lista e da tabela
                listaPerguntas.remove(selectedRow);
                tableModel.removeRow(selectedRow);
                
                // Aqui você pode adicionar a lógica para deletar a pergunta do banco de dados
                excluirPerguntaDoBanco(perguntaRemovida);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma pergunta válida para deletar.");
        }
    }
    private void excluirPerguntaDoBanco(String pergunta) {
        try (Connection conn = connectionFactory.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM perguntas WHERE pergunta = ?")) {
            stmt.setString(1, pergunta);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Pergunta excluída com sucesso do banco de dados.");
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir pergunta do banco de dados.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir pergunta do banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer(ImageIcon icon) {
        setIcon(icon);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setIcon((ImageIcon) value);
        return this;
    }
    
}

class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private AdminPerguntas adminPerguntas;

    public ButtonEditor(JCheckBox checkBox, ImageIcon icon, String label, AdminPerguntas adminPerguntas) {
        super(checkBox);
        this.label = label;
        this.adminPerguntas = adminPerguntas;
        button = new JButton();
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setIcon(icon);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                if ("Editar".equals(label)) {
                    adminPerguntas.editarPergunta();
                } else if ("Deletar".equals(label)) {
                    adminPerguntas.deletarPergunta();
                }
            }
        });
    
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        button.setIcon((ImageIcon) value);
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return button.getIcon();
    }

    @Override
    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }
}

class CustomScrollBarUI extends BasicScrollBarUI {
    private ImageIcon thumbIcon;

    public CustomScrollBarUI(ImageIcon thumbIcon) {
        this.thumbIcon = thumbIcon;
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.drawImage(thumbIcon.getImage(), thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, null);
        g2.dispose();
    }
}
