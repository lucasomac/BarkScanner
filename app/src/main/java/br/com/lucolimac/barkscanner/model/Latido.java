package br.com.lucolimac.barkscanner.model;

import android.provider.MediaStore;

public class Latido {
    private Cachorro cao;
    private MediaStore.Audio sinal;

    public Latido(Cachorro cao, MediaStore.Audio sinal) {
        this.cao = cao;
        this.sinal = sinal;
    }

    public Latido() {
    }

    public Cachorro getCao() {
        return cao;
    }

    public MediaStore.Audio getSinal() {
        return sinal;
    }
}
