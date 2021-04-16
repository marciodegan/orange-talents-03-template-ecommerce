package br.com.zupacademy.marcio.ecommerce.categoria;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriasController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    @Transactional
    public String criarCategoria(@RequestBody @Valid NovaCategoriaRequest novaCategoriaRequest){
        Categoria categoria = novaCategoriaRequest.toModel(manager);
        manager.persist(categoria);
        return categoria.toString();
    }
}

