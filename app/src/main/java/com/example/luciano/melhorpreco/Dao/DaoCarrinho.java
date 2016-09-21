package com.example.luciano.melhorpreco.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.example.luciano.melhorpreco.Negocio.Carrinho;
import com.example.luciano.melhorpreco.Negocio.Produto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luciano on 29/08/2016.
 */
public class DaoCarrinho {

    private SQLiteDatabase con;

    public DaoCarrinho(SQLiteDatabase con) {
        this.con = con;
    }

    public void inserir(Carrinho carrinho) {

        ContentValues valores = new ContentValues();

        valores.put("Quantidade", carrinho.getQuant());
        valores.put("id_produto", carrinho.getProduto().get_id());
        valores.put("Preco", carrinho.getPreco());

        con.insertOrThrow("Carrinho", null, valores);
    }

    public ArrayAdapter<Carrinho> recuperaTodos(Context context) {

        ArrayAdapter<Carrinho> ProdCarrinho = new ArrayAdapter<Carrinho>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = con.query("Carrinho", null, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {

                Carrinho carrinho = new Carrinho();
                carrinho.set_id(cursor.getInt(0));
                carrinho.setQuant(cursor.getInt(1));
                carrinho.setProduto(recuperar(cursor.getInt(2)).get(0));
                carrinho.setPreco(cursor.getDouble(3));

                ProdCarrinho.add(carrinho);


            } while (cursor.moveToNext());
        }
        return ProdCarrinho;
    }

    public List<Produto> recuperar(int id) {
        List<Produto> lista = new ArrayList<Produto>();
        Cursor cursor = con.rawQuery("Select * from Produto where _id = ?", new String[]{String.valueOf(id)});


        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {

                Produto produto = new Produto();

                produto.set_id(cursor.getInt(0));
                produto.setCodigoBarra(cursor.getString(1));
                produto.setDescricao(cursor.getString(2));

                lista.add(produto);

            } while (cursor.moveToNext());
        }
        return lista;
    }
}
