package com.victorvieira.lifeway.apresentacao.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.apresentacao.extras.ImageManager;

import java.io.IOException;

public class WelcomeActivity extends AppCompatActivity {

    private String nomeUsuario;
    private ImageView bgLogin;
    private EditText editNome;
    private Button btnContinuar;

    private int random;

    private int[] imagens = {
            R.drawable.exercising_woman,
            R.drawable.exercising_woman_2,
            R.drawable.man_running_2,
            R.drawable.man_running,
            R.drawable.food,
            R.drawable.foods
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initViews();
        setupListeners();

    }

    @Override
    protected void onDestroy() {
        bgLogin = null;
        System.gc();
        super.onDestroy();
    }

    private void initViews() {

        editNome = (EditText) this.findViewById(R.id.editNome);
        btnContinuar = (Button) this.findViewById(R.id.btnContinuar);

        random = (int) (Math.random() * 6);
        MySingleton.getBancoDeDados().getApp().setImageLogin(imagens[random]);

        ImageManager imageManager = new ImageManager();

        try {
            bgLogin = (ImageView) this.findViewById(R.id.bgLogin);
            Bitmap bitmap = imageManager.createBitmap(getResources(), imagens[random], bgLogin.getWidth(), bgLogin.getHeight());
            bgLogin.setImageBitmap(bitmap);
            bitmap.recycle();
            bitmap = null;
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

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

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editNome.getText().toString() != "" &&
                        editNome.getText().toString() != null &&
                        editNome.getText().toString() != "nome completo") {

                    nomeUsuario = editNome.getText().toString();
                    MySingleton.getBancoDeDados().getApp().setNomeUsuario(nomeUsuario);
                    MySingleton.getBancoDeDados().getApp().setImageLogin(imagens[random]);
                    bgLogin = null;
                    startActivity(new Intent(WelcomeActivity.this, CreateAccountActivity.class));
                    finish();

                } else {
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getApplicationContext(), "Insira um nome válido", duration);
                    toast.show();
                }
            }
        });

    }


}
