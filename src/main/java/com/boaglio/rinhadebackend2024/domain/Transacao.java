package com.boaglio.rinhadebackend2024.domain;

import java.time.ZonedDateTime;

public class Transacao {

    Long valor;
    String tipo;
    String descricao; // max(10)

    String realizadaEm;

    public Transacao(Long valor, String tipo, String descricao) {
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.realizadaEm = ZonedDateTime.now().toString();
    }

    public static boolean validTipoTransacao(String tipo) {
        return tipo.equals("c") || tipo.equals("d");
    }

    public Long getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getRealizadaEm() {
        return realizadaEm;
    }

    @Override
    public String toString() {
        return "Transacao{" +
                " valor=" + valor +
                ", tipo=" + tipo +
                ", descricao='" + descricao + '\'' +
                ", realizadaEm='" + realizadaEm + '\'' +
                '}';
    }
}