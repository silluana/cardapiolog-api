package com.client.api.cardapiolog.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ordens_cardapio")
public class OrdensCardapio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ordem ordem;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cardapio cardapio;

    private Integer quantidade;

    @Column(name = "valor_de_registro")
    private BigDecimal valorDeRegistro;

    public OrdensCardapio() {
    }

    public OrdensCardapio(Cardapio cardapio, Integer quantidade) {
        this.cardapio = cardapio;
        this.quantidade = quantidade;
        this.valorDeRegistro = cardapio.getValor();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Ordem getOrdem() {
        return ordem;
    }

    public void setOrdem(Ordem ordem) {
        this.ordem = ordem;
    }

    public Cardapio getCardapio() {
        return cardapio;
    }

    public void setCardapio(Cardapio cardapio) {
        this.cardapio = cardapio;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorDeRegistro() {
        return valorDeRegistro;
    }

    public void setValorDeRegistro(BigDecimal valor) {
        this.valorDeRegistro = valor;
    }

    @Override
    public String toString() {
        return "OrdensCardapio{" +
                "id=" + id +
                ", cardapio=" + cardapio +
                ", quantidade=" + quantidade +
                ", valorDeRegistro=" + valorDeRegistro +
                '}';
    }

}
