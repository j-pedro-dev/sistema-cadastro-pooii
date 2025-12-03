# Relatório Técnico - Sistema de Cadastro POO II

## 1. Contexto Comercial

Sistema desktop para cadastro e gestão de usuários em empresas.

## 2. Validações Implementadas

**Estruturas de Decisão:**
- Validação de idade (if/else)
- Validação de CPF com dígitos verificadores
- Verificação de duplicatas

**Expressões Regulares:**
- Nome: `^[A-Za-zÀ-ÿ\\s]{3,}$`
- CPF: `^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$`
- Telefone: `^\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4}$`
- Email: `^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$`
- Senha: `^(?=.*[a-zA-Z])(?=.*[0-9]).{8,}$`

**Tratamento de Exceções:**
- `ValidacaoException` e `AutenticacaoException`
- Mensagens claras na GUI e back-end

## 3. Estrutura do Banco de Dados

Armazenamento em memória (`List<Usuario>`):

```
USUARIOS
├── id, nome, idade
├── cpf (UNIQUE), telefone
├── rua, numero, cidade, estado
├── email (UNIQUE), senha
```

## 4. Fluxo de Autenticação

1. Usuário insere email e senha
2. Sistema valida e busca usuário
3. Cria sessão via `SessaoService` (Singleton)
4. Redireciona para Dashboard
5. Logout encerra sessão

## 5. Telas

- **Cadastro:** Formulário com validações
- **Login:** Autenticação
- **Dashboard:** Lista de usuários

## 6. Boas Práticas

- POO (encapsulamento, separação de responsabilidades)
- Padrões: DAO, Singleton
- Tratamento de exceções
- Validação no front-end e back-end

## 7. Testes

Testes JUnit para validações e autenticação. Executar: `mvn test`
