package projeto.demo.produto;

import io.swagger.v3.oas.annotations.media.Schema;

public record DadosAtualizarProduto(
        @Schema(example = "1", description = "Identificador do produto no Banco de Dados")
        Long id,

        @Schema(example = "Smartphone XYZ", description = "Nome do produto")
        String nome,

        @Schema(example = "Dispositivo Andorid - Display de 8'", description = "Descrição do produto")
        String descricao
) {

}
