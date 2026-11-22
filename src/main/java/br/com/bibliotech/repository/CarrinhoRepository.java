package br.com.bibliotech.repository;

import br.com.bibliotech.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    Optional<Carrinho> findByAlunoId(Long alunoId);
}