package com.example.luciano.melhorpreco.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;


import com.example.luciano.melhorpreco.Negocio.Produto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luciano on 14/07/2016.
 */
public class DaoProduto {

    private SQLiteDatabase con;

    public DaoProduto(SQLiteDatabase con) {
        this.con = con;
    }

    public void inserir(Produto produto){

        ContentValues valores = new ContentValues();

        valores.put("CodigoBarra", produto.getCodigoBarra());
        valores.put("Descricao", produto.getDescricao());

        con.insertOrThrow("Produto",null, valores);
    }
//----------------------------------------------------------------------------------------------------------------------------------
    public void Alterar(Produto produto){

        ContentValues valores = new ContentValues();

        valores.put("CodigoBarra", produto.getCodigoBarra());
        valores.put("Descricao", produto.getDescricao());

        con.update("Produto", valores, "_id = ?", new String[]{ String.valueOf(produto.get_id())});

    }

//--------------------------------------------------------------------------------------------------------------------------------
    public void Deletar(Produto produto){
        con.delete("Produto", "_id = ?", new String[]{String.valueOf(produto.get_id())});
    }
 //-----------------------------------------------------------------------------------------------------------------------------
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
//-------------------------------------------------------------------------------------------------------------------------------
    public ArrayAdapter<Produto> recuperaProdutos(Context context){

        ArrayAdapter<Produto> lstProdutos = new ArrayAdapter<Produto>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = con.query("Produto",null,null,null,null,null,null);

        if(cursor.getCount() > 0 ){
            cursor.moveToFirst();
            do {

                Produto p = new Produto();
                p.set_id(cursor.getInt(0));
                p.setCodigoBarra(cursor.getString(1));
                p.setDescricao(cursor.getString(2));

                lstProdutos.add(p);


            }while (cursor.moveToNext());
        }
        return lstProdutos;
    }
}
