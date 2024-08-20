package br.ufscar.dc.compiladores.compiladorT5;
// Giovani Guidini RA:790862
// Bruno Zenatti de Caires Marcelo RA: 769821
import br.ufscar.dc.compiladores.compiladorT5.AlgumaParser;
import java.util.*;

/**
 * Classe que representa uma tabela de símbolos, armazenando variáveis e seus tipos.
 */
public class TabelaDeSimbolos {
    
    // HashMap que armazena variáveis usando seus nomes como chaves.
    private final HashMap<String, Variavel> Tabela;

    /**
     * Construtor da classe TabelaDeSimbolos. Inicializa o HashMap de variáveis.
     */
    public TabelaDeSimbolos() {
        Tabela = new HashMap<>();
    }

    /**
     * Retorna o tipo da variável com o nome especificado.
     * 
     * @param nome Nome da variável.
     * @return Tipo da variável.
     */
    public Tipo getTipo(String nome) {
        // Obtém a variável com o nome fornecido e retorna seu tipo.
        return Tabela.get(nome).tipo;
    }

    /**
     * Retorna a variável com o nome especificado.
     * 
     * @param nome Nome da variável.
     * @return Variável correspondente ao nome fornecido.
     */
    public Variavel getVar(String nome) {
        // Obtém e retorna a variável com o nome fornecido.
        return Tabela.get(nome);
    }

    /**
     * Adiciona uma nova variável à tabela de símbolos.
     * 
     * @param v Variável a ser adicionada.
     */
    public void adicionar(Variavel v) {
        // Adiciona a variável ao HashMap usando seu nome como chave.
        Tabela.put(v.varNome, v);
    }

    /**
     * Verifica se a tabela contém uma variável com o nome especificado.
     * 
     * @param nome Nome da variável.
     * @return Verdadeiro se a variável estiver na tabela, falso caso contrário.
     */
    public boolean contem(String nome) {
        // Verifica se o nome da variável está presente na tabela.
        return Tabela.containsKey(nome);
    }

    /**
     * Método não implementado. Lançará uma exceção se for chamado.
     * 
     * @param v Contexto de declaração local.
     */
    void adicionar(AlgumaParser.Declaracao_localContext v) {
        throw new UnsupportedOperationException("Not supported yet."); // Método não suportado.
    }
}
