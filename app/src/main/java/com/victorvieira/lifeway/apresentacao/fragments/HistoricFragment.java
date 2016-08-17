package com.victorvieira.lifeway.apresentacao.fragments;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.apresentacao.extras.ImageManager;
import com.victorvieira.lifeway.apresentacao.extras.ListaHistoricoAdapter;
import com.victorvieira.lifeway.apresentacao.extras.MyListView;
import com.victorvieira.lifeway.dominio.Refeicao;
import com.victorvieira.lifeway.persistencia.ControladorBD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class HistoricFragment extends MyFragment {

    private int bgWidth;
    private int bgHeight;

    private ControladorBD bd;
    private View mView;

    private ImageView bgHistoric;

    private NestedScrollView nsv;
    private ImageButton btnDataAnterior;
    private ImageButton btnDataPosterior;
    private TextView txtIntroHistoric;
    private TextView txtDataAtual;
    private MyListView lvHistorico;
    private List<Refeicao> listRefeicoes;

    private int ref = -1;
    private ArrayList<Date> horariosExistentes;

    private GregorianCalendar gcSelected = new GregorianCalendar();
    private GregorianCalendar gcReference = new GregorianCalendar();

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
        mView = inflater.inflate(R.layout.fragment_historic, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bd = new ControladorBD(getContext());

        initViews();
        setupListeners();
        updateFragment(true);

    }

    private void initViews() {

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        bgWidth = metrics.widthPixels;
        bgHeight = (int) (150 * Resources.getSystem().getDisplayMetrics().density);

        lvHistorico = (MyListView) mView.findViewById(R.id.lvHistoric);
        txtIntroHistoric = (TextView) mView.findViewById(R.id.txtIntroHistoric);
        txtIntroHistoric.setText("Esse é seu histórico." +
                "\nVocê ainda não possui alimentos cadastrados." +
                "\nQuando você consumir alimentos, eles ficarão guardados aqui. Basta então selecionar a data e ver suas refeições.");

        txtDataAtual = (TextView) mView.findViewById(R.id.txtDataAtual);
        txtDataAtual.setText("hoje");

        btnDataAnterior = (ImageButton) mView.findViewById(R.id.btnDataAnterior);
        btnDataPosterior = (ImageButton) mView.findViewById(R.id.btnDataPosterior);

        btnDataAnterior.setEnabled(false);
        btnDataAnterior.setAlpha((float) 0.5);
        btnDataPosterior.setEnabled(false);
        btnDataPosterior.setAlpha((float) 0.5);

        nsv = (NestedScrollView) mView.findViewById(R.id.nsvHistoricFragment);
        nsv.smoothScrollTo(0, (int) txtIntroHistoric.getY());

        gcSelected.setTime(new Date());

        ImageManager imageManager = new ImageManager();
        bgHistoric = (ImageView) mView.findViewById(R.id.bgHistoric);

        if(!MySingleton.getApp().hasImageHistoric()) {
            MySingleton.getApp().setImageHistoric(imageManager.createBitmap(getResources(), R.drawable.salmon_steak,
                    bgWidth, bgHeight));
        }

        try {
            bgHistoric.setImageBitmap(MySingleton.getApp().getImageHistoric());
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

        imageManager = null;
        System.gc();

    }

    private void setupListeners() {
        btnDataAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                --ref;
                gcSelected.setTime(horariosExistentes.get(ref));
                updateFragment(false);
            }
        });

        btnDataPosterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++ref;
                gcSelected.setTime(horariosExistentes.get(ref));
                updateFragment(false);
            }
        });

    }

    @Override
    public void updateFragment(boolean mBoolean) { //false = date picker    true = tab

        if(bd.hasConsumo()) {

            horariosExistentes = bd.getConsumo().getHorarios();

            if(mBoolean) {
                ref = horariosExistentes.size()-1;
                gcSelected.setTime(horariosExistentes.get(ref));
            }

            gcReference.setTime(new Date());
            verificarDisponibilidade();
            listRefeicoes = bd.getConsumo().getRefeicoesByDayInOrder(gcSelected.getTime());

            try {
                listRefeicoes.get(0).getAlimentos(); //teste de existencia

                txtDataAtual.setText(MySingleton.getApp().getStringOfHour(gcReference, gcSelected));
                txtIntroHistoric.setText("Histórico desse dia");
                ListaHistoricoAdapter adapter = new ListaHistoricoAdapter(getContext(), listRefeicoes, 'b', txtDataAtual.getText().toString());

                try {
                    lvHistorico.setAdapter(adapter);
                } catch (Exception e) {
                    //do nothing
                }


            } catch (Exception e) {
                txtIntroHistoric.setText("Você ainda não possui alimentos cadastrados neste dia." +
                        "\nQuando você consumir alimentos, eles ficarão guardados aqui. Basta então selecionar a data e ver suas refeições.");

                ListaHistoricoAdapter adapterEmpty = new ListaHistoricoAdapter(getContext(), new ArrayList<Refeicao>(), 'b', "");
                lvHistorico.setAdapter(adapterEmpty);
            }


        }

    }

    public void verificarDisponibilidade() {
        if(bd.getConsumo().getHorarios().size() >= 1) {

            boolean before = ref > 0;
            boolean after = (ref >= 0 && ref < (horariosExistentes.size()-1));

            if(before) {
                btnDataAnterior.setAlpha((float) 1);
            } else {
                btnDataAnterior.setAlpha((float) 0.5);
            }

            if(after) {
                btnDataPosterior.setAlpha((float) 1);
            } else {
                btnDataPosterior.setAlpha((float) 0.5);
            }

            btnDataAnterior.setEnabled(before);
            btnDataPosterior.setEnabled(after);

        }
    }
}
