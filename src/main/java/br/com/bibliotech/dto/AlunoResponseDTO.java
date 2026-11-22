package br.com.bibliotech.dto;

import java.util.List;

public record AlunoResponseDTO(
        Long id, String nome, String email, String telefone,
        List<CursoDTO> cursos) {
}