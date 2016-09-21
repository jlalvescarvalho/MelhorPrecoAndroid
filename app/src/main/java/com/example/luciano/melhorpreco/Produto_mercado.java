package com.example.luciano.melhorpreco;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.example.luciano.melhorpreco.Dao.BancoDados;
import com.example.luciano.melhorpreco.Dao.DaoMercado;
import com.example.luciano.melhorpreco.Dao.DaoProdutoMercado;
import com.example.luciano.melhorpreco.Negocio.Mercado;
import com.example.luciano.melhorpreco.Negocio.Produto;
import com.example.luciano.melhorpreco.Negocio.Produto_Mercado;

import java.util.List;


public class Produto_mercado extends AppCompatActivity implements View.OnClickListener{

    private EditText mercado;
    private EditText produto;
    private EditText Preco;
    private Button Salvar;
    private Button Cancelar;

    private SQLiteDatabase con;
    private BancoDados bd;
    private DaoProdutoMercado daoProdutoMercado;
    private DaoMercado daoMercado;

    private Produto p;
    private Mercado m;

    private  String Nome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_mercado);

        mercado = (EditText) findViewById(R.id.edtMercado);
        produto = (EditText) findViewById(R.id.edtProduto);
        Preco = (EditText) findViewById(R.id.edtPreco);
        Salvar = (Button) findViewById(R.id.btSalvar);
        Cancelar = (Button) findViewById(R.id.btCancel);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.containsKey("Produto")){
            p = (Produto) bundle.getSerializable("Produto");
        }
        if(bundle != null && bundle.containsKey("m"))
            Nome = bundle.getString("m");


        preencher();
        Salvar.setOnClickListener(this);
        Cancelar.setOnClickListener(this);

        try{
            bd = new BancoDados(this);
            con = bd.getWritableDatabase();

            daoProdutoMercado = new DaoProdutoMercado(con);
            daoMercado = new DaoMercado(con);

        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao Criar Conexão ");
            dlg.setNeutralButton("Ok", null);
            dlg.show();
        }


    }

    public void preencher(){
        mercado.setText(Nome);
        produto.setText(p.getDescricao());

    }
    public Mercado recuperarMercado(String nome){
        Mercado m = new Mercado();
        List<Mercado> lista = daoMercado.recuperar(Nome);
        m = lista.get(0);
        return m;
    }
    public Produto_Mercado VerificaSeExiste(){
        Produto_Mercado pm = null;
        ArrayAdapter<Produto_Mercado> lista = daoProdutoMercado.recuperaProdutos(this);
        for(int i = 0; i < lista.getCount(); i++){
            if(lista.getItem(i).getProduto().getDescricao().toString().equals(p.getDescricao().toString()) &&
                    lista.getItem(i).getMercado().getNome().toString().equals(Nome)){
                pm = new Produto_Mercado();
                pm = lista.getItem(i);
                break;
            }
        }
        return pm;
    }

    public void inserir(){
        try {
            if(VerificaSeExiste() != null) {
                Produto_Mercado pm = VerificaSeExiste();
                pm.setPreco(Double.parseDouble(Preco.getText().toString()));
                pm.setMercado(recuperarMercado(Nome));
                pm.setProduto(p);
                daoProdutoMercado.Atualizar(pm);

            }else{
                Mercado m = recuperarMercado(Nome);
                Produto_Mercado pm = new Produto_Mercado();
                pm.setPreco(Double.parseDouble(Preco.getText().toString()));
                pm.setProduto(p);
                pm.setMercado(m);

                daoProdutoMercado.inserir(pm);
            }
        }catch (SQLException ex){
            AlertDialog.Builder msg = new AlertDialog.Builder(this);
            msg.setMessage("Erro ao Inserir");
            msg.setNeutralButton("Ok", null);
            msg.show();
        }
    }



    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btSalvar) {
            inserir();
            finish();
        }else if(view.getId() == R.id.btCancel){
            finish();

        }
    }

    @Override
    public void finish() {
        Intent i = new Intent(this, PesquisarPreco.class);
        setResult(1, i);
        super.finish();
    }
}
