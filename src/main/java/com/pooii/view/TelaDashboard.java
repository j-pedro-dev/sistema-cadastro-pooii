package com.pooii.view;

import com.pooii.model.Usuario;
import com.pooii.service.AutenticacaoService;
import com.pooii.service.SessaoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaDashboard extends JFrame {
    
    private AutenticacaoService autenticacaoService;
    private SessaoService sessaoService;
    private JTable tabelaUsuarios;
    private DefaultTableModel modeloTabela;
    private Runnable onLogout;

    public TelaDashboard(AutenticacaoService autenticacaoService, Runnable onLogout) {
        this.autenticacaoService = autenticacaoService;
        this.sessaoService = SessaoService.getInstancia();
        this.onLogout = onLogout;
        inicializarComponentes();
        carregarUsuarios();
    }

    private void inicializarComponentes() {
        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelCabecalho = new JPanel(new BorderLayout());
        painelCabecalho.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelCabecalho.setBackground(new Color(240, 240, 240));

        JLabel titulo = new JLabel("📊 Dashboard");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        painelCabecalho.add(titulo, BorderLayout.WEST);

        JButton botaoLogout = new JButton("Sair");
        botaoLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazerLogout();
            }
        });
        painelCabecalho.add(botaoLogout, BorderLayout.EAST);

        add(painelCabecalho, BorderLayout.NORTH);

        Usuario usuarioAtual = sessaoService.getUsuarioAtual();
        JPanel painelBemVindo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBemVindo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        painelBemVindo.setBackground(new Color(230, 240, 255));
        
        String nomeUsuario = usuarioAtual != null ? usuarioAtual.getNome() : "Usuário";
        JLabel labelBemVindo = new JLabel("Bem-vindo, " + nomeUsuario + "! | Área restrita - Controle de sessão ativo.");
        labelBemVindo.setFont(new Font("Arial", Font.PLAIN, 14));
        painelBemVindo.add(labelBemVindo);
        
        add(painelBemVindo, BorderLayout.CENTER);

        String[] colunas = {"Nome", "Email", "Cidade"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabela não editável
            }
        };
        
        tabelaUsuarios = new JTable(modeloTabela);
        tabelaUsuarios.setFont(new Font("Arial", Font.PLAIN, 12));
        tabelaUsuarios.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tabelaUsuarios);
        scrollPane.setBorder(BorderFactory.createTitledBorder("👥 Usuários Cadastrados"));
        
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        painelTabela.add(scrollPane, BorderLayout.CENTER);
        
        add(painelTabela, BorderLayout.SOUTH);
    }

    private void carregarUsuarios() {
        modeloTabela.setRowCount(0);
        List<Usuario> usuarios = autenticacaoService.listarUsuarios();
        for (Usuario usuario : usuarios) {
            String cidade = usuario.getEndereco() != null ? usuario.getEndereco().getCidade() : "";
            modeloTabela.addRow(new Object[]{
                usuario.getNome(),
                usuario.getEmail(),
                cidade
            });
        }
    }

    private void fazerLogout() {
        sessaoService.encerrarSessao();
        autenticacaoService.logout();
        
        if (onLogout != null) {
            onLogout.run();
        }
    }
}

