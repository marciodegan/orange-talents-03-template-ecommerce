package br.com.zupacademy.marcio.ecommerce.fechamentocompra;

import br.com.zupacademy.marcio.ecommerce.produto.Produto;
import br.com.zupacademy.marcio.ecommerce.usuario.Usuario;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Compra {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @NotNull @Valid
    private Produto produtoEscolhido;
    @Positive
    private int quantidade;
    @ManyToOne @NotNull @Valid
    private Usuario comprador;
    @Enumerated @NotNull
    private GatewayPagamento gatewayPagamento;
    @OneToMany(mappedBy="compra", cascade = CascadeType.MERGE) // qdo atualizar compra, dá um merge nas transações
    private Set<Transacao> transacoes = new HashSet<>();

    @Deprecated
    public Compra() {
    }

    public Compra(@NotNull @Valid Produto produtoASerComprado,
                  @Positive int quantidade,
                  @NotNull @Valid Usuario comprador,
                  GatewayPagamento gatewayPagamento) {
        this.produtoEscolhido = produtoASerComprado;
        this.quantidade = quantidade;
        this.comprador = comprador;
        this.gatewayPagamento = gatewayPagamento;
    }

    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id +
                ", produtoEscolhido=" + produtoEscolhido +
                ", quantidade=" + quantidade +
                ", comprador=" + comprador +
                ", gatewayPagamento=" + gatewayPagamento +
                ", transacoes=" + transacoes +
                '}';
    }

    public Long getId() {
        return id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Produto getProdutoEscolhido() {
        return produtoEscolhido;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public GatewayPagamento getGatewayPagamento() {
        return gatewayPagamento;
    }

    public void adicionaTransacao(@Valid RetornoGatewayPagamento request) { // RetornoGatewayPagamento=interface que
        Transacao novaTransacao = request.toTransacao(this);
        Assert.state(!this.transacoes.contains(novaTransacao), "Já existe transação igual:" + novaTransacao);

        Assert.state(transacoesConcluidasComSucesso().isEmpty(), "Essa compra já tem transação concluída com sucesso");

        this.transacoes.add(novaTransacao);
    }


    private Set<Transacao> transacoesConcluidasComSucesso() {
        Set<Transacao> transacoesConcluidasComSucesso = this.transacoes.stream()
                .filter(Transacao::concluidaComSucesso)
                .collect(Collectors.toSet());

        Assert.isTrue(transacoesConcluidasComSucesso.size() <= 1,"ATENÇÃO. Há mais de uma transação concluída c/ sucesso nesta compra de id: " +this.id);

        return transacoesConcluidasComSucesso;
    }

    public String urlRedirecionamento(
            UriComponentsBuilder uriComponentsBuilder) {
        return this.gatewayPagamento.criaUrlRetorno(this, uriComponentsBuilder);
    }

    public boolean processadaComSucesso(){
        return !transacoesConcluidasComSucesso().isEmpty();
    }

    public Usuario getDonoDoProduto() {
        return getProdutoEscolhido().getDono();
    }
}