package com.mauricio.todolist;
import android.provider.BaseColumns;

public class TarefaContrato {

    private TarefaContrato() {
    }

    public static final class TarefaEntry implements BaseColumns {
        public static final String TABELA_NOME = "tarefas";
        public static final String COLUNA_TAREFA = "tarefa";
        public static final String COLUNA_CATEGORIA = "categoria";
        public static final String COLUNA_PRIORIDADE = "prioridade";
        public static final String COLUNA_NOTAS = "notas";
        public static final String COLUNA_DATA_PRAZO = "data_prazo";
        public static final String COLUNA_HORA_PRAZO = "hora_prazo";
        public static final String COLUNA_CONCLUIDA = "concluida";
    }
}
