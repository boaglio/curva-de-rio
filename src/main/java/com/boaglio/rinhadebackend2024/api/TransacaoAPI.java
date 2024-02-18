package com.boaglio.rinhadebackend2024.api;

import com.boaglio.rinhadebackend2024.domain.Cliente;
import com.boaglio.rinhadebackend2024.domain.Transacao;
import com.boaglio.rinhadebackend2024.dto.TransacaoRequest;
import com.boaglio.rinhadebackend2024.dto.TransacaoResponse;
import com.boaglio.rinhadebackend2024.repository.ClienteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class TransacaoAPI {

    public static final int MAX_SIZE_DESCRICAO = 10;
    private static long contador = 0L;

    private static  final String CREDITO = "c";
    private static  final String DEBITO = "d";

    private static final ObjectMapper mapper = new ObjectMapper();
    private final ClienteRepository clienteRepository;
    Logger log = LoggerFactory.getLogger(TransacaoAPI.class.getSimpleName());

    public TransacaoAPI( ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }


    @PostMapping("/clientes/{id}/transacoes")
    public ResponseEntity<Object> transacoes(@PathVariable Long id, @RequestBody String  bodyStr ) {
        // validation - pra q Spring Validation, veja q codigo lindo:
        TransacaoRequest body;
        try {
            body = mapper.readValue(bodyStr, TransacaoRequest.class);
            // tudo isso porque o Spring faz o cast de "1.2" (double) para "1" e não lança erro
            String  valor = bodyStr.split(",")[0];
            if (valor.contains("."))  {
                log.info(++contador + " Invalid request ! - valor decimal");
                return ResponseEntity.unprocessableEntity().build();
            }
        } catch (JsonProcessingException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
        if (Objects.isNull(body)||
            Cliente.invalidCustomer(id) || // ID cliente
            Objects.isNull(body.tipo()) || body.tipo().isEmpty() ||  !Transacao.validTipoTransacao(body.tipo())  ||  // tipo
            Objects.isNull(body.descricao())|| body.descricao().isEmpty() ||  body.descricao().length()> MAX_SIZE_DESCRICAO ||    // descricao
            body.valor() <= 0 // somente valores positivos
         ) {
            log.info(++contador+" Invalid request ! ");
            return ResponseEntity.unprocessableEntity().build();
        }
        // comeco da transacao
        var valorDaTransacao = body.valor();
        var cliente = clienteRepository.findById(id).get();
        var saldoAtual = cliente.getSaldo();
        var limiteDoCliente = cliente.getLimite();
        if (DEBITO.equals(body.tipo())) {
            if ((saldoAtual + limiteDoCliente) >= valorDaTransacao) {
                saldoAtual -= valorDaTransacao;
                log.info(++contador + " Debito: " + valorDaTransacao + " saldo: " + saldoAtual + " limite: " + limiteDoCliente + " - cliente: " + id);
            } else {
                log.info(++contador + " Sem saldo - cliente: " + id);
                return ResponseEntity.unprocessableEntity().build();
            }
        } else {
            // credito
            saldoAtual+=valorDaTransacao;
            log.info(++contador + " Credito - cliente: " + valorDaTransacao + " saldo: "+ saldoAtual + " limite: " +limiteDoCliente +  "- cliente: "+id);
        }
        cliente.setTransacoes(cliente.adicionaNovaTransacao(new Transacao(valorDaTransacao, body.tipo(), body.descricao())));
        cliente.setSaldo(saldoAtual);
        // salva transacao
        clienteRepository.save(cliente);

       return ResponseEntity.ok(new TransacaoResponse(cliente.getLimite(), saldoAtual));
    }

}