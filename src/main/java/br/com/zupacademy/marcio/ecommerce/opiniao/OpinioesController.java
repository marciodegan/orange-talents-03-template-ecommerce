package br.com.zupacademy.marcio.ecommerce.opiniao;

import br.com.zupacademy.marcio.ecommerce.compartilhado.UsuarioLogado;
import br.com.zupacademy.marcio.ecommerce.produto.Produto;
import br.com.zupacademy.marcio.ecommerce.usuario.Usuario;
import br.com.zupacademy.marcio.ecommerce.usuario.UsuarioRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class OpinioesController {

    @PersistenceContext
    private EntityManager manager;

    private UsuarioRepository usuarioRepository;

    public OpinioesController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping(value="/produtos/{id}/opinioes")
    @Transactional
    public String adicionaOpiniao(@PathVariable("id") Long id, @RequestBody @Valid NovaOpiniaoRequest request, @AuthenticationPrincipal UsuarioLogado usuarioLogado){

        Usuario usuario = usuarioLogado.get();
        Produto produto = manager.find(Produto.class, id);

        Opiniao opiniao = request.toModel(produto, usuario);
        manager.persist(opiniao);
        return opiniao.toString();

    }
}