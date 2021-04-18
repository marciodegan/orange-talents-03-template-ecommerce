package br.com.zupacademy.marcio.ecommerce.compartilhado.seguranca;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // se integra automaticamente com SpringMVC
public class SecurityConfiguration extends WebSecurityConfigurerAdapter  {

    @Autowired
    private UsersService usersService;

    @Autowired
    private TokenManager tokenManager;


    private static final Logger log = LoggerFactory
            .getLogger(SecurityConfiguration.class);


    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //configurações de autorização (urls, quem pode acessar cada url, perfil de acesso).
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/produtos/{id:[0-9]+}").permitAll()
                .antMatchers(HttpMethod.POST, "/usuarios").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated() // todos os demais endpoints, necessitam autenticacao.
                .and()
                .cors()
                .and()
                .csrf().disable() // proteção contra um tipo de ataque que não faz sentido para APIs, pq não guardamos estado do lado do servidor.
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // nunca quero criar uma sessao no servidor. Nova autenticação em cada requisição
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(tokenManager, usersService),
                        UsernamePasswordAuthenticationFilter.class) // antes de passar pelo filtro de autenticação do Spring Secutiry, agora precisamos carregar
                .exceptionHandling()                                // o token que veio na requisição(no header), colocar um usuário na memória e fingir pro Spring Security que aquele usuário foi autenticado de maneira regular.
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint());

    }
    // Configurações de Autenticação (
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.usersService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**.html",  "/v2/api-docs", "/webjars/**",
                "/configuration/**", "/swagger-resources/**", "/css/**", "/**.ico", "/js/**");
    }

}