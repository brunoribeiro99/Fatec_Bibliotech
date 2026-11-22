package br.com.bibliotech.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 8)
    private String ra;

    @Column(nullable = false, length = 120)
    private String nomeCurso;

    @Column(nullable = false, length = 40)
    private String nivel;

    @Column(nullable = false, length = 20)
    private String turno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    public Curso() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    
}