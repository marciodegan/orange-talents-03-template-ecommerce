package br.com.zupacademy.marcio.ecommerce.pergunta;

import br.com.zupacademy.marcio.ecommerce.produto.Produto;
import br.com.zupacademy.marcio.ecommerce.usuario.Usuario;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Pergunta implements Comparable<Pergunta>{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String titulo;
    @ManyToOne @NotNull @Valid
    private Usuario usuario;
    @ManyToOne @NotNull @Valid
    private Produto produto;
    private LocalDateTime instanteCriado;

    @Deprecated
    public Pergunta() {
    }

    public Pergunta(String titulo, @NotNull Usuario usuario, @NotNull Produto produto) {
        this.titulo = titulo;
        this.usuario = usuario;
        this.produto = produto;
        this.instanteCriado = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Pergunta{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", usuario=" + usuario +
                ", produto=" + produto +
                ", instanteCriado=" + instanteCriado +
                '}';
    }

    // p/ enviar pergunta - classe Emails
    public Usuario getUsuario() {
        return usuario;
    }

    // p/ enviar pergunta - classe Emails
    public Usuario getDonoProduto() {
        return produto.getDono();
    }

    // p/ enviar pergunta - classe Emails e detalheProduto
    public String getTitulo() {
        return titulo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((usuario == null) ? 0 : usuario.hashCode());
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
        Pergunta other = (Pergunta) obj;
        if (usuario == null) {
            if (other.usuario != null)
                return false;
        } else if (!usuario.equals(other.usuario))
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

    @Override
    public int compareTo(Pergunta o) {
        return this.titulo.compareTo(o.titulo);
    }
}