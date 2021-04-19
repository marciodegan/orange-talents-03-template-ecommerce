package br.com.zupacademy.marcio.ecommerce.produto;

import br.com.zupacademy.marcio.ecommerce.categoria.Categoria;
import br.com.zupacademy.marcio.ecommerce.compartilhado.ExistsId;
import br.com.zupacademy.marcio.ecommerce.usuario.Usuario;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NovoProdutoRequest {

    @NotBlank // permite nomes iguais ou @UniqueValue(domainClass = Produto.class, fieldName = "nome")
    private String nome;
    @NotNull @Positive
    private BigDecimal valor;
    @NotNull @Positive
    private int quantidade;
    @NotBlank @Length(max = 1000)
    private String descricao;
    @NotNull @ExistsId(klass = Categoria.class)
    private Long idCategoria;
    @Size(min=3) @Valid
    private List<NovaCaracteristicaRequest> caracteristicas = new ArrayList<>();


    public NovoProdutoRequest(@NotBlank String nome,
                              @NotNull @Positive BigDecimal valor,
                              @NotBlank @Positive int quantidade,
                              @NotBlank @Length(max = 1000) String descricao,
                              @NotNull Long idCategoria,
                              List<NovaCaracteristicaRequest> caracteristicas) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.idCategoria = idCategoria;
        this.caracteristicas.addAll(caracteristicas);
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<NovaCaracteristicaRequest> getCaracteristicas() {
        return caracteristicas;
    }

    @Override
    public String toString() {
        return "NovoProdutoRequest{" +
                "nome='" + nome + '\'' +
                ", valor=" + valor +
                ", quantidade=" + quantidade +
                ", descricao='" + descricao + '\'' +
                ", idCategoria=" + idCategoria +
                ", caracteristicas=" + caracteristicas +
                '}';
    }

    public Produto toModel(EntityManager manager, Usuario dono) {
        Categoria categoria = manager.find(Categoria.class,idCategoria);
        return new Produto(nome, valor, quantidade, descricao, categoria, dono, caracteristicas);
    }

    public Set<String> buscaCaracteristicasIguais(){
        HashSet<String> nomesIguais = new HashSet<>();
        HashSet<String> resultados = new HashSet<>();
        for(NovaCaracteristicaRequest caracteristica : caracteristicas){
            String nome = caracteristica.getNome();
            if(!nomesIguais.add(nome)){         // se não consegue adicionar
                resultados.add(nome);           // então guarda na HashSet resultados.
            }
        }
        return resultados;
    }


    /* Só mantendo esse código para referência. Foi substituído pelo método buscaCaracteristicasIguais().

    public boolean temCaracteristicasIguais() {
        HashSet<String> nomesIguais = new HashSet<>(); // conjunto auxiliar - o Hashset não suporta elementos iguais e a classe String já tem os métodos de comparação entre strings implementados.
        for(NovaCaracteristicaRequest caracteristica : caracteristicas) {
            if(!nomesIguais.add(caracteristica.getNome())) { // se não tiver adicionado características iguais, retorna true.
                return true;
            }
        }
        return false;

    }*/

}

