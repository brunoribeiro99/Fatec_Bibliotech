package br.com.bibliotech.controller;

import br.com.bibliotech.dto.AlunoDTO;
import br.com.bibliotech.dto.AlunoResponseDTO;
import br.com.bibliotech.dto.CursoDTO;
import br.com.bibliotech.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlunoResponseDTO criar(@Valid @RequestBody AlunoDTO dto) {
        return alunoService.criar(dto);
    }

    @GetMapping("/{id}")
    public AlunoResponseDTO buscar(@PathVariable Long id) {
        return alunoService.buscar(id);
    }

    @PostMapping("/{id}/cursos")
    @ResponseStatus(HttpStatus.CREATED)
    public void adicionarCurso(@PathVariable Long id,
                               @Valid @RequestBody CursoDTO dto) {
        alunoService.adicionarCurso(id, dto);
    }
}