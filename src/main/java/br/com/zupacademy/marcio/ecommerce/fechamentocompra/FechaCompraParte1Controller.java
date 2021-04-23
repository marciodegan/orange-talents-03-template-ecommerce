package br.com.zupacademy.marcio.ecommerce.fechamentocompra;

import br.com.zupacademy.marcio.ecommerce.compartilhado.Emails;
import br.com.zupacademy.marcio.ecommerce.compartilhado.UsuarioLogado;
import br.com.zupacademy.marcio.ecommerce.produto.Produto;
import br.com.zupacademy.marcio.ecommerce.usuario.Usuario;
import br.com.zupacademy.marcio.ecommerce.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.validation.BindException;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class FechaCompraParte1Controller {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private Emails emails;

    @PostMapping(value="/compras")
    @Transactional
    public String novaCompra(@RequestBody @Valid NovaCompraRequest request,
                             @AuthenticationPrincipal UsuarioLogado usuarioLogado,
                             UriComponentsBuilder uriComponentsBuilder) throws BindException {

        Produto produtoASerComprado = manager.find(Produto.class, request.getIdProduto());

        int quantidade = request.getQuantidade();
        boolean abateuEstoque = produtoASerComprado.abateEstoque(quantidade);

        if(abateuEstoque) {
            Usuario comprador = usuarioLogado.get();
            GatewayPagamento gateway = request.getGateway();

            Compra novaCompra = new Compra(produtoASerComprado, quantidade, comprador, gateway);
            manager.persist(novaCompra);
            emails.novaCompra(novaCompra);
            if(gateway.equals(GatewayPagamento.pagseguro)){
                String urlRetornoPagSeguro = uriComponentsBuilder.path("/retorno-pagseguro/{id}")
                        .buildAndExpand(novaCompra.getId()).toString();
                return "pagseguro.com?returnId="+novaCompra.getId()+"&redirectUrl="+urlRetornoPagSeguro;
            }else {
                String urlRetornoPaypal = uriComponentsBuilder.path("/retorno-paypal/{id}")
                        .buildAndExpand(novaCompra.getId()).toString();
                return "paypal.com?buyerId="+novaCompra.getId()+"&redirectUrl="+urlRetornoPaypal;

            }
        }
        BindException problemaComEstoque = new BindException(request, "NovaCompraRequest");
        problemaComEstoque.reject(null, "Não foi possível realizar a compra. Estoque insuficiente.");

        throw problemaComEstoque;
    }
}