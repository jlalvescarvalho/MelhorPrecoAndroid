package com.example.luciano.melhorpreco.Negocio;

import java.io.Serializable;

/**
 * Created by Luciano on 14/07/2016.
 */
public class Produto implements Serializable{

    private int _id;
    private String codigoBarra;
    private String descricao;

    public Produto(String codigoBarra, String descricao) {
        this.codigoBarra = codigoBarra;
        this.descricao = descricao;
    }

    public Produto() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return this.codigoBarra + "   "+this.descricao;
    }
}
