package br.com.zupacademy.marcio.ecommerce.usuario;

import java.time.LocalDateTime;

public class UsuarioResponse {

    private String login;
    private String senha;
    private LocalDateTime instanteCadastro;

    public UsuarioResponse(Usuario usuario) {
        this.login = usuario.getLogin();
        this.senha = usuario.getSenha();
        this.instanteCadastro = usuario.getInstanteCadastro();
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public LocalDateTime getInstanteCadastro() {
        return instanteCadastro;
    }
}
