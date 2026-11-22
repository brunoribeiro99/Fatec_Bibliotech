package br.com.bibliotech.dto;

import jakarta.validation.constraints.*;

public record CursoDTO(
        @NotBlank @Pattern(regexp = "\\d{8}") String ra,
        @NotBlank @Size(max = 120) String nomeCurso,
        @NotBlank String nivel,
        @NotBlank String turno) {
}