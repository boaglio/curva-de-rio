package com.boaglio.rinhadebackend2024;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;

@Document(collection = "transacao")
public class Transacao {
    enum TipoTransacao { c,d } // credito / débito
    @Id
    String id;
    @Indexed
    Long clienteId;
    Long valor;
    TipoTransacao tipo;
    String descricao; // max(10)
    @Indexed
    String realizadaEm;

    public Transacao(Long clienteId, Long valor, TipoTransacao tipo, String descricao) {
        this.clienteId = clienteId;
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.realizadaEm = ZonedDateTime.now().toString();
    }

    public static boolean validTipoTransacao(String tipo) {
        return tipo.equals(TipoTransacao.c.name()) || tipo.equals(TipoTransacao.d.name());
    }

    public String getId() {
        return id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public Long getValor() {
        return valor;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getRealizadaEm() {
        return realizadaEm;
    }
}