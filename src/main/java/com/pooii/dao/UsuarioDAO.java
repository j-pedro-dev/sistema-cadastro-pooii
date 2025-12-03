package com.pooii.dao;

import com.pooii.model.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UsuarioDAO {
    
    private static final List<Usuario> usuarios = new ArrayList<>();
    private static final AtomicInteger contadorId = new AtomicInteger(1);

    public static void limparUsuariosParaTestes() {
        usuarios.clear();
        contadorId.set(1);
    }

    public Usuario salvar(Usuario usuario) {
        usuario.setId(contadorId.getAndIncrement());
        usuarios.add(usuario);
        return usuario;
    }

    public Usuario buscarPorEmail(String email) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    public Usuario buscarPorCPF(String cpf) {
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        return usuarios.stream()
                .filter(u -> u.getCpf().replaceAll("[^0-9]", "").equals(cpfLimpo))
                .findFirst()
                .orElse(null);
    }

    public Usuario buscarPorEmailESenha(String email, String senha) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email) && u.getSenha().equals(senha))
                .findFirst()
                .orElse(null);
    }

    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }

    public boolean emailExiste(String email) {
        return buscarPorEmail(email) != null;
    }

    public boolean cpfExiste(String cpf) {
        return buscarPorCPF(cpf) != null;
    }
}
