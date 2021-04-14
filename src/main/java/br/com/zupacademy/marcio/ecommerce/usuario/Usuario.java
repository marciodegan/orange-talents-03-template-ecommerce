package br.com.zupacademy.marcio.ecommerce.usuario;

import javax.persistence.*;
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
    private LocalDateTime dataCadastro = LocalDateTime.now();

    @Deprecated
    public Usuario(){

    }

    public Usuario(String login, @Size(min = 6) String senha) {
        this.login = login;
        this.senha = senha;
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

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
}
