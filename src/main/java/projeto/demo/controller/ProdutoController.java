package projeto.demo.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import projeto.demo.produto.*;
import projeto.demo.repository.ProdutoRepository;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping
    @Transactional
    @Operation ( summary = "Criar um produto", description = "Cadastra um novo produto no sistema" )
    @ApiResponses ( value = {
            @ApiResponse (responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse (responseCode = "400", description = "Dados inválidos"),
            @ApiResponse (responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<DadosDetalhamentoProduto> criarProduto(
            @RequestBody @Valid DadosCadastroProduto dados,
            UriComponentsBuilder uriBulder
    ){
        var produto = new Produto(dados);
        produtoRepository.save(produto);

        var uri = uriBulder.path("produtos/{id}").buildAndExpand(produto.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoProduto(produto));
    }

    @GetMapping
    @Operation ( summary = "Listar Produtos", description = "Retorna uma lista de produtos ativos" )
    @ApiResponses ( value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<DadosListagemProduto>> listarProdutos () {
        var produtos = produtoRepository.findAllByAtivoTrue().stream().map(DadosListagemProduto::new).toList();

        return ResponseEntity.ok(produtos);
    }

    @GetMapping ("/{id}")
    @Operation ( summary = "Obter um produto", description = "Retorna os detalhes de um produto específico" )
    @ApiResponses ( value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado e retornado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<DadosDetalhamentoProduto> obterProduto (@PathVariable Long id) {
        var produto = produtoRepository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoProduto(produto));
    }

    @PutMapping
    @Transactional
    @Operation ( summary = "Atualizar um produto", description = "Atualiza os dados de um produto existente" )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado e atualizado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<DadosDetalhamentoProduto> atualizar (
            @RequestBody @Valid DadosAtualizarProduto dados
    ) {
        var produto = produtoRepository.getReferenceById(dados.id());
        produto.atualizarDados(dados);

        return ResponseEntity.ok(new DadosDetalhamentoProduto(produto));
    }

    @PutMapping ("ativar/{id}")
    @Transactional
    @Operation ( summary = "Ativar um produto", description = "Ativa um produto previamente desativado" )
    @ApiResponses ( value = {
            @ApiResponse (responseCode = "204", description = "Produto ativado com sucesso"),
            @ApiResponse (responseCode = "500", description = "Erro interno no servidor"),
            @ApiResponse (responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<Void> ativar (@PathVariable Long id) {
        var produto = produtoRepository.getReferenceById(id);
        produto.ativar();

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping ("desativar/{id}")
    @Transactional
    @Operation ( summary = "Desativar um produto", description = "Desativa um produto sem excluí-lo" )
    @ApiResponses ( value = {
            @ApiResponse(responseCode = "204", description = "Produto desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Void> desativar (@PathVariable Long id) {
        var produto = produtoRepository.getReferenceById(id);
        produto.desativar();

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping ("/{id}")
    @Transactional
    @Operation(summary = "Excluir um produto", description = "Remove um produto do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Void> deletar (@PathVariable Long id) {
        produtoRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
