package br.com.bibliotech.repository;

import br.com.bibliotech.model.LivroEmprestado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroEmprestadoRepository extends JpaRepository<LivroEmprestado, Long> {
}