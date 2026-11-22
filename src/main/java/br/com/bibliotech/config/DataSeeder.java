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

           var fantasia = categorias.save(new Categoria("Fantasia", "Livros de fantasia e universos inventados"));
var games = categorias.save(new Categoria("Games", "Livros baseados em jogos e suas franquias"));

livros.save(new Livro("Assassin's Creed", "Oliver Bowden", "LIV-001", 5, games));
livros.save(new Livro("Harry Potter e a Pedra Filosofal", "J.K. Rowling", "LIV-002", 8, fantasia));
livros.save(new Livro("O Último Desejo (The Witcher)", "Andrzej Sapkowski", "LIV-003", 4, fantasia));
livros.save(new Livro("O Senhor dos Anéis: A Sociedade do Anel", "J.R.R. Tolkien", "LIV-004", 6, fantasia));
livros.save(new Livro("God of War", "J.M. Barlog", "LIV-005", 3, games));
livros.save(new Livro("Uncharted: O Quarto Labirinto", "Christopher Golden", "LIV-006", 4, games));
        };
    }
}