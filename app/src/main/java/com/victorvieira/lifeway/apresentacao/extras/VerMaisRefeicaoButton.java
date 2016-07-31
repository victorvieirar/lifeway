package com.victorvieira.lifeway.apresentacao.extras;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class VerMaisRefeicaoButton extends Button {

    private int id_refeicao;

    public VerMaisRefeicaoButton(Context context) {
        super(context);
    }
    public VerMaisRefeicaoButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public VerMaisRefeicaoButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getId_refeicao() { return id_refeicao; }
    public void setId_refeicao(int id_refeicao) { this.id_refeicao = id_refeicao; }

}
