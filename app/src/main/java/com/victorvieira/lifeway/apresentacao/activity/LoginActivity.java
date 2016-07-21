package com.victorvieira.lifeway.apresentacao.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.apresentacao.extras.Mask;
import com.victorvieira.lifeway.dominio.Usuario;

import java.util.Date;
import java.util.GregorianCalendar;

public class LoginActivity extends AppCompatActivity {

    private EditText editNome;
    private EditText editDataNascimento;
    private EditText editPeso;
    private EditText editAltura;

    private TextWatcher dataNascimentoMask;
    private TextWatcher alturaMask;

    private Button btnAcessar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        dataNascimentoMask = Mask.insert("##/##/####", editDataNascimento);
        alturaMask = Mask.insert("#.##", editAltura);
        setupListeners();

    }

    @Override
    public void onBackPressed() {
        //do nothing
    }

    private void initViews() {

        editNome = (EditText) this.findViewById(R.id.editNome);
        editDataNascimento = (EditText) this.findViewById(R.id.editData);
        editPeso = (EditText) this.findViewById(R.id.editPeso);
        editAltura = (EditText) this.findViewById(R.id.editAltura);
        btnAcessar = (Button) this.findViewById(R.id.btnAcessar);

    }

    private void setupListeners() {

        editNome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if(editNome.getText().toString() != "" && editNome.getText().toString() != "nome completo") {
                        editNome.setText("");
                    }
                } else {
                    if(editNome.getText().toString().equals("")) {
                        editNome.setText("nome completo");
                    }
                }
            }
        });

        editDataNascimento.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    if(editDataNascimento.getText().toString() != "" && editDataNascimento.getText().toString() != "data de nascimento") {
                        editDataNascimento.setText("");
                        dataNascimentoMask = null;
                        dataNascimentoMask = Mask.insert("##/##/####", editDataNascimento);
                    }
                } else {
                    if(editDataNascimento.getText().toString().equals("")) {
                        dataNascimentoMask = null;
                        editDataNascimento.setText("data de nascimento");
                    }
                }
            }
        });

        editPeso.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    if(editPeso.getText().toString() != "" && editPeso.getText().toString() != "peso (em kg)") {
                        editPeso.setText("");
                    }
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
                    if(editAltura.getText().toString() != "" && editAltura.getText().toString() != "altura (em metros)") {
                        editAltura.setText("");
                        alturaMask = null;
                        alturaMask = Mask.insert("#.##", editAltura);
                    }
                } else {
                    if(editAltura.getText().toString().equals("")) {
                        alturaMask = null;
                        editAltura.setText("altura (em metros)");
                    }
                }
            }
        });

        editDataNascimento.addTextChangedListener(dataNascimentoMask);
        editAltura.addTextChangedListener(alturaMask);

        btnAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(
                        editNome.getText().toString() != "" &&
                        editNome.getText().toString() != null &&
                        editNome.getText().toString() != "nome completo" &&
                        editDataNascimento.getText().toString() != "" &&
                        editDataNascimento.getText().toString() != null &&
                        editDataNascimento.getText().toString() != "data de nascimento" &&
                        editPeso.getText().toString() != "" &&
                        editPeso.getText().toString() != null &&
                        editPeso.getText().toString() != "peso (em kg)" &&
                        editAltura.getText().toString() != "" &&
                        editAltura.getText().toString() != null &&
                        editAltura.getText().toString() != "altura (em metros)") {
                    try {

                        int dia = Integer.parseInt(editDataNascimento.getText().toString().substring(0, 2));
                        int mes = Integer.parseInt(editDataNascimento.getText().toString().substring(3, 5));
                        int ano = Integer.parseInt(editDataNascimento.getText().toString().substring(6));

                        GregorianCalendar gregorianCalendar = new GregorianCalendar(dia, mes, ano);
                        Date dataNascimento = gregorianCalendar.getTime();

                        try {
                            MySingleton.getBancoDeDados().setupUsuario(new Usuario(
                                    editNome.getText().toString(), dataNascimento, editPeso, editAltura)
                            );
                            startActivity(new Intent(LoginActivity.this, ConfigInicialActivity.class));
                            finish();
                        } catch (Exception e) {
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), duration);
                            toast.show();
                        }

                    } catch (Exception e) {
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getApplicationContext(), "Insira uma data de nascimento válida!", duration);
                        toast.show();
                    }
                }
            }
        });

    }

}
