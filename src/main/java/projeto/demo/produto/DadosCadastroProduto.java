package projeto.demo.produto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record DadosCadastroProduto(
        @NotBlank
        @Schema(example = "Smartphone XYZ", description = "Nome do produto")
        String nome,

        @NotBlank
        @Schema(example = "Dispositivo Andorid - Display de 8'", description = "Descrição do produto")
        String descricao,

        @Positive
        @Schema(example = "99.99", description = "Preço do produto")
        Double preco,

        @PositiveOrZero
        @Schema(example = "5", description = "Quantidade de produtos em estoque")
        Integer quantidadeEstoque
) {


}
