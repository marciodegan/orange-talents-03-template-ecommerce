package br.com.zupacademy.marcio.ecommerce.fechamentocompra;

import io.jsonwebtoken.lang.Assert;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class Ranking implements EventoCompraSucesso{

    @Override
    public void processa(Compra compra) {

        Assert.isTrue(compra.processadaComSucesso(), "COMPRA NÃO PROCESSADA COM SUCESSO " + compra);

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> request = Map.of(
                "idCompra", compra.getId(),
                "idDonoDoProduto", compra.getDonoDoProduto());

        restTemplate.postForEntity("http://localhost:8080/notas-fiscais", request, String.class);
    }

}