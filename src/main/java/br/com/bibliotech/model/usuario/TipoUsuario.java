package br.com.bibliotech.model.usuario;

public abstract class TipoUsuario {

    private final String codigo;
    private final String descricao;

    protected TipoUsuario(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() { return codigo; }
    public String getDescricao() { return descricao; }

    public abstract int calcularPrazoDias(int prazoPadrao);
    public abstract int calcularLimiteLivros(int limitePadrao);
}