package com.pooii.view;

import com.pooii.exception.ValidacaoException;
import com.pooii.model.Endereco;
import com.pooii.model.Usuario;
import com.pooii.service.AutenticacaoService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastro extends JFrame {
    
    private AutenticacaoService autenticacaoService;
    private JTextField campoNome;
    private JTextField campoIdade;
    private JTextField campoCPF;
    private JTextField campoTelefone;
    private JTextField campoRua;
    private JTextField campoNumero;
    private JTextField campoCidade;
    private JComboBox<String> campoEstado;
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JLabel labelMensagem;
    private Runnable onCadastroSucesso;

    public TelaCadastro(AutenticacaoService autenticacaoService, Runnable onCadastroSucesso) {
        this.autenticacaoService = autenticacaoService;
        this.onCadastroSucesso = onCadastroSucesso;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Cadastro de Usuário");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("📋 Cadastro de Usuário");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(titulo);
        painelPrincipal.add(Box.createVerticalStrut(20));

        campoNome = criarCampoTexto("Nome Completo *", painelPrincipal);
        campoIdade = criarCampoTexto("Idade *", painelPrincipal);
        campoCPF = criarCampoTexto("CPF * (123.456.789-00)", painelPrincipal);
        campoTelefone = criarCampoTexto("Telefone * ((XX) XXXXX-XXXX)", painelPrincipal);
        
        JLabel labelEndereco = new JLabel("📍 Endereço");
        labelEndereco.setFont(new Font("Arial", Font.BOLD, 14));
        labelEndereco.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(labelEndereco);
        painelPrincipal.add(Box.createVerticalStrut(10));

        campoRua = criarCampoTexto("Rua *", painelPrincipal);
        campoNumero = criarCampoTexto("Número *", painelPrincipal);
        campoCidade = criarCampoTexto("Cidade *", painelPrincipal);
        
        JLabel labelEstado = new JLabel("Estado *");
        labelEstado.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(labelEstado);
        String[] estados = {"", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", 
                          "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", 
                          "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
        campoEstado = new JComboBox<>(estados);
        campoEstado.setAlignmentX(Component.CENTER_ALIGNMENT);
        campoEstado.setMaximumSize(new Dimension(400, 30));
        campoEstado.setPreferredSize(new Dimension(400, 30));
        painelPrincipal.add(campoEstado);
        painelPrincipal.add(Box.createVerticalStrut(10));

        campoEmail = criarCampoTexto("E-mail *", painelPrincipal);
        campoSenha = criarCampoPassword("Senha * (mín. 8 caracteres, letras e números)", painelPrincipal);

        labelMensagem = new JLabel(" ");
        labelMensagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelMensagem.setForeground(Color.RED);
        painelPrincipal.add(Box.createVerticalStrut(10));
        painelPrincipal.add(labelMensagem);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrar();
            }
        });
        painelBotoes.add(botaoCadastrar);

        JButton botaoLogin = new JButton("Já tenho conta - Fazer Login");
        botaoLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (onCadastroSucesso != null) {
                    onCadastroSucesso.run();
                }
            }
        });
        painelBotoes.add(botaoLogin);
        painelBotoes.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(painelBotoes);

        JScrollPane scrollPane = new JScrollPane(painelPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JTextField criarCampoTexto(String label, JPanel painel) {
        JLabel jLabel = new JLabel(label);
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.add(jLabel);
        
        JTextField campo = new JTextField();
        campo.setAlignmentX(Component.CENTER_ALIGNMENT);
        campo.setMaximumSize(new Dimension(400, 30));
        campo.setPreferredSize(new Dimension(400, 30));
        painel.add(campo);
        painel.add(Box.createVerticalStrut(10));
        
        return campo;
    }

    private JPasswordField criarCampoPassword(String label, JPanel painel) {
        JLabel jLabel = new JLabel(label);
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.add(jLabel);
        
        JPasswordField campo = new JPasswordField();
        campo.setAlignmentX(Component.CENTER_ALIGNMENT);
        campo.setMaximumSize(new Dimension(400, 30));
        campo.setPreferredSize(new Dimension(400, 30));
        painel.add(campo);
        painel.add(Box.createVerticalStrut(10));
        
        return campo;
    }

    private void cadastrar() {
        try {
            labelMensagem.setText(" ");
            labelMensagem.setForeground(Color.RED);

            String nome = campoNome.getText().trim();
            Integer idade = null;
            try {
                idade = Integer.parseInt(campoIdade.getText().trim());
            } catch (NumberFormatException e) {
                throw new ValidacaoException("Idade deve ser um número válido");
            }
            String cpf = campoCPF.getText().trim();
            String telefone = campoTelefone.getText().trim();
            String rua = campoRua.getText().trim();
            String numero = campoNumero.getText().trim();
            String cidade = campoCidade.getText().trim();
            String estado = (String) campoEstado.getSelectedItem();
            String email = campoEmail.getText().trim();
            String senha = new String(campoSenha.getPassword());

            Endereco endereco = new Endereco(rua, numero, cidade, estado);
            Usuario usuario = new Usuario(nome, idade, cpf, telefone, endereco, email, senha);

            autenticacaoService.cadastrarUsuario(usuario);

            labelMensagem.setForeground(new Color(0, 128, 0));
            labelMensagem.setText("✓ Cadastro realizado com sucesso!");
            
            limparCampos();
            
            Timer timer = new Timer(1500, e -> {
                if (onCadastroSucesso != null) {
                    onCadastroSucesso.run();
                }
            });
            timer.setRepeats(false);
            timer.start();

        } catch (ValidacaoException e) {
            labelMensagem.setText("✗ " + e.getMessage());
        } catch (Exception e) {
            labelMensagem.setText("✗ Erro inesperado: " + e.getMessage());
        }
    }

    private void limparCampos() {
        campoNome.setText("");
        campoIdade.setText("");
        campoCPF.setText("");
        campoTelefone.setText("");
        campoRua.setText("");
        campoNumero.setText("");
        campoCidade.setText("");
        campoEstado.setSelectedIndex(0);
        campoEmail.setText("");
        campoSenha.setText("");
    }
}

