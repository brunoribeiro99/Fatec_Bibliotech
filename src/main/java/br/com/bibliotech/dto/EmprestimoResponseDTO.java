package br.com.bibliotech.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record EmprestimoResponseDTO(
        Long id,
        String numero,
        String status,
        int totalLivros,
        LocalDateTime dataEmprestimo,
        LocalDate dataPrevistaDevolucao,
        List<LivroResponseDTO> itens) { 

            
        }