package br.com.bibliotech.controller;

import br.com.bibliotech.dto.*;
import br.com.bibliotech.service.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmprestimoResponseDTO finalizar(@RequestParam Long alunoId,
                                           @Valid @RequestBody EmprestimoRequestDTO dto) {
        return emprestimoService.finalizar(alunoId, dto);
    }

    @GetMapping
    public List<EmprestimoResponseDTO> historico(@RequestParam Long alunoId) {
        return emprestimoService.historico(alunoId);
    }

    @GetMapping("/{id}")
    public EmprestimoResponseDTO buscar(@RequestParam Long alunoId, @PathVariable Long id) {
        return emprestimoService.buscar(alunoId, id);
    }

    @PostMapping("/{id}/devolucao")
    public EmprestimoResponseDTO devolver(@RequestParam Long alunoId, @PathVariable Long id) {
        return emprestimoService.devolver(alunoId, id);
    }

    @PostMapping("/{id}/cancelamento")
    public EmprestimoResponseDTO cancelar(@RequestParam Long alunoId, @PathVariable Long id) {
        return emprestimoService.cancelar(alunoId, id);
    }
}