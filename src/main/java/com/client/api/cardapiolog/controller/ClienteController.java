package com.client.api.cardapiolog.controller;

import com.client.api.cardapiolog.entity.Cliente;
import com.client.api.cardapiolog.entity.ClienteId;
import com.client.api.cardapiolog.repository.ClienteRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;

    ClienteController (ClienteRepository clienteRepository, ObjectMapper objectMapper) {
        this.clienteRepository = clienteRepository;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseEntity<Page<Cliente>> consultarTodos(@RequestParam("page") Integer page, @RequestParam("size") Integer size,
                                                         @RequestParam(value = "sort", required = false) Sort.Direction sort,
                                                         @RequestParam(value = "property", required = false) String property) {
        Pageable pageable = Objects.nonNull(sort)
                ? PageRequest.of(page, size, Sort.by(sort, property))
                : PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(clienteRepository.findAll(pageable));
    }

    @GetMapping("/{email}/{cpf}")
    public ResponseEntity<Cliente> consultarPorEmailCpf(@PathVariable final String email, @PathVariable final String cpf) {
        return clienteRepository.findById(new ClienteId(email, cpf))
                .map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable("id") final String id, @RequestBody final Cliente cliente) throws JsonMappingException {
        Optional<Cliente> clienteEncontrado = this.clienteRepository.findByEmailOrCpf(id);
        if (clienteEncontrado.isPresent()) {
            objectMapper.updateValue(clienteEncontrado.get(), cliente);
            return ResponseEntity.status(HttpStatus.OK).body(this.clienteRepository.save(clienteEncontrado.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
