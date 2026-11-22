package br.com.bibliotech.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record LivroCarrinhoDTO(
        @NotNull Long livroId,
        @NotNull @Min(1) Integer quantidade) { 
    
            
        }