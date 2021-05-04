package br.com.zupacademy.marcio.ecommerce.fechamentocompra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EventosNovaCompra {

    @Autowired
    private Set<EventoCompraSucesso> eventosCompraSucesso;

    public void processa(Compra compra) {

        if (compra.processadaComSucesso()) {
            System.out.println(compra);
            eventosCompraSucesso.forEach(evento -> evento.processa(compra));
            System.out.println(eventosCompraSucesso);
        } else {

        }
    }

}