package br.com.lucolimac.barkscanner.local;

import java.util.ArrayList;

public class Estado {


    private String nome;
    private String sigla;
    private ArrayList<Cidade> cidades;

    public Estado(String nome, String sigla, ArrayList<Cidade> cidades) {
        this.nome = nome;
        this.sigla = sigla;
        this.cidades = cidades;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public ArrayList<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(ArrayList<Cidade> cidades) {
        this.cidades = cidades;
    }

    @Override
    public String toString() {
        return sigla;
    }
}
