package br.com.bibliotech.model.usuario;

public class UsuarioEstudante extends TipoUsuario {

    private final int livrosAdicionais;

    public UsuarioEstudante(String codigo, String descricao, int livrosAdicionais) {
        super(codigo, descricao);
        this.livrosAdicionais = livrosAdicionais;
    }

    @Override
    public int calcularPrazoDias(int prazoPadrao) {
        return prazoPadrao; 
    }

    @Override
    public int calcularLimiteLivros(int limitePadrao) {
        return limitePadrao + livrosAdicionais; 
    }
}