package com.victorvieira.lifeway.apresentacao.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.apresentacao.extras.ImageManager;
import com.victorvieira.lifeway.dominio.Agua;
import com.victorvieira.lifeway.dominio.Exercicio;
import com.victorvieira.lifeway.persistencia.ControladorBD;

import java.util.Date;

public class DialogExercise extends Dialog {

    private ControladorBD bd;

    private int bgWidth;
    private int bgHeight;

    private int countTime;

    private ImageView bgDialog;
    private ImageButton plusTime;
    private ImageButton reduceTime;
    private TextView txtTime;

    private Button btnSalvar;
    private Button btnCancelar;

    private View.OnClickListener onClick;

    public DialogExercise(Context context) {
        super(context);
    }

    public DialogExercise(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogExercise(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_exercises);

        bd = new ControladorBD(getContext());

        initViews();
        setupListeners();
    }

    private void initViews() {
        bgDialog = (ImageView) findViewById(R.id.bgExercises);
        plusTime = (ImageButton) findViewById(R.id.plusTime);
        reduceTime = (ImageButton) findViewById(R.id.reduceTime);
        txtTime = (TextView) findViewById(R.id.txtTempoExercicio);

        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);

        countTime = 10;
        txtTime.setText(countTime+"");

        DisplayMetrics metrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        bgWidth = metrics.widthPixels;
        bgHeight = (int) (75 * Resources.getSystem().getDisplayMetrics().density);

        ImageManager imageManager = new ImageManager();

        try {
            Bitmap bitmap = imageManager.createBitmap(getContext().getResources(), R.drawable.persist,
                    bgWidth, bgHeight);

            bgDialog.setImageBitmap(bitmap);
            bitmap = null;
            System.gc();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupListeners() {
        onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.plusTime:
                        if(countTime < 60) {
                            countTime += 5;
                            txtTime.setText(countTime + "");
                            reduceTime.setEnabled(true);
                            if (countTime == 60) {
                                plusTime.setEnabled(false);
                            }
                        }
                        break;
                    case R.id.reduceTime:
                        if(countTime > 0) {
                            countTime -= 5;
                            txtTime.setText(countTime + "");
                            plusTime.setEnabled(true);
                            if(countTime == 0) {
                                reduceTime.setEnabled(false);
                            }
                        }
                        break;
                }
            }
        };

        plusTime.setOnClickListener(onClick);
        reduceTime.setOnClickListener(onClick);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bd.isExercicioUpToDate(new Date())) {
                    Exercicio e = bd.getLastExercicio();
                    e.addTempo(countTime);
                    bd.atualizarExercicio(e);

                    dismiss();
                } else {
                    Exercicio e = new Exercicio();
                    e.setTempo(countTime);
                    e.setData(new Date());
                    bd.inserirExercicio(e);

                    dismiss();
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }


}
