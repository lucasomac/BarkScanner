package br.com.lucolimac.barkscanner.model;

import android.media.MediaRecorder;

public class Latido {
    private Cachorro cao;
    private MediaRecorder sinal;
    private String situacao;

    public Latido(Cachorro cao, MediaRecorder sinal, String situacao) {
        this.cao = cao;
        this.sinal = sinal;
        this.situacao = situacao;
    }

    public Latido() {
    }

    public Cachorro getCao() {
        return cao;
    }

    public MediaRecorder getSinal() {
        return sinal;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
