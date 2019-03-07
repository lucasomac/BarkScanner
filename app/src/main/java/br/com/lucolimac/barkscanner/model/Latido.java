package br.com.lucolimac.barkscanner.model;

public class Latido {
    private Cachorro cao;
    private String sinal;
    private String situacao;

    public Latido(Cachorro cao, String sinal, String situacao) {
        this.cao = cao;
        this.sinal = sinal;
        this.situacao = situacao;
    }

    public Latido() {
    }

    public Cachorro getCao() {
        return cao;
    }

    public String getSinal() {
        return sinal;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
