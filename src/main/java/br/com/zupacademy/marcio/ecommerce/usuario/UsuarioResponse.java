package br.com.zupacademy.marcio.ecommerce.usuario;

import java.time.LocalDateTime;

public class UsuarioResponse {

    private String login;
    private String senha;
    private LocalDateTime dataCadastro;

    public UsuarioResponse(Usuario usuario) {
        this.login = usuario.getLogin();
        this.senha = usuario.getSenha();
        this.dataCadastro = usuario.getDataCadastro();
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
}
