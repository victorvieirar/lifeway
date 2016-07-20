package com.victorvieira.lifeway.apresentacao.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.dominio.Alimento;
import com.victorvieira.lifeway.apresentacao.extras.AdapterGroup;
import com.victorvieira.lifeway.apresentacao.extras.ListaAlimentosAdapter;

import java.util.Date;
import java.util.List;

public class MyDialogFragment extends DialogFragment {

    private ListView lvAlimentos;
    private List<Alimento> listAlimentos;

    private TextView txtTipoRefeicao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment, container, false);



        txtTipoRefeicao = (TextView) view.findViewById(R.id.txtTipoRefeicao);

        setupViews();

        final AdapterGroup adapterGroup = new AdapterGroup();

        lvAlimentos = (ListView) view.findViewById(R.id.listAlimentos);
        adapterGroup.addAdapter(new ListaAlimentosAdapter(getContext(), listAlimentos, null, 'a', 'b'));
        lvAlimentos.setAdapter(adapterGroup.getAdapter(0));

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private void setupViews() {

        Date tempoAtual = new Date();

        int horaAtual = Integer.parseInt(tempoAtual.toString().substring(11,13));
        if((horaAtual >= 6) && (horaAtual <= 11)) {
            listAlimentos = MySingleton.getBancoDeDados().getApp().getAlimentosByType('c');
            txtTipoRefeicao.setText("Café da Manhã");
        } else {
            if((horaAtual > 11) && (horaAtual <= 17)) {
                listAlimentos = MySingleton.getBancoDeDados().getApp().getAlimentosByType('a');
                txtTipoRefeicao.setText("Almoço");
            } else {
                listAlimentos = MySingleton.getBancoDeDados().getApp().getAlimentosByType('j');
                txtTipoRefeicao.setText("Jantar");
            }
        }

    }
}
