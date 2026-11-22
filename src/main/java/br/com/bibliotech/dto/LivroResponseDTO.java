package br.com.bibliotech.dto;

public record LivroResponseDTO(
        Long id,
        Long livroId,
        String tituloLivro,
        int quantidade) { 

            
        }