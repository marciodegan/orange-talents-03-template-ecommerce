package br.com.zupacademy.marcio.ecommerce.detalheProduto;

import br.com.zupacademy.marcio.ecommerce.produto.Produto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public class DetalheProdutoView {

    private String descricao;
    private String nome;
    private BigDecimal preco;
    private Set<DetalheProdutoCaracteristica> caracteristicas;
    private Set<String> linksImagens;
    private SortedSet<String> perguntas;
    private Set<Map<String,String>> opinioes; // Um set de map vai virar um objeto no json
    private double mediaNotas;
    private int total;

    public DetalheProdutoView(Produto produto) {
        this.descricao = produto.getDescricao();
        this.nome = produto.getNome();
        this.preco = produto.getValor();
        this.caracteristicas = produto
                .mapeiaCaracteristicas(DetalheProdutoCaracteristica::new);
        this.linksImagens = produto.mapeiaImagens(imagem -> imagem.getLink());
        this.perguntas = produto.mapeiaPerguntas(pergunta -> pergunta.getTitulo());

        Opinioes opinioes = produto.getOpinioes();

        this.opinioes = opinioes.mapeiaOpinioes(opiniao -> {
            return Map.of("titulo",opiniao.getTitulo(),"descricao",opiniao.getDescricao());
        });
        this.mediaNotas = opinioes.media();
        this.total = opinioes.total();

    }

    public SortedSet<String> getPerguntas() {
        return perguntas;
    }

    public Set<String> getLinksImagens() {
        return linksImagens;
    }

    public Set<DetalheProdutoCaracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Set<Map<String, String>> getOpinioes() {
        return opinioes;
    }

    public int getTotal() {
        return total;
    }

    public double getMediaNotas() {
        return mediaNotas;
    }
}