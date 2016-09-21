package com.example.luciano.melhorpreco;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Opcoes extends AppCompatActivity {

    private Button btCadastrar;
    private Button btSelect;
    private Button btPesquisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcoes);

        btCadastrar = (Button)findViewById(R.id.btCadastrar);
        btSelect = (Button) findViewById(R.id.btSelect);
        btPesquisa = (Button) findViewById(R.id.btPesquisa);

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Opcoes.this, Cad_Produtos.class);
                startActivityForResult(it, 4);
            }
        });
        btSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Opcoes.this, Compare.class);
                startActivityForResult(it, 5);
            }
        });
        btPesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Opcoes.this, PesquisarPreco.class);
                startActivityForResult(it, 4);
            }
        });

    }
}
