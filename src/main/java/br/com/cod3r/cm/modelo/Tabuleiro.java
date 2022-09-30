package br.com.cod3r.cm.modelo;

import br.com.cod3r.cm.excecao.ExplosaoException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tabuleiro {

    private int linhas;
    private int colunas;
    private int minas;

    private final List<Campo> campos = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int minas){
        this.colunas = colunas;
        this.linhas = linhas;
        this.minas = minas;

        gerarCampos();
        associarOsVizinhos();
        sortearMinas();
    }

    private void sortearMinas() {
        long minasArmadas = 0;
        Predicate<Campo> minado = c -> c.isMinado();

        do {
            int aleatorio = (int)(Math.random() * campos.size());
            campos.get(aleatorio).minar();
            minasArmadas = campos.stream().filter(minado).count();
        } while(minasArmadas < minas);
    }

    private void associarOsVizinhos() {
        for (Campo c1: campos){
            for (Campo c2: campos){
                c1.adicionarVizinho(c2);
            }
        }
    }

    private void gerarCampos() {
        for (int linha = 0; linha < linhas; linha++){
            for (int coluna = 0; coluna < colunas; coluna++){
                campos.add(new Campo(linha, coluna));
            }
        }
    }

    public void abrir(int coluna, int linha){
        try{
            campos.parallelStream()
                    .filter(campo -> campo.getLinha() == linha && campo.getColuna() == coluna)
                    .findFirst()
                    .ifPresent(campo -> campo.abrir());
        }catch (ExplosaoException e){
            campos.forEach(c -> c.setAberto(true));
            throw e;
        }
    }

    public void alterarMarcacao(int coluna, int linha){
        campos.parallelStream()
                .filter(campo -> campo.getLinha() == linha && campo.getColuna() == coluna)
                .findFirst()
                .ifPresent(campo -> campo.alternarMarcacao());
    }

    public boolean objetivoAlcancado(){
        return campos.stream().allMatch(c -> c.objetivoAlcancado());
    }

    public void reiniciar(){
        campos.stream().forEach(c -> c.reiniciar());
        sortearMinas();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        for (int i = 0; i < colunas; i++) {
            sb.append(" ");
            sb.append(i);
            sb.append(" ");
        }
        sb.append("\n");

        int c = 0;
        for (int i = 0; i < linhas; i++) {
            sb.append(i);
            sb.append(" ");
            for (int j = 0; j < colunas; j++) {
                sb.append(" ");
                sb.append(campos.get(c));
                sb.append(" ");
                c++;
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
