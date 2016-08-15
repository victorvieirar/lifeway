package com.victorvieira.lifeway.apresentacao.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.apresentacao.extras.ImageManager;
import com.victorvieira.lifeway.apresentacao.extras.ListaAlimentosAdapter;
import com.victorvieira.lifeway.apresentacao.extras.MyListView;
import com.victorvieira.lifeway.dominio.Alimento;
import com.victorvieira.lifeway.dominio.Refeicao;

import java.io.IOException;
import java.util.List;

public class DetalhesRefeicaoActivity extends BaseActivity {

    private Toolbar toolbar;

    private ImageView bgDetalhesRefeicao;

    private TextView txtNomeRefeicao;
    private ListView lvAlimentos;
    private List<Alimento> listAlimentos;

    private int id_refeicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        enterFromBottomAnimation();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_refeicao);

        id_refeicao = MySingleton.getBancoDeDados().getApp().getId_refeicao();

        initViews();
    }

    @Override
    protected void onPause() {
        exitToBottomAnimation();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        bgDetalhesRefeicao = null;
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
        toolbar = (Toolbar) findViewById(R.id.toolbarDetalhesRefeicao);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtNomeRefeicao = (TextView) findViewById(R.id.txtNomeRefeicaoOnDetails);
        lvAlimentos = (MyListView) findViewById(R.id.lvAlimentosOnDetails);

        Refeicao refeicao = MySingleton.getBancoDeDados().getUsuario().getConsumo().getRefeicaoById(id_refeicao);

        switch(refeicao.getTipo()) {
            case 'c':
                txtNomeRefeicao.setText("Café da manhã");
                break;
            case 'a':
                txtNomeRefeicao.setText("Almoço");
                break;
            case 'j':
                txtNomeRefeicao.setText("Jantar");
                break;
        }

        ImageManager imageManager = new ImageManager();
        bgDetalhesRefeicao = (ImageView) findViewById(R.id.bgDetalhesRefeicao);

        if(!(MySingleton.getBancoDeDados().getApp().hasImageHistoric())) {
            MySingleton.getBancoDeDados().getApp().setImageHistoric(imageManager.createBitmap(getResources(), R.drawable.salmon_steak,
                    bgDetalhesRefeicao.getWidth(), bgDetalhesRefeicao.getHeight()));
        }

        try {
            bgDetalhesRefeicao.setImageBitmap(MySingleton.getBancoDeDados().getApp().getImageHistoric());
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

        listAlimentos = refeicao.getAlimentos();
        ListaAlimentosAdapter adapter = new ListaAlimentosAdapter(this, listAlimentos, 'b', 'b');
        lvAlimentos.setAdapter(adapter);

        imageManager = null;
        System.gc();

    }
}
