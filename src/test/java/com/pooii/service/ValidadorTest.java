package com.pooii.service;

import com.pooii.exception.ValidacaoException;
import com.pooii.model.Endereco;
import com.pooii.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorTest {

    private Validador validador;

    @BeforeEach
    void setUp() {
        validador = new Validador();
    }

    @Test
    void testValidarNomeValido() throws ValidacaoException {
        validador.validarNome("João da Silva");
        assertTrue(true);
    }

    @Test
    void testValidarNomeInvalidoVazio() {
        assertThrows(ValidacaoException.class, () -> {
            validador.validarNome("");
        });
    }

    @Test
    void testValidarNomeInvalidoComNumeros() {
        assertThrows(ValidacaoException.class, () -> {
            validador.validarNome("João123");
        });
    }

    @Test
    void testValidarNomeInvalidoMuitoCurto() {
        assertThrows(ValidacaoException.class, () -> {
            validador.validarNome("Jo");
        });
    }

    @Test
    void testValidarIdadeValida() throws ValidacaoException {
        validador.validarIdade(25);
        assertTrue(true);
    }

    @Test
    void testValidarIdadeInvalidaMenorQue18() {
        assertThrows(ValidacaoException.class, () -> {
            validador.validarIdade(17);
        });
    }

    @Test
    void testValidarIdadeInvalidaNula() {
        assertThrows(ValidacaoException.class, () -> {
            validador.validarIdade(null);
        });
    }

    @Test
    void testValidarCPFValido() throws ValidacaoException {
        validador.validarCPF("123.456.789-09"); // CPF válido (dígitos verificadores corretos)
        assertTrue(true);
    }

    @Test
    void testValidarCPFInvalidoFormato() {
        assertThrows(ValidacaoException.class, () -> {
            validador.validarCPF("12345678909");
        });
    }

    @Test
    void testValidarCPFInvalidoDigitosVerificadores() {
        assertThrows(ValidacaoException.class, () -> {
            validador.validarCPF("123.456.789-00");
        });
    }

    @Test
    void testValidarCPFInvalidoTodosDigitosIguais() {
        assertThrows(ValidacaoException.class, () -> {
            validador.validarCPF("111.111.111-11");
        });
    }

    @Test
    void testValidarTelefoneValido() throws ValidacaoException {
        validador.validarTelefone("(11) 99999-9999");
        assertTrue(true);
    }

    @Test
    void testValidarTelefoneInvalido() {
        assertThrows(ValidacaoException.class, () -> {
            validador.validarTelefone("11999999999");
        });
    }

    @Test
    void testValidarEmailValido() throws ValidacaoException {
        validador.validarEmail("joao@email.com");
        assertTrue(true);
    }

    @Test
    void testValidarEmailInvalido() {
        assertThrows(ValidacaoException.class, () -> {
            validador.validarEmail("email-invalido");
        });
    }

    @Test
    void testValidarSenhaValida() throws ValidacaoException {
        validador.validarSenha("senha123");
        assertTrue(true);
    }

    @Test
    void testValidarSenhaInvalidaSemNumeros() {
        assertThrows(ValidacaoException.class, () -> {
            validador.validarSenha("senhaabc");
        });
    }

    @Test
    void testValidarSenhaInvalidaMuitoCurta() {
        assertThrows(ValidacaoException.class, () -> {
            validador.validarSenha("senha1");
        });
    }

    @Test
    void testValidarEnderecoValido() throws ValidacaoException {
        Endereco endereco = new Endereco("Rua das Flores", "123", "São Paulo", "SP");
        validador.validarEndereco(endereco);
        assertTrue(true);
    }

    @Test
    void testValidarEnderecoInvalidoRuaVazia() {
        Endereco endereco = new Endereco("", "123", "São Paulo", "SP");
        assertThrows(ValidacaoException.class, () -> {
            validador.validarEndereco(endereco);
        });
    }

    @Test
    void testValidarUsuarioCompletoValido() throws ValidacaoException {
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
        validador.validarUsuario(usuario);
        assertTrue(true);
    }

    @Test
    void testValidarUsuarioInvalidoNulo() {
        assertThrows(ValidacaoException.class, () -> {
            validador.validarUsuario(null);
        });
    }
}

