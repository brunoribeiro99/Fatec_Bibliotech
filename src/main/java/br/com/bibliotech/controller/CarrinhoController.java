package br.com.bibliotech.controller;

import br.com.bibliotech.dto.*;
import br.com.bibliotech.service.CarrinhoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    @GetMapping
    public CarrinhoResponseDTO obter(@RequestParam Long alunoId) {
        return carrinhoService.obter(alunoId);
    }

    @PostMapping("/itens")
    @ResponseStatus(HttpStatus.CREATED)
    public CarrinhoResponseDTO adicionar(@RequestParam Long alunoId,
                                         @Valid @RequestBody LivroCarrinhoDTO dto) {
        return carrinhoService.adicionarLivro(alunoId, dto);
    }

    @PutMapping("/itens/{itemId}")
    public CarrinhoResponseDTO alterar(@RequestParam Long alunoId,
                                       @PathVariable Long itemId,
                                       @RequestParam int quantidade) {
        return carrinhoService.alterarQuantidade(alunoId, itemId, quantidade);
    }

    @DeleteMapping("/itens/{itemId}")
    public CarrinhoResponseDTO remover(@RequestParam Long alunoId,
                                       @PathVariable Long itemId) {
        return carrinhoService.removerLivro(alunoId, itemId);
    }

    @PostMapping("/tipo-usuario")
    public CarrinhoResponseDTO aplicarTipoUsuario(@RequestParam Long alunoId,
                                                  @RequestParam String codigo) {
        return carrinhoService.aplicarTipoUsuario(alunoId, codigo);
    }

    @DeleteMapping("/tipo-usuario")
    public CarrinhoResponseDTO removerTipoUsuario(@RequestParam Long alunoId) {
        return carrinhoService.removerTipoUsuario(alunoId);
    }

    @PostMapping("/prazo")
    public CarrinhoResponseDTO definirPrazo(@RequestParam Long alunoId,
                                            @RequestParam String tipo) {
        return carrinhoService.definirEstrategiaPrazo(alunoId, tipo);
    }
}