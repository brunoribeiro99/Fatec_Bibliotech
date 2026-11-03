package br.com.bibliotech.config;

import br.com.bibliotech.model.Categoria;
import br.com.bibliotech.model.Livro;
import br.com.bibliotech.repository.CategoriaRepository;
import br.com.bibliotech.repository.LivroRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    ApplicationRunner seed(CategoriaRepository categorias, LivroRepository livros) {
        return args -> {
            if (categorias.count() > 0) return;

            var computacao = categorias.save(new Categoria("Computação", "Livros técnicos de TI e Software"));
            var literatura = categorias.save(new Categoria("Literatura Clássica", "Romances e obras consagradas"));

            livros.save(new Livro("Design Patterns (GoF)", "Erich Gamma et al.", "LIV-001", 5, computacao));
            livros.save(new Livro("Clean Code", "Robert C. Martin", "LIV-002", 3, computacao));
            livros.save(new Livro("Dom Casmurro", "Machado de Assis", "LIV-003", 8, literatura));
        };
    }
}