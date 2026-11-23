package br.com.bibliotech.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component // Escopo padrão do Spring: SINGLETON (1 instância por container)
public class ConfiguracoesBiblioTech {

    private static final Logger log = LoggerFactory.getLogger(ConfiguracoesBiblioTech.class);

    private final int prazoPadraoDias = 7;
    private final int limitePadraoLivros = 3;
    private final BigDecimal multaDiariaPadrao = new BigDecimal("2.50");
    private final String nomeBiblioteca = "BiblioTech";

    @PostConstruct
    void logInstancia() {
        log.info("ConfiguracoesBiblioTech criada: identityHashCode={}", System.identityHashCode(this));
    }

    public int getPrazoPadraoDias() { return prazoPadraoDias; }
    public int getLimitePadraoLivros() { return limitePadraoLivros; }
    public BigDecimal getMultaDiariaPadrao() { return multaDiariaPadrao; }
    public String getNomeBiblioteca() { return nomeBiblioteca; }
}