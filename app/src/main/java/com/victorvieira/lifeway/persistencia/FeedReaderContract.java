package com.victorvieira.lifeway.persistencia;

import android.provider.BaseColumns;

public final class FeedReaderContract {

    public FeedReaderContract() {}

    public static abstract class FeedEntry implements BaseColumns {
        /** Usuário */
        public static final String USER_TABLE_NAME = "usuario";
        public static final String USER_COLUMN_ID = "_id";
        public static final String USER_COLUMN_NOME = "nome";
        public static final String USER_COLUMN_PESO = "peso";
        public static final String USER_COLUMN_ALTURA = "altura";
        public static final String USER_COLUMN_IMC = "imc";
        public static final String USER_COLUMN_META_DE_PESO = "meta";
        public static final String USER_COLUMN_DATA_NASCIMENTO = "data_nascimento";

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

        /** Alimento*/
        public static final String ALIMENTO_TABLE_NAME = "alimento";
        public static final String ALIMENTO_COLUMN_ID = "_id";
        public static final String ALIMENTO_COLUMN_NOME = "nome";
        public static final String ALIMENTO_COLUMN_TIPO = "tipo";
        public static final String ALIMENTO_COLUMN_INDICACAO = "indicacao";
        public static final String ALIMENTO_COLUMN_VALOR_CALORICO = "valor_calorico";

    }
}