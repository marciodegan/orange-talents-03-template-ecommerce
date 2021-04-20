package br.com.zupacademy.marcio.ecommerce.produto;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
@param imagens
@return links p/ imagens uploadeadas
*/

@Component // pra poder ser injetado no controller
public class UploaderFake {

    public Set<String> envia(List<MultipartFile> imagens){

        return imagens.stream()
                .map(imagem -> "http://bucket.io/"
                        + imagem.getOriginalFilename())
                        .collect(Collectors.toSet());
    }
}