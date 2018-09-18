package br.com.lucolimac.barkscanner.model;

import java.util.Base64;
import java.util.Objects;

public class Usuario {
    private String nome;
    private String email;
    private Base64 senha;
    private String uf;
    private String cidade;
    private String bairro;
    private String cpf;

    public Usuario(String nome, String email, Base64 senha, String uf, String cidade, String bairro, String cpf) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.uf = uf;
        this.cidade = cidade;
        this.bairro = bairro;
        this.cpf = cpf;
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

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCpf() {
        return cpf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(getEmail(), usuario.getEmail()) ||
                Objects.equals(getCpf(), usuario.getCpf());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getEmail(), getCpf());
    }
}
