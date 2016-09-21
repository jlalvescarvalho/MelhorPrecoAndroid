package com.example.luciano.melhorpreco.Dao;

import android.content.Context;
import android.database.sqlite.*;


/**
 * Created by Luciano on 13/07/2016.
 */
public class BancoDados extends SQLiteOpenHelper {


    public BancoDados(Context context) {

        super(context, "DataBase", null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(ScriptSQL.getCreateProduto());
        db.execSQL(ScriptSQL.getCreateMercado());
        db.execSQL(ScriptSQL.getCreateProduto_Mercardo());
        db.execSQL(ScriptSQL.getCreateCarrinho());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
