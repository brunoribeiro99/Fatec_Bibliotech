package br.com.bibliotech.strategy;

import org.springframework.stereotype.Component;

@Component("PADRAO")
public class PrazoPadraoStrategy implements StrategyPrazo {

    @Override
    public int calcular(int prazoBaseDias) {
        return prazoBaseDias;
    }
}