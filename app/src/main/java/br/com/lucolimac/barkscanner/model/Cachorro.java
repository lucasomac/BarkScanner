package br.com.lucolimac.barkscanner.model;

import android.media.Image;

import java.util.Objects;

public class Cachorro {
    private String nome;
    private String raca;
    private int idade;
    private Usuario dono;
    private Porte porte;
    private String veterinario;
    private String crmv;
    private Image foto;

    public Cachorro() {
    }

    public Cachorro(String nome, String raca, int idade, Usuario dono, Porte porte) {

        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.dono = dono;
        this.porte = porte;
    }

    public Cachorro(String nome, String raca, int idade, Usuario dono, Porte porte, Image foto) {

        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.dono = dono;
        this.porte = porte;
        this.foto = foto;
    }

    public Cachorro(String nome, String raca, int idade, Usuario dono, Porte porte, String veterinario, String crmv) {
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.dono = dono;
        this.porte = porte;
        this.veterinario = veterinario;
        this.crmv = crmv;
    }

    public Cachorro(String nome, String raca, int idade, Usuario dono, Porte porte, String veterinario, String crmv, Image foto) {
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.dono = dono;
        this.porte = porte;
        this.veterinario = veterinario;
        this.crmv = crmv;
        this.foto = foto;
    }

    public Usuario getDono() {
        return dono;
    }

    public Porte getPorte() {
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
}
