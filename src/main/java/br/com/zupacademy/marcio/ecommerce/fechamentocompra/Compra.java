package br.com.zupacademy.marcio.ecommerce.fechamentocompra;

import br.com.zupacademy.marcio.ecommerce.produto.Produto;
import br.com.zupacademy.marcio.ecommerce.usuario.Usuario;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
}