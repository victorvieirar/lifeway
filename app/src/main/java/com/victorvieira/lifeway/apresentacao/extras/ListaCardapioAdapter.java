package com.victorvieira.lifeway.apresentacao.extras;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.dominio.Alimento;
import com.victorvieira.lifeway.dominio.RefeicaoDisponivel;

import java.util.Date;
import java.util.List;

public class ListaCardapioAdapter extends ArrayAdapter<RefeicaoDisponivel> {

    private Context context;
    private List<RefeicaoDisponivel> listRefeicoes;
    private List<Alimento> listAlimentos;

    private TextView txtNomeRefeicao;
    private ListView lvAlimentos;

    public ListaCardapioAdapter(Context context, List<RefeicaoDisponivel> listRefeicoes) {
        super(context, 0, listRefeicoes);
        this.context = context;
        this.listRefeicoes = listRefeicoes;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        RefeicaoDisponivel refeicaoDisponivel = listRefeicoes.get(position);

        view = LayoutInflater.from(context).inflate(R.layout.item_list_cardapio, null);

        setupViews(view, refeicaoDisponivel, position);

        return view;
    }

    private void setupViews(View view, RefeicaoDisponivel refeicaoDisponivel, int position) {
        txtNomeRefeicao = (TextView) view.findViewById(R.id.txtNomeRefeicao);
        lvAlimentos = (ListView) view.findViewById(R.id.lvAlimentos);

        txtNomeRefeicao.setText(refeicaoDisponivel.getNomeRefeicao());

        listAlimentos = refeicaoDisponivel.getAlimentos();
        lvAlimentos.setAdapter(new ListaAlimentosAdapter(getContext(), listAlimentos, 'a', 'b'));

    }

    private void setupListener(View view, final Alimento alimento) {

    }

}
