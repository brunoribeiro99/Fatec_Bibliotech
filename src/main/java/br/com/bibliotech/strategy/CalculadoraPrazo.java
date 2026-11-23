package br.com.bibliotech.strategy;

import br.com.bibliotech.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CalculadoraPrazo {

    // O Spring injeta TODOS os beans StrategyPrazo, chaveados pelo nome do bean
    private final Map<String, StrategyPrazo> strategies;

    public CalculadoraPrazo(Map<String, StrategyPrazo> strategies) {
        this.strategies = strategies;
    }

    public boolean existe(String tipo) {
        return strategies.containsKey(normalizar(tipo));
    }

    public int calcular(String tipo, int prazoBaseDias) {
        var normalizado = normalizar(tipo);
        if (!strategies.containsKey(normalizado)) {
            throw new ResourceNotFoundException("Estratégia de prazo inválida: " + tipo);
        }
        return strategies.get(normalizado).calcular(prazoBaseDias);
    }

    private String normalizar(String tipo) {
        return (tipo == null || tipo.isBlank()) ? "PADRAO" : tipo.toUpperCase();
    }
}