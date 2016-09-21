package com.example.luciano.melhorpreco.Negocio;

import java.io.Serializable;

/**
 * Created by Luciano on 29/08/2016.
 */
public class Carrinho implements Serializable{

    private int _id;
    private int Quant;
    private Produto produto;
    private double preco;

    public Carrinho() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getQuant() {
        return Quant;
    }

    public void setQuant(int quant) {
        Quant = quant;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return this.produto.getDescricao()+" Quant. "+this.Quant+" Preço: "+this.preco;
    }
}
