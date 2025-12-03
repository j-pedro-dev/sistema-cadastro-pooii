package com.pooii.service;

import com.pooii.model.Usuario;

public class SessaoService {
    
    private static SessaoService instancia;
    private Usuario usuarioAtual;

    private SessaoService() {
    }

    public static synchronized SessaoService getInstancia() {
        if (instancia == null) {
            instancia = new SessaoService();
        }
        return instancia;
    }

    public void iniciarSessao(Usuario usuario) {
        this.usuarioAtual = usuario;
    }

    public void encerrarSessao() {
        this.usuarioAtual = null;
    }

    public boolean temSessaoAtiva() {
        return usuarioAtual != null;
    }

    public Usuario getUsuarioAtual() {
        return usuarioAtual;
    }
}
