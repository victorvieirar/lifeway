package com.victorvieira.lifeway.persistencia;

import android.provider.BaseColumns;

public final class FeedReaderContract {

    public FeedReaderContract() {}

    public static abstract class FeedEntry implements BaseColumns {
        /** Usuário */
        public static final String USER_TABLE_NAME = "usuario";
        public static final String USER_COLUMN_ID = "_id";
        public static final String USER_COLUMN_NOME = "nome";
        public static final String USER_COLUMN_SEXO = "sexo";
        public static final String USER_COLUMN_PESO = "peso";
        public static final String USER_COLUMN_ALTURA = "altura";
        public static final String USER_COLUMN_IMC = "imc";
        public static final String USER_COLUMN_META_DE_PESO = "meta";
        public static final String USER_COLUMN_DATA_NASCIMENTO = "data_nascimento";
        public static final String USER_COLUMN_KCAL_DIARIA = "kcal_diaria";
        public static final String USER_COLUMN_AGUA_DIARIA = "agua_diaria";
        public static final String USER_COLUMN_CONSUMO = "consumo";
        public static final String USER_COLUMN_CONSUMO_AGUA = "consumo_agua";

        /** Consumo */
        public static final String CONSUMO_TABLE_NAME = "consumo";
        public static final String CONSUMO_COLUMN_ID = "_id";
        public static final String CONSUMO_COLUMN_ID_REFEICAO = "_id_ref";

        /** Refeição*/
        public static final String REFEICAO_TABLE_NAME = "refeicao";
        public static final String REFEICAO_COLUMN_ID = "_id";
        public static final String REFEICAO_COLUMN_ID_ALIMENTO = "_id_ali";
        public static final String REFEICAO_COLUMN_HORARIO = "horario";
        public static final String REFEICAO_COLUMN_TIPO = "tipo";
        public static final String REFEICAO_COLUMN_PORCAO = "porcao";

        /** Consumo de Água */
        public static final String CONSUMO_AGUA_TABLE_NAME = "consumo_agua";
        public static final String CONSUMO_AGUA_COLUMN_ID = "_id";
        public static final String CONSUMO_AGUA_COLUMN_ID_AGUA = "_id_agua";

        /** Água */
        public static final String AGUA_TABLE_NAME = "agua";
        public static final String AGUA_COLUMN_ID = "_id";
        public static final String AGUA_COLUMN_QUANTIDADE = "quantidade";
        public static final String AGUA_COLUMN_HORARIO = "horario";

        /** Prática de Exercícios */
        public static final String PRATICA_EXERCICIOS_TABLE_NAME = "pratica_exercicios";
        public static final String PRATICA_EXERCICIOS_COLUMN_ID = "_id";
        public static final String PRATICA_EXERCICIOS_COLUMN_ID_EXERCICIO = "_id_exercicio";

        /** Exercícios */
        public static final String EXERCICIO_TABLE_NAME = "exercicio";
        public static final String EXERCICIO_COLUMN_ID = "_id";
        public static final String EXERCICIO_COLUMN_TEMPO = "tempo";
        public static final String EXERCICIO_COLUMN_HORARIO = "horario";

        /** Alimento*/
        public static final String ALIMENTO_TABLE_NAME = "alimento";
        public static final String ALIMENTO_COLUMN_ID = "_id";
        public static final String ALIMENTO_COLUMN_NOME = "nome";
        public static final String ALIMENTO_COLUMN_TIPO = "tipo";
        public static final String ALIMENTO_COLUMN_INDICACAO = "indicacao";
        public static final String ALIMENTO_COLUMN_VALOR_CALORICO = "valor_calorico";

    }
}