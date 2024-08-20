package br.ufscar.dc.compiladores.compiladorT5;
// Giovani Guidini RA:790862
// Bruno Zenatti de Caires Marcelo RA: 769821
import java.util.*;

public class Escopos {
    
    // Lista que armazena as tabelas de símbolos para cada escopo
    private final LinkedList<TabelaDeSimbolos> escopoTabSimb;
    
    /**
     * Construtor da classe Escopos. Inicializa a lista de escopos e cria um novo escopo.
     */
    public Escopos() {
        escopoTabSimb = new LinkedList<>();
        criarNovoEscopo(); // Cria o primeiro escopo no início
    }
    
    /**
     * Cria um novo escopo e adiciona uma nova tabela de símbolos à lista de escopos.
     */
    public final void criarNovoEscopo() {
        escopoTabSimb.push(new TabelaDeSimbolos());
    }
    
    /**
     * Retorna a tabela de símbolos do escopo atual (o mais recente).
     * 
     * @return Tabela de símbolos do escopo atual
     */
    public TabelaDeSimbolos verEscopo() {
        return escopoTabSimb.peek();
    }
    
    /**
     * Remove o escopo atual (o mais recente) da lista de escopos.
     */
    public void abandonarEscopo() {
        escopoTabSimb.pop();
    }
    
    /**
     * Retorna uma lista com todos os escopos presentes.
     * 
     * @return Lista de tabelas de símbolos de todos os escopos
     */
    public List<TabelaDeSimbolos> percorrerEscopo() {
        return escopoTabSimb;
    }
}
