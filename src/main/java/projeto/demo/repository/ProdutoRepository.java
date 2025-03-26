package projeto.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.demo.produto.Produto;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findAllByAtivoTrue();
}
