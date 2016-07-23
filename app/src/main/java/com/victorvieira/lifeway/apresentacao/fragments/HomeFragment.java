package com.victorvieira.lifeway.apresentacao.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liulishuo.magicprogresswidget.MagicProgressCircle;

import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.dominio.Usuario;

import java.util.Date;
import java.util.GregorianCalendar;

public class HomeFragment extends MyFragment {

    private boolean mIsLargeLayout;

    private MagicProgressCircle userProgress;
    private TextView txtSaudacao;
    private ImageView iconSaudacao;
    private TextView txtPesoAtual;
    private TextView txtMetaDePeso;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initViews(view);

        updateViews();

        return view;

    }

    private void initViews(View view) {
        txtSaudacao = (TextView) view.findViewById(R.id.txtSaudacao);
        iconSaudacao = (ImageView) view.findViewById(R.id.ic_saudacao);
        txtPesoAtual = (TextView) view.findViewById(R.id.txtPesoAtual);
        txtMetaDePeso = (TextView) view.findViewById(R.id.txtMetaDePeso);
        userProgress = (MagicProgressCircle) view.findViewById(R.id.userProgress);
        userProgress.setPercent((float) 0.5);
    }

    private void updateViews() {

        new Thread() {
            @Override
            public void run() {
                boolean bContinue = true;
                do {

                    if(MySingleton.getBancoDeDados().getUsuario() != null  &&
                            MySingleton.getBancoDeDados().getUsuario().getMetaDePeso() != 0) {

                        bContinue = false;

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
                                userProgress.setPercent((float) PERCENT);
                                txtPesoAtual.setText(sPeso);
                                String txtMeta = "meta de peso: " + Double.toString(METADEPESO) + " kg";
                                txtMetaDePeso.setText(txtMeta);
                            }
                        });

                        break;
                    }

                } while(bContinue);

                try {
                    this.finalize();
                } catch (Throwable throwable) {
                    //do nothing
                }
            }
        }.start();

    }

    @Override
    public void updateFragment() {}



}
