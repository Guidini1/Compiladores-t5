package br.ufscar.dc.compiladores.compiladorT5;
// Giovani Guidini RA:790862
// Bruno Zenatti de Caires Marcelo RA: 769821
import java.util.ArrayList;

/**
 * Classe que representa tipos de dados no compilador.
 * Inclui tipos nativos e tipos criados pelo usuário.
 */
public class Tipo {
    
    // Enumeração dos tipos nativos suportados.
    public enum Nativos {
        INTEIRO,
        REAL,
        LITERAL,
        LOGICO,
        PONTEIRO,
        ENDERECO,
        REGISTRO,
        PROCEDIMENTO,
        FUNCAO,
        INVALIDO
    }

    // Lista de tipos criados pelo usuário.
    public static ArrayList<String> Criados = new ArrayList<>();
    
    // Tipo nativo do tipo, se aplicável.
    public Nativos nativos = null;
    // Nome do tipo criado pelo usuário, se aplicável.
    public String criados = null;   
    // Tipo apontado por um ponteiro, se aplicável.
    public Tipo apontado = null;

    /**
     * Construtor padrão que inicializa um tipo vazio.
     */
    public Tipo() {
        nativos = null;
        criados = null;
    }

    /**
     * Construtor que inicializa o tipo com um tipo nativo.
     * 
     * @param tipo Tipo nativo a ser atribuído.
     */
    public Tipo(Nativos tipo) {
        nativos = tipo;
    }
    
    /**
     * Construtor que inicializa o tipo com um tipo criado pelo usuário.
     * 
     * @param tipo Nome do tipo criado pelo usuário.
     */
    public Tipo(String tipo) {
        criados = tipo;
    }
    
    /**
     * Construtor que inicializa um tipo ponteiro para outro tipo.
     * 
     * @param filho Tipo apontado pelo ponteiro.
     */
    public Tipo(Tipo filho) {
        nativos = Tipo.Nativos.PONTEIRO;
        apontado = filho;
    }
    
    /**
     * Obtém o tipo base, seguindo os ponteiros até encontrar o tipo real.
     * 
     * @return Tipo base.
     */
    public Tipo getTipo() {
        if (apontado != null) 
            return apontado.getTipo();        
        return this;
    }
    
    /**
     * Verifica se o tipo é vazio ou não.
     * 
     * @return Verdadeiro se o tipo não for nulo.
     */
    public boolean tipoVazio(){
        return (this != null && this.nativos != null); 
    }
    
    /**
     * Obtém o tipo criado correspondente ao nome fornecido.
     * 
     * @param tipo Nome do tipo criado.
     * @return Nome do tipo criado se existir, caso contrário, null.
     */
    public static String getTipo(String tipo) {
        String existe = Criados.stream()
                .filter(str -> str.trim().contains(tipo))
                .findAny()
                .orElse("");                    
        if (!"".equals(existe))    
            return existe;
        else
            return null; 
    }
    
    /**
     * Obtém o tipo aninhado, seguindo os ponteiros até o tipo base.
     * 
     * @return Tipo base do tipo aninhado.
     */
    public Tipo getTipoAninhado() {
        if (apontado == null) 
            return this;
        
        Tipo tipo = apontado;
        while (tipo.apontado != null) 
            tipo = tipo.getTipoAninhado();
        
        return tipo;
    }
    
    /**
     * Valida a compatibilidade entre o tipo atual e outro tipo fornecido.
     * 
     * @param tipo Tipo a ser comparado.
     * @return Tipo válido se compatível, caso contrário, tipo inválido.
     */
    public Tipo validaTipo(Tipo tipo) {
        if (this.nativos == Tipo.Nativos.PONTEIRO && tipo.nativos == Tipo.Nativos.ENDERECO)
            return new Tipo(Tipo.Nativos.PONTEIRO);
        else if ((this.nativos == Tipo.Nativos.REAL && (tipo.nativos == Tipo.Nativos.REAL || tipo.nativos == Tipo.Nativos.INTEIRO)) 
                || (this.nativos == Tipo.Nativos.INTEIRO && (tipo.nativos == Tipo.Nativos.REAL || tipo.nativos == Tipo.Nativos.INTEIRO)))
            return new Tipo(Tipo.Nativos.REAL);
        if (this.nativos == Tipo.Nativos.LITERAL && tipo.nativos == Tipo.Nativos.LITERAL)
            return new Tipo(Tipo.Nativos.LITERAL);
        if (this.nativos == Tipo.Nativos.LOGICO && tipo.nativos == Tipo.Nativos.LOGICO)
            return new Tipo(Tipo.Nativos.LOGICO);
        if (this.nativos == Tipo.Nativos.REGISTRO && tipo.nativos == Tipo.Nativos.REGISTRO)
            return new Tipo(Tipo.Nativos.REGISTRO);
        return new Tipo(Tipo.Nativos.INVALIDO);
    }
    
    /**
     * Verifica a equivalência entre o tipo atual e outro tipo fornecido.
     * 
     * @param tipo Tipo a ser comparado.
     * @return Tipo equivalente se compatível, caso contrário, tipo inválido.
     */
    public Tipo verificaEquivalenciaTipo(Tipo tipo) {
        if (this.nativos == Tipo.Nativos.ENDERECO && tipo.nativos == Tipo.Nativos.PONTEIRO)
            return new Tipo(Tipo.Nativos.ENDERECO);
        if (this.nativos == Tipo.Nativos.REGISTRO && tipo.nativos == Tipo.Nativos.REGISTRO)
            return new Tipo(Tipo.Nativos.REGISTRO);
        if (this.nativos == Tipo.Nativos.REAL && tipo.nativos == Tipo.Nativos.REAL)
            return validaTipo(tipo);
        return new Tipo(Tipo.Nativos.INVALIDO);
    }
    
    /**
     * Adiciona um novo tipo criado à lista de tipos criados.
     * 
     * @param tipo Nome do novo tipo criado.
     */
    public static void adicionaNovoTipo(String tipo) {
        Criados.add(tipo);
    }
    
    /**
     * Obtém a representação formatada do tipo.
     * 
     * @return String representando o tipo para impressão.
     */
    public String getFormat() {
        if (nativos != null) {
            if (nativos == Nativos.INTEIRO)
                return "int";
            if (nativos == Nativos.REAL)
                return "float";
            if (nativos == Nativos.LITERAL)
                return "char";
        }
        return criados;
    }
    
    /**
     * Obtém a especificação de formato para impressão do tipo.
     * 
     * @return String representando a especificação de formato.
     */
    public String getFormatSpec() {
        if (nativos != null) {
            if (nativos == Nativos.INTEIRO)
                return "%d";
            if (nativos == Nativos.REAL)
                return "%f";
            if (nativos == Nativos.LITERAL)
                return "%s";
        }
        return "";
    }
}
