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

import java.util.Date;
import java.util.List;

public class ListaAlimentosAdapter extends ArrayAdapter<Alimento> {

    private Context context;
    private List<Alimento> listAlimentos = null;
    private List<Date> listHorarios = null;
    private char type;
    private char color;

    public ListaAlimentosAdapter(Context context,  List<Alimento> listAlimentos, List<Date> listHorarios, char type, char color) {
        super(context, 0, listAlimentos);
        this.listAlimentos = listAlimentos;
        this.listHorarios = listHorarios;
        this.context = context;
        this.type = type;
        this.color = color;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Alimento alimento = listAlimentos.get(position);

        if(alimento == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_date_of_list, null);
            setupViews(view, position);
        } else {
            if(view == null) {
                switch (type) {
                    case 'a':
                        view = LayoutInflater.from(context).inflate(R.layout.item_list_alimentos, null);
                        setupListener(view, alimento);
                        break;
                    case 'b':
                        view = LayoutInflater.from(context).inflate(R.layout.item_list_historico, null);
                        break;
                }
            }

            setupViews(view, alimento, position);
        }

        return view;
    }

    private void setupViews(View view, Alimento alimento, int position) {

        TextView textViewNomeAlimento = (TextView) view.findViewById(R.id.txtNomeAlimento);

        if(type == 'b') {
            TextView txtHoraConsumo = (TextView)view.findViewById(R.id.txtHoraConsumo);
            String horaConsumo = MySingleton.getBancoDeDados().getUsuario().getConsumo().getHorarioByOrder(position).toString().substring(11, 16);

            switch(color) {
                case 'w':
                    txtHoraConsumo.setTextColor(getContext().getResources().getColor(R.color.textColorPrimary));
                    textViewNomeAlimento.setTextColor(getContext().getResources().getColor(R.color.textColorPrimary));
                    break;
                case 'b':
                    txtHoraConsumo.setTextColor(getContext().getResources().getColor(R.color.textColorSecundary));
                    textViewNomeAlimento.setTextColor(getContext().getResources().getColor(R.color.textColorSecundary));
                    break;
            }

            txtHoraConsumo.setText(horaConsumo);

        } else {
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

    private void setupViews(View view, int position) {
        TextView txtDate = (TextView) view.findViewById(R.id.txtDateOfList);

        String sDate = MySingleton.getBancoDeDados().getUsuario().getConsumo().getStringOfHourByHorario(listHorarios.get(position));
        txtDate.setText(sDate);
    }

    private void setupListener(View view, final Alimento alimento) {
        ImageButton btnAddAlimento = (ImageButton) view.findViewById(R.id.btnAddAlimento);
        btnAddAlimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySingleton.getBancoDeDados().getUsuario().addRefeicao(alimento);
            }
        });
    }

}
