package com.client.api.cardapiolog.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Contato {

    private String ddd;
    private String numero;

    public Contato() {
    }

    public Contato(String ddd, String numero) {
        this.ddd = ddd;
        this.numero = numero;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Contato{" +
                "ddd='" + ddd + '\'' +
                ", numero='" + numero + '\'' +
                '}';
    }
}
