package br.com.bibliotech.service;

import br.com.bibliotech.dto.*;
import br.com.bibliotech.exception.RegraDeNegocioException;
import br.com.bibliotech.exception.ResourceNotFoundException;
import br.com.bibliotech.model.*;
import br.com.bibliotech.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final LivroRepository livroRepository;
    private final AlunoRepository alunoRepository;

    public CarrinhoService(CarrinhoRepository carrinhoRepository,
                           LivroRepository livroRepository,
                           AlunoRepository alunoRepository) {
        this.carrinhoRepository = carrinhoRepository;
        this.livroRepository = livroRepository;
        this.alunoRepository = alunoRepository;
    }

    @Transactional
    public CarrinhoResponseDTO obter(Long alunoId) {
        return paraResponse(buscarOuCriar(alunoId));
    }

    @Transactional
    public CarrinhoResponseDTO adicionarLivro(Long alunoId, LivroCarrinhoDTO dto) {
        var carrinho = buscarOuCriar(alunoId);
        var livro = livroRepository.findById(dto.livroId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Livro não encontrado: " + dto.livroId()));

        if (livro.getCopiasDisponiveis() <= 0) {
            throw new RegraDeNegocioException("Livro indisponível no momento: " + livro.getTitulo());
        }

        carrinho.getItens().stream()
                .filter(i -> i.getLivro().getId().equals(dto.livroId()))
                .findFirst()
                .ifPresentOrElse(
                    item -> setQuantidade(item, item.getQuantidade() + dto.quantidade()),
                    () -> {
                        var item = new ItemCarrinho(carrinho, livro, dto.quantidade());
                        setQuantidade(item, dto.quantidade());
                        carrinho.getItens().add(item);
                    });
        return paraResponse(carrinho);
    }

    @Transactional
    public CarrinhoResponseDTO alterarQuantidade(Long alunoId, Long itemId, int quantidade) {
        var carrinho = buscarOuCriar(alunoId);
        var item = carrinho.getItens().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado no carrinho: " + itemId));

        if (quantidade <= 0) {
            carrinho.getItens().remove(item);
        } else {
            setQuantidade(item, quantidade);
        }
        return paraResponse(carrinho);
    }

    @Transactional
    public CarrinhoResponseDTO removerLivro(Long alunoId, Long itemId) {
        var carrinho = buscarOuCriar(alunoId);
        carrinho.getItens().removeIf(i -> i.getId().equals(itemId));
        return paraResponse(carrinho);
    }

    // R1: quantidade nunca excede as cópias disponíveis
    private void setQuantidade(ItemCarrinho item, int quantidade) {
        if (quantidade > item.getLivro().getCopiasDisponiveis()) {
            throw new RegraDeNegocioException("Cópias insuficientes para "
                    + item.getLivro().getTitulo()
                    + " (disponível: " + item.getLivro().getCopiasDisponiveis() + ")");
        }
        item.setQuantidade(quantidade);
    }

    private Carrinho buscarOuCriar(Long alunoId) {
        return carrinhoRepository.findByAlunoId(alunoId)
                .orElseGet(() -> {
                    var aluno = alunoRepository.findById(alunoId)
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Aluno não encontrado: " + alunoId));
                    return carrinhoRepository.save(new Carrinho(aluno));
                });
    }

    private CarrinhoResponseDTO paraResponse(Carrinho c) {
        var itens = c.getItens().stream()
                .map(i -> new LivroResponseDTO(
                        i.getId(),
                        i.getLivro().getId(),
                        i.getLivro().getTitulo(),
                        i.getQuantidade()))
                .toList();

        int totalLivros = c.getItens().stream()
                .mapToInt(ItemCarrinho::getQuantidade)
                .sum();

        return new CarrinhoResponseDTO(c.getId(), itens, totalLivros);
    }
}