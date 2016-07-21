package com.victorvieira.lifeway.apresentacao.extras;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.dominio.Alimento;
import com.victorvieira.lifeway.dominio.Refeicao;

import java.util.Date;
import java.util.List;

public class ListaHistoricoAdapter extends ArrayAdapter<Refeicao> {

    private RelativeLayout rlRefeicao;
    private ImageView iconFood;
    private TextView txtRefeicao;
    private TextView txtData;
    private ListView lvAlimentos;

    private Context context;
    private List<Refeicao> listRefeicoes = null;
    private List<Alimento> listAlimentos;
    private List<Date> listHorarios;
    private char type;
    private char color;

    public ListaHistoricoAdapter(Context context,  List<Refeicao> listRefeicoes, char type) {
        super(context, 0, listRefeicoes);
        this.listRefeicoes = listRefeicoes;
        this.context = context;
        this.type = type;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Refeicao refeicao = listRefeicoes.get(position);
        view = LayoutInflater.from(context).inflate(R.layout.item_list_refeicoes, null);

        setupViews(view, refeicao, position);

        return view;
    }

    private void setupViews(View view, Refeicao refeicao, int position) {
        rlRefeicao = (RelativeLayout) view.findViewById(R.id.rlRefeicao);
        iconFood = (ImageView) view.findViewById(R.id.ic_food);
        txtRefeicao = (TextView) view.findViewById(R.id.txtRefeicao);
        txtData = (TextView) view.findViewById(R.id.txtData);
        lvAlimentos = (ListView) view.findViewById(R.id.lvRefeicao);

        switch(refeicao.getLastAlimento().getTipo()) {
            case 'c':
                txtRefeicao.setText("Café da Manhã");
                color = 'b';
                txtRefeicao.setTextColor(getContext().getResources().getColor(R.color.textColorSecundary));
                txtData.setTextColor(getContext().getResources().getColor(R.color.textColorSecundary));
                rlRefeicao.setBackground(getContext().getResources().getDrawable(R.drawable.bg_breakfast));
                iconFood.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_breakfast));
                break;
            case 'a':
                color = 'w';
                txtRefeicao.setText("Almoço");
                txtRefeicao.setTextColor(getContext().getResources().getColor(R.color.textColorPrimary));
                txtData.setTextColor(getContext().getResources().getColor(R.color.textColorPrimary));
                rlRefeicao.setBackground(getContext().getResources().getDrawable(R.drawable.bg_lunch));
                iconFood.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_lunch));
                break;
            case 'j':
                color = 'w';
                txtRefeicao.setTextColor(getContext().getResources().getColor(R.color.textColorPrimary));
                txtData.setTextColor(getContext().getResources().getColor(R.color.textColorPrimary));
                txtRefeicao.setText("Jantar");
                rlRefeicao.setBackground(getContext().getResources().getDrawable(R.drawable.bg_dinner));
                iconFood.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_dinner));
                break;
        }

        listAlimentos = refeicao.getAlimentos();
        listHorarios = refeicao.getHorarios();
        ListaAlimentosAdapter listaAlimentosAdapter = new ListaAlimentosAdapter(getContext(),listAlimentos, listHorarios, 'b', color);
        lvAlimentos.setAdapter(listaAlimentosAdapter);

        String sData = refeicao.getStringOfHourByHorario(listHorarios.get(0));
        txtData.setText(sData);

    }

    private void setupListener(View view, final Alimento alimento) {

    }

}
