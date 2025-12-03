package com.pooii.service;

import com.pooii.exception.AutenticacaoException;
import com.pooii.exception.ValidacaoException;
import com.pooii.dao.UsuarioDAO;
import com.pooii.model.Endereco;
import com.pooii.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AutenticacaoServiceTest {

    private AutenticacaoService autenticacaoService;

    @BeforeEach
    void setUp() {
        UsuarioDAO.limparUsuariosParaTestes();
        autenticacaoService = new AutenticacaoService();
    }

    @Test
    void testCadastrarUsuarioValido() throws ValidacaoException {
        Endereco endereco = new Endereco("Rua das Flores", "123", "São Paulo", "SP");
        Usuario usuario = new Usuario(
            "João da Silva",
            25,
            "123.456.789-09",
            "(11) 99999-9999",
            endereco,
            "joao@email.com",
            "senha123"
        );

        assertDoesNotThrow(() -> {
            autenticacaoService.cadastrarUsuario(usuario);
        });
    }

    @Test
    void testCadastrarUsuarioEmailDuplicado() throws ValidacaoException {
        Endereco endereco = new Endereco("Rua das Flores", "123", "São Paulo", "SP");
        Usuario usuario1 = new Usuario(
            "João da Silva",
            25,
            "123.456.789-09",
            "(11) 99999-9999",
            endereco,
            "joao@email.com",
            "senha123"
        );

        autenticacaoService.cadastrarUsuario(usuario1);

        Usuario usuario2 = new Usuario(
            "Maria Silva",
            30,
            "987.654.321-00",
            "(11) 88888-8888",
            endereco,
            "joao@email.com", // Mesmo email
            "senha456"
        );

        assertThrows(ValidacaoException.class, () -> {
            autenticacaoService.cadastrarUsuario(usuario2);
        });
    }

    @Test
    void testAutenticarUsuarioValido() throws ValidacaoException, AutenticacaoException {
        Endereco endereco = new Endereco("Rua das Flores", "123", "São Paulo", "SP");
        Usuario usuario = new Usuario(
            "João da Silva",
            25,
            "123.456.789-09",
            "(11) 99999-9999",
            endereco,
            "joao@email.com",
            "senha123"
        );

        autenticacaoService.cadastrarUsuario(usuario);
        autenticacaoService.autenticar("joao@email.com", "senha123");

        assertTrue(autenticacaoService.isAutenticado());
        assertNotNull(autenticacaoService.getUsuarioLogado());
    }

    @Test
    void testAutenticarUsuarioSenhaIncorreta() throws ValidacaoException {
        Endereco endereco = new Endereco("Rua das Flores", "123", "São Paulo", "SP");
        Usuario usuario = new Usuario(
            "João da Silva",
            25,
            "123.456.789-09",
            "(11) 99999-9999",
            endereco,
            "joao@email.com",
            "senha123"
        );

        autenticacaoService.cadastrarUsuario(usuario);

        assertThrows(AutenticacaoException.class, () -> {
            autenticacaoService.autenticar("joao@email.com", "senhaErrada");
        });
    }

    @Test
    void testLogout() throws ValidacaoException, AutenticacaoException {
        Endereco endereco = new Endereco("Rua das Flores", "123", "São Paulo", "SP");
        Usuario usuario = new Usuario(
            "João da Silva",
            25,
            "123.456.789-09",
            "(11) 99999-9999",
            endereco,
            "joao@email.com",
            "senha123"
        );

        autenticacaoService.cadastrarUsuario(usuario);
        autenticacaoService.autenticar("joao@email.com", "senha123");
        assertTrue(autenticacaoService.isAutenticado());

        autenticacaoService.logout();
        assertFalse(autenticacaoService.isAutenticado());
    }
}

