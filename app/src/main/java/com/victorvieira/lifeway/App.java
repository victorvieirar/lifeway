package com.victorvieira.lifeway;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

import com.victorvieira.lifeway.dominio.Alimento;
import com.victorvieira.lifeway.dominio.Refeicao;
import com.victorvieira.lifeway.dominio.RefeicaoDisponivel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class App {

    private final GregorianCalendar DATA_CRIACAO = new GregorianCalendar();
    private int id_refeicao;

    private int imageLogin;

    private Bitmap image_historic;

    private String nomeUsuario;
    private Date dataNascimento;
    private double peso;
    private double altura;
    private double metaDePeso;

    private ArrayList<RefeicaoDisponivel> refeicoesDisponiveis = new ArrayList<RefeicaoDisponivel>();

    public App() {
        super();
        DATA_CRIACAO.setTime(new Date());
        createRefeicoes();
    }

    public ArrayList<Alimento> getAlimentosInRefeicoesDisponiveis() {
        ArrayList<Alimento> alimentos = new ArrayList<Alimento>();

        for(RefeicaoDisponivel r : getRefeicoesDisponiveis()) {
            for(Alimento a : r.getAlimentos()) {
                alimentos.add(a);
            }
        }

        return alimentos;
    }


    private void createRefeicoes() {
        String[] nome = { "Banana", "Feijão", "Arroz" };
        String[] nomeRefeicao = {"Café da Manhã", "Almoço", "Jantar"};
        char[] type = { 'c', 'a', 'j' };

        int count = 1;

        for(int i=0; i < 3; i++) {
            RefeicaoDisponivel refeicaoDisponivel = new RefeicaoDisponivel(type[i],nomeRefeicao[i]);
            for(int j = 0; j < 5; j++) {
                String sNome = nome[i] + " " + j;
                int indicacao = (int) (Math.random() * 3);
                Alimento alimento = new Alimento(count++, sNome, type[i], ++indicacao, 120);
                refeicaoDisponivel.addAlimento(alimento);
            }
            refeicoesDisponiveis.add(refeicaoDisponivel);
        }
    }

    public boolean hasImageHistoric() {
        if(image_historic == null) {
            return false;
        } else {
            return true;
        }
    }
    public void setImageHistoric(Bitmap bitmap) { this.image_historic = bitmap; bitmap = null; System.gc(); }
    public Bitmap getImageHistoric() { return image_historic; }
    public void clearImageHistoric() { image_historic = null; System.gc(); }

    public ArrayList<RefeicaoDisponivel> getRefeicoesDisponiveis() { return refeicoesDisponiveis; }

    public ArrayList<RefeicaoDisponivel> getRefeicoesDisponiveisBySearch(String nomeAlimento) {

        ArrayList<RefeicaoDisponivel> resultSearch = new ArrayList<RefeicaoDisponivel>();

        for(int i = 0; i < refeicoesDisponiveis.size(); i++) {

            RefeicaoDisponivel refeicaoAtual = new RefeicaoDisponivel(refeicoesDisponiveis.get(i).getType(), refeicoesDisponiveis.get(i).getNomeRefeicao());
            ArrayList<Alimento> alimentosAtuais = refeicoesDisponiveis.get(i).getAlimentos();

            for(int j = 0; j < alimentosAtuais.size(); j++) {
                if(alimentosAtuais.get(j).getNome().toLowerCase().contains(nomeAlimento.toLowerCase())) {
                    refeicaoAtual.addAlimento(alimentosAtuais.get(j));
                }
            }

            if(!(refeicaoAtual.getAlimentos().size() <= 0)) {
                resultSearch.add(refeicaoAtual);
            }

        }

        return resultSearch;
    }

    public String transformDiaDaSemana(String diaDaSemana) {

        String[] semanaI = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        String[] semanaP = { "domingo", "segunda-feira", "terça-feira", "quarta-feira", "quinta-feira", "sexta-feira", "sábado"};

        for (int i = 0; i < semanaI.length; i++) {
            if(diaDaSemana.contains(semanaI[i])) {
                diaDaSemana = semanaP[i];
                break;
            }
        }

        return diaDaSemana;
    }

    public String transformMes(String mes) {
        String[] mesI = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        String[] mesP = { "janeiro", "fevereiro", "março", "abril", "maio", "junho", "julho", "agosto", "setembro", "outubro", "novembro", "dezembro" };

        for(int i = 0; i < mesI.length; i++) {
            if(mes.contains(mesI[i])) {
                mes = mesP[i];
                break;
            }
        }

        return mes;
    }

    public int getIndexOfMonth(String mes) {
        String[] mesI = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

        for(int i = 0; i < mesI.length; i++) {
            if(mesI[i].contains(mes)) {
                return ++i;
            }
        }

        return 0;

    }

    public String getStringOfHour(GregorianCalendar reference, GregorianCalendar selected) {

        String sData = "";

        int diaDoMesS = selected.get(selected.DAY_OF_MONTH);
        int mesS = selected.get(selected.MONTH);
        int anoS = selected.get(selected.YEAR);

        int diaDoMesR = reference.get(reference.DAY_OF_MONTH);
        int mesR = reference.get(reference.MONTH);
        int anoR = reference.get(reference.YEAR);

        if(diaDoMesS == diaDoMesR && mesS == mesR && anoS == anoR) {
            sData = "hoje";
        } else {
            reference.add(reference.DAY_OF_MONTH, -1);

            diaDoMesR = reference.get(reference.DAY_OF_MONTH);
            mesR = reference.get(reference.MONTH);
            anoR = reference.get(reference.YEAR);

            if(diaDoMesS == diaDoMesR && mesS == mesR && anoS == anoR) {
                sData = "ontem";
            } else {
                reference.add(reference.DAY_OF_MONTH, 1);

                diaDoMesR = reference.get(reference.DAY_OF_MONTH);
                mesR = reference.get(reference.MONTH);
                anoR = reference.get(reference.YEAR);

                sData = getStringOfDayByIndex(selected.get(selected.DAY_OF_WEEK)) + ", "
                        + selected.get(selected.DAY_OF_MONTH) + " de "
                        + getStringOfMonthByIndex(selected.get(selected.MONTH)) + " de "
                        + selected.get(selected.YEAR);
            }
        }

        return sData;

    }

    public String getStringOfDayByIndex(int index) {
        String[] semana = { "domingo", "segunda-feira", "terça-feira", "quarta-feira", "quinta-feira", "sexta-feira", "sábado"};
        return semana[index];
    }
    public String getStringOfMonthByIndex(int index) {
        index -= 1;
        String[] mes = { "janeiro", "fevereiro", "março", "abril", "maio", "junho", "julho", "agosto", "setembro", "outubro", "novembro", "dezembro" };
        return mes[index];
    }

    public int getId_refeicao() { return id_refeicao; }
    public void setId_refeicao(int id_refeicao) { this.id_refeicao = id_refeicao; }

    public int getIdade(int anoNascimento, int anoAtual) {
        int idade = anoAtual - anoNascimento;
        return idade;
    }

    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }
    public void setAltura(double altura) {
        this.altura = altura;
    }
    public void setMetaDePeso(double metaDePeso) { this.metaDePeso = metaDePeso; }

    public String getNomeUsuario() {
        return nomeUsuario;
    }
    public Date getDataNascimento() {
        return dataNascimento;
    }
    public double getPeso() {
        return peso;
    }
    public double getAltura() {
        return altura;
    }
    public double getMetaDePeso() { return metaDePeso; }

    public int getImageLogin() { return imageLogin; }
    public void setImageLogin(int img) { this.imageLogin = img; }
}
