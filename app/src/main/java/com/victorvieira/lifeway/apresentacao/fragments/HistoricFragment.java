package com.victorvieira.lifeway.apresentacao.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.apresentacao.extras.ListaHistoricoAdapter;
import com.victorvieira.lifeway.dominio.Refeicao;

import java.util.List;

public class HistoricFragment extends MyFragment {

    private View view;

    private ListView lvHistorico;
    private List<Refeicao> listRefeicoes;

    public HistoricFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_historic, container, false);

        initViews();
        updateFragment();

        return view;
    }

    private void initViews() {
        lvHistorico = (ListView) view.findViewById(R.id.lvHistoric);

    }

    @Override
    public void updateFragment() {

        if(MySingleton.getBancoDeDados().getUsuario() != null && MySingleton.getBancoDeDados().getUsuario().getConsumo() != null) {

            listRefeicoes = MySingleton.getBancoDeDados().getUsuario().getConsumo().getRefeicoesInverse();
            ListaHistoricoAdapter adapter = new ListaHistoricoAdapter(getContext(), listRefeicoes, 'b');

            try {
                lvHistorico.setAdapter(adapter);
            } catch (Exception e) {
                //do nothing
            }

        } else {

        }

    }
}
