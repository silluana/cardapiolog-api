package com.client.api.cardapiolog.controller;

import com.client.api.cardapiolog.dto.CardapioDto;
import com.client.api.cardapiolog.entity.Cardapio;
import com.client.api.cardapiolog.repository.CardapioRepository;
import com.client.api.cardapiolog.repository.projection.CardapioProjection;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/cardapio")
public class CardapioController {

    private final CardapioRepository cardapioRepository;
    private final ObjectMapper objectMapper;

    CardapioController(CardapioRepository cardapioRepository, ObjectMapper objectMapper) {
        this.cardapioRepository = cardapioRepository;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseEntity<List<Cardapio>> consultarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(cardapioRepository.findAll());
    }

    @GetMapping("/categoria/{categoriaId}/disponivel")
    public ResponseEntity<List<CardapioProjection>> consultarTodosPorCategoria(@PathVariable("categoriaId") final Integer categoriaId) {
        return ResponseEntity.status(HttpStatus.OK).body(cardapioRepository.findAllByCategoria(categoriaId));
    }

    @GetMapping("/nome/{nome}/disponivel")
    public ResponseEntity<List<CardapioDto>> consultarTodosPorNome(@PathVariable("nome") final String nome) {
        return ResponseEntity.status(HttpStatus.OK).body(cardapioRepository.findAllByNome(nome));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cardapio> consultarPorId(@PathVariable("id") final Integer id) {
        Optional<Cardapio> cardapio = this.cardapioRepository.findById(id);
        if (cardapio.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(cardapio.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping
    public ResponseEntity<Cardapio> criar(@RequestBody final Cardapio cardapio) throws JsonMappingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.cardapioRepository.save(cardapio));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Cardapio> atualizar(@PathVariable("id") final Integer id, @RequestBody final Cardapio cardapio) throws JsonMappingException {
        Optional<Cardapio> cardapioEncontrado = this.cardapioRepository.findById(id);
        if (cardapioEncontrado.isPresent()) {
            objectMapper.updateValue(cardapioEncontrado.get(), cardapio);
            return ResponseEntity.status(HttpStatus.OK).body(this.cardapioRepository.save(cardapioEncontrado.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirPorId(@PathVariable("id") final Integer id) {
        Optional<Cardapio> cardapio = this.cardapioRepository.findById(id);
        if (cardapio.isPresent()) {
            this.cardapioRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Elemento não encontrado");
    }
}
