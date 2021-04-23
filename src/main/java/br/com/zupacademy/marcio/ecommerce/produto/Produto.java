package br.com.zupacademy.marcio.ecommerce.produto;
import br.com.zupacademy.marcio.ecommerce.categoria.Categoria;
import br.com.zupacademy.marcio.ecommerce.detalheProduto.Opinioes;
import br.com.zupacademy.marcio.ecommerce.opiniao.Opiniao;
import br.com.zupacademy.marcio.ecommerce.pergunta.Pergunta;
import br.com.zupacademy.marcio.ecommerce.usuario.Usuario;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
public class Produto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false) @Positive
    private BigDecimal valor;
    @Column(nullable = false) @Positive
    private int quantidade;
    @Column(nullable = false) @Length(max = 1000)
    private String descricao;
    @NotNull @ManyToOne @Valid
    private Categoria categoria;
    @NotNull @ManyToOne @Valid
    private Usuario dono;
    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
    private Set<CaracteristicaProduto> caracteristicas = new HashSet<>();
    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE) // qdo atualizar o produto, atualiza junto => CascadeType.MERGE
    private Set<ImagemProduto> imagens = new HashSet<>();
    @OneToMany(mappedBy = "produto") @OrderBy("titulo asc")
    private Set<Pergunta> perguntas = new HashSet<>();
    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    private Set<Opiniao> opinioes = new HashSet<>();
    private LocalDateTime instanteCadastro;

    @Deprecated
    public Produto() {
    }

    public Produto(@NotBlank String nome,
                   @NotNull @Positive BigDecimal valor,
                   @NotNull @Positive int quantidade,
                   @NotBlank @Length(max = 1000) String descricao,
                   @NotNull @Valid Categoria categoria,
                   @NotNull @Valid Usuario dono,
                   @Valid Collection<NovaCaracteristicaRequest> caracteristicas) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.categoria = categoria;
        this.instanteCadastro = LocalDateTime.now();
        this.dono = dono;
        this.caracteristicas.addAll(caracteristicas
                .stream().map(caracteristica -> caracteristica.toModel(this))
                .collect(Collectors.toSet()));
        Assert.isTrue(this.caracteristicas.size() >= 3, "Todo produto precisa pelo menos 3 características.");
    }


    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", valor=" + valor +
                ", quantidade=" + quantidade +
                ", descricao='" + descricao + '\'' +
                ", categoria=" + categoria +
                ", dono=" + dono +
                ", caracteristicas=" + caracteristicas +
                ", imagens=" + imagens +
                ", instanteCadastro=" + instanteCadastro +
                '}';
    }

    public void associaImagens(Set<String> links) {
        Set<ImagemProduto> imagens = links.stream().map(link -> new ImagemProduto(this, link))
                .collect(Collectors.toSet());
        this.imagens.addAll(imagens);
    }

    public boolean pertenceAoUsuario(Usuario dono) {
        return this.dono.equals(dono);
    }

    // p enviar email
    public Usuario getDono() {
        return this.dono;
    }

    // p DetalhesProduto
    public String getDescricao() {
        return descricao;
    }

    // p DetalhesProduto
    public String getNome() {
        return nome;
    }

    // p DetalhesProduto
    public BigDecimal getValor() {
        return valor;
    }

    public <T> Set<T> mapeiaCaracteristicas(
            Function<CaracteristicaProduto, T> funcaoMapeadora) {
        return this.caracteristicas.stream().map(funcaoMapeadora)
                .collect(Collectors.toSet());
    }

    public <T> Set<T> mapeiaImagens(Function<ImagemProduto, T> funcaoMapeadora) {
        return this.imagens.stream().map(funcaoMapeadora)
                .collect(Collectors.toSet());
    }

    public <T extends Comparable<T>> SortedSet<T> mapeiaPerguntas(Function<Pergunta, T> funcaoMapeadora) {
        return this.perguntas.stream().map(funcaoMapeadora)
                .collect(Collectors.toCollection(TreeSet :: new));
    }

    // opinioes p/ gerar coleção no DetalheProdutoView
    public Opinioes getOpinioes() {
        return new Opinioes(this.opinioes);
    }

    public boolean abateEstoque(@Positive int quantidade) { // booleano p/ confirmar se abateu ou não o estoque
        Assert.isTrue(quantidade > 0, "Qtde deve ser maior p/ abater o estoque.");
        if(quantidade <= this.quantidade) {
            this.quantidade-=quantidade;
            return true;
        }
        return false;
    }
}