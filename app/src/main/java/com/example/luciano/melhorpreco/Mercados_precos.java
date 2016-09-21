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
import com.example.luciano.melhorpreco.Dao.DaoProdutoMercado;
import com.example.luciano.melhorpreco.Negocio.Produto;
import com.example.luciano.melhorpreco.Negocio.Produto_Mercado;

public class Mercados_precos extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private BancoDados bd;
    private SQLiteDatabase con;
    private DaoProdutoMercado dpm;

    private Produto produto;

    private ListView listaCompara;
    private ArrayAdapter<Produto_Mercado> adpPm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercados_precos);

        listaCompara = (ListView) findViewById(R.id.lstMercados);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.containsKey("Produto")){
            produto =(Produto) bundle.getSerializable("Produto");
        }



        try {
            bd = new BancoDados(this);
            con = bd.getWritableDatabase();
            dpm = new DaoProdutoMercado(con);

            adpPm = dpm.recuperarProduto_Mercado(this, produto.get_id());
            listaCompara.setAdapter(adpPm);

            listaCompara.setOnItemClickListener(this);


        } catch (SQLException ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao Criar Conexão ");
            dlg.setNeutralButton("Ok", null);
            dlg.show();
        }


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Produto_Mercado produto_mercado = adpPm.getItem(i);

        Intent it = new Intent(this, Quantidades.class);
        it.putExtra("PM", produto_mercado);
        startActivity(it);
    }
}
