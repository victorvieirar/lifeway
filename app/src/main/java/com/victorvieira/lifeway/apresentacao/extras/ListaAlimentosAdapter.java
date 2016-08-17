package com.victorvieira.lifeway.apresentacao.extras;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.dominio.Alimento;
import com.victorvieira.lifeway.persistencia.ControladorBD;

import java.util.Date;
import java.util.List;

public class ListaAlimentosAdapter extends ArrayAdapter<Alimento> {

    private ControladorBD bd;

    private Context context;
    private List<Alimento> listAlimentos = null;
    private char type;
    private char color;

    public ListaAlimentosAdapter(Context context,  List<Alimento> listAlimentos, char type, char color) {
        super(context, 0, listAlimentos);
        this.listAlimentos = listAlimentos;
        this.context = context;
        this.type = type;
        this.color = color;

        bd = new ControladorBD(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Alimento alimento = listAlimentos.get(position);

        if(view == null) {
            switch (type) {
                case 'a': //lista de alimentos
                    view = LayoutInflater.from(context).inflate(R.layout.item_list_alimentos, null);
                    setupListener(view, alimento);
                    break;
                case 'b': //lista de historico
                    view = LayoutInflater.from(context).inflate(R.layout.item_list_alimentos_on_historico, null);
                    break;
            }
        }

        setupViews(view, alimento, position);

        return view;
    }

    private void setupViews(View view, Alimento alimento, int position) {

        TextView textViewNomeAlimento = (TextView) view.findViewById(R.id.txtNomeAlimento);

        if(type == 'b') {
            switch(color) {
                case 'w':
                    textViewNomeAlimento.setTextColor(getContext().getResources().getColor(R.color.textColorPrimary));
                    break;
                case 'b':
                    textViewNomeAlimento.setTextColor(getContext().getResources().getColor(R.color.textColorSecundary));
                    break;
            }
        }

        textViewNomeAlimento.setText(alimento.getNome());

    }

    private void setupListener(View view, final Alimento ALIMENTO) {
        ImageButton btnAddAlimento = (ImageButton) view.findViewById(R.id.btnAddAlimento);
        btnAddAlimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bd.inserirRefeicao(bd.getRefeicaoId(new Date(), ALIMENTO.getTipo()), ALIMENTO.getTipo(), ALIMENTO, new Date());
            }
        });
    }

}
