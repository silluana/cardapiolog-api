package com.client.api.cardapiolog.controller;

import com.client.api.cardapiolog.entity.Categoria;
import com.client.api.cardapiolog.repository.CategoriaRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/categoria")
public class CategoriaController {
    
    private final CategoriaRepository categoriaRepository;
    private final ObjectMapper objectMapper;
    
    CategoriaController(CategoriaRepository categoriaRepository, ObjectMapper objectMapper) {
        this.categoriaRepository = categoriaRepository;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> consultarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(this.categoriaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> consultarPorId(@PathVariable("id") final Integer id) {
        Optional<Categoria> categoria = this.categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(categoria.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping
    public ResponseEntity<Categoria> criar(@RequestBody final Categoria categoria) throws JsonMappingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.categoriaRepository.save(categoria));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Categoria> atualizar(@PathVariable("id") final Integer id, @RequestBody final Categoria categoria) throws JsonMappingException {
        Optional<Categoria> categoriaEncontrada = this.categoriaRepository.findById(id);
        if (categoriaEncontrada.isPresent()) {
            objectMapper.updateValue(categoriaEncontrada.get(), categoria);
            return ResponseEntity.status(HttpStatus.OK).body(this.categoriaRepository.save(categoriaEncontrada.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirPorId(@PathVariable("id") final Integer id) {
        Optional<Categoria> categoria = this.categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            this.categoriaRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Elemento não encontrado");
    }
}
