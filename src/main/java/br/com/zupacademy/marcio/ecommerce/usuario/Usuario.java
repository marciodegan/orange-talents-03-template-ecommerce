package br.com.zupacademy.marcio.ecommerce.usuario;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String login;
    @Column(nullable = false) @Size(min=6)
    private String senha;
    @Column(nullable=false) @PastOrPresent
    private LocalDateTime instanteCadastro;

    @Deprecated
    public Usuario(){

    }

    public Usuario(@Email String login, @Valid @NotNull SenhaLimpa senhaLimpa) {
        Assert.isTrue(StringUtils.hasLength(login),"email não pode ser em branco");
        Assert.notNull(senhaLimpa,"o objeto do tipo senha limpa nao pode ser nulo");
        this.login = login;
        this.senha = senhaLimpa.hash();
        this.instanteCadastro = LocalDateTime.now();

    }

    public Long getId() {
        return id;
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
