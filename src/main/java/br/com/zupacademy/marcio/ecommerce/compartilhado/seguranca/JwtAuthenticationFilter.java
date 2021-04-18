package br.com.zupacademy.marcio.ecommerce.compartilhado.seguranca;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private TokenManager tokenManager;
    private UsersService usersService;

    public JwtAuthenticationFilter(TokenManager tokenManager, UsersService usersService) {
        this.tokenManager = tokenManager;
        this.usersService = usersService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        Optional<String> possibleToken = getTokenFromRequest(request);

        if (possibleToken.isPresent() && tokenManager.isValid(possibleToken.get())) { // se tem um header, verifica se a token é válido. A classe TokenManager é a que lida com JWT

            String userName = tokenManager.getUserName(possibleToken.get()); // busca o usuário através do TokenManager
            UserDetails userDetails = usersService.loadUserByUsername(userName); // carrega o usuário

            UsernamePasswordAuthenticationToken authentication = // cria um authenticantion token para dentro do Spring. Abstração do SpringSecutiry que fala que estou autenticando alguém.
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); // Basta instanciar o objeto.

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response); // se não tem um header, segue normal sem colocar usuário na memória
    }

    private Optional<String> getTokenFromRequest(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization"); // recebe o token da requisição pelo header Authorization. Bearer

        return Optional.ofNullable(authToken);
    }

}