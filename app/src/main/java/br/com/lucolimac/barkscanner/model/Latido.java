package br.com.lucolimac.barkscanner.model;

import android.provider.MediaStore;

public class Latido {
    private Cachorro cao;
    private MediaStore.Audio sinal;
    private String situacao;

    public Latido(Cachorro cao, MediaStore.Audio sinal, String situacao) {
        this.cao = cao;
        this.sinal = sinal;
        this.situacao = situacao;
    }

    public Latido() {
    }

    public Cachorro getCao() {
        return cao;
    }

    public MediaStore.Audio getSinal() {
        return sinal;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
