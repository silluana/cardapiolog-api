package com.client.api.cardapiolog.repository.specification;

import com.client.api.cardapiolog.entity.Cardapio;
import org.springframework.data.jpa.domain.Specification;

public class CardapioSpec {

    public static Specification<Cardapio> nome(String nome) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("nome"), "%"+nome+"%"));
    }

    public static Specification<Cardapio> categoria(Integer categoria) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("categoria"), categoria));
    }

    public static Specification<Cardapio> disponivel(boolean disponivel) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("disponivel"), disponivel));
    }
}
