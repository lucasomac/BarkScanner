package br.com.lucolimac.barkscanner.model;

public enum Porte {
    GRANDE(1), MÉDIO(0), PEQUENO(-1);
    public int valor;

    Porte(int valor_enum) {
        valor = valor_enum;
    }

    public int getValor() {
        return valor;
    }
}
