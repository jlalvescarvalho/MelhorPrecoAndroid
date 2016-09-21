package com.example.luciano.melhorpreco.Negocio;

import java.io.Serializable;

/**
 * Created by Luciano on 23/07/2016.
 */
public class Produto_Mercado implements Serializable{

    private int _id;
    private double preco;
    private Produto produto;
    private Mercado mercado;

    public Produto_Mercado() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Mercado getMercado() {
        return mercado;
    }

    public void setMercado(Mercado mercado) {
        this.mercado = mercado;
    }

    @Override
    public String toString() {
        return this.getMercado()+"   R$ "+this.getPreco();
    }
}
