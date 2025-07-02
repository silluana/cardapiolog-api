package com.client.api.cardapiolog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/cardapio")
public class CardapioController {

    @GetMapping("/teste")
    private String teste() {
        return "Teste Sucesso!";
    }
}
