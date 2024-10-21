package com.mauricio.todolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TarefaDBHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO_DADOS = "listatarefas.db";
    private static final int VERSAO_BANCO_DADOS = 1;

    private static final String SQL_CRIAR_TABELA_TAREFAS =
            "CREATE TABLE " + TarefaContrato.TarefaEntry.TABELA_NOME + " (" +
                    TarefaContrato.TarefaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TarefaContrato.TarefaEntry.COLUNA_TAREFA + " TEXT NOT NULL, " +
                    TarefaContrato.TarefaEntry.COLUNA_CATEGORIA + " TEXT, " +
                    TarefaContrato.TarefaEntry.COLUNA_PRIORIDADE + " TEXT, " +
                    TarefaContrato.TarefaEntry.COLUNA_NOTAS + " TEXT, " +
                    TarefaContrato.TarefaEntry.COLUNA_DATA_PRAZO + " TEXT, " +
                    TarefaContrato.TarefaEntry.COLUNA_HORA_PRAZO + " TEXT, " +
                    TarefaContrato.TarefaEntry.COLUNA_CONCLUIDA + " INTEGER DEFAULT 0);";

    public TarefaDBHelper(Context contexto) {
        super(contexto, NOME_BANCO_DADOS, null, VERSAO_BANCO_DADOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CRIAR_TABELA_TAREFAS);
    }

    public Cursor obterTodasTarefas() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                TarefaContrato.TarefaEntry.TABELA_NOME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int novaVersao) {
        // Código para manipular atualizações de versão do banco de dados, se necessário
    }
}
