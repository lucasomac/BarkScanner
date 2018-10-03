package br.com.lucolimac.barkscanner.local;

public class Cidade {
    private String nome;

    public Cidade(String nome) {
        this.nome = nome;
    }

    public Cidade() {

    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome.toUpperCase();
    }

}
