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
import com.victorvieira.lifeway.apresentacao.dialog.DialogExercise;
import com.victorvieira.lifeway.apresentacao.extras.Card;
import com.victorvieira.lifeway.apresentacao.extras.ImageManager;
import com.victorvieira.lifeway.dominio.Agua;
import com.victorvieira.lifeway.dominio.Consumo;
import com.victorvieira.lifeway.dominio.Exercicio;
import com.victorvieira.lifeway.dominio.Usuario;
import com.victorvieira.lifeway.persistencia.ControladorBD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class HomeFragment extends MyFragment {

    private int posY = 0;

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

    private View.OnClickListener onClickCards;

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

        initViews();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                do {
                    updateFragment(false);
                } while(!(isUpdated));
                if(isUpdated) {
                    try {
                        this.finalize();
                    } catch (Throwable throwable) {
                        /** do nothing */
                    }
                }
            }
        });
    }

    private void initViews() {

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        bgWidth = metrics.widthPixels;
        bgHeight = (int) (270 * Resources.getSystem().getDisplayMetrics().density);

        txtSaudacao = (TextView) mView.findViewById(R.id.txtSaudacao);
        txtPesoAtual = (TextView) mView.findViewById(R.id.txtPesoAtual);
        userProgress = (MagicProgressCircle) mView.findViewById(R.id.userProgress);
        userProgress.setPercent((float) 0.5);

        txtQntKcalDiaria = (TextView) this.mView.findViewById(R.id.txtQntKcalDiaria);
        txtQntAguaDiaria = (TextView) this.mView.findViewById(R.id.txtQntAguaDiaria);

        random = (int) (Math.random() * 4);

        MySingleton.getApp().clearImageHistoric();

        ImageManager imageManager = new ImageManager();
        bgHome = (ImageView) mView.findViewById(R.id.bgHome);

        try {
            Bitmap bitmap = imageManager.createBitmap(getResources(), imgs[random],
                    bgWidth, bgHeight);

            bgHome.setImageBitmap(bitmap);
            bitmap = null;
            System.gc();

        } catch (Exception e) {
            e.printStackTrace();
        }

        nsv = (NestedScrollView) mView.findViewById(R.id.nsvHomeFragment);

        nsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(oldScrollY < scrollY) {
                    int scroll = (scrollY - oldScrollY);
                    posY += (scrollY - oldScrollY);
                    ((MainActivity) getActivity()).hideToolbar(scroll, posY, bgHeight);
                } else {
                    posY -= (oldScrollY - scrollY);
                    ((MainActivity) getActivity()).showToolbar(posY, bgHeight);
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

        views_alimentos.add(this.mView.findViewById(R.id.title_alimentos));
        views_alimentos.add(this.mView.findViewById(R.id.info_alimentos));
        views_alimentos.add(this.mView.findViewById(R.id.descricao_alimentos));
        views_alimentos.add(this.mView.findViewById(R.id.ic_food));

        views_agua.add(this.mView.findViewById(R.id.title_agua));
        views_agua.add(this.mView.findViewById(R.id.info_agua));
        views_agua.add(this.mView.findViewById(R.id.descricao_agua));
        views_agua.add(this.mView.findViewById(R.id.ic_water));

        views_exercicios.add(this.mView.findViewById(R.id.title_exercicios));
        views_exercicios.add(this.mView.findViewById(R.id.info_exercicios));
        views_exercicios.add(this.mView.findViewById(R.id.descricao_exercicios));
        views_exercicios.add(this.mView.findViewById(R.id.ic_exercise));

        views_dica_1.add(this.mView.findViewById(R.id.title_dica_1));
        views_dica_1.add(this.mView.findViewById(R.id.descricao_dica_1));
        views_dica_1.add(this.mView.findViewById(R.id.ic_dica_1));

        views_dica_2.add(this.mView.findViewById(R.id.title_dica_2));
        views_dica_2.add(this.mView.findViewById(R.id.descricao_dica_2));
        views_dica_2.add(this.mView.findViewById(R.id.ic_dica_2));

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

        rlAlimentos = (RelativeLayout) this.mView.findViewById(R.id.card_alimentos);
        rlAlimentos.setClickable(true);
        rlAgua = (RelativeLayout) this.mView.findViewById(R.id.card_agua);
        rlAgua.setClickable(true);
        rlExercicios = (RelativeLayout) this.mView.findViewById(R.id.card_exercicios);
        rlExercicios.setClickable(true);
        rlDica1 = (RelativeLayout) this.mView.findViewById(R.id.card_dica_1);
        rlDica1.setClickable(true);
        rlDica2 = (RelativeLayout) this.mView.findViewById(R.id.card_dica_2);
        rlDica2.setClickable(true);

        onClickCards = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.card_alimentos:
                        startActivity(new Intent(getActivity(), AddAlimentoActivity.class));
                        break;
                    case R.id.card_agua:
                        if(bd.isAguaUpToDate(new Date())) {
                            Agua a = bd.getLastAgua();
                            a.addQuantidade(200);
                            bd.atualizarAgua(a);

                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(getContext(), "Foi adicionado 200 ml de água", duration);
                            toast.show();

                            updateFragment(false);
                        } else {
                            Agua a = new Agua();
                            a.setQuantidade(200);
                            a.setData(new Date());
                            bd.inserirAgua(a);

                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(getContext(), "Foi adicionado 200 ml de água", duration);
                            toast.show();

                            updateFragment(false);
                        }
                        break;
                    case R.id.card_exercicios:
                        DialogExercise dialog = new DialogExercise(getContext());
                        dialog.show();
                        break;


                    case R.id.card_dica_1:
                        rlDica1.setVisibility(View.GONE);
                        break;
                    case R.id.card_dica_2:
                        rlDica2.setVisibility(View.GONE);
                        break;
                }
            }
        };

        rlAlimentos.setOnClickListener(onClickCards);
        rlAgua.setOnClickListener(onClickCards);
        rlExercicios.setOnClickListener(onClickCards);
        rlDica1.setOnClickListener(onClickCards);
        rlDica2.setOnClickListener(onClickCards);


        /** add a listener to rlAgua and rlExercicios */

        btn_assinar = (Button) this.mView.findViewById(R.id.btn_assinar);
        btn_assinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getContext(), "Em breve", duration);
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
                final int KCAL = usuario.getKcal_diaria();
                final int AGUA = usuario.getAgua_diaria();

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

                try {
                    Consumo consumo = bd.getConsumo();
                    double consumoTotalDiario = consumo.getKcalByDay(new Date());
                    Card card = cards.get(0);
                    card.setInfo(((int) consumoTotalDiario) + " kcal");

                    if(consumoTotalDiario == 0) {
                        card.setDescricao("Você ainda não consumiu alimentos hoje.");
                    } else {
                        card.setDescricao("Você já consumiu "+((int) consumoTotalDiario)+" kcal de alimentos hoje.");
                    }

                    setupCard(card, views.get(0));

                } catch(Exception e) {
                }

                try {
                    Agua agua;
                    if(bd.isAguaUpToDate(new Date())) {
                        agua = bd.getLastAgua();
                    }  else {
                        agua = new Agua();
                        agua.setData(new Date());
                        agua.setQuantidade(0);
                    }

                    Card card = cards.get(1);

                    if(agua.getQuantidade() == 0) {
                        card.setDescricao("Você ainda não consumiu água hoje.");
                    } else {
                        card.setDescricao("Você já consumiu "+((int) agua.getQuantidade())+" ml de água hoje.");
                    }
                    card.setInfo(((int) agua.getQuantidade()) + " ml");

                    setupCard(card, views.get(1));

                } catch(Exception e) {
                }

                try {
                    Exercicio exercicio;

                    if(bd.isExercicioUpToDate(new Date())) {
                        exercicio = bd.getLastExercicio();
                    }  else {
                        exercicio = new Exercicio();
                        exercicio.setData(new Date());
                        exercicio.setTempo(0);
                    }

                    Card card = cards.get(2);

                    if(exercicio.getTempo() == 0) {
                        card.setDescricao("Você ainda não praticou nenhum exercício hoje.");
                    } else {
                        card.setDescricao("Você já praticou "+((int) exercicio.getTempo())+" min de exercício hoje.");
                    }
                    card.setInfo(((int) exercicio.getTempo()) + " min");

                    setupCard(card, views.get(2));

                } catch(Exception e) {
                }

                isUpdated = true;

            }

        } while(mBoolean);

    }



}
