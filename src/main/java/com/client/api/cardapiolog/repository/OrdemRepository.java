package com.client.api.cardapiolog.repository;

import com.client.api.cardapiolog.entity.Ordem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdemRepository extends JpaRepository<Ordem, Integer> {
}
