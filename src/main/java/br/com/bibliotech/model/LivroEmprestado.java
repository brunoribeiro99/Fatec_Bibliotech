package br.com.bibliotech.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros_emprestados")
public class LivroEmprestado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emprestimo_id")
    private Emprestimo emprestimo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livro_id")
    private Livro livro;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false, length = 150)
    private String tituloSnapshot;

    protected LivroEmprestado() { }

    public LivroEmprestado(Emprestimo emprestimo, Livro livro, int quantidade) {
        this.emprestimo = emprestimo;
        this.livro = livro;
        this.quantidade = quantidade;
        this.tituloSnapshot = livro.getTitulo();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Emprestimo getEmprestimo() { return emprestimo; }
    public void setEmprestimo(Emprestimo emprestimo) { this.emprestimo = emprestimo; }

    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public String getTituloSnapshot() { return tituloSnapshot; }
    public void setTituloSnapshot(String tituloSnapshot) { this.tituloSnapshot = tituloSnapshot; }
}