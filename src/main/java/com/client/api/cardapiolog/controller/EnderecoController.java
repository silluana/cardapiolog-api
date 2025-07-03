package com.client.api.cardapiolog.controller;

import com.client.api.cardapiolog.entity.Endereco;
import com.client.api.cardapiolog.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cliente/endereco")
public class EnderecoController {

    private final EnderecoRepository enderecoRepository;
    private final ObjectMapper objectMapper;

    EnderecoController(EnderecoRepository enderecoRepository, ObjectMapper objectMapper) {
        this.enderecoRepository = enderecoRepository;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseEntity<List<Endereco>> consultarTodos(){
        return ResponseEntity.status(HttpStatus.OK).body(enderecoRepository.findAll());
    }

    @GetMapping("/cep/{cep}")
    public ResponseEntity<List<Endereco>> consultarPorCep(@PathVariable("cep") final String cep){
        return ResponseEntity.status(HttpStatus.OK).body(enderecoRepository.findByCep(cep));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Endereco>> consultarPorEmailCpf(@PathVariable final String id) {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoRepository.findListByClienteId(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Endereco> atualizar(@PathVariable("id") final Integer id, @RequestBody final Endereco endereco) throws JsonMappingException {
        Optional<Endereco> enderecoEncontrado = this.enderecoRepository.findById(id);
        if (enderecoEncontrado.isPresent()) {
            objectMapper.updateValue(enderecoEncontrado.get(), endereco);
            return ResponseEntity.status(HttpStatus.OK).body(this.enderecoRepository.save(enderecoEncontrado.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
