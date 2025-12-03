package com.pooii.service;

import com.pooii.dao.UsuarioDAO;
import com.pooii.exception.AutenticacaoException;
import com.pooii.exception.ValidacaoException;
import com.pooii.model.Usuario;

public class AutenticacaoService {
    
    private final UsuarioDAO usuarioDAO;
    private final Validador validador;
    private Usuario usuarioLogado;

    public AutenticacaoService() {
        this.usuarioDAO = new UsuarioDAO();
        this.validador = new Validador();
    }

    public void cadastrarUsuario(Usuario usuario) throws ValidacaoException {
        try {
            validador.validarUsuario(usuario);

            if (usuarioDAO.emailExiste(usuario.getEmail())) {
                throw new ValidacaoException("Email já cadastrado");
            }

            if (usuarioDAO.cpfExiste(usuario.getCpf())) {
                throw new ValidacaoException("CPF já cadastrado");
            }

            usuarioDAO.salvar(usuario);
        } catch (ValidacaoException e) {
            throw e;
        } catch (Exception e) {
            throw new ValidacaoException("Erro ao cadastrar usuário: " + e.getMessage(), e);
        }
    }

    public void autenticar(String email, String senha) throws AutenticacaoException {
        try {
            if (email == null || email.trim().isEmpty()) {
                throw new AutenticacaoException("Email é obrigatório");
            }

            if (senha == null || senha.trim().isEmpty()) {
                throw new AutenticacaoException("Senha é obrigatória");
            }

            Usuario usuario = usuarioDAO.buscarPorEmailESenha(email, senha);

            if (usuario == null) {
                throw new AutenticacaoException("Email ou senha incorretos");
            }

            this.usuarioLogado = usuario;
        } catch (AutenticacaoException e) {
            throw e;
        } catch (Exception e) {
            throw new AutenticacaoException("Erro ao autenticar: " + e.getMessage(), e);
        }
    }

    public void logout() {
        this.usuarioLogado = null;
    }

    public boolean isAutenticado() {
        return usuarioLogado != null;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public java.util.List<Usuario> listarUsuarios() {
        return usuarioDAO.listarTodos();
    }
}
