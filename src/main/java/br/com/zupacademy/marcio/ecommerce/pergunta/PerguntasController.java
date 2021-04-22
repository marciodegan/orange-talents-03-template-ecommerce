package br.com.zupacademy.marcio.ecommerce.pergunta;

import br.com.zupacademy.marcio.ecommerce.compartilhado.Emails;
import br.com.zupacademy.marcio.ecommerce.compartilhado.UsuarioLogado;
import br.com.zupacademy.marcio.ecommerce.produto.Produto;
import br.com.zupacademy.marcio.ecommerce.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PerguntasController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private Emails emails;

    @PostMapping(value = "/produtos/{id}/perguntas")
    @Transactional
    public String adicionaPergunta(@RequestBody @Valid NovaPerguntaRequest request,
                                   @PathVariable("id") Long id,
                                   @AuthenticationPrincipal UsuarioLogado usuarioLogado) {

        Usuario usuario = usuarioLogado.get();
        Produto produto = manager.find(Produto.class, id);
        Pergunta pergunta = request.toModel(usuario, produto);
        manager.persist(pergunta);

        emails.novaPergunta(pergunta);

        return pergunta.toString();

    }
}