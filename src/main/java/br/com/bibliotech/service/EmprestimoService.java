package br.com.bibliotech.service;

import br.com.bibliotech.config.ConfiguracoesBiblioTech;
import br.com.bibliotech.dto.*;
import br.com.bibliotech.exception.RegraDeNegocioException;
import br.com.bibliotech.exception.ResourceNotFoundException;
import br.com.bibliotech.factory.TipoUsuarioFactory;
import br.com.bibliotech.model.*;
import br.com.bibliotech.repository.*;
import br.com.bibliotech.strategy.CalculadoraPrazo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final CarrinhoRepository carrinhoRepository;
    private final CursoRepository cursoRepository;
    private final ConfiguracoesBiblioTech configuracoes;
    private final TipoUsuarioFactory tipoUsuarioFactory;
    private final CalculadoraPrazo calculadoraPrazo;

    public EmprestimoService(EmprestimoRepository emprestimoRepository,
                             CarrinhoRepository carrinhoRepository,
                             CursoRepository cursoRepository,
                             ConfiguracoesBiblioTech configuracoes,
                             TipoUsuarioFactory tipoUsuarioFactory,
                             CalculadoraPrazo calculadoraPrazo) {
        this.emprestimoRepository = emprestimoRepository;
        this.carrinhoRepository = carrinhoRepository;
        this.cursoRepository = cursoRepository;
        this.configuracoes = configuracoes;
        this.tipoUsuarioFactory = tipoUsuarioFactory;
        this.calculadoraPrazo = calculadoraPrazo;
    }

    // Diagnóstico didático (Aula 10 - Singleton)
    public long identityHashCodeConfiguracoes() {
        return System.identityHashCode(configuracoes);
    }

    @Transactional
    public EmprestimoResponseDTO finalizar(Long alunoId, EmprestimoRequestDTO dto) {
        var carrinho = carrinhoRepository.findByAlunoId(alunoId)
                .filter(c -> !c.getItens().isEmpty())
                .orElseThrow(() -> new RegraDeNegocioException(
                        "Carrinho vazio: adicione livros antes de finalizar o empréstimo"));

        var curso = cursoRepository.findById(dto.cursoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Curso não encontrado: " + dto.cursoId()));

        int prazoDias = configuracoes.getPrazoPadraoDias();
        int limiteLivros = configuracoes.getLimitePadraoLivros();

        if (carrinho.getCodigoTipoUsuario() != null) {
            var tipoUsuario = tipoUsuarioFactory.criar(carrinho.getCodigoTipoUsuario());
            prazoDias = tipoUsuario.calcularPrazoDias(prazoDias);
            limiteLivros = tipoUsuario.calcularLimiteLivros(limiteLivros);
        }

        String estrategia = carrinho.getEstrategiaPrazo() == null ? "PADRAO" : carrinho.getEstrategiaPrazo();
        prazoDias = calculadoraPrazo.calcular(estrategia, prazoDias); // Strategy em ação

        var emprestimo = new Emprestimo();
        emprestimo.setAluno(carrinho.getAluno());
        emprestimo.setCurso(curso);
        emprestimo.setStatus(Status.EMPRESTADO);
        emprestimo.setDataPrevistaDevolucao(LocalDate.now().plusDays(prazoDias));

        int totalLivros = 0;
        for (var item : carrinho.getItens()) {
            var livro = item.getLivro();

            if (livro.getCopiasDisponiveis() < item.getQuantidade()) {
                throw new RegraDeNegocioException("Cópias insuficientes para " + livro.getTitulo());
            }
            livro.setCopiasDisponiveis(livro.getCopiasDisponiveis() - item.getQuantidade());

            var livroEmprestado = new LivroEmprestado(emprestimo, livro, item.getQuantidade());
            emprestimo.getItens().add(livroEmprestado);
            totalLivros += item.getQuantidade();
        }

        if (totalLivros > limiteLivros) {
            throw new RegraDeNegocioException(
                    "Limite de " + limiteLivros + " livros por empréstimo excedido");
        }

        emprestimo.setTotalLivros(totalLivros);

        emprestimoRepository.save(emprestimo);
        emprestimo.setNumero(String.format("BT-%04d", emprestimo.getId()));

        carrinho.getItens().clear();
        carrinho.setCodigoTipoUsuario(null); // tipo de usuário não sobrevive ao empréstimo
        carrinho.setEstrategiaPrazo(null);   // estratégia não sobrevive ao empréstimo
        return paraResponse(emprestimo);
    }

    @Transactional(readOnly = true)
    public EmprestimoResponseDTO buscar(Long alunoId, Long emprestimoId) {
        return paraResponse(buscarEmprestimo(alunoId, emprestimoId));
    }

    @Transactional(readOnly = true)
    public List<EmprestimoResponseDTO> historico(Long alunoId) {
        return emprestimoRepository.findByAlunoIdOrderByDataEmprestimoDesc(alunoId)
                .stream().map(this::paraResponse).toList();
    }

    @Transactional
    public EmprestimoResponseDTO devolver(Long alunoId, Long emprestimoId) {
        var emprestimo = buscarEmprestimo(alunoId, emprestimoId);
        if (emprestimo.getStatus() != Status.EMPRESTADO && emprestimo.getStatus() != Status.ATRASADO) {
            throw new RegraDeNegocioException(
                    "Só é possível devolver empréstimos em andamento");
        }
        emprestimo.setStatus(Status.DEVOLVIDO);
        emprestimo.getItens().forEach(i -> i.getLivro()
                .setCopiasDisponiveis(i.getLivro().getCopiasDisponiveis() + i.getQuantidade()));
        return paraResponse(emprestimo);
    }

    @Transactional
    public EmprestimoResponseDTO cancelar(Long alunoId, Long emprestimoId) {
        var emprestimo = buscarEmprestimo(alunoId, emprestimoId);
        if (emprestimo.getStatus() != Status.EMPRESTADO) {
            throw new RegraDeNegocioException(
                    "Só é possível cancelar empréstimos com status EMPRESTADO");
        }
        emprestimo.setStatus(Status.CANCELADO);
        emprestimo.getItens().forEach(i -> i.getLivro()
                .setCopiasDisponiveis(i.getLivro().getCopiasDisponiveis() + i.getQuantidade()));
        return paraResponse(emprestimo);
    }

    private Emprestimo buscarEmprestimo(Long alunoId, Long emprestimoId) {
        var emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Empréstimo não encontrado: " + emprestimoId));
        if (!emprestimo.getAluno().getId().equals(alunoId)) {
            throw new ResourceNotFoundException("Empréstimo não encontrado: " + emprestimoId);
        }
        return emprestimo;
    }

    private EmprestimoResponseDTO paraResponse(Emprestimo e) {
        var itens = e.getItens().stream()
                .map(i -> new LivroResponseDTO(
                        i.getId(),
                        i.getLivro().getId(),
                        i.getTituloSnapshot(),
                        i.getQuantidade()))
                .toList();

        return new EmprestimoResponseDTO(
                e.getId(),
                e.getNumero(),
                e.getStatus().name(),
                e.getTotalLivros(),
                e.getDataEmprestimo(),
                e.getDataPrevistaDevolucao(),
                itens);
    }
}