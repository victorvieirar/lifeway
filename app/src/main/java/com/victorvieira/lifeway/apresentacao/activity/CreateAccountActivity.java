package com.victorvieira.lifeway.apresentacao.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
import com.victorvieira.lifeway.apresentacao.extras.ImageManager;
import com.victorvieira.lifeway.dominio.Usuario;
import com.victorvieira.lifeway.persistencia.ControladorBD;

import java.util.GregorianCalendar;

public class CreateAccountActivity extends BaseActivity {

    private ControladorBD bd;

    int diaNasc = 1;
    int mesNasc = 0;
    int anoNasc = new GregorianCalendar().getInstance().get(GregorianCalendar.YEAR);

    private boolean keyboardIsHide = true;

    private int bgWidth;
    private int bgHeight;

    private ImageView bgCreateAccount;
    private TextView txtSaudacaoInicial;

    private EditText editMetaDePeso;
    private EditText editPeso;
    private EditText editAltura;
    private TextView txtDataNascimento;

    private DatePicker dpDataNascimento;
    private RelativeLayout rlDatePicker;
    private RelativeLayout rlTextDataNascimento;

    private Button btnMasculino;
    private Button btnFeminino;

    private Button btnOkDatePicker;
    private Button btnCancelDatePicker;
    private Button btnContinuar;

    private View.OnClickListener onClickListener;

    @Override
    protected void onDestroy() {
        bgCreateAccount = null;
        System.gc();
        super.onDestroy();
    }

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

        bd = new ControladorBD(this);

        setContentView(R.layout.activity_create_account);
        initViews();
        setupListeners();
    }

    private void initViews() {

        MySingleton.getApp().setSexo('m');

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        bgWidth = metrics.widthPixels;
        bgHeight = metrics.heightPixels;

        editMetaDePeso = (EditText) findViewById(R.id.editMetaDePeso);
        editPeso = (EditText) findViewById(R.id.editPeso);
        editAltura = (EditText) findViewById(R.id.editAltura);
        txtDataNascimento = (TextView) findViewById(R.id.editDataNascimento);
        btnContinuar = (Button) findViewById(R.id.btnCadatrar);
        btnCancelDatePicker = (Button) findViewById(R.id.btnCancelDatePicker);
        btnOkDatePicker = (Button) findViewById(R.id.btnOkDatePicker);

        btnMasculino = (Button) findViewById(R.id.btnMasculino);
        btnFeminino = (Button) findViewById(R.id.btnFeminino);

        txtSaudacaoInicial = (TextView) findViewById(R.id.txtSaudacaoInicial);
        txtSaudacaoInicial.setText("Olá, " + MySingleton.getApp().getNomeUsuario().split(" ")[0]);


        ImageManager imageManager = new ImageManager();

        try {
            bgCreateAccount = (ImageView) findViewById(R.id.bgCreateAccount);

            Bitmap bitmap = imageManager.createBitmap(getResources(), MySingleton.getApp().getImageLogin(), bgWidth, bgHeight);
            bgCreateAccount.setImageBitmap(bitmap);
            bitmap = null;
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

        txtDataNascimento.setText(diaNasc + " de " + MySingleton.getApp().getStringOfMonthByIndex(1 + mesNasc) + " de " + anoNasc);
        txtDataNascimento.setClickable(true);
        rlTextDataNascimento = (RelativeLayout) findViewById(R.id.rlTextDataNascimento);

        dpDataNascimento = (DatePicker) findViewById(R.id.dpDataNascimento);
        GregorianCalendar gcMinDate = new GregorianCalendar(1900, 0, 1);
        dpDataNascimento.setMinDate(gcMinDate.getTimeInMillis());
        dpDataNascimento.updateDate(anoNasc, mesNasc, diaNasc);

        rlDatePicker = (RelativeLayout) findViewById(R.id.rlDatePicker);
    }

    private void setupListeners() {

        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.btnMasculino:
                        btnMasculino.setBackground(getResources().getDrawable(R.drawable.bg_btn_sexo_selected_create_account));
                        btnMasculino.setTextColor(getResources().getColor(R.color.black));

                        btnFeminino.setBackground(getResources().getDrawable(R.drawable.bg_btn_sexo_create_account));
                        btnFeminino.setTextColor(getResources().getColor(R.color.white));

                        MySingleton.getApp().setSexo('m');
                        break;

                    case R.id.btnFeminino:
                        btnFeminino.setBackground(getResources().getDrawable(R.drawable.bg_btn_sexo_selected_create_account));
                        btnFeminino.setTextColor(getResources().getColor(R.color.black));

                        btnMasculino.setBackground(getResources().getDrawable(R.drawable.bg_btn_sexo_create_account));
                        btnMasculino.setTextColor(getResources().getColor(R.color.white));

                        MySingleton.getApp().setSexo('f');
                        break;
                }
            }
        };

        btnMasculino.setOnClickListener(onClickListener);
        btnFeminino.setOnClickListener(onClickListener);

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
                mesNasc = dpDataNascimento.getMonth();
                anoNasc = dpDataNascimento.getYear();

                GregorianCalendar gcDataNascimento = new GregorianCalendar(anoNasc, mesNasc, diaNasc);

                String sData = diaNasc + " de " + MySingleton.getApp().getStringOfMonthByIndex(1+mesNasc) + " de " + anoNasc;
                txtDataNascimento.setText(sData);

                rlDatePicker.setVisibility(View.GONE);
            }
        });

        btnCancelDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlDatePicker.setVisibility(View.GONE);
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
                                        1, //default
                                        MySingleton.getApp().getNomeUsuario(),
                                        MySingleton.getApp().getSexo(),
                                        MySingleton.getApp().getDataNascimento(),
                                        MySingleton.getApp().getPeso(),
                                        MySingleton.getApp().getAltura(),
                                        MySingleton.getApp().getMetaDePeso(),
                                        2000, /** not setup */
                                        2000  /** not setup */
                                );

                                bd.inserirUsuario(usuario);
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
                MySingleton.getApp().setPeso(peso);
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
                MySingleton.getApp().setAltura(altura);
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
            MySingleton.getApp().setDataNascimento(gcDataNascimento.getTime());
            return true;
        } catch(Exception e) {
            return false;
        }

    }
    private boolean stepFour() {
        try {
            if(Double.parseDouble(editMetaDePeso.getText().toString()) != 0) {
                double metaDePeso = Double.parseDouble(editMetaDePeso.getText().toString());
                MySingleton.getApp().setMetaDePeso(metaDePeso);
                return true;
            } else {
                return false;
            }
        } catch(Exception e) {
            return false;
        }
    }

}
