package br.com.lucolimac.barkscanner.model;

import android.media.Image;

import java.util.Objects;

public class Cachorro2 {
    private String nome;
    private String raca;
    private int idade;
    private String dono;
    private String porte;
    private String veterinario;
    private String crmv;
    private Image foto;

    public Cachorro2() {
    }

    public Cachorro2(String nome, String raca, int idade, String dono, String porte) {

        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.dono = dono;
        this.porte = porte;
    }

    public Cachorro2(String nome, String raca, int idade, String dono, String porte, Image foto) {

        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.dono = dono;
        this.porte = porte;
        this.foto = foto;
    }

    public Cachorro2(String nome, String raca, int idade, String dono, String porte, String veterinario, String crmv) {
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.dono = dono;
        this.porte = porte;
        this.veterinario = veterinario;
        this.crmv = crmv;
    }

    public Cachorro2(String nome, String raca, int idade, String dono, String porte, String veterinario, String crmv, Image foto) {
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.dono = dono;
        this.porte = porte;
        this.veterinario = veterinario;
        this.crmv = crmv;
        this.foto = foto;
    }

    public String getDono() {
        return dono;
    }

    public String getPorte() {
        return porte;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }

    public String getCrmv() {
        return crmv;
    }

    public void setCrmv(String crmv) {
        this.crmv = crmv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cachorro2)) return false;
        Cachorro2 cachorro = (Cachorro2) o;
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

    public Image getFoto() {
        return foto;
    }

    public void setFoto(Image foto) {
        this.foto = foto;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + "\n" + "Raça: " + raca;
    }
}
