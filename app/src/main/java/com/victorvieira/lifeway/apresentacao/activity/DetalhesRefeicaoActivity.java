package com.victorvieira.lifeway.apresentacao.activity;

import android.content.res.Resources;
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
import com.victorvieira.lifeway.persistencia.ControladorBD;

import java.io.IOException;
import java.util.List;

public class DetalhesRefeicaoActivity extends BaseActivity {

    private int bgWidth;
    private int bgHeight;

    private ControladorBD bd;
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

        bd = new ControladorBD(this);

        id_refeicao = MySingleton.getApp().getId_refeicao();

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

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        bgWidth = metrics.widthPixels;
        bgHeight = (int) (150 * Resources.getSystem().getDisplayMetrics().density);

        toolbar = (Toolbar) findViewById(R.id.toolbarDetalhesRefeicao);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtNomeRefeicao = (TextView) findViewById(R.id.txtNomeRefeicaoOnDetails);
        lvAlimentos = (MyListView) findViewById(R.id.lvAlimentosOnDetails);

        Refeicao refeicao = bd.getConsumo().getRefeicaoById(id_refeicao);

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

        if(!(MySingleton.getApp().hasImageHistoric())) {
            MySingleton.getApp().setImageHistoric(imageManager.createBitmap(getResources(), R.drawable.salmon_steak,
                    bgWidth, bgHeight));
        }

        try {
            bgDetalhesRefeicao.setImageBitmap(MySingleton.getApp().getImageHistoric());
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
