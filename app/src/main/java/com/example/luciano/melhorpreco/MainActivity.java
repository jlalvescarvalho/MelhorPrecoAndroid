package com.example.luciano.melhorpreco;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.icu.math.*;
import android.renderscript.Sampler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luciano.melhorpreco.Dao.BancoDados;
import com.example.luciano.melhorpreco.Dao.DaoCarrinho;
import com.example.luciano.melhorpreco.Negocio.Item_carrinho;

public class MainActivity extends AppCompatActivity{

    private ImageButton ImgAdd;
    private TextView txtTotal;
    private ListView lstCarrinho;

    private BancoDados bd;
    private SQLiteDatabase con;
    private DaoCarrinho dcar;

    private ArrayAdapter<Item_carrinho> adpCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImgAdd = (ImageButton) findViewById(R.id.imageButtonAdd);
        lstCarrinho = (ListView) findViewById(R.id.listViewCarrinho);
        txtTotal = (TextView) findViewById(R.id.txtValor);

        try {
            bd = new BancoDados(this);
            con = bd.getWritableDatabase();
            dcar = new DaoCarrinho(con);

            adpCar = dcar.recuperaTodos(this);
            lstCarrinho.setAdapter(adpCar);

            txtTotal.setText("R$ "+String.valueOf(PreencherTotal()));

        }catch (SQLException ex){

            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao Criar Conexão ");
            dlg.setNeutralButton("Ok", null);
            dlg.show();
        }


        ImgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, Opcoes.class);
                startActivity(it);
            }
        });


    }

    public double PreencherTotal(){
        double total = 0;
        for(int i=0; i < adpCar.getCount(); i++){
            total += adpCar.getItem(i).getTotal();
        }
        double d = total*100;
        int n = (int) d;

        return n/100.00;
    }

}
