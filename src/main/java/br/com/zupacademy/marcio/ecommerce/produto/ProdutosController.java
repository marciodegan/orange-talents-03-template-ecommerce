package br.com.zupacademy.marcio.ecommerce.produto;

import br.com.zupacademy.marcio.ecommerce.usuario.Usuario;
import br.com.zupacademy.marcio.ecommerce.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutosController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new ProibeCaracteristicaComNomeIgualValidator());
    }

    @PostMapping
    @Transactional
    public String cadastrar(@RequestBody @Valid NovoProdutoRequest request) {
        Usuario dono = usuarioRepository.findByEmail("m@m.com").get(); // simulando usuário logado

        Produto produto = request.toModel(manager, dono);
        manager.persist(produto);

        return produto.toString();
    }
}