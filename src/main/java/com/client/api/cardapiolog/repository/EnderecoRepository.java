package com.client.api.cardapiolog.repository;

import com.client.api.cardapiolog.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    @Query("SELECT e FROM Endereco e WHERE e.cliente.clienteId.email = :id OR e.cliente.clienteId.cpf = :id")
    List<Endereco> findListByClienteId(@Param("id") final String id);

    List<Endereco> findByCep(final String cep);
}
