package br.com.zupacademy.marcio.ecommerce.opiniao;

import br.com.zupacademy.marcio.ecommerce.produto.Produto;
import br.com.zupacademy.marcio.ecommerce.usuario.Usuario;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class NovaOpiniaoRequest {

    @Min(1) @Max(5)
    private int nota;
    @NotBlank
    private String titulo;
    @NotBlank @Size(max = 500)
    private String descricao;

    public NovaOpiniaoRequest(@Min(1) @Max(5) int nota,
                              @NotBlank String titulo,
                              @NotBlank @Size(max = 500) String descricao) {
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Opiniao toModel(@NotNull @Valid Produto produto,
                           @NotNull @Valid Usuario usuario) {
        return new Opiniao(nota, titulo, descricao, usuario, produto);
    }
}