package com.client.api.cardapiolog.repository;

import com.client.api.cardapiolog.dto.CardapioDto;
import com.client.api.cardapiolog.entity.Cardapio;
import com.client.api.cardapiolog.repository.projection.CardapioProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface CardapioRepository extends JpaRepository<Cardapio, Integer>, JpaSpecificationExecutor<Cardapio> {

    @Query("SELECT new com.client.api.cardapiolog.dto.CardapioDto(c.nome, c.descricao, c.valor, c.categoria.nome)" +
            " FROM Cardapio c WHERE c.nome LIKE %:nome% AND c.disponivel = true")
    Page<CardapioDto> findAllByNome(final String nome, final Pageable pageable);

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
            "       WHERE c.categoria_id = ?1 AND c.disponivel = true ",
            nativeQuery = true,
            countQuery = "SELECT count(*) FROM cardapio ")
    Page<CardapioProjection> findAllByCategoria(final Integer categoriaId, final Pageable pageable);

    /**
     * Exemplo de Query Modify
     */
    @Transactional
    @Modifying
    @Query("UPDATE Cardapio c SET c.disponivel = " +
            "CASE c.disponivel " +
            "WHEN true THEN false " +
            "ELSE true END " +
            "WHERE c.id = :id")
    Integer updateDisponibilidade(@Param("id")final Integer id);
}
