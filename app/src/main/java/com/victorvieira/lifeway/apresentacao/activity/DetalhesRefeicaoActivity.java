package com.victorvieira.lifeway.apresentacao.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.apresentacao.extras.ListaAlimentosAdapter;
import com.victorvieira.lifeway.apresentacao.extras.MyListView;
import com.victorvieira.lifeway.dominio.Alimento;
import com.victorvieira.lifeway.dominio.Refeicao;

import java.util.List;

public class DetalhesRefeicaoActivity extends BaseActivity {

    private Toolbar toolbar;

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
        toolbar.setTitle("Detalhes da refeição");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDarkTwo));
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

        listAlimentos = refeicao.getAlimentos();
        ListaAlimentosAdapter adapter = new ListaAlimentosAdapter(this, listAlimentos, 'b', 'b');
        lvAlimentos.setAdapter(adapter);

    }
}
