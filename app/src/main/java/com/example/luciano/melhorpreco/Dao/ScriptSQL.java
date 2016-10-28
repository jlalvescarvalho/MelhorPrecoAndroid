package com.example.luciano.melhorpreco.Dao;

/**
 * Created by Luciano on 13/07/2016.
 */
public class ScriptSQL {

    public static String getCreateProduto(){


        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS Produto ( ");
        sqlBuilder.append("_id   INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("CodigoBarra VARCHAR NOT NULL, ");
        sqlBuilder.append("Descricao   VARCHAR NOT NULL); ");

        return sqlBuilder.toString();
    }

    public static String getCreateMercado(){


        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("CREATE TABLE IF NOT EXISTS Mercado ( ");
        stringBuilder.append("_id  INTEGER PRIMARY KEY AUTOINCREMENT, ");
        stringBuilder.append("Nome VARCHAR NOT NULL); ");

        return stringBuilder.toString();
    }

    public static String getCreateProduto_Mercardo(){

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("CREATE TABLE IF NOT EXISTS Mercado_Produto ( ");
        stringBuilder.append("_id        INTEGER PRIMARY KEY AUTOINCREMENT, ");
        stringBuilder.append("Preco      DOUBLE  NOT NULL, ");
        stringBuilder.append("id_produto INTEGER NOT NULL, ");
        stringBuilder.append("id_mercado INTEGER NOT NULL, ");
        stringBuilder.append("FOREIGN KEY(id_produto) REFERENCES Produto(_id), ");
        stringBuilder.append("FOREIGN KEY(id_mercado) REFERENCES Mercado(_id) );");

        return stringBuilder.toString();
    }

    public static String getCreateCarrinho(){

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("CREATE TABLE IF NOT EXISTS Item_carrinho ( ");
        stringBuilder.append("_id        INTEGER PRIMARY KEY AUTOINCREMENT, ");
        stringBuilder.append("Quantidade      INTEGER  NOT NULL, ");
        stringBuilder.append("id_produto INTEGER NOT NULL, ");
        stringBuilder.append("Preco DOUBLE NOT NULL, ");
        stringBuilder.append("Total DOUBLE NOT NULL, ");
        stringBuilder.append("FOREIGN KEY(id_produto) REFERENCES Produto(_id)); ");
        return stringBuilder.toString();
    }
}
