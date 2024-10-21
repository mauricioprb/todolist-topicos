package com.mauricio.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mauricio.to_dolist.R;

import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder> {

    private Context mContexto;
    private List<MainActivity.Dados> listaTarefas;
    private AoClicarItem mListener;

    public interface AoClicarItem {
        void aoClicarEditar(int posicao);
        void aoClicarDeletar(int posicao);
        void aoClicarCheckbox(int posicao);
    }

    public void setAoClicarItem(AoClicarItem listener) {
        mListener = listener;
    }

    public TarefaAdapter(Context contexto, List<MainActivity.Dados> listaTarefas) {
        mContexto = contexto;
        this.listaTarefas = listaTarefas;
    }

    @NonNull
    @Override
    public TarefaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContexto).inflate(R.layout.card_tarefa_layout, parent, false);
        return new TarefaViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TarefaViewHolder holder, int posicao) {
        MainActivity.Dados tarefaAtual = listaTarefas.get(posicao);

        holder.textoNome.setText(tarefaAtual.getNome());
        holder.textoData.setText("Data: " + tarefaAtual.getData());
        holder.textoHora.setText("Hora: " + tarefaAtual.getHora());
        holder.textoCategoria.setText("Categoria: " + tarefaAtual.getCategoria());
        holder.textoPrioridade.setText("Prioridade: " + tarefaAtual.getPrioridade());
        holder.textoNotas.setText("Nota: " + tarefaAtual.getNotas());
    }

    @Override
    public int getItemCount() {
        return listaTarefas.size();
    }

    public static class TarefaViewHolder extends RecyclerView.ViewHolder {

        public TextView textoNome;
        public TextView textoData;
        public TextView textoHora;
        public TextView textoCategoria;
        public TextView textoPrioridade;
        public TextView textoNotas;
        public CheckBox checkBox;
        public Button botaoEditar;
        public Button botaoDeletar;

        public TarefaViewHolder(@NonNull View itemView, final AoClicarItem listener) {
            super(itemView);
            textoNome = itemView.findViewById(R.id.text_name);
            textoData = itemView.findViewById(R.id.text_date);
            textoHora = itemView.findViewById(R.id.text_time);
            textoCategoria = itemView.findViewById(R.id.text_category);
            textoPrioridade = itemView.findViewById(R.id.text_priority);
            textoNotas = itemView.findViewById(R.id.text_notes);

            checkBox = itemView.findViewById(R.id.check_box);
            botaoEditar = itemView.findViewById(R.id.btn_edit);
            botaoDeletar = itemView.findViewById(R.id.btn_delete);

            botaoEditar.setOnClickListener(v -> {
                if (listener != null) {
                    int posicao = getAdapterPosition();
                    if (posicao != RecyclerView.NO_POSITION) {
                        listener.aoClicarEditar(posicao);
                    }
                }
            });

            botaoDeletar.setOnClickListener(v -> {
                if (listener != null) {
                    int posicao = getAdapterPosition();
                    if (posicao != RecyclerView.NO_POSITION) {
                        listener.aoClicarDeletar(posicao);
                    }
                }
            });

            checkBox.setOnClickListener(v -> {
                if (listener != null) {
                    int posicao = getAdapterPosition();
                    if (posicao != RecyclerView.NO_POSITION) {
                        listener.aoClicarCheckbox(posicao);
                    }
                }
            });
        }
    }
}
