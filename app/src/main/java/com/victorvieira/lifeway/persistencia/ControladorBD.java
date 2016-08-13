package com.victorvieira.lifeway.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.victorvieira.lifeway.App;
import com.victorvieira.lifeway.dominio.Alimento;
import com.victorvieira.lifeway.dominio.Consumo;
import com.victorvieira.lifeway.dominio.Refeicao;
import com.victorvieira.lifeway.dominio.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ControladorBD {

    private App app;
    private Usuario usuario = null;
    private Consumo consumo = null;
    private Alimento alimento = null;
    private Refeicao refeicao = null;
    private SQLiteDatabase db;

    public ControladorBD(Context context) {
        super();
        app = new App();
        SQLiteDB auxDB = new SQLiteDB(context);
        db = auxDB.getWritableDatabase();
    }



       /** Usuário */
    public void setupUsuario(Usuario usuario) { this.usuario =usuario; }

    public Usuario getUsuario() { return usuario; }
    public App getApp() { return app; }


    /** Inserir os dados em usuário */
    public void inserir (Usuario usuario){
        ContentValues valores = new ContentValues();
        valores.put("nome", usuario.getNome());
        valores.put("peso", usuario.getPeso());
        valores.put("altura", usuario.getAltura());
        valores.put("meta", usuario.getMetaDePeso());
        valores.put("imc", usuario.getImc());
        valores.put("dataNasc", usuario.getDataNascimento().toString());
        valores.put("meta", usuario.getMetaDePeso());
        db.insert("usuario", null, valores);
    }

    /** Atualizar os dados em usuário */
    public void atualizar(Usuario usuario){
        ContentValues valores = new ContentValues();
        valores.put("nome", usuario.getNome());
        valores.put("peso", usuario.getPeso());
        valores.put("altura", usuario.getAltura());
        valores.put("imc", usuario.getImc());
        valores.put("dataNasc", usuario.getDataNascimento().toString());
        valores.put("meta", usuario.getMetaDePeso());
        db.update("usuario", valores, "_id= ?", new String[]{""+usuario.set_id});
    }

    /** Deletar usuário */
    public void deletar (Usuario usuario){
        db.delete("usuario", "_id = ?"+usuario.get_id(), null);
    }

    /** Buscar em usuário*/
    public List<Usuario> buscar(){
        List<Usuario> list = new ArrayList<Usuario>();
        String[] colunas = new String[]{"_id", "nome", "peso", "altura",
                "meta", "imc", "dataNasc"};

        Cursor cursor = Usuario.query("Usuario", colunas, null, null, null, null, "nome ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                usuario u = new Usuario();
                u.setId(cursor.getLong(0));
                u.setNome(cursor.getNome(1));
                u.setPeso(cursor.getPeso(2));
                u.setAltura(cursor.getAltura(3));
                u.setMeta(cursor.getMeta(4));
                u.setImc(cursor.getImc(5));
                u.setDataNasc(cursor.getDataNasc(6));
                list.add(u);

            } while(cursor.moveToNext());

        }
        return(list);
    }



    /** Consumo */

    public void setupConsumo(Consumo consumo) { this.consumo = consumo; }
    public Consumo getConsumo() { return consumo; }


    /** Inserir os dados em consumo */
    public void inserir (Consumo consumo){
        ContentValues valores = new ContentValues();
        valores.put("_id_ref", consumo.get_id_ref());
        db.insert("consumo", null, valores);
    }

    /** Atualizar os dados em consumo */
    public void atualizar(Consumo consumo){
        ContentValues valores = new ContentValues();
        valores.put("_id", consumo.get_id());
        valores.put("_id_ref", consumo.get_id_ref());
        db.update("consumo", valores, "_id= ?", new String[]{""+consumo.set_id});
    }

    /** Deletar consumo */
    public void deletar (Consumo consumo){
        db.delete("consumo", "_id = ?"+consumo.get_id(), null);
    }

    /** Buscar em consumo*/
    public List<Consumo> buscar(){
        List<Consumo> list = new ArrayList<Consumo>();
        String[] colunas = new String[]{"_id", "_id_ref"};

        String cursor =Consumo.query("Consumo", colunas,"_id ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                consumo u = new Consumo();
                u.set_id(cursor.getLong(0));
                u.set_id_ref(cursor.get_id_ref(1));
                list.add(u);

            } while(cursor.moveToNext());

        }
        return(list);
    }



    /** Refeição */
    public void setupRefeicao(Refeicao refeicao) { this.refeicao = refeicao; }
    public Refeicao getRefeicao() { return refeicao; }


    /** Inserir os dados em refeicao */
    public void inserir (Refeicao  refeicao){
        ContentValues valores = new ContentValues();
        valores.put("_id_ref", refeicao.get_id_ali());
        db.insert("refeicao", null, valores);
    }

    /** Atualizar os dados em refeicao */
    public void atualizar(Refeicao refeicao){
        ContentValues valores = new ContentValues();
        valores.put("_id", refeicao.get_id());
        valores.put("_id_ali", refeicao.get_id_ali());
        db.update("refeicao", valores, "_id= ?", new String[]{""+refeicao.set_id});
    }

    /** Deletar refeicao */
    public void deletar (Refeicao refeicao){
        db.delete("refeicao", "_id = ?"+refeicao.get_id(), null);
    }

    /** Buscar em refeicao*/
    public List<Refeicao> buscar(){
        List<Refeicao> list = new ArrayList<Refeicao>();
        String[] colunas = new String[]{"_id", "_id_ali"};

        String cursor =refeicao.query("refeicao", colunas,"_id ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                refeicao u = new Refeicao();
                u.set_id(cursor.getLong(0));
                u.set_id_ali(cursor.get_id_ali(1));
                list.add(u);

            } while(cursor.moveToNext());

        }
        return(list);
    }



    /** Alimento */
    public void setupAliemento(Alimento alimento) { this.alimento =alimento; }

    public Alimento getAlimento() { return alimento; }
    public App getApp() { return app; }


    /** Inserir os dados em Alimento */
    public void inserir (Alimento alimento){
        ContentValues valores = new ContentValues();
        valores.put("nome",alimento.getNome());
        valores.put("tipo", (byte) alimento.getTipo());
        valores.put("indicacao", alimento.getIndicacao());
        valores.put("valor_caloria", alimento.getValor_Caloria());
        db.insert("usuario", null, valores);
    }

    /** Atualizar os dados em alimentos */
    public void atualizar(Alimento alimento){
        ContentValues valores = new ContentValues();
        valores.put("nome",alimento.getNome());
        valores.put("tipo", (byte) alimento.getTipo());
        valores.put("indicacao", alimento.getIndicacao());
        valores.put("valor_caloria", alimento.getValor_Caloria());
        db.update("alimento", valores, "_id= ?", new String[]{""+alimento.set_id});
    }

    /** Deletar alimentos */
    public void deletar (Alimento alimento){
        db.delete("alimento", "_id = ?"+alimento.get_id(), null);
    }

    /** Buscar em alimento*/
    public List<Alimento> buscar(){
        List<Alimento> list = new ArrayList<Alimento>();
        String[] colunas = new String[]{"_id", "nome", "tipo", "indicacao",
                "valor_caloria"};

        Cursor cursor = Alimento.query("Alimento", colunas, "nome ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                usuario u = new Usuario();
                u.setId(cursor.getLong(0));
                u.setNome(cursor.getNome(1));
                u.setTipo(cursor.getTipo(2));
                u.setIndicacao(cursor.getIndicacao(3));
                u.setValor_Caloria(cursor.getValor_Caloria(4));
                list.add(u);

            } while(cursor.moveToNext());

        }
        return(list);
    }

}







