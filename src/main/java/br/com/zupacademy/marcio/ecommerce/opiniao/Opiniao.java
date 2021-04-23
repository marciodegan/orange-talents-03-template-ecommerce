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

    // precisou p/ detalheProdutoView
    @Deprecated
    public Opiniao() {
    }

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

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getNota() {
        return nota;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((usuario == null) ? 0 : usuario.hashCode());
        result = prime * result
                + ((descricao == null) ? 0 : descricao.hashCode());
        result = prime * result + ((produto == null) ? 0 : produto.hashCode());
        result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Opiniao other = (Opiniao) obj;
        if (usuario == null) {
            if (other.usuario != null)
                return false;
        } else if (!usuario.equals(other.usuario))
            return false;
        if (descricao == null) {
            if (other.descricao != null)
                return false;
        } else if (!descricao.equals(other.descricao))
            return false;
        if (produto == null) {
            if (other.produto != null)
                return false;
        } else if (!produto.equals(other.produto))
            return false;
        if (titulo == null) {
            if (other.titulo != null)
                return false;
        } else if (!titulo.equals(other.titulo))
            return false;
        return true;
    }


}