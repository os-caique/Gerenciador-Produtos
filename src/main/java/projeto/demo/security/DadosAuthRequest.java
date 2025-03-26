package projeto.demo.security;

import io.swagger.v3.oas.annotations.media.Schema;

// DTO para a requisição de login
public record DadosAuthRequest(

        @Schema(example = "admin@example.com", description = "Identificador do usuario")
        String email,

        @Schema(example = "senha123", description = "Senha para o login")
        String password
) {}