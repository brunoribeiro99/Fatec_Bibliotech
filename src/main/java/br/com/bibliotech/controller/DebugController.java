package br.com.bibliotech.controller;

import br.com.bibliotech.config.ConfiguracoesBiblioTech;
import br.com.bibliotech.service.EmprestimoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/debug")
public class DebugController {

    private final ConfiguracoesBiblioTech configuracoes;
    private final EmprestimoService emprestimoService;

    public DebugController(ConfiguracoesBiblioTech configuracoes,
                           EmprestimoService emprestimoService) {
        this.configuracoes = configuracoes;
        this.emprestimoService = emprestimoService;
    }

    @GetMapping("/instancias")
    public Map<String, Object> instancias() {
        long hashController = System.identityHashCode(configuracoes);
        long hashService = emprestimoService.identityHashCodeConfiguracoes();
        return Map.of(
            "hashNoController", hashController,
            "hashNoEmprestimoService", hashService,
            "mesmaInstancia", hashController == hashService,
            "explicacao", "Escopo padrão do Spring é singleton: 1 instância por container"
        );
    }
}