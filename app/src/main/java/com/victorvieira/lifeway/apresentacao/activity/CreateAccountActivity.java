package com.victorvieira.lifeway.apresentacao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.dominio.Usuario;

import java.util.GregorianCalendar;

public class CreateAccountActivity extends BaseActivity {

    int diaNasc = 1;
    int mesNasc = 1;
    int anoNasc = new GregorianCalendar().getInstance().get(GregorianCalendar.YEAR);

    private boolean keyboardIsHide = false;

    private ImageView bgCreateAccount;
    private TextView txtSaudacaoInicial;

    private EditText editMetaDePeso;
    private EditText editPeso;
    private EditText editAltura;
    private TextView txtDataNascimento;

    private DatePicker dpDataNascimento;
    private RelativeLayout rlDatePicker;
    private RelativeLayout rlTextDataNascimento;

    private Button btnOkDatePicker;
    private Button btnCancelDatePicker;
    private Button btnContinuar;

    @Override
    public void onBackPressed() {
        exitToBottomAnimation();
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        exitToBottomAnimation();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        enterFromBottomAnimation();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        initViews();
        setupListeners();
    }

    private void initViews() {

        editMetaDePeso = (EditText) findViewById(R.id.editMetaDePeso);
        editPeso = (EditText) findViewById(R.id.editPeso);
        editAltura = (EditText) findViewById(R.id.editAltura);
        txtDataNascimento = (TextView) findViewById(R.id.editDataNascimento);
        btnContinuar = (Button) findViewById(R.id.btnCadatrar);
        btnCancelDatePicker = (Button) findViewById(R.id.btnCancelDatePicker);
        btnOkDatePicker = (Button) findViewById(R.id.btnOkDatePicker);

        txtSaudacaoInicial = (TextView) findViewById(R.id.txtSaudacaoInicial);
        txtSaudacaoInicial.setText("Olá, " + MySingleton.getBancoDeDados().getApp().getNomeUsuario().split(" ")[0]);

        bgCreateAccount = (ImageView) findViewById(R.id.bgCreateAccount);
        bgCreateAccount.setImageDrawable(getResources().getDrawable(MySingleton.getBancoDeDados().getApp().getImageLogin()));

        txtDataNascimento.setText(diaNasc + " de " + MySingleton.getBancoDeDados().getApp().getStringOfMonthByIndex(mesNasc) + " de " + anoNasc);
        txtDataNascimento.setClickable(true);
        rlTextDataNascimento = (RelativeLayout) findViewById(R.id.rlTextDataNascimento);

        dpDataNascimento = (DatePicker) findViewById(R.id.dpDataNascimento);
        GregorianCalendar gcMinDate = new GregorianCalendar(1900, 1, 1);
        dpDataNascimento.setMinDate(gcMinDate.getTimeInMillis());
        dpDataNascimento.updateDate(anoNasc, mesNasc, diaNasc);

        rlDatePicker = (RelativeLayout) findViewById(R.id.rlDatePicker);
    }

    private void setupListeners() {

        editMetaDePeso.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    if(editMetaDePeso.getText().toString().toLowerCase().contains("peso")) {
                        editMetaDePeso.setText("");
                    }
                    keyboardIsHide = false;
                } else {
                    if(editMetaDePeso.getText().toString().equals("")) {
                        editMetaDePeso.setText("meta de peso (em kg)");
                    }

                }
            }
        });

        editPeso.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    if(editPeso.getText().toString().toLowerCase().contains("peso")) {
                        editPeso.setText("");
                    }
                    keyboardIsHide = false;
                } else {
                    if(editPeso.getText().toString().equals("")) {
                        editPeso.setText("peso (em kg)");
                    }
                }
            }
        });

        editAltura.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    if(editAltura.getText().toString().toLowerCase().contains("altura")) {
                        editAltura.setText("");
                    }
                    keyboardIsHide = false;
                } else {
                    if(editAltura.getText().toString().equals("")) {
                        editAltura.setText("altura (em metros)");
                    }
                }
            }
        });

        rlTextDataNascimento.setOnClickListener(new View.OnClickListener() {
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

        btnOkDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaNasc = dpDataNascimento.getDayOfMonth();
                mesNasc = dpDataNascimento.getMonth() + 1;
                anoNasc = dpDataNascimento.getYear();

                GregorianCalendar gcDataNascimento = new GregorianCalendar(anoNasc, mesNasc, diaNasc);

                String sData = diaNasc + " de " + MySingleton.getBancoDeDados().getApp().getStringOfMonthByIndex(mesNasc+1) + " de " + anoNasc;
                txtDataNascimento.setText(sData);

                rlDatePicker.setVisibility(View.INVISIBLE);
            }
        });

        btnCancelDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlDatePicker.setVisibility(View.INVISIBLE);
            }
        });

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(stepOne()) {
                    if(stepTwo()) {
                        if(stepThree()) {
                            if(stepFour()) {
                                Usuario usuario = new Usuario(
                                        MySingleton.getBancoDeDados().getApp().getNomeUsuario(),
                                        MySingleton.getBancoDeDados().getApp().getDataNascimento(),
                                        MySingleton.getBancoDeDados().getApp().getPeso(),
                                        MySingleton.getBancoDeDados().getApp().getAltura(),
                                        MySingleton.getBancoDeDados().getApp().getMetaDePeso()
                                );

                                MySingleton.getBancoDeDados().setupUsuario(usuario);
                                startActivity(new Intent(CreateAccountActivity.this, MainActivity.class));
                                finish();

                            } else {
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(getApplicationContext(), "Insira uma meta de peso válida.", duration);
                                toast.show();
                            }
                        } else {
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(getApplicationContext(), "Insira uma data de nascimento válida.", duration);
                            toast.show();
                        }
                    } else {
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getApplicationContext(), "Insira uma altura válida.", duration);
                        toast.show();
                    }
                } else {
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getApplicationContext(), "Insira um peso válido.", duration);
                    toast.show();
                }

            }
        });

    }

    private boolean stepOne() {
        if(editPeso.getText().toString() != "" &&
                editPeso.getText().toString() != null &&
                !(editPeso.getText().toString().toLowerCase().contains("peso"))) {
            try {
                double peso = Double.parseDouble(editPeso.getText().toString());
                MySingleton.getBancoDeDados().getApp().setPeso(peso);
                return true;
            } catch(Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }
    private boolean stepTwo() {
        if(editAltura.getText().toString() != "" &&
                editAltura.getText().toString() != null &&
                !(editAltura.getText().toString().toLowerCase().contains("altura"))) {
            try {
                Double altura = Double.parseDouble(editAltura.getText().toString());
                MySingleton.getBancoDeDados().getApp().setAltura(altura);
                return true;
            } catch(Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }
    private boolean stepThree() {
        try {
            GregorianCalendar gcDataNascimento = new GregorianCalendar(anoNasc, mesNasc, diaNasc);
            MySingleton.getBancoDeDados().getApp().setDataNascimento(gcDataNascimento.getTime());
            return true;
        } catch(Exception e) {
            return false;
        }

    }
    private boolean stepFour() {
        try {
            if(Double.parseDouble(editMetaDePeso.getText().toString()) != 0) {
                double metaDePeso = Double.parseDouble(editMetaDePeso.getText().toString());
                MySingleton.getBancoDeDados().getApp().setMetaDePeso(metaDePeso);
                return true;
            } else {
                return false;
            }
        } catch(Exception e) {
            return false;
        }
    }

}
