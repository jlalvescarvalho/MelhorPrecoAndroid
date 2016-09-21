package com.example.luciano.melhorpreco;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.luciano.melhorpreco.Dao.BancoDados;
import com.example.luciano.melhorpreco.Dao.DaoProduto;
import com.example.luciano.melhorpreco.Negocio.Produto;

public class Compare extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView lstProds;

    private BancoDados bd;
    private SQLiteDatabase con;
    private DaoProduto dp;

    private ArrayAdapter<Produto> adpLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        lstProds = (ListView) findViewById(R.id.lstPro_Merc);

        try{
            bd = new BancoDados(this);
            con = bd.getWritableDatabase();

            dp = new DaoProduto(con);
            adpLista = dp.recuperaProdutos(this);

            lstProds.setAdapter(adpLista);
            lstProds.setOnItemClickListener(this);

        }catch (SQLException ex){

            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao Criar Conexão ");
            dlg.setNeutralButton("Ok", null);
            dlg.show();
        }
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Produto p = adpLista.getItem(i);

        Intent it = new Intent(this, Mercados_precos.class);
        it.putExtra("Produto", p);
        startActivity(it);
    }
}
