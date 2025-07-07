package com.client.api.cardapiolog.controller;

import com.client.api.cardapiolog.entity.ClienteId;
import com.client.api.cardapiolog.entity.Ordem;
import com.client.api.cardapiolog.entity.OrdensCardapio;
import com.client.api.cardapiolog.repository.ClienteRepository;
import com.client.api.cardapiolog.repository.OrdemRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/ordem")
public class OrdemController {

    private final OrdemRepository ordemRepository;
    private final ObjectMapper objectMapper;
    private final ClienteRepository clienteRepository;

    OrdemController(OrdemRepository ordemRepository, ObjectMapper objectMapper, ClienteRepository clienteRepository) {
        this.ordemRepository = ordemRepository;
        this.objectMapper = objectMapper;
        this.clienteRepository = clienteRepository;
    }

    @PostMapping
    public ResponseEntity<Ordem> criar(@RequestBody final Ordem ordem) {
        var cliente = clienteRepository.findById(new ClienteId(ordem.getCliente().getEmail(), ordem.getCliente().getCpf()))
                .orElseThrow(() -> new IllegalArgumentException("Cliente not found"));
        ordem.setCliente(cliente);

        for (OrdensCardapio oc : ordem.getOrdensCardapioList()) {
            oc.setOrdem(ordem);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(this.ordemRepository.save(ordem));
    }

    @GetMapping
    public ResponseEntity<List<Ordem>> consultarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(ordemRepository.findAll());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Ordem> atualizar(@PathVariable("id") final Integer id, @RequestBody final Ordem ordem) throws JsonMappingException {
        Optional<Ordem> ordemEncontrado = this.ordemRepository.findById(id);
        if (ordemEncontrado.isPresent()) {
            objectMapper.updateValue(ordemEncontrado.get(), ordem);
            return ResponseEntity.status(HttpStatus.OK).body(this.ordemRepository.save(ordemEncontrado.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
