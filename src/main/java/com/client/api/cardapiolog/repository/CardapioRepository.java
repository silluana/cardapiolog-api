package com.client.api.cardapiolog.repository;

import com.client.api.cardapiolog.entity.Cardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardapioRepository extends JpaRepository<Cardapio, Integer> {
}
