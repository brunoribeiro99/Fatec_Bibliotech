package br.com.bibliotech.repository;

import br.com.bibliotech.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {



}