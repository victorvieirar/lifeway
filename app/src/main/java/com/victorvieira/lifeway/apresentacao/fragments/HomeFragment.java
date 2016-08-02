package com.victorvieira.lifeway.apresentacao.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.liulishuo.magicprogresswidget.MagicProgressCircle;

import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.apresentacao.activity.MainActivity;
import com.victorvieira.lifeway.dominio.Usuario;

import java.util.Date;
import java.util.GregorianCalendar;

public class HomeFragment extends MyFragment {

    private MagicProgressCircle userProgress;
    private TextView txtSaudacao;
    private ImageView iconSaudacao;
    private TextView txtPesoAtual;
    private TextView txtMetaDePeso;

    private boolean isUpdated = false;
    private NestedScrollView nsv;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initViews(view);

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

        return view;

    }

    private void initViews(View view) {
        txtSaudacao = (TextView) view.findViewById(R.id.txtSaudacao);
        iconSaudacao = (ImageView) view.findViewById(R.id.ic_saudacao);
        txtPesoAtual = (TextView) view.findViewById(R.id.txtPesoAtual);
        txtMetaDePeso = (TextView) view.findViewById(R.id.txtMetaDePeso);
        userProgress = (MagicProgressCircle) view.findViewById(R.id.userProgress);
        userProgress.setPercent((float) 0.5);

        nsv = (NestedScrollView) view.findViewById(R.id.nsvHomeFragment);
        nsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (oldScrollY < scrollY){
                    ((MainActivity) getActivity()).hideFloatingActionButton();
                }  else {
                    ((MainActivity) getActivity()).showFloatingActionButton();
                }
            }
        });
    }

    @Override
    public void updateFragment(boolean mBoolean) {

        do {

            if(MySingleton.getBancoDeDados().getUsuario() != null  &&
                    MySingleton.getBancoDeDados().getUsuario().getMetaDePeso() != 0) {

                mBoolean = false;

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String nomeUsuario = MySingleton.getBancoDeDados().getUsuario().getNome().split(" ")[0];
                        Date horaAtual = new GregorianCalendar().getTime();
                        int hora = Integer.parseInt(horaAtual.toString().substring(11, 13));

                        if (hora >= 5 && hora <= 11) {
                            txtSaudacao.setText("Bom dia, " + nomeUsuario + "!");
                            iconSaudacao.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_goodmorning));
                        } else {
                            if (hora >= 12 && hora <= 17) {
                                txtSaudacao.setText("Boa tarde, " + nomeUsuario + "!");
                                iconSaudacao.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_goodafternoon));
                            } else {
                                if (hora >= 18 && hora <= 23 || hora == 0) {
                                    txtSaudacao.setText("Boa noite, " + nomeUsuario + "!");
                                    iconSaudacao.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_goodnight));
                                } else {
                                    if (hora >= 0 && hora <= 4) {
                                        txtSaudacao.setText("Vá dormir, " + nomeUsuario + "!");
                                        iconSaudacao.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_goodnight));
                                    }
                                }
                            }
                        }

                        Usuario usuario = MySingleton.getBancoDeDados().getUsuario();
                        final double PESO = usuario.getPeso();
                        final double METADEPESO = usuario.getMetaDePeso();
                        final double PERCENT = 0;
                        String sPeso;
                        try {
                            sPeso = Double.toString(PESO).split(".")[0] + "," + Double.toString(PESO).split(".")[1];
                        } catch (Exception e) {
                            sPeso = Double.toString(PESO);
                        }
                        if(PESO == METADEPESO) {
                            userProgress.setPercent((float) 1);
                        } else {
                            userProgress.setPercent((float) PERCENT);
                        }
                        txtPesoAtual.setText(sPeso);
                        String txtMeta = "meta de peso: " + Double.toString(METADEPESO) + " kg";
                        txtMetaDePeso.setText(txtMeta);

                        isUpdated = true;
                    }
                });
                break;
            }

        } while(mBoolean);


    }



}
