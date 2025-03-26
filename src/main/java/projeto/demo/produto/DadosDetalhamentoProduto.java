package projeto.demo.produto;

import io.swagger.v3.oas.annotations.media.Schema;

public record DadosDetalhamentoProduto(
        @Schema(example = "1", description = "Identificador do produto no Banco de Dados")
        Long id,

        @Schema(example = "Smartphone XYZ", description = "Nome do produto")
        String nome,

        @Schema(example = "Dispositivo Andorid - Display de 8'", description = "Descrição do produto")
        String descricao,

        @Schema(example = "99.99", description = "Preço do produto")
        Double preco,

        @Schema(example = "5", description = "Quantidade de produtos em estoque")
        Integer quantidadeEstoque,

        @Schema(example = "true", description = "Propriedade que omite o produto sem apaga-lo")
        Boolean ativo
) {
    public DadosDetalhamentoProduto (Produto produto) {
        this(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getQuantidadeEstoque(),
                produto.getAtivo()
        );
    }
}
