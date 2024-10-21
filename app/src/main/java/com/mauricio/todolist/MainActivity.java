package com.mauricio.todolist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Toast;
import android.view.View;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mauricio.to_dolist.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ScrollView barraRolagemTarefas;
    private ArrayAdapter<String> adaptadorTarefas;
    private TarefaDBHelper dbHelper;
    private List<Dados> listaTarefas;
    FloatingActionButton botaoAdicionarTarefa;
    RecyclerView recyclerView;
    private TarefaAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaTarefas = new ArrayList<>();
        adaptadorTarefas = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        botaoAdicionarTarefa = findViewById(R.id.fab_add_task);
        dbHelper = new TarefaDBHelper(this);
        carregarTarefasDoSQLite(listaTarefas);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new TarefaAdapter(this, listaTarefas);
        recyclerView.setAdapter(adaptador);
        adaptador.setAoClicarItem(new TarefaAdapter.AoClicarItem() {
            @Override
            public void aoClicarEditar(int posicao) {
                Intent intent = new Intent(MainActivity.this, EditarTarefa.class);
                intent.putExtra("tarefa", listaTarefas.get(posicao).getNome());
                startActivity(intent);
            }

            @Override
            public void aoClicarDeletar(int posicao) {
                marcarTarefaComoConcluida(posicao);
                listaTarefas.remove(posicao);
                Toast.makeText(MainActivity.this, "Tarefa Excluída", Toast.LENGTH_SHORT).show();
                adaptador.notifyItemRemoved(posicao);
            }

            @Override
            public void aoClicarCheckbox(int posicao) {
                marcarTarefaComoConcluida(posicao);
                listaTarefas.remove(posicao);
                Toast.makeText(MainActivity.this, "Tarefa Concluída", Toast.LENGTH_SHORT).show();
                adaptador.notifyItemRemoved(posicao);
            }
        });

        botaoAdicionarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AdicionarTarefaActivity.class);
                startActivity(intent);
            }
        });
    }

    public void carregarTarefasDoSQLite(List<Dados> dados) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TarefaContrato.TarefaEntry.TABELA_NOME, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String nomeTarefa = cursor.getString(cursor.getColumnIndex(TarefaContrato.TarefaEntry.COLUNA_TAREFA));
            @SuppressLint("Range") String dataTarefa = cursor.getString(cursor.getColumnIndex(TarefaContrato.TarefaEntry.COLUNA_DATA_PRAZO));
            @SuppressLint("Range") String horaTarefa = cursor.getString(cursor.getColumnIndex(TarefaContrato.TarefaEntry.COLUNA_HORA_PRAZO));
            @SuppressLint("Range") String categoria = cursor.getString(cursor.getColumnIndex(TarefaContrato.TarefaEntry.COLUNA_CATEGORIA));
            @SuppressLint("Range") String prioridade = cursor.getString(cursor.getColumnIndex(TarefaContrato.TarefaEntry.COLUNA_PRIORIDADE));
            @SuppressLint("Range") String notas = cursor.getString(cursor.getColumnIndex(TarefaContrato.TarefaEntry.COLUNA_NOTAS));

            dados.add(new Dados(nomeTarefa, dataTarefa, horaTarefa, categoria, prioridade, notas));
        }

        cursor.close();
        db.close();
    }

    public void marcarTarefaComoConcluida(int posicao) {
        String tarefa = listaTarefas.get(posicao).getNome();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(TarefaContrato.TarefaEntry.COLUNA_CONCLUIDA, 1);
        db.delete(TarefaContrato.TarefaEntry.TABELA_NOME,
                TarefaContrato.TarefaEntry.COLUNA_TAREFA + " = ?", new String[]{tarefa});
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    public class Dados {
        String nome;
        String data;
        String hora;
        String categoria;
        String prioridade;
        String notas;

        Dados(String nome, String data, String hora, String categoria, String prioridade, String notas) {
            this.nome = nome;
            this.data = data;
            this.hora = hora;
            this.categoria = categoria;
            this.prioridade = prioridade;
            this.notas = notas;
        }

        public String getNome() {
            return nome;
        }

        public String getData() {
            return data;
        }

        public String getHora() {
            return hora;
        }

        public String getCategoria() {
            return categoria;
        }

        public String getPrioridade() {
            return prioridade;
        }

        public String getNotas() {
            return notas;
        }
    }
}
