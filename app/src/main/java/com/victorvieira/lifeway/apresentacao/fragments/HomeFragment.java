package com.victorvieira.lifeway.apresentacao.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liulishuo.magicprogresswidget.MagicProgressCircle;

import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.apresentacao.activity.AddAlimentoActivity;
import com.victorvieira.lifeway.apresentacao.activity.MainActivity;
import com.victorvieira.lifeway.apresentacao.extras.Card;
import com.victorvieira.lifeway.apresentacao.extras.ImageManager;
import com.victorvieira.lifeway.dominio.Usuario;
import com.victorvieira.lifeway.persistencia.ControladorBD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class HomeFragment extends MyFragment {

    private int scroll;

    private int bgWidth;
    private int bgHeight;

    private ControladorBD bd;
    private View mView;

    private int random;

    private Button btn_assinar;

    private RelativeLayout rlAlimentos;
    private RelativeLayout rlAgua;
    private RelativeLayout rlExercicios;
    private RelativeLayout rlDica1;
    private RelativeLayout rlDica2;

    private List<List<View>> views;
    private List<Card> cards;

    private int[] imgs = {
        R.drawable.nature,
        R.drawable.good_morning_1,
        R.drawable.good_morning_2,
        R.drawable.capuccino
    };

    private ImageView bgHome;
    private MagicProgressCircle userProgress;
    private TextView txtSaudacao;
    private TextView txtPesoAtual;
    private TextView txtQntKcalDiaria;
    private TextView txtQntAguaDiaria;

    private boolean isUpdated = false;
    private NestedScrollView nsv;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bd = new ControladorBD(getContext());

        initViews(mView);

        new Thread() {
            @Override
            public void run() {
                do {
                    updateFragment(false);
                } while(!(isUpdated));
                if(isUpdated) {
                    try {
                        this.finalize();
                    } catch (Throwable throwable) {
                        //do nothing
                    }
                }
            }
        }.start();
    }

    private void initViews(View view) {

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        bgWidth = metrics.widthPixels;
        bgHeight = (int) (270 * Resources.getSystem().getDisplayMetrics().density);

        txtSaudacao = (TextView) view.findViewById(R.id.txtSaudacao);
        txtPesoAtual = (TextView) view.findViewById(R.id.txtPesoAtual);
        userProgress = (MagicProgressCircle) view.findViewById(R.id.userProgress);
        userProgress.setPercent((float) 0.5);

        txtQntKcalDiaria = (TextView) mView.findViewById(R.id.txtQntKcalDiaria);
        txtQntAguaDiaria = (TextView) mView.findViewById(R.id.txtQntAguaDiaria);

        random = (int) (Math.random() * 4);

        MySingleton.getApp().clearImageHistoric();

        ImageManager imageManager = new ImageManager();
        bgHome = (ImageView) view.findViewById(R.id.bgHome);

        try {
            Bitmap bitmap = imageManager.createBitmap(getResources(), imgs[random],
                    bgWidth, bgHeight);

            bgHome.setImageBitmap(bitmap);
            bitmap = null;
            System.gc();

        } catch (Exception e) {
            e.printStackTrace();
        }

        nsv = (NestedScrollView) view.findViewById(R.id.nsvHomeFragment);

        nsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(oldScrollY < scrollY) {
                    scroll += (scrollY - oldScrollY);
                } else {
                    scroll -= (oldScrollY - scrollY);
                }
            }
        });

        cards = new ArrayList<Card>();

        /** type = 'd' > dicas    type = 'c' > card comum */
        cards.add(new Card('c', "Alimentos", "0 kcal", "Você ainda não consumiu alimentos hoje.", R.drawable.food_icon));

        cards.add(new Card('c', "Água", "0 ml", "Você ainda não consumiu água hoje.", R.drawable.water_icon));
        cards.add(new Card('c', "Exercícios", "0 min", "Você ainda não praticou nenhum exercício hoje.", R.drawable.exercise_icon));

        /** Dicas ainda não estão dinamicamente prontas */
        cards.add(new Card('d', "Sono", "Tente sempre dormir mais de 6 horas por dia, faz bem.", R.drawable.sleep_icon));
        cards.add(new Card('d', "Exercícios", "Exercite-se ao menos 30 min por dia.", R.drawable.exercise_icon_dica));

        views = new ArrayList<List<View>>();

        List<View> views_alimentos = new ArrayList<>();
        List<View> views_agua = new ArrayList<>();
        List<View> views_exercicios = new ArrayList<>();
        List<View> views_dica_1 = new ArrayList<>();
        List<View> views_dica_2 = new ArrayList<>();

        views_alimentos.add(mView.findViewById(R.id.title_alimentos));
        views_alimentos.add(mView.findViewById(R.id.info_alimentos));
        views_alimentos.add(mView.findViewById(R.id.descricao_alimentos));
        views_alimentos.add(mView.findViewById(R.id.ic_food));

        views_agua.add(mView.findViewById(R.id.title_agua));
        views_agua.add(mView.findViewById(R.id.info_agua));
        views_agua.add(mView.findViewById(R.id.descricao_agua));
        views_agua.add(mView.findViewById(R.id.ic_water));

        views_exercicios.add(mView.findViewById(R.id.title_exercicios));
        views_exercicios.add(mView.findViewById(R.id.info_exercicios));
        views_exercicios.add(mView.findViewById(R.id.descricao_exercicios));
        views_exercicios.add(mView.findViewById(R.id.ic_exercise));

        views_dica_1.add(mView.findViewById(R.id.title_dica_1));
        views_dica_1.add(mView.findViewById(R.id.descricao_dica_1));
        views_dica_1.add(mView.findViewById(R.id.ic_dica_1));

        views_dica_2.add(mView.findViewById(R.id.title_dica_2));
        views_dica_2.add(mView.findViewById(R.id.descricao_dica_2));
        views_dica_2.add(mView.findViewById(R.id.ic_dica_2));

        views.add(views_alimentos);
        views.add(views_agua);
        views.add(views_exercicios);
        views.add(views_dica_1);
        views.add(views_dica_2);

        int count = 0;
        for(Card card : cards) {
            setupCard(card, views.get(count));
            count++;
        }

        rlAlimentos = (RelativeLayout) mView.findViewById(R.id.card_alimentos);
        rlAlimentos.setClickable(true);
        rlAgua = (RelativeLayout) mView.findViewById(R.id.card_agua);
        rlAgua.setClickable(true);
        rlExercicios = (RelativeLayout) mView.findViewById(R.id.card_exercicios);
        rlExercicios.setClickable(true);
        rlDica1 = (RelativeLayout) mView.findViewById(R.id.card_dica_1);
        rlDica1.setClickable(true);
        rlDica2 = (RelativeLayout) mView.findViewById(R.id.card_dica_2);
        rlDica2.setClickable(true);

        rlAlimentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddAlimentoActivity.class));
            }
        });

        /** add a listener to rlAgua and rlExercicios */

        btn_assinar = (Button) mView.findViewById(R.id.btn_assinar);
        btn_assinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getContext(), "Em breve.", duration);
                toast.show();
            }
        });

    }

    private void setupCard(Card card, List<View> views) {
        TextView title;
        TextView desc;
        ImageView ic;

        switch(card.getTipo()) {
            case 'c':
                title = (TextView) views.get(0);
                title.setText(card.getTitulo());

                TextView info = (TextView) views.get(1);
                info.setText(card.getInfo());

                desc = (TextView) views.get(2);
                desc.setText(card.getDescricao());

                ic = (ImageView) views.get(3);
                ic.setImageDrawable(getResources().getDrawable(card.getIconResource()));
                break;
            case 'd':
                title = (TextView) views.get(0);
                title.setText(card.getTitulo());

                desc = (TextView) views.get(1);
                desc.setText(card.getDescricao());

                ic = (ImageView) views.get(2);
                ic.setImageDrawable(getResources().getDrawable(card.getIconResource()));
                break;
        }
    }

    @Override
    public void updateFragment(boolean mBoolean) { //true == repeat   false == no repeat

        do {

            if(bd.hasUser()) {

                mBoolean = false;

                String nomeUsuario = bd.getUsuario().getNome().split(" ")[0];
                Date horaAtual = new GregorianCalendar().getTime();
                int hora = Integer.parseInt(horaAtual.toString().substring(11, 13));

                if (hora >= 5 && hora <= 11) {
                    txtSaudacao.setText("Bom dia, " + nomeUsuario + "!");
                } else {
                    if (hora >= 12 && hora <= 17) {
                        txtSaudacao.setText("Boa tarde, " + nomeUsuario + "!");

                    } else {
                        if (hora >= 18 && hora <= 23 || hora == 0) {
                            txtSaudacao.setText("Boa noite, " + nomeUsuario + "!");

                        } else {
                            if (hora >= 0 && hora <= 4) {
                                txtSaudacao.setText("Vá dormir, " + nomeUsuario + "!");

                            }
                        }
                    }
                }

                Usuario usuario = bd.getUsuario();
                final double KCAL = usuario.getKcal_diaria();
                final double AGUA = usuario.getAgua_diaria();

                txtQntKcalDiaria.setText(Double.toString(KCAL));
                txtQntAguaDiaria.setText(Double.toString(AGUA));

                final double PESO = usuario.getPeso();
                final double METADEPESO = usuario.getMetaDePeso();
                final double PERCENT = 0;
                String sPeso = Integer.toString((int) PESO);
                if(PESO == METADEPESO) {
                    userProgress.setPercent((float) 1);
                } else {
                    userProgress.setPercent((float) PERCENT);
                }
                txtPesoAtual.setText(sPeso);

                isUpdated = true;

            }

        } while(mBoolean);


    }



}
