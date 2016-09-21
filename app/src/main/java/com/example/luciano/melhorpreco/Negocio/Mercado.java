package com.example.luciano.melhorpreco.Negocio;

import java.io.Serializable;

/**
 * Created by Luciano on 14/07/2016.
 */
public class Mercado implements Serializable{

    private int _id;
    private String nome;

    public Mercado(String nome) {
        this.nome = nome;
    }

    public Mercado(){

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return ""+this.nome;
    }
}
