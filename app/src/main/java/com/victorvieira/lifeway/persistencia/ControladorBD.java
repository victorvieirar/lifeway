package com.victorvieira.lifeway.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.victorvieira.lifeway.App;
import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.dominio.Agua;
import com.victorvieira.lifeway.dominio.Alimento;
import com.victorvieira.lifeway.dominio.Consumo;
import com.victorvieira.lifeway.dominio.Exercicio;
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

        if(!(hasAlimentos()))  adicionarAlimentos();
    }


    /** Usuário */
    /** Inserir os dados em usuário */
    public void inserirUsuario(Usuario usuario){
        ContentValues valores = new ContentValues();
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_ID, usuario.getId());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_NOME, usuario.getNome());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_SEXO, ""+usuario.getSexo());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_PESO, usuario.getPeso());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_ALTURA, usuario.getAltura());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_META_DE_PESO, usuario.getMetaDePeso());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_IMC, usuario.getImc());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_DATA_NASCIMENTO, usuario.getDataNascimentoBD());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_KCAL_DIARIA, usuario.getKcal_diaria());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_AGUA_DIARIA, usuario.getAgua_diaria());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_CONSUMO, 1);
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_CONSUMO_AGUA, 1);
        db.insert(FeedReaderContract.FeedEntry.USER_TABLE_NAME, null, valores);
    }
    /** Atualizar os dados em usuário */
    public void atualizarUsuario(Usuario usuario){
        ContentValues valores = new ContentValues();
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_ID, usuario.getId());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_NOME, usuario.getNome());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_SEXO, ""+usuario.getSexo());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_PESO, usuario.getPeso());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_ALTURA, usuario.getAltura());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_META_DE_PESO, usuario.getMetaDePeso());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_IMC, usuario.getImc());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_DATA_NASCIMENTO, usuario.getDataNascimentoBD());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_KCAL_DIARIA, usuario.getKcal_diaria());
        valores.put(FeedReaderContract.FeedEntry.USER_COLUMN_AGUA_DIARIA, usuario.getAgua_diaria());
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
                FeedReaderContract.FeedEntry.USER_COLUMN_SEXO,
                FeedReaderContract.FeedEntry.USER_COLUMN_PESO,
                FeedReaderContract.FeedEntry.USER_COLUMN_ALTURA,
                FeedReaderContract.FeedEntry.USER_COLUMN_META_DE_PESO,
                FeedReaderContract.FeedEntry.USER_COLUMN_IMC,
                FeedReaderContract.FeedEntry.USER_COLUMN_DATA_NASCIMENTO,
                FeedReaderContract.FeedEntry.USER_COLUMN_KCAL_DIARIA,
                FeedReaderContract.FeedEntry.USER_COLUMN_AGUA_DIARIA,
                FeedReaderContract.FeedEntry.USER_COLUMN_CONSUMO,
                FeedReaderContract.FeedEntry.USER_COLUMN_CONSUMO_AGUA
        };

        Cursor cursor = db.query(FeedReaderContract.FeedEntry.USER_TABLE_NAME, colunas, null, null, null, null, "_id ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                Usuario u = new Usuario();
                u.setId((int) cursor.getLong(0));
                u.setNome(cursor.getString(1));
                u.setSexo(cursor.getString(2).charAt(0));
                u.setPeso(cursor.getDouble(3));
                u.setAltura(cursor.getDouble(4));
                u.setMetaDePeso(cursor.getDouble(5));
                u.setImc(cursor.getDouble(6));
                u.setDataNascimentoBD(cursor.getString(7)); //Recebe tempo em ms || gc.getTimeInMillis()
                u.setKcal_diaria((int) cursor.getLong(8));
                u.setAgua_diaria((int) cursor.getLong(9));
                u.setConsumo(getConsumo());
                u.setConsumo_agua(buscarAgua());
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
                FeedReaderContract.FeedEntry.CONSUMO_COLUMN_ID + " ASC");

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
        List<Refeicao> refeicoes = buscarConsumo();
        for(Refeicao r : refeicoes) {
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

        if(!(hasAlimentoAdded(alimento, type, id))) {

            ContentValues valores = new ContentValues();
            valores.put(FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID, id);
            valores.put(FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID_ALIMENTO, alimento.getId());
            valores.put(FeedReaderContract.FeedEntry.REFEICAO_COLUMN_HORARIO, ""+horario.getTime());
            valores.put(FeedReaderContract.FeedEntry.REFEICAO_COLUMN_TIPO, Character.toString(type));
            valores.put(FeedReaderContract.FeedEntry.REFEICAO_COLUMN_PORCAO, ""+alimento.getPorcoes());
            db.insert(FeedReaderContract.FeedEntry.REFEICAO_TABLE_NAME, null, valores);

            Refeicao r = new Refeicao(type, id);
            r.addAlimento(alimento, horario);
            if(!(refeicaoAdded(r))) {
                inserirConsumo(r);
            }

        } else {

            alimento.setPorcoes(1 + alimento.getPorcoes());
            atualizarRefeicao(new Refeicao(type, id), alimento);

        }


    }
    /** Atualizar os dados em refeicao */
    public void atualizarRefeicao(Refeicao refeicao, Alimento alimento){
        ContentValues valores = new ContentValues();
        valores.put(FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID, refeicao.getId());
        valores.put(FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID_ALIMENTO, alimento.getId());
        valores.put(FeedReaderContract.FeedEntry.REFEICAO_COLUMN_TIPO, Character.toString(alimento.getTipo()));
        valores.put(FeedReaderContract.FeedEntry.REFEICAO_COLUMN_PORCAO, ""+alimento.getPorcoes());
        db.update(FeedReaderContract.FeedEntry.REFEICAO_TABLE_NAME, valores,
                FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID + " = ? AND " +
                FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID_ALIMENTO + " = ?", new String[]{"" + refeicao.getId(), "" + alimento.getId()});
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
                FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID_ALIMENTO,
                FeedReaderContract.FeedEntry.REFEICAO_COLUMN_PORCAO
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
                a.setPorcoes((int) cursor.getLong(2));
                list.add(a);

            } while(cursor.moveToNext());

        }
        return(list);
    }
    /** Buscar refeição específica */
    public Refeicao buscarRefeicao(Refeicao r) {

        String[] colunas = new String[]{
                FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID,
                FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID_ALIMENTO,
                FeedReaderContract.FeedEntry.REFEICAO_COLUMN_HORARIO,
                FeedReaderContract.FeedEntry.REFEICAO_COLUMN_PORCAO,
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

                Alimento a = new Alimento();
                a.setId((int) cursor.getLong(1));
                a = buscarAlimento(a);

                long time = Long.parseLong(cursor.getString(2));
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTimeInMillis(time);

                char type = '0';
                if(type == '0') {
                    type = a.getTipo();
                    r.setTipo(type);
                }

                r.addAlimento(a, gc.getTime());

            } while(cursor.moveToNext());

        }

        return r;
    }
    /** Buscar todas as refeições */
    public List<Refeicao> buscarRefeicao() {
        int ref_id;

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

            ref_id = 0;

            do {
                Refeicao r = new Refeicao();
                r.setId((int) cursor.getLong(0));

                if(!(ref_id == (int)(cursor.getLong(0)))) {
                    ref_id = (int) cursor.getLong(0);
                    r = buscarRefeicao(r);
                    list.add(r);
                }

            } while(cursor.moveToNext());

        }
        return(list);
    }
    /** EXTRAS */
    public int getRefeicaoId(Date horario, char type) {
        if(buscarRefeicao().size() == 0) {
            return 1;
        } else {
            if(mesmoDia(horario)) {
                return refeicaoReferente(type);
            } else {
                return getLastRefeicaoId()+1;
            }
        }
    }
    public int refeicaoReferente(char type) {
        List<Refeicao> refeicoes = buscarRefeicao();
        for(Refeicao r : refeicoes) {
            if(r.getTipo() == type) {
                return r.getId();
            }
        }
        return 1+getLastRefeicaoId();
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
    public boolean hasAlimentoAdded(Alimento alimento, char type, int id) {
        List<Alimento> alimentos = buscarAlimentosDaRefeicao(new Refeicao(type, id));
        for(Alimento a : alimentos) {
            if (a.getId() == alimento.getId()) {
                return true;
            }
        }
        return false;
    }
    public boolean refeicaoAdded(Refeicao refeicao) {
        List<Refeicao> refeicoes = buscarConsumo();
        for(Refeicao r : refeicoes) {
            if(refeicao.getId() == r.getId()) {
                return true;
            }
        }
        return false;
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
        ArrayList<Alimento> alimentos = MySingleton.getApp().getAlimentosInRefeicoesDisponiveis();
        for(Alimento a : alimentos) {
            inserirAlimento(a);
        }
    }
    public boolean hasAlimentos() {
        if(buscarAlimento().size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    /** AGUA */
    /** INSERIR AGUA */
    public void inserirAgua(Agua agua) {
        ContentValues valoresAgua = new ContentValues();
        valoresAgua.put(FeedReaderContract.FeedEntry.AGUA_COLUMN_QUANTIDADE, agua.getQuantidade());
        valoresAgua.put(FeedReaderContract.FeedEntry.AGUA_COLUMN_HORARIO, agua.getDataBD());
        db.insert(FeedReaderContract.FeedEntry.AGUA_TABLE_NAME, null, valoresAgua);

        Agua a = getLastAgua();

        ContentValues valoresConsumoAgua = new ContentValues();
        valoresConsumoAgua.put(FeedReaderContract.FeedEntry.CONSUMO_AGUA_COLUMN_ID, 1);
        valoresConsumoAgua.put(FeedReaderContract.FeedEntry.CONSUMO_AGUA_COLUMN_ID_AGUA, a.getId());
        db.insert(FeedReaderContract.FeedEntry.CONSUMO_AGUA_TABLE_NAME, null, valoresConsumoAgua);
    }
    /** ATUALIZAR AGUA */
    public void atualizarAgua(Agua agua) {
        ContentValues valores = new ContentValues();
        valores.put(FeedReaderContract.FeedEntry.AGUA_COLUMN_QUANTIDADE, agua.getQuantidade());
        valores.put(FeedReaderContract.FeedEntry.AGUA_COLUMN_HORARIO, agua.getDataBD());
        db.update(FeedReaderContract.FeedEntry.AGUA_TABLE_NAME, valores,
                FeedReaderContract.FeedEntry.AGUA_COLUMN_ID + " = ?", new String[] { ""+agua.getId() });
    }
    /** BUSCAR TODAS AS AGUAS */
    public ArrayList<Agua> buscarAgua() {
        ArrayList<Agua> list = new ArrayList<Agua>();
        String[] colunas = new String[] {
                FeedReaderContract.FeedEntry.AGUA_COLUMN_ID,
                FeedReaderContract.FeedEntry.AGUA_COLUMN_QUANTIDADE,
                FeedReaderContract.FeedEntry.AGUA_COLUMN_HORARIO
        };

        Cursor cursor = db.query(FeedReaderContract.FeedEntry.AGUA_TABLE_NAME, colunas, null, null, null, null, "_id ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                Agua a = new Agua();
                a.setId((int) cursor.getLong(0));
                a.setQuantidade(cursor.getDouble(1));
                a.setDataBD(cursor.getString(2));
                list.add(a);

            } while(cursor.moveToNext());

        }

        return(list);
    }

    /** EXTRAS */
    public Agua getLastAgua() {
        ArrayList<Agua> aguas = buscarAgua();
        return aguas.get(aguas.size()-1);
    }
    public boolean isAguaUpToDate(Date data) {
        GregorianCalendar gcN = new GregorianCalendar();
        gcN.setTime(data);

        Agua a;

        try {
            a = getLastAgua();
        } catch(Exception e) {
            return false;
        }

        GregorianCalendar gcA = new GregorianCalendar();
        gcA.setTime(a.getData());

        if(gcN.get(gcN.DAY_OF_MONTH) == gcA.get(gcA.DAY_OF_MONTH) &&
                gcN.get(gcN.MONTH) == gcA.get(gcA.MONTH) &&
                gcN.get(gcN.YEAR) == gcA.get(gcA.YEAR)) {

            return true;
        }

        return false;
    }

    /** EXERCÍCIO */
    /** INSERIR EXERCÍCIO */
    public void inserirExercicio(Exercicio exercicio) {
        ContentValues valoresExercicio = new ContentValues();
        valoresExercicio.put(FeedReaderContract.FeedEntry.EXERCICIO_COLUMN_TEMPO, exercicio.getTempo());
        valoresExercicio.put(FeedReaderContract.FeedEntry.EXERCICIO_COLUMN_HORARIO, exercicio.getDataBD());
        db.insert(FeedReaderContract.FeedEntry.EXERCICIO_TABLE_NAME, null, valoresExercicio);

        Exercicio e = getLastExercicio();

        ContentValues valoresPratica = new ContentValues();
        valoresPratica.put(FeedReaderContract.FeedEntry.PRATICA_EXERCICIOS_COLUMN_ID, 1);
        valoresPratica.put(FeedReaderContract.FeedEntry.PRATICA_EXERCICIOS_COLUMN_ID_EXERCICIO, e.getId());
        db.insert(FeedReaderContract.FeedEntry.PRATICA_EXERCICIOS_TABLE_NAME, null, valoresPratica);
    }
    /** ATUALIZAR EXERCICIO */
    public void atualizarExercicio(Exercicio exercicio) {
        ContentValues valores = new ContentValues();
        valores.put(FeedReaderContract.FeedEntry.EXERCICIO_COLUMN_TEMPO, exercicio.getTempo());
        valores.put(FeedReaderContract.FeedEntry.EXERCICIO_COLUMN_HORARIO, exercicio.getDataBD());
        db.update(FeedReaderContract.FeedEntry.EXERCICIO_TABLE_NAME, valores,
                FeedReaderContract.FeedEntry.EXERCICIO_COLUMN_ID + " = ?", new String[] { ""+exercicio.getId() });
    }
    /** BUSCAR TODOS OS EXERCÍCIOS */
    public ArrayList<Exercicio> buscarExercicios() {
        ArrayList<Exercicio> list = new ArrayList<Exercicio>();
        String[] colunas = new String[] {
                FeedReaderContract.FeedEntry.EXERCICIO_COLUMN_ID,
                FeedReaderContract.FeedEntry.EXERCICIO_COLUMN_TEMPO,
                FeedReaderContract.FeedEntry.EXERCICIO_COLUMN_HORARIO
        };

        Cursor cursor = db.query(FeedReaderContract.FeedEntry.EXERCICIO_TABLE_NAME, colunas, null, null, null, null, "_id ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                Exercicio e = new Exercicio();
                e.setId((int) cursor.getLong(0));
                e.setTempo(cursor.getDouble(1));
                e.setDataBD(cursor.getString(2));
                list.add(e);

            } while(cursor.moveToNext());

        }

        return(list);
    }

    /** EXTRAS */
    public Exercicio getLastExercicio() {
        ArrayList<Exercicio> exercicios = buscarExercicios();
        return exercicios.get(exercicios.size()-1);
    }
    public boolean isExercicioUpToDate(Date data) {
        GregorianCalendar gcN = new GregorianCalendar();
        gcN.setTime(data);

        Exercicio e;

        try {
            e = getLastExercicio();
        } catch(Exception f) {
            return false;
        }

        GregorianCalendar gcA = new GregorianCalendar();
        gcA.setTime(e.getData());

        if(gcN.get(gcN.DAY_OF_MONTH) == gcA.get(gcA.DAY_OF_MONTH) &&
                gcN.get(gcN.MONTH) == gcA.get(gcA.MONTH) &&
                gcN.get(gcN.YEAR) == gcA.get(gcA.YEAR)) {

            return true;
        }

        return false;
    }
}



