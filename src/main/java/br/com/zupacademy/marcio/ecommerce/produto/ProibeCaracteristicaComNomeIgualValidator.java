package br.com.zupacademy.marcio.ecommerce.produto;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

public class ProibeCaracteristicaComNomeIgualValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return NovoProdutoRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(errors.hasErrors()) {
            return ;
        }
        NovoProdutoRequest request = (NovoProdutoRequest) target;
        Set<String> nomesIguais = request.buscaCaracteristicasIguais();
        if(!nomesIguais.isEmpty()) {    // se não estiver vazio, mostra os nomes
                                        // if(request.temCaracteristicasIguais()){ // se tiver caracteristicas iguais, rejeita o parâmetro "caracteristicas"
            errors.rejectValue("caracteristicas", null, "Características iguais" + nomesIguais);

        }

    }
}