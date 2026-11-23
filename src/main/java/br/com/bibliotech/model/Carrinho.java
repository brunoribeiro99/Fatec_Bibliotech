package br.com.bibliotech.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carrinhos")
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "aluno_id", unique = true)
    private Aluno aluno;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrinho> itens = new ArrayList<>();

    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Column(name = "codigo_tipo_usuario", length = 20)
private String codigoTipoUsuario;

public String getCodigoTipoUsuario() { return codigoTipoUsuario; }
public void setCodigoTipoUsuario(String codigoTipoUsuario) { this.codigoTipoUsuario = codigoTipoUsuario; }

@Column(name = "estrategia_prazo", length = 20)
private String estrategiaPrazo;

public String getEstrategiaPrazo() {
    return estrategiaPrazo;
}

public void setEstrategiaPrazo(String estrategiaPrazo) {
    this.estrategiaPrazo = estrategiaPrazo;
}

    protected Carrinho() { }

    public Carrinho(Aluno aluno) {
        this.aluno = aluno;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public List<ItemCarrinho> getItens() {
        return itens;
    }

    public void setItens(List<ItemCarrinho> itens) {
        this.itens = itens;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}