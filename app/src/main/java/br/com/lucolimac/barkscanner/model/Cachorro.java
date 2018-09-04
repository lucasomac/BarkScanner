package br.com.lucolimac.barkscanner.model;

import java.util.Objects;

public class Cachorro {
    private String nome;
    private String raca;
    private int idade;
    private Usuario dono;
    private Porte porte;

    public Cachorro() {
    }

    public Cachorro(String nome, String raca, int idade, Usuario dono, Porte porte) {

        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.dono = dono;
        this.porte = porte;
    }

    public Usuario getDono() {
        return dono;
    }

    public Porte getPorte() {
        return porte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cachorro)) return false;
        Cachorro cachorro = (Cachorro) o;
        return getIdade() == cachorro.getIdade() &&
                Objects.equals(getNome(), cachorro.getNome()) &&
                Objects.equals(getRaca(), cachorro.getRaca()) &&
                Objects.equals(dono, cachorro.dono);
    }

    @Override
    public int hashCode() {

        return Objects.hash(getNome(), getRaca(), getIdade(), dono);
    }

    public String getNome() {

        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}
