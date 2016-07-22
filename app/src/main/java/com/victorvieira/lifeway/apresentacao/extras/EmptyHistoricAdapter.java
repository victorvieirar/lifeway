package com.victorvieira.lifeway.apresentacao.extras;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.dominio.Alimento;
import com.victorvieira.lifeway.dominio.Refeicao;

import java.util.Date;
import java.util.List;

public class EmptyHistoricAdapter extends ArrayAdapter<Refeicao> {

    private Context context;

    public EmptyHistoricAdapter(Context context,  List<Refeicao> listRefeicoes) {
        super(context, 0, listRefeicoes);
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = LayoutInflater.from(context).inflate(R.layout.item_empty_list, null);
        return view;
    }



}
