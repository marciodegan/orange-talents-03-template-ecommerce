package br.com.zupacademy.marcio.ecommerce.fechamentocompra;

import javax.validation.constraints.NotNull;

public class RankingNovaCompraRequest {

    @NotNull
    private Long idCompra;
    @NotNull
    private Long idDonoProduto;

    public RankingNovaCompraRequest(Long idCompra, Long idDonoProduto) {
        this.idCompra = idCompra;
        this.idDonoProduto = idDonoProduto;
    }

    @Override
    public String toString() {
        return "NovaCompraNFRequest [idCompra=" + idCompra + ", idComprador="
                + idDonoProduto + "]";
    }
}