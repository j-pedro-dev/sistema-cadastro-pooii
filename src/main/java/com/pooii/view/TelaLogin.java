package com.pooii.view;

import com.pooii.exception.AutenticacaoException;
import com.pooii.service.AutenticacaoService;
import com.pooii.service.SessaoService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin extends JFrame {
    
    private AutenticacaoService autenticacaoService;
    private SessaoService sessaoService;
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JLabel labelMensagem;
    private Runnable onLoginSucesso;
    private Runnable onIrParaCadastro;

    public TelaLogin(AutenticacaoService autenticacaoService, 
                     Runnable onLoginSucesso, 
                     Runnable onIrParaCadastro) {
        this.autenticacaoService = autenticacaoService;
        this.sessaoService = SessaoService.getInstancia();
        this.onLoginSucesso = onLoginSucesso;
        this.onIrParaCadastro = onIrParaCadastro;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel titulo = new JLabel("🔐 Login");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(titulo);
        painelPrincipal.add(Box.createVerticalStrut(30));

        JLabel labelEmail = new JLabel("E-mail");
        labelEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(labelEmail);
        
        campoEmail = new JTextField();
        campoEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
        campoEmail.setMaximumSize(new Dimension(300, 30));
        campoEmail.setPreferredSize(new Dimension(300, 30));
        painelPrincipal.add(campoEmail);
        painelPrincipal.add(Box.createVerticalStrut(15));

        JLabel labelSenha = new JLabel("Senha");
        labelSenha.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(labelSenha);
        
        campoSenha = new JPasswordField();
        campoSenha.setAlignmentX(Component.CENTER_ALIGNMENT);
        campoSenha.setMaximumSize(new Dimension(300, 30));
        campoSenha.setPreferredSize(new Dimension(300, 30));
        painelPrincipal.add(campoSenha);
        painelPrincipal.add(Box.createVerticalStrut(20));

        labelMensagem = new JLabel(" ");
        labelMensagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelMensagem.setForeground(Color.RED);
        painelPrincipal.add(labelMensagem);
        painelPrincipal.add(Box.createVerticalStrut(10));

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton botaoEntrar = new JButton("Entrar");
        botaoEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazerLogin();
            }
        });
        painelBotoes.add(botaoEntrar);

        JButton botaoCadastro = new JButton("Não tenho conta - Cadastrar");
        botaoCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (onIrParaCadastro != null) {
                    onIrParaCadastro.run();
                }
            }
        });
        painelBotoes.add(botaoCadastro);
        painelBotoes.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(painelBotoes);

        add(painelPrincipal, BorderLayout.CENTER);
    }

    private void fazerLogin() {
        try {
            labelMensagem.setText(" ");
            labelMensagem.setForeground(Color.RED);

            String email = campoEmail.getText().trim();
            String senha = new String(campoSenha.getPassword());

            autenticacaoService.autenticar(email, senha);
            sessaoService.iniciarSessao(autenticacaoService.getUsuarioLogado());

            labelMensagem.setForeground(new Color(0, 128, 0));
            labelMensagem.setText("✓ Login realizado com sucesso!");

            Timer timer = new Timer(1000, e -> {
                if (onLoginSucesso != null) {
                    onLoginSucesso.run();
                }
            });
            timer.setRepeats(false);
            timer.start();

        } catch (AutenticacaoException e) {
            labelMensagem.setText("✗ " + e.getMessage());
        } catch (Exception e) {
            labelMensagem.setText("✗ Erro inesperado: " + e.getMessage());
        }
    }
}

