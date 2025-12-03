# Sistema de Cadastro - POO II

Sistema desktop de cadastro e gestão de usuários desenvolvido em Java com Swing.

## Como Executar

### IntelliJ IDEA (Recomendado)

1. Abra o IntelliJ IDEA
2. File → Open → Selecione a pasta do projeto
3. Aguarde o Maven baixar as dependências automaticamente
4. Clique com botão direito em `src/main/java/com/pooii/Main.java`
5. Selecione **Run 'Main.main()'**

### Maven (Linha de Comando)

```bash
mvn clean compile
mvn exec:java
```

## Estrutura

```
src/main/java/com/pooii/
├── Main.java
├── model/       (Usuario, Endereco)
├── dao/         (UsuarioDAO)
├── service/     (Validador, AutenticacaoService, SessaoService)
├── exception/   (ValidacaoException, AutenticacaoException)
└── view/        (TelaCadastro, TelaLogin, TelaDashboard)
```

## Funcionalidades

- Cadastro de usuários com validação
- Login e autenticação
- Dashboard com lista de usuários
- Controle de sessão

## Tecnologias

Java 21, Swing, JUnit 5, Maven
