package br.com.bibliotech.dto;

import java.util.List;

public record CarrinhoResponseDTO(
        Long id,
        List<LivroResponseDTO> itens,
        String tipoUsuario,
        String estrategiaPrazo,
        int prazoDias,
        int limiteLivros,
        int totalLivros
) { 

        
}