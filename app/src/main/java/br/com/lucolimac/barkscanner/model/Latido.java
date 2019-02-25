package br.com.lucolimac.barkscanner.model;

import android.net.Uri;

public class Latido {
    private Cachorro cao;
    private Uri sinal;
    private String situacao;

    public Latido(Cachorro cao, Uri sinal, String situacao) {
        this.cao = cao;
        this.sinal = sinal;
        this.situacao = situacao;
    }

    public Latido() {
    }

    public Cachorro getCao() {
        return cao;
    }

    public Uri getSinal() {
        return sinal;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
