package com.pooii;

import com.pooii.service.AutenticacaoService;
import com.pooii.service.SessaoService;
import com.pooii.view.TelaCadastro;
import com.pooii.view.TelaDashboard;
import com.pooii.view.TelaLogin;

import javax.swing.*;

public class Main {
    
    private static AutenticacaoService autenticacaoService;
    private static JFrame telaAtual;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        autenticacaoService = new AutenticacaoService();
        SessaoService sessaoService = SessaoService.getInstancia();
        if (sessaoService.temSessaoAtiva()) {
            mostrarDashboard();
        } else {
            mostrarLogin();
        }
    }

    private static void mostrarLogin() {
        if (telaAtual != null) {
            telaAtual.dispose();
        }

        TelaLogin telaLogin = new TelaLogin(
            autenticacaoService,
            () -> mostrarDashboard(),
            () -> mostrarCadastro()
        );
        telaLogin.setVisible(true);
        telaAtual = telaLogin;
    }

    private static void mostrarCadastro() {
        if (telaAtual != null) {
            telaAtual.dispose();
        }

        TelaCadastro telaCadastro = new TelaCadastro(
            autenticacaoService,
            () -> mostrarLogin()
        );
        telaCadastro.setVisible(true);
        telaAtual = telaCadastro;
    }

    private static void mostrarDashboard() {
        if (telaAtual != null) {
            telaAtual.dispose();
        }

        TelaDashboard telaDashboard = new TelaDashboard(
            autenticacaoService,
            () -> mostrarLogin()
        );
        telaDashboard.setVisible(true);
        telaAtual = telaDashboard;
    }
}

