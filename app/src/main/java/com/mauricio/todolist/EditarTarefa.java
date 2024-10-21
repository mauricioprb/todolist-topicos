package com.mauricio.todolist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.mauricio.to_dolist.R;

import java.util.Calendar;
import java.util.Locale;

public class EditarTarefa extends AppCompatActivity {

    private TextView textoDataSelecionada;
    private TextView textoHoraSelecionada;
    private Spinner spinnerCategoria;
    private Spinner spinnerPrioridade;
    private EditText campoNotas;
    private TextView textoTarefa;
    private TarefaDBHelper dbHelper;

    private Calendar calendario;
    String tarefa;
    Intent intent;
    private int ano, mes, dia, hora, minuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tarefa);
        intent = getIntent();
        tarefa = intent.getStringExtra("task");
        textoTarefa = findViewById(R.id.text_view_task);
        textoTarefa.setText(tarefa);
        textoDataSelecionada = findViewById(R.id.selected_date_text_view);
        textoHoraSelecionada = findViewById(R.id.selected_time_text_view);
        spinnerCategoria = findViewById(R.id.category_spinner);
        spinnerPrioridade = findViewById(R.id.priority_spinner);
        campoNotas = findViewById(R.id.notes_edit_text);
        Button botaoSelecionarData = findViewById(R.id.button_select_due_date);
        Button botaoSelecionarHora = findViewById(R.id.button_select_due_time);
        Button botaoEditarTarefa = findViewById(R.id.button_add_task);

        calendario = Calendar.getInstance();
        ano = calendario.get(Calendar.YEAR);
        mes = calendario.get(Calendar.MONTH);
        dia = calendario.get(Calendar.DAY_OF_MONTH);
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minuto = calendario.get(Calendar.MINUTE);

        ArrayAdapter<CharSequence> adaptadorCategoria = ArrayAdapter.createFromResource(
                this,
                R.array.categories_array,
                android.R.layout.simple_spinner_item
        );
        adaptadorCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adaptadorCategoria);

        ArrayAdapter<CharSequence> adaptadorPrioridade = ArrayAdapter.createFromResource(
                this,
                R.array.priorities_array,
                android.R.layout.simple_spinner_item
        );
        adaptadorPrioridade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrioridade.setAdapter(adaptadorPrioridade);

        atualizarTextoDataEHora();

        botaoSelecionarData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoSelecionarData();
            }
        });

        botaoSelecionarHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoSelecionarHora();
            }
        });

        botaoEditarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarTarefa(tarefa);
                Toast.makeText(EditarTarefa.this, "Tarefa editada", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditarTarefa.this, MainActivity.class);
                startActivity(intent);
            }
        });

        dbHelper = new TarefaDBHelper(this);
    }

    private void atualizarTextoDataEHora() {
        String dataFormatada = String.format(Locale.getDefault(), "%02d/%02d/%d", dia, mes + 1, ano);
        textoDataSelecionada.setText(dataFormatada);

        String horaFormatada = String.format(Locale.getDefault(), "%02d:%02d", hora, minuto);
        textoHoraSelecionada.setText(horaFormatada);
    }

    private void mostrarDialogoSelecionarData() {
        DatePickerDialog dialogoData = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int anoSelecionado, int mesSelecionado, int diaSelecionado) {
                        ano = anoSelecionado;
                        mes = mesSelecionado;
                        dia = diaSelecionado;
                        atualizarTextoDataEHora();
                    }
                },
                ano, mes, dia);
        dialogoData.show();
    }

    private void mostrarDialogoSelecionarHora() {
        TimePickerDialog dialogoHora = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int horaSelecionada, int minutoSelecionado) {
                        hora = horaSelecionada;
                        minuto = minutoSelecionado;
                        atualizarTextoDataEHora();
                    }
                },
                hora, minuto, true);
        dialogoHora.show();
    }

    private void editarTarefa(String tarefa) {
        String categoria = spinnerCategoria.getSelectedItem().toString();
        String prioridade = spinnerPrioridade.getSelectedItem().toString();
        String notas = campoNotas.getText().toString().trim();
        String dataPrazo = textoDataSelecionada.getText().toString().trim();
        String horaPrazo = textoHoraSelecionada.getText().toString().trim();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(TarefaContrato.TarefaEntry.COLUNA_TAREFA, tarefa);
        valores.put(TarefaContrato.TarefaEntry.COLUNA_CATEGORIA, categoria);
        valores.put(TarefaContrato.TarefaEntry.COLUNA_PRIORIDADE, prioridade);
        valores.put(TarefaContrato.TarefaEntry.COLUNA_NOTAS, notas);
        valores.put(TarefaContrato.TarefaEntry.COLUNA_DATA_PRAZO, dataPrazo);
        valores.put(TarefaContrato.TarefaEntry.COLUNA_HORA_PRAZO, horaPrazo);
        valores.put(TarefaContrato.TarefaEntry.COLUNA_CONCLUIDA, 0);
        db.update(TarefaContrato.TarefaEntry.TABELA_NOME, valores, TarefaContrato.TarefaEntry.COLUNA_TAREFA + " = ?", new String[]{tarefa});
        db.close();
    }
}
