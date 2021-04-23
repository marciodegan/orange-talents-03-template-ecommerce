package br.com.zupacademy.marcio.ecommerce.compartilhado;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class FakeMailer implements Mailer {

    @Override
    public void send(String body, String subject, String nameFrom, String from,
                     String to) {
        System.out.println(body);
        System.out.println(subject);
        System.out.println(nameFrom);
        System.out.println(from);
        System.out.println(to);
    }

    public void sendNovaCompra(String body, Long id, String to, String from,
                     String produto, int quantidade, String gateway) {
        System.out.println(body);
        System.out.println("id da Compra: " + id);
        System.out.println("Comprador: " + from);
        System.out.println(to);
        System.out.println("Produto: " + produto);
        System.out.println("Quantidade: " + quantidade);
        System.out.println("Gateway de Pagamento: " + gateway);
    }
}

