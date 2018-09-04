package br.com.lucolimac.barkscanner.model;

import java.util.Base64;
import java.util.Objects;

public class Usuario {
    private String nome;
    private String email;
    private Base64 senha;

    public Usuario(String nome, String email, Base64 senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Base64 getSenha() {
        return senha;
    }

    public void setSenha(Base64 senha) {
        this.senha = senha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(getNome(), usuario.getNome()) &&
                Objects.equals(getEmail(), usuario.getEmail());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getNome(), getEmail());
    }
}
