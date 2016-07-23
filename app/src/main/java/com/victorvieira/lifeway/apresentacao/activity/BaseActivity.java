package com.victorvieira.lifeway.apresentacao.activity;

import android.support.v7.app.AppCompatActivity;

import com.victorvieira.lifeway.R;

public class BaseActivity extends AppCompatActivity {



    protected void enterFromBottomAnimation(){
        overridePendingTransition(R.anim.activity_open_translate_from_bottom, R.anim.activity_no_animation);
    }

    protected void exitToBottomAnimation(){
        overridePendingTransition(R.anim.activity_no_animation, R.anim.activity_close_translate_to_bottom);
    }

}
