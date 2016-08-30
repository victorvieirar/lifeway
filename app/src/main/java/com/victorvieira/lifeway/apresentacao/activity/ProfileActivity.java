package com.victorvieira.lifeway.apresentacao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.dominio.Usuario;
import com.victorvieira.lifeway.persistencia.ControladorBD;

import java.util.Date;
import java.util.GregorianCalendar;

public class ProfileActivity extends BaseActivity {

    private Toolbar toolbar;

    private ControladorBD bd;

    private boolean keyboardIsHide = true;

    private RelativeLayout rlNomeEdit;
    private RelativeLayout rlDataNascimentoEdit;
    private RelativeLayout rlMetaDePesoEdit;

    private EditText editNomeProfile;
    private EditText editMetaDePesoProfile;

    private TextView txtDataNascimentoProfile;

    private RelativeLayout rlDatePicker;
    private DatePicker dpDataNascimento;
    private Button btnOkDatePicker;
    private Button btnCancelDatePicker;
    private int diaNasc = 1;
    private int mesNasc = 0;
    private int anoNasc = new GregorianCalendar().getInstance().get(GregorianCalendar.YEAR);

    private Button btnCancelar;

    private Button btnSalvar;

    private Button btnMasculino;
    private Button btnFeminino;

    private String nomeUsuario;
    private Double metaDePeso;
    private char sexo;

    private View.OnClickListener onClickListener;
    private View.OnClickListener onClickListenerSexo;

    @Override
    protected void onDestroy() {
        exitToRigthAnimation();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        enterFromRightAnimation();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bd = new ControladorBD(this);

        initViews();
        setupListeners();
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbarProfile);
        toolbar.setTitle("Perfil");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rlNomeEdit = (RelativeLayout) findViewById(R.id.rlNomeEdit);
        rlDataNascimentoEdit = (RelativeLayout) findViewById(R.id.rlDataNascimentoEdit);
        rlMetaDePesoEdit = (RelativeLayout) findViewById(R.id.rlMetaDePesoEdit);

        editNomeProfile = (EditText) findViewById(R.id.editNomeProfile);
        editNomeProfile.setText(bd.getUsuario().getNome());

        txtDataNascimentoProfile = (TextView) findViewById(R.id.txtDataNascimentoProfile);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(bd.getUsuario().getDataNascimento());

        diaNasc = gc.get(gc.DAY_OF_MONTH);
        mesNasc = gc.get(gc.MONTH);
        anoNasc = gc.get(gc.YEAR);

        String dataNascimento = ""+ diaNasc + " de " + MySingleton.getApp().getStringOfMonthByIndex(1+mesNasc) + " de " + anoNasc;
        txtDataNascimentoProfile.setText(dataNascimento);

        editMetaDePesoProfile = (EditText) findViewById(R.id.editMetaDePesoProfile);
        editMetaDePesoProfile.setText(Double.toString(bd.getUsuario().getMetaDePeso()));

        btnMasculino = (Button) findViewById(R.id.btnMasculino);
        btnFeminino = (Button) findViewById(R.id.btnFeminino);

        sexo = bd.getUsuario().getSexo();
        switch(sexo) {
            case 'm':
                selectButton(btnMasculino);
                break;
            case 'f':
                selectButton(btnFeminino);
                break;
        }

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);

        rlDatePicker = (RelativeLayout) findViewById(R.id.rlDatePicker);

        dpDataNascimento = (DatePicker) findViewById(R.id.dpDataNascimento);
        GregorianCalendar gcMinDate = new GregorianCalendar(1900, 0, 1);
        dpDataNascimento.setMinDate(gcMinDate.getTimeInMillis());
        dpDataNascimento.updateDate(anoNasc, mesNasc, diaNasc);

        btnOkDatePicker = (Button) findViewById(R.id.btnOkDatePicker);
        btnCancelDatePicker = (Button) findViewById(R.id.btnCancelDatePicker);
    }

    private void setupListeners() {
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {

                    case R.id.btnCancelar:
                        finish();
                        break;

                    case R.id.btnSalvar:
                        if(verifyNome()) {
                            if(verifyPeso()) {
                                Usuario uAntigo = bd.getUsuario();
                                GregorianCalendar gc = new GregorianCalendar(anoNasc, mesNasc, diaNasc);

                                Usuario uNovo = new Usuario(
                                        1, //default
                                        nomeUsuario,
                                        sexo,
                                        gc.getTime(),
                                        uAntigo.getPeso(),
                                        uAntigo.getAltura(),
                                        metaDePeso,
                                        2000, /** not setup */
                                        2000  /** not setup */
                                        );

                                bd.atualizarUsuario(uNovo);
                                finish();
                            }
                        }
                        break;
                }
            }
        };

        onClickListenerSexo = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectButton((Button) v);
            }
        };

        btnFeminino.setOnClickListener(onClickListenerSexo);
        btnMasculino.setOnClickListener(onClickListenerSexo);

        btnOkDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaNasc = dpDataNascimento.getDayOfMonth();
                mesNasc = dpDataNascimento.getMonth();
                anoNasc = dpDataNascimento.getYear();

                GregorianCalendar gcDataNascimento = new GregorianCalendar(anoNasc, mesNasc, diaNasc);

                String sData = diaNasc + " de " + MySingleton.getApp().getStringOfMonthByIndex(1+mesNasc) + " de " + anoNasc;
                txtDataNascimentoProfile.setText(sData);

                rlDatePicker.setVisibility(View.GONE);
            }
        });

        btnCancelDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlDatePicker.setVisibility(View.GONE);
            }
        });

        rlDataNascimentoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlDatePicker.setVisibility(View.VISIBLE);

                if(!(keyboardIsHide)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                    View view = getCurrentFocus();
                    view.clearFocus();

                    keyboardIsHide = true;
                }
            }
        });

        btnMasculino.setOnClickListener(onClickListener);
        btnFeminino.setOnClickListener(onClickListener);

        btnCancelar.setOnClickListener(onClickListener);
        btnSalvar.setOnClickListener(onClickListener);
    }

    private boolean verifyNome() {
        if(editNomeProfile.getText().toString() != "" &&
                editNomeProfile.getText().toString() != null) {

            nomeUsuario = editNomeProfile.getText().toString();
            return true;

        } else {
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(), "Insira um nome válido", duration);
            toast.show();
            return true;
        }
    }

    private boolean verifyPeso() {
        try {
            if(Double.parseDouble(editMetaDePesoProfile.getText().toString()) != 0) {
                metaDePeso = Double.parseDouble(editMetaDePesoProfile.getText().toString());
                return true;
            } else {
                return false;
            }
        } catch(Exception e) {
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(), "Insira uma meta de peso válida", duration);
            toast.show();
            return false;
        }
    }

    private void selectButton(Button b) {
        switch (b.getId()) {
            case R.id.btnMasculino:
                btnMasculino.setBackground(getResources().getDrawable(R.drawable.bg_btn_sexo_selected));
                btnMasculino.setTextColor(getResources().getColor(R.color.white));

                btnFeminino.setBackground(getResources().getDrawable(R.drawable.bg_btn_sexo));
                btnFeminino.setTextColor(getResources().getColor(R.color.colorPrimary));

                sexo = 'm';
                break;

            case R.id.btnFeminino:
                btnFeminino.setBackground(getResources().getDrawable(R.drawable.bg_btn_sexo_selected));
                btnFeminino.setTextColor(getResources().getColor(R.color.white));

                btnMasculino.setBackground(getResources().getDrawable(R.drawable.bg_btn_sexo));
                btnMasculino.setTextColor(getResources().getColor(R.color.colorPrimary));

                sexo = 'f';
                break;
        }
    }
}
