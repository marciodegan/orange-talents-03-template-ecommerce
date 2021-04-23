package br.com.zupacademy.marcio.ecommerce.compartilhado;

import br.com.zupacademy.marcio.ecommerce.fechamentocompra.Compra;
import br.com.zupacademy.marcio.ecommerce.pergunta.Pergunta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
public class Emails {

    @Autowired
    private Mailer mailer;

    public void novaPergunta(@NotNull @Valid Pergunta pergunta) {
        mailer.send("<html>...</html>","Nova pergunta:" + pergunta.getTitulo() ,pergunta.getUsuario().getEmail(),"novapergunta@nossomercadolivre.com",pergunta.getDonoProduto().getEmail());
    }

    public void novaCompra(@NotNull @Valid Compra compra) {
        mailer.sendNovaCompra("<html>...</html>",
                compra.getId(),
                "novacompra@nossomercadolivre.com",
                compra.getComprador().getEmail(),
                compra.getProdutoEscolhido().getNome(),
                compra.getQuantidade(),
                compra.getGatewayPagamento().name());
    }
}