package com.victorvieira.lifeway.apresentacao.activity;

import android.content.Intent;
import android.content.res.Resources;
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
import com.victorvieira.lifeway.persistencia.ControladorBD;

import java.io.IOException;

public class WelcomeActivity extends AppCompatActivity {

    private int bgWidth;
    private int bgHeight;

    private ControladorBD bd;
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

        bd = new ControladorBD(this);

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

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        bgWidth = metrics.widthPixels;
        bgHeight = metrics.heightPixels;

        editNome = (EditText) this.findViewById(R.id.editNome);
        btnContinuar = (Button) this.findViewById(R.id.btnContinuar);

        random = (int) (Math.random() * 6);
        MySingleton.getApp().setImageLogin(imagens[random]);

        ImageManager imageManager = new ImageManager();

        try {
            bgLogin = (ImageView) this.findViewById(R.id.bgLogin);
            Bitmap bitmap = imageManager.createBitmap(getResources(), imagens[random], bgWidth, bgHeight);
            bgLogin.setImageBitmap(bitmap);
            bitmap = null;
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setupListeners() {


        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editNome.getText().toString() != "" &&
                        editNome.getText().toString() != null &&
                        editNome.getText().toString() != "nome completo") {

                    nomeUsuario = editNome.getText().toString();
                    MySingleton.getApp().setNomeUsuario(nomeUsuario);
                    MySingleton.getApp().setImageLogin(imagens[random]);
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
