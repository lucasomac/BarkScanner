package br.com.lucolimac.barkscanner.model;

import android.media.Image;

import java.util.Date;
import java.util.Objects;

public class Cachorro {
    private String nome;
    private String raca;
    private Date nascimento;
    private String porte;
    private String veterinario;
    private String crmv;
    private Image foto;

    public Cachorro() {
    }

    public Cachorro(String nome, String raca, Date nascimento, String porte) {

        this.nome = nome;
        this.raca = raca;
        this.nascimento = nascimento;
        this.porte = porte;
    }

    public Cachorro(String nome, String raca, Date nascimento, String porte, Image foto) {

        this.nome = nome;
        this.raca = raca;
        this.nascimento = nascimento;
        this.porte = porte;
        this.foto = foto;
    }

    public Cachorro(String nome, String raca, Date nascimento, String porte, String veterinario, String crmv) {
        this.nome = nome;
        this.raca = raca;
        this.nascimento = nascimento;
        this.porte = porte;
        this.veterinario = veterinario;
        this.crmv = crmv;
    }

    public Cachorro(String nome, String raca, Date nascimento, String porte, String veterinario, String crmv, Image foto) {
        this.nome = nome;
        this.raca = raca;
        this.nascimento = nascimento;
        this.porte = porte;
        this.veterinario = veterinario;
        this.crmv = crmv;
        this.foto = foto;
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
        if (!(o instanceof Cachorro)) return false;
        Cachorro cachorro = (Cachorro) o;
        return getNascimento() == cachorro.getNascimento() &&
                Objects.equals(getNome(), cachorro.getNome()) &&
                Objects.equals(getRaca(), cachorro.getRaca());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getNome(), getRaca(), getNascimento());
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

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + "\n" + "Raça: " + raca;
    }
}
