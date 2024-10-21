package com.mauricio.todolist;

public class Tarefa {
    private String nomeTarefa;
    private String prioridade;
    private String categoria;
    private String dataPrazo;
    private String horaPrazo;
    private boolean concluida;

    public Tarefa(String nomeTarefa, String prioridade, String categoria, String dataPrazo, String horaPrazo, String string, boolean concluida) {
        this.nomeTarefa = nomeTarefa;
        this.prioridade = prioridade;
        this.categoria = categoria;
        this.dataPrazo = dataPrazo;
        this.horaPrazo = horaPrazo;
        this.concluida = concluida;
    }

    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public void setNomeTarefa(String nomeTarefa) {
        this.nomeTarefa = nomeTarefa;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDataPrazo() {
        return dataPrazo;
    }

    public void setDataPrazo(String dataPrazo) {
        this.dataPrazo = dataPrazo;
    }

    public String getHoraPrazo() {
        return horaPrazo;
    }

    public void setHoraPrazo(String horaPrazo) {
        this.horaPrazo = horaPrazo;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }
}
