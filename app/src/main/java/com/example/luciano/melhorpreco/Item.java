package com.example.luciano.melhorpreco;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.luciano.melhorpreco.Dao.BancoDados;
import com.example.luciano.melhorpreco.Dao.DaoCarrinho;
import com.example.luciano.melhorpreco.Negocio.Item_carrinho;
import com.example.luciano.melhorpreco.Negocio.Produto_Mercado;

public class Item extends AppCompatActivity implements View.OnClickListener{

    private EditText edtProd;
    private TextView txtTotal;
    private EditText edtQuant;

    private Button btSalvar;
    private Button btCancelar;

    private Produto_Mercado pm;

    private SQLiteDatabase con;
    private DaoCarrinho dc;
    private BancoDados bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        edtProd = (EditText) findViewById(R.id.edtProd);
        txtTotal = (TextView)findViewById(R.id.txtTotal);
        edtQuant = (EditText) findViewById(R.id.edtQuant);

        btSalvar = (Button) findViewById(R.id.btAdd);
        btCancelar = (Button) findViewById(R.id.btCancelar);

        Bundle bundle = getIntent().getExtras();
        if(bundle  != null && bundle.containsKey("PM")) {
            pm = (Produto_Mercado) bundle.getSerializable("PM");
        }

        try {
            bd = new BancoDados(this);
            con = bd.getWritableDatabase();
            dc = new DaoCarrinho(con);
            Preencher();

        } catch (SQLException ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao Criar Conexão ");
            dlg.setNeutralButton("Ok", null);
            dlg.show();
        }

        btSalvar.setOnClickListener(this);
        btCancelar.setOnClickListener(this);

    }

    public void inserir(){
        try {
            Item_carrinho c = new Item_carrinho();

            c.setQuant(Integer.parseInt(edtQuant.getText().toString()));
            c.setProduto(pm.getProduto());
            c.setPreco(Double.parseDouble(txtTotal.getText().toString()));
            c.setTotal(Double.parseDouble(txtTotal.getText().toString())*Integer.parseInt(edtQuant.getText().toString()));

            dc.inserir(c);
        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao Inserir ");
            dlg.setNeutralButton("Ok", null);
            dlg.show();
        }
    }

    public void Preencher(){
        edtProd.setText(pm.getProduto().getDescricao());
        edtQuant.setText(String.valueOf(1));
        txtTotal.setText((String.valueOf(pm.getPreco() * Integer.parseInt(edtQuant.getText().toString()))));
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btAdd){
            inserir();

            Intent it = new Intent(this, Compare.class);
            startActivity(it);

        }else if(view.getId() == R.id.btCancelar){
            Intent it = new Intent(this, Compare.class);
            startActivity(it);
        }
    }
}
