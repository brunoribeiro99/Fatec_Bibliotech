package br.com.bibliotech.service;

import br.com.bibliotech.dto.*;
import br.com.bibliotech.exception.RegraDeNegocioException;
import br.com.bibliotech.exception.ResourceNotFoundException;
import br.com.bibliotech.model.Aluno;
import br.com.bibliotech.model.Curso;
import br.com.bibliotech.repository.AlunoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Transactional
    public AlunoResponseDTO criar(AlunoDTO dto) {
        if (alunoRepository.existsByEmail(dto.email())) {
            throw new RegraDeNegocioException("E-mail já cadastrado: " + dto.email());
        }
        var aluno = new Aluno();
        aluno.setNome(dto.nome());
        aluno.setEmail(dto.email());
        aluno.setSenhaHash(dto.senha());
        aluno.setTelefone(dto.telefone());
        return paraResponse(alunoRepository.save(aluno));
    }

    @Transactional(readOnly = true)
    public AlunoResponseDTO buscar(Long id) {
        return paraResponse(buscarAluno(id));
    }

    @Transactional
    public void adicionarCurso(Long alunoId, CursoDTO dto) {
        var aluno = buscarAluno(alunoId);
        var curso = new Curso();
        curso.setRa(dto.ra());
        curso.setNomeCurso(dto.nomeCurso());
        curso.setNivel(dto.nivel());
        curso.setTurno(dto.turno());
        curso.setAluno(aluno);
        aluno.getCursos().add(curso);
    }

    private Aluno buscarAluno(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado: " + id));
    }

    private AlunoResponseDTO paraResponse(Aluno a) {
        return new AlunoResponseDTO(
                a.getId(), a.getNome(), a.getEmail(), a.getTelefone(),
                a.getCursos().stream()
                        .map(c -> new CursoDTO(c.getRa(), c.getNomeCurso(),
                                c.getNivel(), c.getTurno()))
                        .toList());
    }
}