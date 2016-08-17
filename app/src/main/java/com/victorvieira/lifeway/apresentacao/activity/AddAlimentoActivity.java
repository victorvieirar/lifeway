package com.victorvieira.lifeway.apresentacao.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.apresentacao.extras.ImageManager;
import com.victorvieira.lifeway.apresentacao.extras.ListaCardapioAdapter;
import com.victorvieira.lifeway.apresentacao.extras.MyListView;
import com.victorvieira.lifeway.dominio.RefeicaoDisponivel;
import com.victorvieira.lifeway.persistencia.ControladorBD;

import java.io.IOException;
import java.util.List;

public class AddAlimentoActivity extends BaseActivity {

    private ControladorBD bd;

    private int bgWidth;
    private int bgHeight;

    private ImageView bgAddAlimento;

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

        bd = new ControladorBD(this);

        initViews();
        setupListeners();
    }

    @Override
    protected void onPause() {
        exitToBottomAnimation();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        bgAddAlimento = null;
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                exitToBottomAnimation();
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        exitToBottomAnimation();
        super.onBackPressed();
    }

    private void initViews() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        bgWidth = metrics.widthPixels;
        bgHeight = (int) (150 * Resources.getSystem().getDisplayMetrics().density);

        toolbar = (Toolbar) findViewById(R.id.toolbarAddAlimento);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        editSearch = (EditText) findViewById(R.id.editSearch);
        lvCardapio = (MyListView) findViewById(R.id.lvCardapio);

        refeicoesDisponiveis = MySingleton.getApp().getRefeicoesDisponiveis();
        adapter = new ListaCardapioAdapter(this, refeicoesDisponiveis);
        lvCardapio.setAdapter(adapter);

        ImageManager imageManager = new ImageManager();
        bgAddAlimento = (ImageView) findViewById(R.id.bgAddAlimento);

        if(!(MySingleton.getApp().hasImageHistoric())) {
            MySingleton.getApp().setImageHistoric(imageManager.createBitmap(getResources(), R.drawable.salmon_steak, bgWidth, bgHeight));
        }

        try {
            bgAddAlimento.setImageBitmap(MySingleton.getApp().getImageHistoric());
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                    List<RefeicaoDisponivel> refeicoesDisponiveis = MySingleton.getApp().getRefeicoesDisponiveisBySearch(pesquisa);
                    lvCardapio.setAdapter(new ListaCardapioAdapter(editSearch.getContext(), refeicoesDisponiveis));
                }

            }
        });
    }
}
