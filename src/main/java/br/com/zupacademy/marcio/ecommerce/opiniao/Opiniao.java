package br.com.zupacademy.marcio.ecommerce.opiniao;

import br.com.zupacademy.marcio.ecommerce.produto.Produto;
import br.com.zupacademy.marcio.ecommerce.usuario.Usuario;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Opiniao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(1) @Max(5)
    private int nota;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false, length = 500)
    private String descricao;
    @ManyToOne @NotNull
    private Usuario usuario;
    @ManyToOne @NotNull
    private Produto produto;

    public Opiniao(@Min(1) @Max(5) int nota,
                   @NotNull String titulo,
                   @NotNull @Size(max=500) String descricao,
                   @NotNull Usuario usuario,
                   @NotNull Produto produto) {
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
        this.usuario = usuario;
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "Opiniao{" +
                "id=" + id +
                ", nota=" + nota +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", usuario=" + usuario +
                ", produto=" + produto +
                '}';
    }
}