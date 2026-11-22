package br.com.bibliotech.dto;

import java.util.List;

public record CarrinhoResponseDTO(
        Long id,
        List<LivroResponseDTO> itens,
        int totalLivros) { 
            
        }