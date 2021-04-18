package br.com.zupacademy.marcio.ecommerce.compartilhado;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.zupacademy.marcio.ecommerce.usuario.Usuario;
import br.com.zupacademy.marcio.ecommerce.compartilhado.seguranca.UserDetailsMapper;

@Configuration
public class AppUserDetailsMapper implements UserDetailsMapper{

    @Override
    public UserDetails map(Object shouldBeASystemUser) {
        return new UsuarioLogado((Usuario)shouldBeASystemUser);
    }

}