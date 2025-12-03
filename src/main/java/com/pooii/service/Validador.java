package com.pooii.service;

import com.pooii.exception.ValidacaoException;
import com.pooii.model.Endereco;
import com.pooii.model.Usuario;

public class Validador {
    
    private static final String REGEX_NOME = "^[A-Za-zÀ-ÿ\\s]{3,}$";
    private static final String REGEX_CPF_FORMATO = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$";
    private static final String REGEX_TELEFONE = "^\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4}$";
    private static final String REGEX_EMAIL = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
    private static final String REGEX_SENHA = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,}$";
    private static final int IDADE_MINIMA = 18;

    public void validarUsuario(Usuario usuario) throws ValidacaoException {
        if (usuario == null) {
            throw new ValidacaoException("Usuário não pode ser nulo");
        }

        validarNome(usuario.getNome());
        validarIdade(usuario.getIdade());
        validarCPF(usuario.getCpf());
        validarTelefone(usuario.getTelefone());
        validarEndereco(usuario.getEndereco());
        validarEmail(usuario.getEmail());
        validarSenha(usuario.getSenha());
    }

    public void validarNome(String nome) throws ValidacaoException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidacaoException("Nome é obrigatório");
        }

        if (!nome.matches(REGEX_NOME)) {
            throw new ValidacaoException("Nome deve conter apenas letras e espaços, com mínimo de 3 caracteres");
        }
    }

    public void validarIdade(Integer idade) throws ValidacaoException {
        if (idade == null) {
            throw new ValidacaoException("Idade é obrigatória");
        }

        if (idade < IDADE_MINIMA) {
            throw new ValidacaoException("Idade deve ser maior ou igual a " + IDADE_MINIMA + " anos");
        }

        if (idade > 150) {
            throw new ValidacaoException("Idade inválida");
        }
    }

    public void validarCPF(String cpf) throws ValidacaoException {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new ValidacaoException("CPF é obrigatório");
        }

        String cpfNumerico = cpf.replaceAll("[^0-9]", "");

        if (!cpf.matches(REGEX_CPF_FORMATO)) {
            throw new ValidacaoException("CPF deve estar no formato: 123.456.789-00");
        }

        if (cpfNumerico.length() != 11) {
            throw new ValidacaoException("CPF deve conter 11 dígitos");
        }

        if (cpfNumerico.matches("(\\d)\\1{10}")) {
            throw new ValidacaoException("CPF inválido: todos os dígitos são iguais");
        }

        if (!validarDigitosVerificadoresCPF(cpfNumerico)) {
            throw new ValidacaoException("CPF inválido: dígitos verificadores incorretos");
        }
    }

    private boolean validarDigitosVerificadoresCPF(String cpf) {
        try {
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int resto = (soma * 10) % 11;
            if (resto == 10) resto = 0;
            
            if (resto != Character.getNumericValue(cpf.charAt(9))) {
                return false;
            }

            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            resto = (soma * 10) % 11;
            if (resto == 10) resto = 0;
            
            return resto == Character.getNumericValue(cpf.charAt(10));
        } catch (Exception e) {
            return false;
        }
    }

    public void validarTelefone(String telefone) throws ValidacaoException {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new ValidacaoException("Telefone é obrigatório");
        }

        if (!telefone.matches(REGEX_TELEFONE)) {
            throw new ValidacaoException("Telefone deve estar no formato: (XX) XXXXX-XXXX ou (XX) XXXX-XXXX");
        }
    }

    public void validarEndereco(Endereco endereco) throws ValidacaoException {
        if (endereco == null) {
            throw new ValidacaoException("Endereço é obrigatório");
        }

        if (endereco.getRua() == null || endereco.getRua().trim().isEmpty()) {
            throw new ValidacaoException("Rua é obrigatória");
        }

        if (endereco.getNumero() == null || endereco.getNumero().trim().isEmpty()) {
            throw new ValidacaoException("Número é obrigatório");
        }

        if (endereco.getCidade() == null || endereco.getCidade().trim().isEmpty()) {
            throw new ValidacaoException("Cidade é obrigatória");
        }

        if (endereco.getEstado() == null || endereco.getEstado().trim().isEmpty()) {
            throw new ValidacaoException("Estado é obrigatório");
        }

        if (endereco.getEstado().length() != 2) {
            throw new ValidacaoException("Estado deve ser uma UF de 2 caracteres");
        }
    }

    public void validarEmail(String email) throws ValidacaoException {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidacaoException("Email é obrigatório");
        }

        if (!email.toLowerCase().matches(REGEX_EMAIL)) {
            throw new ValidacaoException("Email inválido");
        }
    }

    public void validarSenha(String senha) throws ValidacaoException {
        if (senha == null || senha.trim().isEmpty()) {
            throw new ValidacaoException("Senha é obrigatória");
        }

        if (!senha.matches(REGEX_SENHA)) {
            throw new ValidacaoException("Senha deve ter no mínimo 8 caracteres, contendo letras e números");
        }
    }
}
