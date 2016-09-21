package com.example.luciano.melhorpreco;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.*;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.*;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.luciano.melhorpreco.Dao.BancoDados;
import com.example.luciano.melhorpreco.Dao.DaoProduto;
import com.example.luciano.melhorpreco.Negocio.Produto;
import com.example.luciano.melhorpreco.com.google.zxing.integration.android.IntentIntegrator;
import com.example.luciano.melhorpreco.com.google.zxing.integration.android.IntentResult;

public class Cad_Produtos extends AppCompatActivity implements View.OnClickListener {

    private EditText Codigo;
    private EditText Descricao;
    private Button Cadastrar;
    private Button Alterar;
    private Button Excluir;
    private ImageButton scan;

    private BancoDados bd;
    private SQLiteDatabase conect;
    private DaoProduto daoProduto;
    private Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad__produtos);

        Codigo = (EditText) findViewById(R.id.edtCodigo);
        Descricao = (EditText) findViewById(R.id.edtDesc);
        Cadastrar = (Button) findViewById(R.id.btCad);
        Alterar = (Button) findViewById(R.id.btAlterar);
        Excluir = (Button) findViewById(R.id.btExcluir);
        scan = (ImageButton) findViewById(R.id.imgCodigo);

        Bundle ProdutoSerial = getIntent().getExtras();
        if(ProdutoSerial != null && ProdutoSerial.containsKey("produto")){
            produto = (Produto) ProdutoSerial.getSerializable("produto");
            Preencher();
        }
        Cadastrar.setOnClickListener(this);
        Alterar.setOnClickListener(this);
        Excluir.setOnClickListener(this);
        scan.setOnClickListener(this);

        try {
            bd = new BancoDados(this);
            conect = bd.getWritableDatabase();
            daoProduto = new DaoProduto(conect);

        } catch (SQLException ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao Criar Conexão ");
            dlg.setNeutralButton("Ok", null);
            dlg.show();
        }
    }

    private void Preencher(){
        Codigo.setText(Long.valueOf(produto.getCodigoBarra()).toString());
        Descricao.setText(produto.getDescricao());
    }

    private void Alterar(){
        Produto p = new Produto();

        p.set_id(produto.get_id());
        p.setCodigoBarra(Codigo.getText().toString());
        p.setDescricao(Descricao.getText().toString());

        daoProduto.Alterar(p);
    }

    private void Excluir(){
        daoProduto.Deletar(produto);
    }


    public void inserir() {
        Produto produto = new Produto();

        produto.setCodigoBarra(Codigo.getText().toString());
        produto.setDescricao(Descricao.getText().toString());

        daoProduto.inserir(produto);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btCad:
                inserir();
                finish();
                break;
            case R.id.btAlterar:
                Alterar();
                finish();
                break;
            case R.id.btExcluir:
                Excluir();
                finish();
            case R.id.imgCodigo:
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.initiateScan();
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            Codigo.setText(scanContent);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void finish() {
        Intent i = new Intent(this, PesquisarPreco.class);
        setResult(2, i);
        super.finish();
    }
}
