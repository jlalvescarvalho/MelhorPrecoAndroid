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

import org.w3c.dom.Text;

public class Alterar_Excluir_Carrinho extends AppCompatActivity {

    private Item_carrinho item;

    private Button alterar;
    private Button excluir;

    private EditText quant;
    private EditText precoUnitario;

    private TextView Total;
    private TextView produto;

    private BancoDados bd;
    private SQLiteDatabase con;
    private DaoCarrinho dc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar__excluir__carrinho);

        Bundle bundle = getIntent().getExtras();
        if(bundle  != null && bundle.containsKey("item")) {
            item = (Item_carrinho) bundle.getSerializable("item");
        }

        alterar = (Button) findViewById(R.id.btAlterarItem);
        excluir = (Button) findViewById(R.id.btExcluirItem);
        quant = (EditText) findViewById(R.id.edtQuantItem);
        precoUnitario = (EditText) findViewById(R.id.edtPrecoItem);
        Total = (TextView) findViewById(R.id.txtTotalItem);
        produto = (TextView) findViewById(R.id.txtProdutoCarrinho);

        preencher();

        try{
            bd = new BancoDados(this);
            con = bd.getWritableDatabase();
            dc = new DaoCarrinho(con);

        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao criar conexão ");
            dlg.setNeutralButton("Ok", null);
            dlg.show();
        }

        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Item_carrinho it = new Item_carrinho();
                    it.set_id(item.get_id());
                    it.setQuant(Integer.valueOf(quant.getText().toString()));
                    it.setPreco(Double.valueOf(precoUnitario.getText().toString()));
                    it.setTotal(Integer.valueOf(quant.getText().toString()) * Double.valueOf(precoUnitario.getText().toString()));

                    dc.Alterar(it);

                }catch (SQLException ex){
                    AlertDialog.Builder dlg = new AlertDialog.Builder(Alterar_Excluir_Carrinho.this);
                    dlg.setMessage("Erro ao alterar");
                    dlg.setNeutralButton("Ok", null);
                    dlg.show();
                }


                Intent it = new Intent(Alterar_Excluir_Carrinho.this, MainActivity.class);
                startActivity(it);
            }
        });

        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    dc.Excluir(item);

                }catch (SQLException ex){
                    AlertDialog.Builder dlg = new AlertDialog.Builder(Alterar_Excluir_Carrinho.this);
                    dlg.setMessage("Erro ao excluir");
                    dlg.setNeutralButton("Ok", null);
                    dlg.show();
                }


                Intent it = new Intent(Alterar_Excluir_Carrinho.this, MainActivity.class);
                startActivity(it);
            }
        });
    }

    public void preencher(){
        produto.setText(item.getProduto().getDescricao());
        quant.setText(String.valueOf(item.getQuant()));
        precoUnitario.setText(String.valueOf(item.getPreco()));
        Total.setText(String.valueOf(item.getTotal()));


    }
}
