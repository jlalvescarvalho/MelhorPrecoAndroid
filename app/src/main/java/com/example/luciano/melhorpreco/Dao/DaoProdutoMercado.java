package com.example.luciano.melhorpreco.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.example.luciano.melhorpreco.Negocio.Mercado;
import com.example.luciano.melhorpreco.Negocio.Produto;
import com.example.luciano.melhorpreco.Negocio.Produto_Mercado;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luciano on 24/07/2016.
 */
public class DaoProdutoMercado {

    private SQLiteDatabase con;

    public DaoProdutoMercado(SQLiteDatabase con) {
        this.con = con;
    }

    public void inserir(Produto_Mercado produto_mercado){

        ContentValues valores = new ContentValues();

        valores.put("Preco", produto_mercado.getPreco());
        valores.put("id_produto", produto_mercado.getProduto().get_id());
        valores.put("id_mercado", produto_mercado.getMercado().get_id());

        con.insertOrThrow("Mercado_Produto", null, valores);
    }

    public void Atualizar(Produto_Mercado produto_mercado){

        ContentValues valores = new ContentValues();

        valores.put("Preco", produto_mercado.getPreco());
        valores.put("id_produto", produto_mercado.getProduto().get_id());
        valores.put("id_mercado", produto_mercado.getMercado().get_id());

        con.update("Mercado_Produto", valores,"_id = ?", new String[]{String.valueOf(produto_mercado.get_id())});
    }

    public List<Produto> recuperar(int id){
        List<Produto> lista = new ArrayList<Produto>();
        Cursor cursor = con.rawQuery("Select * from Produto where _id = ?", new String[]{String.valueOf(id)});


        if(cursor.getCount() > 0 ){
            cursor.moveToFirst();
            do {

                Produto produto = new Produto();

                produto.set_id(cursor.getInt(0));
                produto.setCodigoBarra(cursor.getString(1));
                produto.setDescricao(cursor.getString(2));

                lista.add(produto);

            }while (cursor.moveToNext());
        }
        return lista;
    }
    public List<Mercado> recuperarMercado(int id){
        List<Mercado> lista = new ArrayList<Mercado>();
        Cursor cursor = con.rawQuery("Select * from Mercado where _id = ?", new String[]{String.valueOf(id)});


        if(cursor.getCount() > 0 ){
            cursor.moveToFirst();
            do {

                Mercado m = new Mercado();

                m.set_id(cursor.getInt(0));
                m.setNome(cursor.getString(1));

                lista.add(m);

            }while (cursor.moveToNext());
        }
        return lista;
    }

    public ArrayAdapter<Produto_Mercado> recuperarProduto_Mercado(Context context, int id){
        ArrayAdapter<Produto_Mercado> lista = new ArrayAdapter<Produto_Mercado>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = con.rawQuery("Select * from Mercado_Produto where id_produto = ?", new String[]{String.valueOf(id)});


        if(cursor.getCount() > 0 ){
            cursor.moveToFirst();
            do {

                Produto_Mercado Pm = new Produto_Mercado();
                Pm.set_id(cursor.getInt(0));
                Pm.setPreco(cursor.getDouble(1));
                Pm.setProduto(recuperar(cursor.getInt(2)).get(0));
                Pm.setMercado(recuperarMercado(cursor.getInt(3)).get(0));

                lista.add(Pm);

            }while (cursor.moveToNext());
        }
        return lista;
    }


    public ArrayAdapter<Produto_Mercado> recuperaProdutos(Context context){

        ArrayAdapter<Produto_Mercado> Precos = new ArrayAdapter<Produto_Mercado>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = con.query("Mercado_Produto",null,null,null,null,null,null);

        if(cursor.getCount() > 0 ){
            cursor.moveToFirst();
            do {

                Produto_Mercado Pm = new Produto_Mercado();
                Pm.set_id(cursor.getInt(0));
                Pm.setPreco(cursor.getDouble(1));
                Pm.setProduto(recuperar(cursor.getInt(2)).get(0));
                Pm.setMercado(recuperarMercado(cursor.getInt(3)).get(0));

                Precos.add(Pm);


            }while (cursor.moveToNext());
        }
        return Precos;
    }
}
