package com.example.luciano.melhorpreco.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.example.luciano.melhorpreco.Negocio.Item_carrinho;
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

    public void inserir(Item_carrinho itemcarrinho) {

        ContentValues valores = new ContentValues();

        valores.put("Quantidade", itemcarrinho.getQuant());
        valores.put("id_produto", itemcarrinho.getProduto().get_id());
        valores.put("Preco", itemcarrinho.getPreco());
        valores.put("Total", itemcarrinho.getTotal());

        con.insertOrThrow("Item_carrinho", null, valores);
    }

    public void Excluir(Item_carrinho item_carrinho){
        con.delete("Item_carrinho", "_id = ?", new String[]{String.valueOf(item_carrinho.get_id())});
    }

    public void Alterar(Item_carrinho item_carrinho){
        ContentValues valores = new ContentValues();

        valores.put("Quantidade", item_carrinho.getQuant());
        valores.put("Preco", item_carrinho.getPreco());
        valores.put("Total", item_carrinho.getTotal());

        con.update("Item_carrinho", valores, "_id = ?", new String[]{ String.valueOf(item_carrinho.get_id())});
    }

    public ArrayAdapter<Item_carrinho> recuperaTodos(Context context) {

        ArrayAdapter<Item_carrinho> ProdCarrinho = new ArrayAdapter<Item_carrinho>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = con.query("Item_carrinho", null, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {

                Item_carrinho itemcarrinho = new Item_carrinho();
                itemcarrinho.set_id(cursor.getInt(0));
                itemcarrinho.setQuant(cursor.getInt(1));
                itemcarrinho.setProduto(recuperar(cursor.getInt(2)).get(0));
                itemcarrinho.setPreco(cursor.getDouble(3));
                itemcarrinho.setTotal(cursor.getDouble(4));

                ProdCarrinho.add(itemcarrinho);


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
