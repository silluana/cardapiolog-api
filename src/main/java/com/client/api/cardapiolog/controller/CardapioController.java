package com.client.api.cardapiolog.controller;

import com.client.api.cardapiolog.dto.CardapioDto;
import com.client.api.cardapiolog.entity.Cardapio;
import com.client.api.cardapiolog.repository.CardapioRepository;
import com.client.api.cardapiolog.repository.projection.CardapioProjection;
import com.client.api.cardapiolog.repository.specification.CardapioSpec;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Objects;
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
    public ResponseEntity<Page<Cardapio>> consultarTodos(@RequestParam("page") Integer page, @RequestParam("size") Integer size,
                                                         @RequestParam(value = "sort", required = false) Sort.Direction sort,
                                                         @RequestParam(value = "property", required = false) String property) {
        Pageable pageable = getPageable(page, size, sort, property);
        return ResponseEntity.status(HttpStatus.OK).body(cardapioRepository.findAll(pageable));
    }

    @GetMapping("/categoria/{categoriaId}/disponivel")
    public ResponseEntity<Page<Cardapio>> consultarTodosPorCategoria(@PathVariable("categoriaId") final Integer categoriaId,
                                                                               @RequestParam("page") Integer page, @RequestParam("size") Integer size,
                                                                               @RequestParam(value = "sort", required = false) Sort.Direction sort,
                                                                               @RequestParam(value = "property", required = false) String property) {
        Pageable pageable = getPageable(page, size, sort, property);
        Specification<Cardapio> spec = CardapioSpec.categoria(categoriaId)
                .and(CardapioSpec.disponivel(true));
        return ResponseEntity.status(HttpStatus.OK).body(cardapioRepository.findAll(spec, pageable));
    }

    @GetMapping("/nome/{nome}/disponivel")
    public ResponseEntity<List<Cardapio>> consultarTodosPorNome(@PathVariable("nome") final String nome,
                                                                   @RequestParam("page") Integer page, @RequestParam("size") Integer size,
                                                                   @RequestParam(value = "sort", required = false) Sort.Direction sort,
                                                                   @RequestParam(value = "property", required = false) String property) {
        Pageable pageable = getPageable(page, size, sort, property);
        Specification<Cardapio> spec = CardapioSpec.nome(nome)
                .and(CardapioSpec.disponivel(true));
        return ResponseEntity.status(HttpStatus.OK).body(cardapioRepository.findAll(spec, pageable).getContent());
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

    private static PageRequest getPageable(Integer page, Integer size, Sort.Direction sort, String property) {
        return Objects.nonNull(sort)
                ? PageRequest.of(page, size, Sort.by(sort, property))
                : PageRequest.of(page, size);
    }
}
