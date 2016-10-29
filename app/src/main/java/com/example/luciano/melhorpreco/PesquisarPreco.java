package com.example.luciano.melhorpreco;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.luciano.melhorpreco.Dao.BancoDados;
import com.example.luciano.melhorpreco.Dao.DaoMercado;
import com.example.luciano.melhorpreco.Dao.DaoProduto;
import com.example.luciano.melhorpreco.Negocio.Mercado;
import com.example.luciano.melhorpreco.Negocio.Produto;
import com.example.luciano.melhorpreco.com.google.zxing.integration.android.IntentIntegrator;

public class PesquisarPreco extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    private ListView lstProdutos;
    private ImageButton btAdd;
    private ImageButton imgCod;
    private String Nome="";
    private EditText edtPesquisa;

    private BancoDados bd;
    private SQLiteDatabase con;
    private DaoMercado daoMercado;
    private DaoProduto daoProduto;
    private ArrayAdapter<Produto> lstProd;

    private Button BtP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar_preco);

        lstProdutos = (ListView) findViewById(R.id.lstProdutos);
        btAdd = (ImageButton) findViewById(R.id.imgAdd);
        imgCod = (ImageButton) findViewById(R.id.imgPesq);
        edtPesquisa = (EditText) findViewById(R.id.edtPesquisa);

        lstProdutos.setOnItemClickListener(this);
        BtP = (Button) findViewById(R.id.btPesquisa);
        btAdd.setOnClickListener(this);
        imgCod.setOnClickListener(this);

        try {
            bd = new BancoDados(this);
            con = bd.getWritableDatabase();

            daoProduto = new DaoProduto(con);
            daoMercado = new DaoMercado(con);
            lstProd = daoProduto.recuperaProdutos(this);
            lstProdutos.setAdapter(lstProd);

            Filtrar filtrar = new Filtrar(lstProd);
            edtPesquisa.addTextChangedListener(filtrar);

        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao Criar Conexão ");
            dlg.setNeutralButton("Ok", null);
            dlg.show();
        }


            AlertDialog.Builder msg = new AlertDialog.Builder(this);
            msg.setTitle("Mercado à ser pesquisado");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
            msg.setView(input);
            msg.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Nome = input.getText().toString();
                    inserir(Nome);
                }
            });
            msg.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            msg.show();


    }

    public void inserir(String nome){
        Mercado m = new Mercado();
        m.setNome(nome);

        daoMercado.inserir(m);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Produto produto = lstProd.getItem(i);
        String m = Nome;

        Intent it = new Intent(this, Produto_mercado.class);
        it.putExtra("Produto", produto);
        it.putExtra("m", m);
        startActivityForResult(it, 1);
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.imgAdd) {
            Intent it = new Intent(this, Cad_Produtos.class);
            startActivityForResult(it, 2);
        }else if(view.getId() == R.id.imgPesq){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }

    private class Filtrar implements TextWatcher{

        private ArrayAdapter<Produto> adpFilter;

        private Filtrar(ArrayAdapter<Produto> adpProd){
            this.adpFilter = adpProd;
        }


        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence cS, int i, int i1, int i2) {
            adpFilter.getFilter().filter(cS);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}



