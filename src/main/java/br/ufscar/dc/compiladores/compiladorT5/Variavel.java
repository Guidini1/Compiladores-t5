package br.ufscar.dc.compiladores.compiladorT5;
// Giovani Guidini RA:790862
// Bruno Zenatti de Caires Marcelo RA: 769821
import br.ufscar.dc.compiladores.compiladorT5.AlgumaParser;
import java.util.*;

/**
 * Representa uma variável no contexto do compilador.
 * Inclui informações sobre o nome da variável, seu tipo e possíveis estruturas associadas.
 */
public class Variavel {

    public String varNome; // Nome da variável
    public Tipo tipo; // Tipo da variável
    public Procedimento procedimento = null; // Procedimento associado à variável, se houver
    public Registro registro = null; // Registro associado à variável, se houver
    public Ponteiro ponteiro = null; // Ponteiro associado à variável, se houver
    public Funcao funcao = null; // Função associada à variável, se houver

    /**
     * Construtor padrão que inicializa a variável com nome vazio e tipo nulo.
     */
    public Variavel(){
        this.varNome = "";
        this.tipo = null;
    }

    /**
     * Construtor que inicializa a variável com nome e tipo especificados.
     * 
     * @param nome Nome da variável.
     * @param tipo Tipo da variável.
     */
    public Variavel(String nome, Tipo tipo) {
        this.varNome = nome;
        this.tipo = tipo;

        // Verifica e associa estruturas adicionais com base no tipo da variável
        if (tipoNaoVazio(tipo)){
            Verifica(tipo);
        }
    }

    /**
     * Obtém o tipo do ponteiro aninhado, se houver.
     * 
     * @return Tipo do ponteiro aninhado.
     */
    public Tipo getTipoPonteiroAninhado() {
        return ponteiro.getTipoAninhado();
    }

    /**
     * Obtém o registro associado à variável.
     * 
     * @return Registro associado à variável.
     */
    public Registro getRegistro() {
        return registro;
    }

    /**
     * Verifica e associa estruturas adicionais com base no tipo da variável.
     * 
     * @param tipo Tipo da variável a ser verificado.
     */
    public final void Verifica(Tipo tipo){
        switch(tipo.nativos){
            case PONTEIRO:
                ponteiro = new Ponteiro(tipo.apontado);
                break;
            case REGISTRO:
                registro = new Registro();
                break;
            case PROCEDIMENTO:
                procedimento = new Procedimento();
                break;
            case FUNCAO:
                funcao = new Funcao();
                break;
        }
    }

    /**
     * Verifica se o tipo não é nulo e tem um valor válido.
     * 
     * @param tipo Tipo a ser verificado.
     * @return Verdadeiro se o tipo não for nulo e tiver um valor válido.
     */
    public static boolean tipoNaoVazio(Tipo tipo) {
        return (tipo != null && tipo.nativos != null);
    }

    /**
     * Classe interna que representa um ponteiro associado à variável.
     */
    public class Ponteiro {
        private final Tipo aponta; // Tipo para o qual o ponteiro aponta
        
        public Ponteiro(Tipo p) {
            this.aponta = p;
        }
        
        public Tipo getTipo() {   
            return aponta.getTipo();
        }
        
        public Tipo getTipoAninhado() {   
            return aponta.getTipoAninhado();
        }
    }

    /**
     * Classe interna que representa um registro associado à variável.
     */
    public class Registro {
        private final ArrayList<Variavel> varRegistro = new ArrayList<>();
        
        public Variavel getVariavel(String nome) {
            for (Variavel v : varRegistro)
                if (v.varNome.equals(nome))
                    return v;
            return null;
        }
        
        public ArrayList<Variavel> getAll() {
            return varRegistro;
        }
        
        public void addRegistro(ArrayList<Variavel> aux) {
            varRegistro.addAll(aux);
        }
    }

    /**
     * Classe interna que representa um procedimento associado à variável.
     */
    public class Procedimento {
        private ArrayList<Variavel> local; // Variáveis locais do procedimento
        private ArrayList<Variavel> parametros; // Parâmetros do procedimento
        
        public void setLocal(ArrayList<Variavel> local) {
            this.local = local;
        }
        
        public void setParametros(ArrayList<Variavel> parametros) {
            this.parametros = parametros;
        }
        
        public ArrayList<Variavel> getParametros() {
            return parametros;
        }
        
        public ArrayList<Variavel> getLocals() {
            return local;
        }
    }

    /**
     * Classe interna que representa uma função associada à variável.
     */
    public class Funcao {
        private ArrayList<Variavel> local; // Variáveis locais da função
        private ArrayList<Variavel> parametros; // Parâmetros da função
        private Tipo tipoRetorno; // Tipo de retorno da função
        
        public void setTipoRetorno(Tipo tipoRetorno) {
            this.tipoRetorno = tipoRetorno;
        }
        
        public void setLocal(ArrayList<Variavel> local) {
            this.local = local;
        }
        
        public void setParametros(ArrayList<Variavel> parametros) {
            this.parametros = parametros;
        }
        
        public Tipo getTipoRetorno() {
            return tipoRetorno;
        }
        
        public ArrayList<Variavel> getParametros() {
            return parametros;
        }
        
        public ArrayList<Variavel> getLocal() {
            return local;
        }

        // Método não implementado ainda
        Iterable<AlgumaParser.Declaracao_localContext> getLocals() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    // Métodos de acesso para as propriedades de Procedimento e Função
    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    public Procedimento getProcedimento() {
        return procedimento;
    }

    public Funcao getFuncao() {
        return funcao;
    }
}
