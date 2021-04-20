package br.com.zupacademy.marcio.ecommerce.produto;

import br.com.zupacademy.marcio.ecommerce.usuario.Usuario;
import br.com.zupacademy.marcio.ecommerce.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import java.util.Set;

@RestController
public class ProdutosController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired // dá nullpointer sem ele ("cannot invoke...")
    private UploaderFake uploaderFake;

    @InitBinder(value = "NovoProdutoRequest") // esse método binder só deve ser aplicado para os métodos que recebam como argumento o "NovoProdutoRequest"
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new ProibeCaracteristicaComNomeIgualValidator());
    }

    @PostMapping(value = "/produtos")
    @Transactional
    public String cadastrar(@RequestBody @Valid NovoProdutoRequest request) {
        Usuario dono = usuarioRepository.findByEmail("m@m.com").get(); // simulando usuário logado

        Produto produto = request.toModel(manager, dono);
        manager.persist(produto);

        return produto.toString();
    }

    @PostMapping(value = "/produtos/{id}/imagens")
    @Transactional
    public String adicionaImagens(@PathVariable("id") Long id, @Valid NovasImagensRequest request){

        /*
         * 1- Enviar imagens para o local onde serão armazenadas
         * 2- pegar links de todas imagens
         * 3- associar esse link com o produto
         * 4- carregar o produto
         * 5- depois que associar, atualiza nova versao do produto
         */

        Usuario dono = usuarioRepository.findByEmail("m@m.com").get();
        Produto produto = manager.find(Produto.class, id); // pra dar o find precisa do construtor vazio

        if(!produto.pertenceAoUsuario(dono)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Set<String> links = uploaderFake.envia(request.getImagens()); // recebe uma lista de multipart files

        produto.associaImagens(links);
        manager.merge(produto);
        return produto.toString();
    }
}