package com.boaglio.rinhadebackend2024.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "cliente")
public class Cliente {
    @Id
    private Integer id;
    private long limite;
    private long saldo;

    public Cliente() {
        this.transacoes = new ArrayList<>();
    }

    private List<Transacao> transacoes;

    public static boolean invalidCustomer(long idCustomer) {
        return idCustomer <= 0 || idCustomer >= 6;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getLimite() {
        return limite;
    }

    public void setLimite(long limite) {
        this.limite = limite;
    }

    public long getSaldo() {
        return saldo;
    }

    public void setSaldo(long saldo) {
        this.saldo = saldo;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }

    public List<Transacao> adicionaNovaTransacao(Transacao transacao) {
        var list = new ArrayList<Transacao>();
        list.add(transacao);
        int loop = Math.min(transacoes.size(), 9);
        for (int a=0;a<loop;a++) {
            list.add(transacoes.get(a));
        }
        return list;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", limite=" + limite +
                ", saldo=" + saldo +
                ", transacoes=" + transacoes +
                '}';
    }
}