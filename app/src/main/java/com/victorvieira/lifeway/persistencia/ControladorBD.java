package com.victorvieira.lifeway.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.victorvieira.lifeway.App;
import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.dominio.Alimento;
import com.victorvieira.lifeway.dominio.Consumo;
import com.victorvieira.lifeway.dominio.Refeicao;
import com.victorvieira.lifeway.dominio.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ControladorBD {

    private SQLiteDatabase db;

    public ControladorBD(Context context) {
        super();
        SQLiteDB auxDB = new SQLiteDB(context);
        db = auxDB.getWritableDatabase();

        adicionarAlimentos();
    }


    /** Usuário */
    /** Inserir os dados em usuário */
    public void inserirUsuario(Usuario usuario){
        ContentValues valores = new ContentValues();
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_NOME, usuario.getNome());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_PESO, usuario.getPeso());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_ALTURA, usuario.getAltura());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_META_DE_PESO, usuario.getMetaDePeso());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_IMC, usuario.getImc());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_DATA_NASCIMENTO, usuario.getDataNascimento().toString());
        db.insert(FeedReaderContract.FeedEntry.USER_TABLE_NAME, null, valores);
    }
    /** Atualizar os dados em usuário */
    public void atualizarUsuario(Usuario usuario){
        ContentValues valores = new ContentValues();
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_NOME, usuario.getNome());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_PESO, usuario.getPeso());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_ALTURA, usuario.getAltura());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_META_DE_PESO, usuario.getMetaDePeso());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_IMC, usuario.getImc());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_DATA_NASCIMENTO, usuario.getDataNascimento().toString());
        db.update(FeedReaderContract.FeedEntry.USER_TABLE_NAME, valores,
                FeedReaderContract.FeedEntry.USER_COLUMN_ID + " = ?", new String[] { ""+usuario.getId() });
    }
    /** Deletar usuário */
    public void deletarUsuario(Usuario usuario){
        db.delete(FeedReaderContract.FeedEntry.USER_TABLE_NAME,
                FeedReaderContract.FeedEntry.USER_COLUMN_ID + " = ?",
                new String[]{""+usuario.getId()});
    }
    /** Buscar em usuário*/
    public List<Usuario> buscarUsuario(){
        List<Usuario> list = new ArrayList<Usuario>();
        String[] colunas = new String[] {
                FeedReaderContract.FeedEntry.USER_COLUMN_ID,
                FeedReaderContract.FeedEntry.USER_COLUMN_NOME,
                FeedReaderContract.FeedEntry.USER_COLUMN_PESO,
                FeedReaderContract.FeedEntry.USER_COLUMN_ALTURA,
                FeedReaderContract.FeedEntry.USER_COLUMN_META_DE_PESO,
                FeedReaderContract.FeedEntry.USER_COLUMN_IMC,
                FeedReaderContract.FeedEntry.USER_COLUMN_DATA_NASCIMENTO
        };

        Cursor cursor = db.query("Usuario", colunas, null, null, null, null, "_id ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                Usuario u = new Usuario();
                u.setId((int) cursor.getLong(0));
                u.setNome(cursor.getString(1));
                u.setPeso(cursor.getDouble(2));
                u.setAltura(cursor.getDouble(3));
                u.setMetaDePeso(cursor.getDouble(4));
                u.setImc(cursor.getDouble(5));
                u.setDataNascimento(cursor.getString(6)); //Recebe dd/mm/aaaa
                list.add(u);

            } while(cursor.moveToNext());

        }

        return(list);
    }
    /** EXTRAS */
    public Usuario getUsuario() {
        Usuario usuario = new Usuario();
        try {
            usuario = buscarUsuario().get(0);
        } catch(Exception e) {}

        return usuario;
    }
    public boolean hasUser() {
        if(buscarUsuario().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /** Consumo */
    /** Inserir os dados em consumo */
    public void inserirConsumo(Refeicao refeicao){
        ContentValues valores = new ContentValues();
        valores.put(FeedReaderContract.FeedEntry.CONSUMO_COLUMN_ID, 1);
        valores.put(FeedReaderContract.FeedEntry.CONSUMO_COLUMN_ID_REFEICAO, refeicao.getId());
        db.insert(FeedReaderContract.FeedEntry.CONSUMO_TABLE_NAME, null, valores);
    }
    /** Atualizar os dados em consumo */
    public void atualizarConsumo(Refeicao refeicao){
        ContentValues valores = new ContentValues();
        valores.put(FeedReaderContract.FeedEntry.CONSUMO_COLUMN_ID, "1");
        valores.put(FeedReaderContract.FeedEntry.CONSUMO_COLUMN_ID_REFEICAO, refeicao.getId());
        db.update(FeedReaderContract.FeedEntry.CONSUMO_TABLE_NAME, valores,
                FeedReaderContract.FeedEntry.CONSUMO_COLUMN_ID_REFEICAO + " = ?",
                new String[]{""+refeicao.getId()});
    }
    /** Deletar consumo */
    public void deletarConsumo(Refeicao refeicao){
        db.delete(FeedReaderContract.FeedEntry.CONSUMO_TABLE_NAME, FeedReaderContract.FeedEntry.CONSUMO_COLUMN_ID_REFEICAO + " = ?",
                new String[] { ""+refeicao.getId() });
    }
    /** Buscar em consumo*/
    public List<Refeicao> buscarConsumo(){
        List<Refeicao> list = new ArrayList<Refeicao>();
        String[] colunas = new String[] {
                FeedReaderContract.FeedEntry.CONSUMO_COLUMN_ID,
                FeedReaderContract.FeedEntry.CONSUMO_COLUMN_ID_REFEICAO
        };

        Cursor cursor = db.query(FeedReaderContract.FeedEntry.CONSUMO_TABLE_NAME, colunas, null, null, null, null,
                FeedReaderContract.FeedEntry.CONSUMO_COLUMN_ID+" ASC");

        if(cursor.getCount() > 0){

            cursor.moveToFirst();

            do {
                Refeicao r = new Refeicao();
                r.setId((int) cursor.getLong(1));
                r = buscarRefeicao(r);
                list.add(r);

            } while(cursor.moveToNext());

        }
        return(list);
    }
    /** EXTRAS */
    public Consumo getConsumo() {
        int count = 0;
        Consumo consumo = new Consumo();
        for(Refeicao r : buscarConsumo()) {
            consumo.addRefeicao(r);
        }
        return consumo;
    }
    public boolean hasConsumo() {
        try {
            buscarConsumo().get(0);
            return true;
        } catch(Exception e) {
            return false;
        }
    }


    /** Refeição */
    /** Inserir os dados em refeicao */
    public void inserirRefeicao(int id, char type, Alimento alimento, Date horario){
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(horario);

        String sHorario = "" + gc.get(gc.DAY_OF_MONTH) + "/" + gc.get(gc.MONTH)+1 + "/" + gc.get(gc.YEAR);

        ContentValues valores = new ContentValues();
        valores.put(FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID, id);
        valores.put(FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID_ALIMENTO, alimento.getId());
        valores.put(FeedReaderContract.FeedEntry.REFEICAO_COLUMN_HORARIO, sHorario);
        valores.put(FeedReaderContract.FeedEntry.REFEICAO_COLUMN_TIPO, Character.toString(type));
        db.insert(FeedReaderContract.FeedEntry.REFEICAO_TABLE_NAME, null, valores);

        Refeicao r = new Refeicao(type, id);
        r.addAlimento(alimento, horario);
        inserirConsumo(r);
    }
    /** Atualizar os dados em refeicao */
    public void atualizarRefeicao(Refeicao refeicao, Alimento alimento){
        ContentValues valores = new ContentValues();
        valores.put(FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID, refeicao.getId());
        valores.put(FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID_ALIMENTO, alimento.getId());
        db.update(FeedReaderContract.FeedEntry.REFEICAO_TABLE_NAME, valores,
                FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID + " = ?", new String[]{"" + refeicao.getId()});
    }
    /** Deletar refeicao */
    public void deletarRefeicao(Alimento alimento){
        db.delete(FeedReaderContract.FeedEntry.REFEICAO_TABLE_NAME,
                FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID_ALIMENTO + " = ?",
                new String[] { ""+alimento.getId() });
    }
    /** Buscar em refeicao*/
    public List<Alimento> buscarAlimentosDaRefeicao(Refeicao refeicao) {
        List<Alimento> list = new ArrayList<Alimento>();
        String[] colunas = new String[]{
                FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID,
                FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID_ALIMENTO
        };

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.REFEICAO_TABLE_NAME,
                colunas,
                FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID + " = ?",
                new String[] { ""+refeicao.getId() },
                null,
                null,
                FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID_ALIMENTO + " ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do {

                Alimento a = new Alimento();
                a.setId((int) cursor.getLong(1));
                a = buscarAlimento(a);
                list.add(a);

            } while(cursor.moveToNext());

        }
        return(list);
    }
    /** Buscar refeição específica */
    public Refeicao buscarRefeicao(Refeicao r) {
        Refeicao result = new Refeicao();

        String[] colunas = new String[]{
                FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID,
                FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID_ALIMENTO,
                FeedReaderContract.FeedEntry.REFEICAO_COLUMN_HORARIO
        };

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.REFEICAO_TABLE_NAME,
                colunas,
                FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID + " = ?",
                new String[] { ""+r.getId() },
                null,
                null,
                FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID + " ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do {

                result.setId((int) cursor.getLong(0));
                for(Alimento a : buscarAlimentosDaRefeicao(result)) {
                    String sHorario = cursor.getString(2);
                    int dia = Integer.parseInt(sHorario.substring(0,2));
                    int mes = Integer.parseInt(sHorario.substring(3,5));
                    int ano = Integer.parseInt(sHorario.substring(6));

                    result.addAlimento(a, new GregorianCalendar(ano, mes, dia).getTime());
                }

            } while(cursor.moveToNext());

        }

        return result;
    }
    /** Buscar todas as refeições */
    public List<Refeicao> buscarRefeicao() {
        List<Refeicao> list = new ArrayList<Refeicao>();
        String[] colunas = new String[]{
                FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID,
                FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID_ALIMENTO,
                FeedReaderContract.FeedEntry.REFEICAO_COLUMN_HORARIO,
                FeedReaderContract.FeedEntry.REFEICAO_COLUMN_TIPO
        };

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.REFEICAO_TABLE_NAME,
                colunas,
                null,
                null,
                null,
                null,
                FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID + " ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                Refeicao r = new Refeicao();
                r.setId((int) cursor.getLong(0));
                r.setTipo(cursor.getString(3).charAt(0));
                for(Alimento a : buscarAlimentosDaRefeicao(r)) {
                    String sHorario = cursor.getString(2);
                    int dia = Integer.parseInt(sHorario.substring(0,2));
                    int mes = Integer.parseInt(sHorario.substring(3,5));
                    int ano = Integer.parseInt(sHorario.substring(6));

                    r.addAlimento(a, new GregorianCalendar(ano, mes, dia).getTime());
                }
                list.add(r);
            } while(cursor.moveToNext());

        }
        return(list);
    }
    /** EXTRAS */
    public int getRefeicaoId(Date horario, char type) {
        if(mesmoDia(horario)) {
            return refeicaoReferente(type);
        } else {
            return (1 + getLastRefeicaoId());
        }
    }
    public int getLastRefeicaoId() {
        int id = 1;
        List<Refeicao> list = buscarRefeicao();

        try {
            id = list.get(list.size() - 1).getId();
        } catch(Exception e) {
            return 1;
        }

        return id;
    }
    public int refeicaoReferente(char type) {
        for(Refeicao r : buscarRefeicao()) {
            if(r.getTipo() == type) {
                return r.getId();
            }
        }
        return 1 + getLastRefeicaoId();
    }
    public boolean mesmoDia(Date horario) {
        GregorianCalendar gcA = new GregorianCalendar();
        gcA.setTime(horario);
        int diaA = gcA.get(gcA.DAY_OF_MONTH);
        int mesA = gcA.get(gcA.MONTH);
        int anoA = gcA.get(gcA.YEAR);

        Refeicao r = new Refeicao();
        r.setId(getLastRefeicaoId());

        Date horarioU;

        try {
            horarioU = buscarRefeicao(r).getLastHorario();
        } catch(Exception e) {
            return true;
        }

        GregorianCalendar gcU = new GregorianCalendar();
        gcU.setTime(horarioU);

        int dia = gcU.get(gcU.DAY_OF_MONTH);
        int mes = gcU.get(gcU.MONTH);
        int ano = gcU.get(gcU.YEAR);

        if(diaA == dia && mesA == mes && anoA == ano) {
            return true;
        } else {
            return false;
        }

    }


    /** Alimento */
    /** Inserir os dados em Alimento */
    public void inserirAlimento(Alimento alimento){
        ContentValues valores = new ContentValues();
        valores.put(FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_NOME, alimento.getNome());
        valores.put(FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_TIPO, Character.toString(alimento.getTipo()));
        valores.put(FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_INDICACAO, alimento.getIndicacao());
        valores.put(FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_VALOR_CALORICO, alimento.getValor_calorico());
        db.insert(FeedReaderContract.FeedEntry.ALIMENTO_TABLE_NAME, null, valores);
    }
    /** Atualizar os dados em alimentos */
    public void atualizarAlimento(Alimento alimento){
        ContentValues valores = new ContentValues();
        valores.put(FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_NOME, alimento.getNome());
        valores.put(FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_TIPO, Character.toString(alimento.getTipo()));
        valores.put(FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_INDICACAO, alimento.getIndicacao());
        valores.put(FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_VALOR_CALORICO, alimento.getValor_calorico());
        db.update(FeedReaderContract.FeedEntry.ALIMENTO_TABLE_NAME, valores,
                FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_ID + " = ?", new String[] { ""+alimento.getId() } );
    }
    /** Deletar alimentos */
    public void deletarAlimento(Alimento alimento){
        db.delete(FeedReaderContract.FeedEntry.ALIMENTO_TABLE_NAME,
                FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_ID + " = ?", new String[] { ""+alimento.getId() } );
    }
    /** Buscar em alimento */
    public Alimento buscarAlimento(Alimento alimento) {
        Alimento result = new Alimento();
        String[] colunas = new String[] {
                FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_ID,
                FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_NOME,
                FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_TIPO,
                FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_VALOR_CALORICO,
                FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_INDICACAO
        };

        Cursor cursor = db.query(FeedReaderContract.FeedEntry.ALIMENTO_TABLE_NAME, colunas,
                FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_ID + " = ?",
                new String[] { ""+alimento.getId() },
                null,
                null,
                FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_ID + " ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do {
                Alimento a = new Alimento();
                a.setId((int) cursor.getLong(0));
                a.setNome(cursor.getString(1));
                a.setTipo(cursor.getString(2).charAt(0));
                a.setValor_calorico(cursor.getDouble(3));
                a.setIndicacao((int) cursor.getLong(4));
                result = a;

            } while(cursor.moveToNext());

        }
        return(result);
    }
    /** Buscar todos os alimentos */
    public List<Alimento> buscarAlimento(){
        List<Alimento> list = new ArrayList<Alimento>();
        String[] colunas = new String[] {
                FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_ID,
                FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_NOME,
                FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_TIPO,
                FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_VALOR_CALORICO,
                FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_INDICACAO
        };

        Cursor cursor = db.query(FeedReaderContract.FeedEntry.ALIMENTO_TABLE_NAME, colunas, null, null, null, null,
                FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_ID + " ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do {
                Alimento a = new Alimento();
                a.setId((int) cursor.getLong(0));
                a.setNome(cursor.getString(1));
                a.setTipo(cursor.getString(2).charAt(0));
                a.setValor_calorico(cursor.getDouble(3));
                a.setIndicacao((int) cursor.getLong(4));
                list.add(a);

            } while(cursor.moveToNext());

        }
        return(list);
    }
    /** EXTRAS */
    public void adicionarAlimentos() {
        for(Alimento a : MySingleton.getApp().getAlimentosInRefeicoesDisponiveis()) {
            inserirAlimento(a);
        }
    }


}



