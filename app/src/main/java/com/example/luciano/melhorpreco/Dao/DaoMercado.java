package com.example.luciano.melhorpreco.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.example.luciano.melhorpreco.Negocio.Mercado;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luciano on 23/07/2016.
 */
public class DaoMercado {

    private SQLiteDatabase con;

    public DaoMercado(SQLiteDatabase con){
        this.con = con;
    }

    public void inserir(Mercado mercado){

        ContentValues valores = new ContentValues();

        valores.put("Nome", mercado.getNome());

        con.insertOrThrow("Mercado", null, valores);
    }

    public List<Mercado> recuperar(String nome){
        List<Mercado> lista = new ArrayList<Mercado>();
        Cursor cursor = con.rawQuery("select * from Mercado where Nome = ?", new String[]{nome});

        if(cursor.getCount() > 0 ){
            cursor.moveToFirst();
            do {

                Mercado M = new Mercado();
                M.set_id(cursor.getInt(0));
                M.setNome(cursor.getString(1));

                lista.add(M);


            }while (cursor.moveToNext());
        }
        //con.query("Mercado",null,"where Nome = ?", new String[]{nome}, null, null,null);
        return lista;
    }


    public ArrayAdapter<Mercado> recuperaProdutos(Context context){

        ArrayAdapter<Mercado> Mercados = new ArrayAdapter<Mercado>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = con.query("Produto",null,null,null,null,null,null);

        if(cursor.getCount() > 0 ){
            cursor.moveToFirst();
            do {

                Mercado M = new Mercado();
                M.set_id(cursor.getInt(0));
                M.setNome(cursor.getString(1));

                Mercados.add(M);


            }while (cursor.moveToNext());
        }
        return Mercados;
    }

}
