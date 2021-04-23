package br.com.zupacademy.marcio.ecommerce.detalheProduto;

import br.com.zupacademy.marcio.ecommerce.produto.Produto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RestController
public class DetalheProdutoController {

    @PersistenceContext
    private EntityManager manager;

    @GetMapping(value="/produtos/{id}")
    public DetalheProdutoView getDetalhesProduto(@PathVariable("id") Long id) {
        Produto produto = manager.find(Produto.class, id);
        return new DetalheProdutoView(produto);
    }
}