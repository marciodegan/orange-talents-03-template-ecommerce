package br.com.zupacademy.marcio.ecommerce.pergunta;

import br.com.zupacademy.marcio.ecommerce.produto.Produto;
import br.com.zupacademy.marcio.ecommerce.usuario.Usuario;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Pergunta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String titulo;
    @ManyToOne @NotNull @Valid
    private Usuario usuario;
    @ManyToOne @NotNull @Valid
    private Produto produto;
    private LocalDateTime instanteCriado;


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

    // p/ enviar pergunta - classe Emails
    public String getTitulo() {
        return titulo;
    }
}