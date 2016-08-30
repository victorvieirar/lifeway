package com.victorvieira.lifeway.apresentacao.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.apresentacao.activity.ProfileActivity;
import com.victorvieira.lifeway.apresentacao.extras.ImageManager;
import com.victorvieira.lifeway.persistencia.ControladorBD;

public class SettingsFragment extends MyFragment {

    private View mView;

    private ControladorBD bd;

    private int bgWidth;
    private int bgHeight;

    private ImageView bgSettings;
    private TextView txtNomeUsuario;
    private TextView txtSobrenomeUsuario;

    private RelativeLayout rlPerfil;
    private RelativeLayout rlHorarios;
    private RelativeLayout rlLimpeza;
    private RelativeLayout rlAgua;
    private LinearLayout llLifeway;

    private View.OnClickListener onClickListener;

    private TextView txtApagarConta;

    public SettingsFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_settings, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bd = new ControladorBD(getActivity());
        initViews();

        setupListeners();
    }

    private void initViews() {
        bgSettings = (ImageView) mView.findViewById(R.id.bgSettings);

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        bgWidth = metrics.widthPixels;
        bgHeight = (int) (150 * Resources.getSystem().getDisplayMetrics().density);

        MySingleton.getApp().clearImageHistoric();

        ImageManager imageManager = new ImageManager();

        try {
            Bitmap bitmap = imageManager.createBitmap(getResources(), R.drawable.man_and_woman_walking,
                    bgWidth, bgHeight);

            bgSettings.setImageBitmap(bitmap);
            bitmap = null;
            System.gc();

        } catch (Exception e) {
            e.printStackTrace();
        }

        txtNomeUsuario = (TextView) mView.findViewById(R.id.txtNomeSettings);
        txtSobrenomeUsuario = (TextView) mView.findViewById(R.id.txtSobrenomeSettings);

        String nome = bd.getUsuario().getNome();
        String sobrenome = "";
        try {
            sobrenome = nome.split(" ")[1];
        } catch(Exception e) {
            /** do nothing */
        }

        txtNomeUsuario.setText(nome.split(" ")[0]);
        txtSobrenomeUsuario.setText(sobrenome);

        rlPerfil = (RelativeLayout) mView.findViewById(R.id.rlPerfil);
        rlHorarios = (RelativeLayout) mView.findViewById(R.id.rlHorarios);
        rlLimpeza = (RelativeLayout) mView.findViewById(R.id.rlLimpeza);
        rlAgua = (RelativeLayout) mView.findViewById(R.id.rlAgua);
        llLifeway = (LinearLayout) mView.findViewById(R.id.llLifeway);
    }

    private void setupListeners() {
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.rlPerfil:
                        startActivity(new Intent(getActivity(), ProfileActivity.class));
                        break;
                    case R.id.rlHorarios:
                        break;
                    case R.id.rlLimpeza:
                        break;
                    case R.id.rlAgua:
                        break;
                    case R.id.llLifeway:
                        break;
                }
            }
        };

        rlPerfil.setOnClickListener(onClickListener);
        rlHorarios.setOnClickListener(onClickListener);
        rlLimpeza.setOnClickListener(onClickListener);
        rlAgua.setOnClickListener(onClickListener);
        llLifeway.setOnClickListener(onClickListener);
    }

    @Override
    public void updateFragment(boolean mBoolean) {
        if(bd.hasUser()) {
            String nome = bd.getUsuario().getNome();
            String sobrenome = "";
            try {
                sobrenome = nome.split(" ")[1];
            } catch(Exception e) {
                /** do nothing */
            }

            txtNomeUsuario.setText(nome.split(" ")[0]);
            txtSobrenomeUsuario.setText(sobrenome);
        }
    }

}
