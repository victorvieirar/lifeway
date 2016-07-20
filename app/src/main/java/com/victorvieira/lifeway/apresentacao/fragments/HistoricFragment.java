package com.victorvieira.lifeway.apresentacao.fragments;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.apresentacao.extras.AdapterGroup;
import com.victorvieira.lifeway.apresentacao.extras.ListaAlimentosAdapter;
import com.victorvieira.lifeway.dominio.Alimento;

import java.util.Date;
import java.util.List;

public class HistoricFragment extends MyFragment {

    private View view;

    private TextView txtData;
    private TextView txtUltimaRefeicao;
    private ImageView icFood;
    private ListView lvHistorico;
    private ListView lvUltimaRefeicao;
    private List<Alimento> listAlimentos;
    private List<Alimento> listLastAlimentos;
    private List<Date> listHorarios;
    private List<Date> listLastHorarios;
    private LinearLayout llUltimaRefeicao;
    private RelativeLayout rlHistoricFragment;
    private final AdapterGroup ADAPTERGROUP = new AdapterGroup();

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
        lvHistorico = (ListView) view.findViewById(R.id.lvHistorico);
        llUltimaRefeicao = (LinearLayout) view.findViewById(R.id.llUltimaRefeicao);
        rlHistoricFragment = (RelativeLayout) view.findViewById(R.id.rlHistoricFragment);
        lvUltimaRefeicao = (ListView) view.findViewById(R.id.lvUltimaRefeicao);
        icFood = (ImageView) view.findViewById(R.id.ic_food);
        txtUltimaRefeicao = (TextView) view.findViewById(R.id.txtUltimaRefeicao);
        txtData = (TextView) view.findViewById(R.id.txtData);

        txtUltimaRefeicao.setTextColor(getResources().getColor(R.color.textAdvertmentColor));
        txtUltimaRefeicao.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        txtUltimaRefeicao.setText("Você ainda não tem nenhum alimento adicionado no seu histórico. Adicione alimentos para visualizar seu historico de refeições.");
        txtData.setText("");
    }

    @Override
    public void updateFragment() {

        char color = 'n';

        if(MySingleton.getBancoDeDados().getUsuario() != null && MySingleton.getBancoDeDados().getUsuario().getConsumo() != null) {

            char type = MySingleton.getBancoDeDados().getUsuario().getConsumo().getLastAlimentos().get(0).getTipo();
            String ultimaRefeicao = null;
            switch(type) {
                case 'c':
                    ultimaRefeicao = "Café da Manhã";
                    icFood.setImageResource(R.drawable.ic_breakfast);
                    llUltimaRefeicao.setBackground(getResources().getDrawable(R.drawable.bg_breakfast));
                    txtData.setTextColor(getResources().getColor(R.color.textColorSecundary));
                    txtUltimaRefeicao.setTextColor(getResources().getColor(R.color.textColorSecundary));
                    color = 'b';
                    break;
                case 'a':
                    ultimaRefeicao = "Almoço";
                    icFood.setImageResource(R.drawable.ic_lunch);
                    llUltimaRefeicao.setBackground(getResources().getDrawable(R.drawable.bg_lunch));
                    txtData.setTextColor(getResources().getColor(R.color.textColorPrimary));
                    txtUltimaRefeicao.setTextColor(getResources().getColor(R.color.textColorPrimary));
                    color = 'w';
                    break;
                case 'j':
                    ultimaRefeicao = "Janta";
                    icFood.setImageResource(R.drawable.ic_dinner);
                    llUltimaRefeicao.setBackground(getResources().getDrawable(R.drawable.bg_dinner));
                    txtData.setTextColor(getResources().getColor(R.color.textColorPrimary));
                    txtUltimaRefeicao.setTextColor(getResources().getColor(R.color.textColorPrimary));
                    color = 'w';
                    break;
            }

            txtUltimaRefeicao.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            txtUltimaRefeicao.setText(ultimaRefeicao);

            listLastAlimentos = MySingleton.getBancoDeDados().getUsuario().getConsumo().getLastAlimentos();
            listAlimentos = MySingleton.getBancoDeDados().getUsuario().getConsumo().getAlimentosAfterLast();

            listLastHorarios = MySingleton.getBancoDeDados().getUsuario().getConsumo().getLastHorarios();
            listHorarios = MySingleton.getBancoDeDados().getUsuario().getConsumo().getHorariosAfterLast();

            ADAPTERGROUP.addAdapter(new ListaAlimentosAdapter(getContext(), listLastAlimentos, listLastHorarios, 'b', color));
            ADAPTERGROUP.addAdapter(new ListaAlimentosAdapter(getContext(), listAlimentos, listHorarios, 'b', 'b'));
            try {
                lvUltimaRefeicao.setAdapter(ADAPTERGROUP.getBeforeLastAdapter());
                lvHistorico.setAdapter(ADAPTERGROUP.getLastAdapter());
            } catch (Exception e) {
                //do nothing
            } finally {
                ADAPTERGROUP.clearAdapters();
            }

            String sData = MySingleton.getBancoDeDados().getUsuario().getConsumo().getStringOfHourByAlimento(listLastAlimentos.get(0));
            txtData.setText(sData);

        }

    }
}
