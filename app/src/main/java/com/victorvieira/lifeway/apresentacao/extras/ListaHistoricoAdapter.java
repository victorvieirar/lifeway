package com.victorvieira.lifeway.apresentacao.extras;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.apresentacao.activity.DetalhesRefeicaoActivity;
import com.victorvieira.lifeway.apresentacao.fragments.HistoricFragment;
import com.victorvieira.lifeway.dominio.Alimento;
import com.victorvieira.lifeway.dominio.Refeicao;
import com.victorvieira.lifeway.persistencia.ControladorBD;

import java.util.Date;
import java.util.List;

public class ListaHistoricoAdapter extends ArrayAdapter<Refeicao> {

    private RelativeLayout rlRefeicao;
    private VerMaisRefeicaoButton btnVerMais;
    private TextView txtAlimentosDaRefeicao;
    private TextView txtNomeRefeicao;
    private TextView txtData;

    private Context context;
    private List<Refeicao> listRefeicoes = null;
    private char type;
    private char color;
    private String sData;

    public ListaHistoricoAdapter(Context context,  List<Refeicao> listRefeicoes, char type, String data) {
        super(context, 0, listRefeicoes);
        this.listRefeicoes = listRefeicoes;
        this.context = context;
        this.type = type;
        this.sData = data;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Refeicao refeicao = listRefeicoes.get(position);
        view = LayoutInflater.from(context).inflate(R.layout.item_list_historico, null);
        setupViews(view, refeicao, position);
        return view;
    }

    private void setupViews(View view, Refeicao refeicao, int position) {
        rlRefeicao = (RelativeLayout) view.findViewById(R.id.rlRefeicao);
        txtNomeRefeicao = (TextView) view.findViewById(R.id.txtRefeicao);
        txtAlimentosDaRefeicao = (TextView) view.findViewById(R.id.txtAlimentosDaRefeicao);
        txtData = (TextView) view.findViewById(R.id.txtData);
        btnVerMais = (VerMaisRefeicaoButton) view.findViewById(R.id.btnVerMais);

        btnVerMais.setId_refeicao(refeicao.getId());

        btnVerMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerMaisRefeicaoButton btnVerMais = (VerMaisRefeicaoButton) v;
                MySingleton.getApp().setId_refeicao(btnVerMais.getId_refeicao());
                getContext().startActivity(new Intent(getContext(), DetalhesRefeicaoActivity.class));
            }
        });

        txtAlimentosDaRefeicao.setText(refeicao.getResumoDeAlimentos());

        switch(refeicao.getLastAlimento().getTipo()) {
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

        txtData.setText(sData);

    }

}
