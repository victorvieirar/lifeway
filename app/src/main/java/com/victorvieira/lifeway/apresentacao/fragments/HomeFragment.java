package com.victorvieira.lifeway.apresentacao.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.liulishuo.magicprogresswidget.MagicProgressCircle;

import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.apresentacao.dialogs.MyDialogFragment;
import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.dominio.Usuario;

import java.util.Date;
import java.util.GregorianCalendar;

public class HomeFragment extends MyFragment {

    private boolean mIsLargeLayout;

    private MagicProgressCircle userProgress;
    private TextView txtSaudacao;
    private TextView txtPesoAtual;
    private TextView txtMetaDePeso;
    private ImageButton btnAddAlimentoFast;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initViews(view);
        setupListeners();

        updateViews();


        mIsLargeLayout = getResources().getBoolean(R.bool.large_layout);

        return view;

    }

    private void initViews(View view) {
        txtSaudacao = (TextView) view.findViewById(R.id.txtSaudacao);
        txtPesoAtual = (TextView) view.findViewById(R.id.txtPesoAtual);
        txtMetaDePeso = (TextView) view.findViewById(R.id.txtMetaDePeso);
        btnAddAlimentoFast = (ImageButton) view.findViewById(R.id.btnAdicionarAlimento);
        userProgress = (MagicProgressCircle) view.findViewById(R.id.userProgress);
        userProgress.setPercent((float) 0.5);
    }

    private void setupListeners() {

        btnAddAlimentoFast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogAlimento();
            }
        });

    }
    private void createDialogAlimento() {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        MyDialogFragment dialogFragment = new MyDialogFragment();

        if (mIsLargeLayout) {
            dialogFragment.show(fragmentManager, "dialog");
        } else {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.add(android.R.id.content, dialogFragment).addToBackStack(null).commit();
        }


    }

    private void updateViews() {

        new Thread() {
            @Override
            public void run() {
                boolean bContinue = true;

                do {
                    if(MySingleton.getBancoDeDados().getUsuario() != null  && MySingleton.getBancoDeDados().getUsuario().getMetaDePeso() != 0) {
                        String nomeUsuario = MySingleton.getBancoDeDados().getUsuario().getNome().split(" ")[0];
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

                        Usuario usuario = MySingleton.getBancoDeDados().getUsuario();
                        final double PESO = usuario.getPeso();
                        final double METADEPESO = usuario.getMetaDePeso();
                        final double PERCENT = 0;

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                userProgress.setPercent((float) PERCENT);
                                txtPesoAtual.setText(Double.toString(PESO));
                                String txtMeta = "sua meta de peso é "+Double.toString(METADEPESO)+" kg";
                                txtMetaDePeso.setText(txtMeta);

                            }
                        });
                        bContinue = false;
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
