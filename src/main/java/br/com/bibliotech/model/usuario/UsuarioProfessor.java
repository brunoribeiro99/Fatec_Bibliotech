package br.com.bibliotech.model.usuario;

public class UsuarioProfessor extends TipoUsuario {

    private final int diasExtras;
    private final int multiplicadorLimite;

    public UsuarioProfessor(String codigo, String descricao, int diasExtras, int multiplicadorLimite) {
        super(codigo, descricao);
        this.diasExtras = diasExtras;
        this.multiplicadorLimite = multiplicadorLimite;
    }

    @Override
    public int calcularPrazoDias(int prazoPadrao) {
        return prazoPadrao + diasExtras; 
    }

    @Override
    public int calcularLimiteLivros(int limitePadrao) {
        return limitePadrao * multiplicadorLimite; 
    }
}