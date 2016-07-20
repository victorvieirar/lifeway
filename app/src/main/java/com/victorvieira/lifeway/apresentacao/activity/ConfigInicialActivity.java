package com.victorvieira.lifeway.apresentacao.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.dominio.excecoes.MetaDePesoException;

public class ConfigInicialActivity extends AppCompatActivity {

    private EditText editMetaDePeso;
    private Button btnContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_inicial);

        initViews();
        setupListeners();
    }

    private void initViews() {

        editMetaDePeso = (EditText) findViewById(R.id.editMetaDePeso);
        btnContinuar = (Button) findViewById(R.id.btnCadatrar);

    }

    private void setupListeners() {

        editMetaDePeso.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    editMetaDePeso.setText("");
                } else {
                    if(editMetaDePeso.getText().toString().equals("")) {
                        editMetaDePeso.setText("qual sua meta de peso?");
                    }

                }
            }
        });

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(Double.parseDouble(editMetaDePeso.getText().toString()) != 0) {
                        MySingleton.getBancoDeDados().getUsuario().setMetaDePeso(editMetaDePeso);
                        finish();
                    } else {
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getApplicationContext(), "Insira uma meta de peso válida.", duration);
                        toast.show();
                    }

                } catch (MetaDePesoException e) {
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getApplicationContext(), "Insira uma meta de peso válida.", duration);
                    toast.show();
                }

            }
        });

    }
}
