package br.com.bibliotech.strategy;

import org.springframework.stereotype.Component;

@Component("CURTO")
public class PrazoCurtoStrategy implements StrategyPrazo {

    @Override
    public int calcular(int prazoBaseDias) {
        return Math.max(1, prazoBaseDias - 3);
    }
}