package com.victorvieira.lifeway.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDB extends SQLiteOpenHelper {

    private static final int DB_VERSION = 6;
    private static final String DB_NAME = "lifeway";

    private static final String INTEGER_TYPE = "INTEGER";
    private static final String TEXT_TYPE = "TEXT";
    private static final String REAL_TYPE = "REAL";
    private static final String COMMA_SEP = ", ";

    private static final String SQL_USER_CREATE_ENTRIES =" CREATE TABLE if not exists " +
            FeedReaderContract.FeedEntry.USER_TABLE_NAME + "("+
            FeedReaderContract.FeedEntry.USER_COLUMN_ID + " " + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            FeedReaderContract.FeedEntry.USER_COLUMN_NOME + " " + TEXT_TYPE + COMMA_SEP +
            FeedReaderContract.FeedEntry.USER_COLUMN_PESO + " " + REAL_TYPE + COMMA_SEP +
            FeedReaderContract.FeedEntry.USER_COLUMN_ALTURA + " " + REAL_TYPE + COMMA_SEP +
            FeedReaderContract.FeedEntry.USER_COLUMN_IMC + " " + REAL_TYPE + COMMA_SEP +
            FeedReaderContract.FeedEntry.USER_COLUMN_DATA_NASCIMENTO + " " + TEXT_TYPE + COMMA_SEP +
            FeedReaderContract.FeedEntry.USER_COLUMN_META_DE_PESO + " " + REAL_TYPE + ")";

    private static final String SQL_CONSUMO_CREATE_ENTRIES = "CREATE TABLE if not exists " +
            FeedReaderContract.FeedEntry.CONSUMO_TABLE_NAME + "(" +
            FeedReaderContract.FeedEntry.CONSUMO_COLUMN_ID + " " + INTEGER_TYPE + COMMA_SEP +
            FeedReaderContract.FeedEntry.CONSUMO_COLUMN_ID_REFEICAO + " " + INTEGER_TYPE + COMMA_SEP +
            "PRIMARY KEY (" +
            FeedReaderContract.FeedEntry.CONSUMO_COLUMN_ID + COMMA_SEP +
            FeedReaderContract.FeedEntry.CONSUMO_COLUMN_ID_REFEICAO + ")" + COMMA_SEP +
            "FOREIGN KEY (" +
            FeedReaderContract.FeedEntry.CONSUMO_COLUMN_ID_REFEICAO + ") REFERENCES " +
            FeedReaderContract.FeedEntry.REFEICAO_TABLE_NAME +
            "(" + FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID + "))";

    private static final String SQL_REFEICAO_CREATE_ENTRIES = "CREATE TABLE if not exists " +
            FeedReaderContract.FeedEntry.REFEICAO_TABLE_NAME + "(" +
            FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID + " " + INTEGER_TYPE + COMMA_SEP +
            FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID_ALIMENTO + " " + INTEGER_TYPE + COMMA_SEP +
            FeedReaderContract.FeedEntry.REFEICAO_COLUMN_HORARIO + " " + TEXT_TYPE + COMMA_SEP +
            FeedReaderContract.FeedEntry.REFEICAO_COLUMN_TIPO + " " + TEXT_TYPE + COMMA_SEP +
            "PRIMARY KEY (" +
            FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID + COMMA_SEP +
            FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID_ALIMENTO + ")" + COMMA_SEP +
            "FOREIGN KEY (" +
            FeedReaderContract.FeedEntry.REFEICAO_COLUMN_ID_ALIMENTO + ") REFERENCES " +
            FeedReaderContract.FeedEntry.ALIMENTO_TABLE_NAME +
            "(" + FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_ID + "))";

    private static final String SQL_ALIMENTO_CREATE_ENTRIES = "CREATE TABLE if not exists "+
            FeedReaderContract.FeedEntry.ALIMENTO_TABLE_NAME + "(" +
            FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_ID  + " " + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_NOME + " " + TEXT_TYPE + COMMA_SEP +
            FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_TIPO + " " + TEXT_TYPE + COMMA_SEP +
            FeedReaderContract.FeedEntry.ALIMENTO_COLUMN_INDICACAO + " " + TEXT_TYPE + COMMA_SEP +
            FeedReaderContract.FeedEntry. ALIMENTO_COLUMN_VALOR_CALORICO + " " + TEXT_TYPE +
            ")";

    public SQLiteDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_ALIMENTO_CREATE_ENTRIES);
        sqLiteDatabase.execSQL(SQL_REFEICAO_CREATE_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CONSUMO_CREATE_ENTRIES);
        sqLiteDatabase.execSQL(SQL_USER_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE if exists " + FeedReaderContract.FeedEntry.USER_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE if exists " + FeedReaderContract.FeedEntry.REFEICAO_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE if exists " + FeedReaderContract.FeedEntry.CONSUMO_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE if exists " + FeedReaderContract.FeedEntry.ALIMENTO_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }





}