package br.com.zupacademy.marcio.ecommerce.produto;
import br.com.zupacademy.marcio.ecommerce.categoria.Categoria;
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
    private LocalDateTime instanteCadastro;


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
                ", instanteCadastro=" + instanteCadastro +
                '}';
    }
}