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
    private String email;
    @Column(nullable = false) @Size(min=6)
    private String senha;
    @Column(nullable=false) @PastOrPresent
    private LocalDateTime instanteCadastro;

    @Deprecated
    public Usuario(){

    }

    public Usuario(@Email String email, @Valid @NotNull SenhaLimpa senhaLimpa) {
        Assert.isTrue(StringUtils.hasLength(email),"email não pode ser em branco");
        Assert.notNull(senhaLimpa,"o objeto do tipo senha limpa nao pode ser nulo");
        this.email = email;
        this.senha = senhaLimpa.hash();
        this.instanteCadastro = LocalDateTime.now();

    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    // para usuário logado
    public String getSenha() {
        return senha;
    }

    public LocalDateTime getInstanteCadastro() {
        return instanteCadastro;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", instanteCadastro=" + instanteCadastro +
                '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
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
        Usuario other = (Usuario) obj;
        if (email == null) {
            return other.email == null;
        } else return email.equals(other.email);
    }
}
