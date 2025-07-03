package com.client.api.cardapiolog.repository;

import com.client.api.cardapiolog.dto.CardapioDto;
import com.client.api.cardapiolog.entity.Cardapio;
import com.client.api.cardapiolog.repository.projection.CardapioProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CardapioRepository extends JpaRepository<Cardapio, Integer> {

    @Query("SELECT new com.client.api.cardapiolog.dto.CardapioDto(c.nome, c.descricao, c.valor, c.categoria.nome)" +
            " FROM Cardapio c WHERE c.nome LIKE %:nome% AND c.disponivel = true")
    List<CardapioDto> findAllByNome(final String nome);

    /**
     * Exemplo de Query Native
     */
    @Query(value = "SELECT " +
            "       c.nome as nome, " +
            "       c.descricao as descricao, " +
            "       c.valor as valor, " +
            "       cat.nome as nomeCategoria " +
            "       FROM cardapio c " +
            "       INNER JOIN categorias cat on categoria_id = cat.id " +
            "       WHERE c.categoria_id = ?1 AND c.disponivel = true ", nativeQuery = true)
    List<CardapioProjection> findAllByCategoria(final Integer categoriaId);

    /**
     * Exemplo de Query Modify
     */
    @Transactional
    @Modifying
    @Query("UPDATE cardapio c SET c.disponivel = " +
            "CASE c.disponivel " +
            "WHEN true THEN false " +
            "ELSE true END " +
            "WHERE c.id = :id")
    Integer updateDisponibilidade(@Param("id")final Integer id);
}
