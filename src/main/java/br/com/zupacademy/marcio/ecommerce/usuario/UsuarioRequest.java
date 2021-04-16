package br.com.zupacademy.marcio.ecommerce.usuario;

import br.com.zupacademy.marcio.ecommerce.compartilhado.UniqueValue;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UsuarioRequest {
    @NotBlank @Email @UniqueValue(domainClass = Usuario.class, fieldName = "login")
    private String login;
    @NotBlank
    @Length(min = 6)
    private String senha;

    public UsuarioRequest(@NotBlank @Email String login, @NotBlank @Length(min = 6) String senha) {
        this.login = login;
        this.senha = senha;
    }

    public Usuario converter(){
        return new Usuario(login, new SenhaLimpa(senha));
    }
}

