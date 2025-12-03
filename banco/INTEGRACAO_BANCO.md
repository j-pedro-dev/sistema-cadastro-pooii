# Integração de Banco de Dados SQL

Guia para integrar MySQL/MariaDB no projeto.

## 1. Adicionar Dependência

No `pom.xml`, descomente:
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.0.33</version>
</dependency>
```

## 2. Criar Banco

```bash
mysql -u root -p < banco/schema.sql
```

## 3. Criar ConexaoBD.java

```java
package com.pooii.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_cadastro";
    private static final String USUARIO = "root";
    private static final String SENHA = "sua_senha";
    
    public static Connection obterConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
```

## 4. Modificar UsuarioDAO

Substitua os métodos para usar JDBC:

```java
public Usuario salvar(Usuario usuario) throws SQLException {
    String sql = "INSERT INTO usuarios (nome, idade, cpf, telefone, rua, numero, cidade, estado, email, senha) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    try (Connection conn = ConexaoBD.obterConexao();
         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        
        stmt.setString(1, usuario.getNome());
        stmt.setInt(2, usuario.getIdade());
        stmt.setString(3, usuario.getCpf());
        stmt.setString(4, usuario.getTelefone());
        stmt.setString(5, usuario.getEndereco().getRua());
        stmt.setString(6, usuario.getEndereco().getNumero());
        stmt.setString(7, usuario.getEndereco().getCidade());
        stmt.setString(8, usuario.getEndereco().getEstado());
        stmt.setString(9, usuario.getEmail());
        stmt.setString(10, usuario.getSenha());
        
        stmt.executeUpdate();
        
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                usuario.setId(rs.getInt(1));
            }
        }
    }
    return usuario;
}
```

Aplique o mesmo padrão para os outros métodos (buscarPorEmail, buscarPorEmailESenha, etc.).

## 5. Atualizar AutenticacaoService

Adicione tratamento de `SQLException` nos métodos que usam o DAO.

## Observações

- Use `PreparedStatement` para evitar SQL Injection
- Sempre feche conexões com try-with-resources
- Em produção, use pool de conexões (HikariCP)
