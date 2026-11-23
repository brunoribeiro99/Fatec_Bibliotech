package br.com.bibliotech.strategy;

import org.springframework.stereotype.Component;

@Component("ESTENDIDO")
public class PrazoEstendidoStrategy implements StrategyPrazo {

    @Override
    public int calcular(int prazoBaseDias) {
        return prazoBaseDias + 7;
    }
}