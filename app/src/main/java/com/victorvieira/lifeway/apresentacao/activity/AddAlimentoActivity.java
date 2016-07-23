package com.victorvieira.lifeway.apresentacao.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.apresentacao.extras.ListaCardapioAdapter;
import com.victorvieira.lifeway.apresentacao.extras.MyListView;
import com.victorvieira.lifeway.dominio.RefeicaoDisponivel;

import java.util.List;

public class AddAlimentoActivity extends BaseActivity {

    private Toolbar toolbar;
    private EditText editSearch;
    private MyListView lvCardapio;

    private ListaCardapioAdapter adapter;
    private List<RefeicaoDisponivel> refeicoesDisponiveis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        enterFromBottomAnimation();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alimento);

        initViews();
        setupListeners();
    }

    @Override
    protected void onPause() {
        exitToBottomAnimation();
        super.onPause();
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbarAddAlimento);
        toolbar.setTitle("Adicionar alimento");
        toolbar.setTitleTextColor(getResources().getColor(R.color.textColorPrimary));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        editSearch = (EditText) findViewById(R.id.editSearch);
        lvCardapio = (MyListView) findViewById(R.id.lvCardapio);

        refeicoesDisponiveis = MySingleton.getBancoDeDados().getApp().getRefeicoesDisponiveis();
        adapter = new ListaCardapioAdapter(this, refeicoesDisponiveis);
        lvCardapio.setAdapter(adapter);
    }


    private void setupListeners() {
        editSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus && editSearch.getText().toString().equals("faça sua busca")) {
                    editSearch.setText("");
                }
            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String pesquisa = editSearch.getText().toString();
                if(pesquisa.equals("")) {
                    lvCardapio.setAdapter(adapter);
                } else {
                    List<RefeicaoDisponivel> refeicoesDisponiveis = MySingleton.getBancoDeDados().getApp().getRefeicoesDisponiveisBySearch(pesquisa);
                    lvCardapio.setAdapter(new ListaCardapioAdapter(editSearch.getContext(), refeicoesDisponiveis));
                }

            }
        });
    }
}
