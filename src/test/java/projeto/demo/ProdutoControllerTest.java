package projeto.demo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import projeto.demo.controller.ProdutoController;
import projeto.demo.produto.*;
import projeto.demo.repository.ProdutoRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProdutoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoController produtoController; // Injeta os mocks no controller

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build(); // Configura o MockMvc manualmente
    }


    @Test
    public void criarProduto_DeveRetornar201_QuandoDadosValidos() throws Exception {
        // Arrange
        DadosCadastroProduto dados = new DadosCadastroProduto("Notebook", "Dell i7", 4500.0, 10);
        Produto produtoSalvo = new Produto(dados);
        produtoSalvo.setId(1L);

        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoSalvo);

        // Act & Assert
        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dados)))
                .andExpect(status().isCreated())    // Status 201
                .andExpect(jsonPath("$.nome").value("Notebook")) // Verifica se o JSON de resposta contém os mesmos dados
                .andExpect(jsonPath("$.descricao").value("Dell i7"))
                .andExpect(jsonPath("$.preco").value(4500))
                .andExpect(jsonPath("$.quantidadeEstoque").value(10));

        verify(produtoRepository, times(1)).save(any(Produto.class)); // Verifica se o save foi chamado
    }

    @Test
    public void listarProdutos_DeveRetornar200_QuandoProdutosAtivosExistem() throws Exception {
        // Arrange
        Produto produto1 = new Produto(new DadosCadastroProduto("Produto 1", "Descrição 1", 100.0, 5));
        produto1.setId(1L);

        Produto produto2 = new Produto(new DadosCadastroProduto("Produto 2", "Descrição 2", 200.0, 10));
        produto2.setId(2L);

        when(produtoRepository.findAllByAtivoTrue()).thenReturn(List.of(produto1, produto2));

        // Act & Assert
        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));

        verify(produtoRepository, times(1)).findAllByAtivoTrue();
    }

    @Test
    public void obterProduto_DeveRetornar200_QuandoProdutoExiste() throws Exception {
        // Arrange
        Long id = 1L;
        Produto produto = new Produto(new DadosCadastroProduto("Mouse", "Sem fio", 150.0, 20));
        produto.setId(id);

        when(produtoRepository.getReferenceById(id)).thenReturn(produto);

        // Act & Assert
        mockMvc.perform(get("/produtos/{id}", id))
                .andExpect(status().isOk()) // Status 200
                .andExpect(jsonPath("$.nome").value("Mouse"));
    }

    @Test
    public void atualizarProduto_DeveRetornar200_QuandoDadosValidos() throws Exception {
        // Arrange
        DadosAtualizarProduto dados = new DadosAtualizarProduto(1L, "Novo Nome", "Nova Descrição");

        Produto produtoExistente = new Produto(
                new DadosCadastroProduto("Nome Antigo", "Descrição Antiga", 100.0, 5)
        );
        produtoExistente.setId(1L);

        when(produtoRepository.getReferenceById(1L)).thenReturn(produtoExistente);

        // Act & Assert
        mockMvc.perform(put("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dados)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Novo Nome"))
                .andExpect(jsonPath("$.descricao").value("Nova Descrição"));

        verify(produtoRepository, times(1)).getReferenceById(1L);
    }

    @Test
    public void desativarProduto_DeveRetornar204_QuandoProdutoExiste() throws Exception {
        // Arrange
        Produto produto = new Produto(new DadosCadastroProduto("Produto", "Descrição", 100.0, 5));
        produto.setId(1L);

        when(produtoRepository.getReferenceById(1L)).thenReturn(produto);

        // Act & Assert
        mockMvc.perform(delete("/produtos/desativar/1"))
                .andExpect(status().isNoContent()); // Status 204

        verify(produtoRepository, times(1)).getReferenceById(1L);
        assertFalse(produto.getAtivo()); // Verifica se o produto foi desativado
    }

    @Test
    public void ativarProduto_DeveRetornar204_QuandoProdutoExiste() throws Exception {
        // Arrange
        Produto produto = new Produto(new DadosCadastroProduto("Produto", "Descrição", 100.0, 5));
        produto.setId(1L);
        produto.desativar();

        when(produtoRepository.getReferenceById(1L)).thenReturn(produto);

        // Act & Assert
        mockMvc.perform(put("/produtos/ativar/1"))
                .andExpect(status().isNoContent()); // Status 204

        verify(produtoRepository, times(1)).getReferenceById(1L);
        assertTrue(produto.getAtivo()); // Verifica se o produto foi reativado
    }


}