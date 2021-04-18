package br.com.zupacademy.marcio.ecommerce.usuario;

import java.time.LocalDateTime;

public class UsuarioResponse {

    private String email;
    private String senha;
    private LocalDateTime instanteCadastro;

    public UsuarioResponse(Usuario usuario) {
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.instanteCadastro = usuario.getInstanteCadastro();
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public LocalDateTime getInstanteCadastro() {
        return instanteCadastro;
    }
}
