package br.edu.ufabc.padm.pocketmentaltest.model;

/**
 * Created by victor on 11/20/16.
 */

public class Result {
    private long id;
    private String data;
    private String teste;
    private double score;


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    public String getTeste() {
        return teste;
    }
    public void setTeste(String teste) {
        this.teste = teste;
    }

    public double getScore() {
        return score;
    }
    public void setScore(double score) {
        this.score = score;
    }
}
