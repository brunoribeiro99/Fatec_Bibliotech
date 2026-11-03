package br.com.bibliotech.repository;

import br.com.bibliotech.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    Optional<Livro> findByCodigoLivro(String codigoLivro);
    List<Livro> findByCategoriaId(Long categoriaId);
}