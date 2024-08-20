package br.ufscar.dc.compiladores.compiladorT5;

import java.util.ArrayList;

public class Erros {
 
    // Lista para armazenar mensagens de erro
    private final ArrayList<String> erros = new ArrayList<>();

    /**
     * Adiciona um erro à lista de erros com base no identificador e outras informações fornecidas.
     * 
     * @param id Identificador do tipo de erro (0 a 5)
     * @param linha Número da linha onde o erro ocorreu
     * @param nome Nome do identificador ou tipo relacionado ao erro
     */
    public void adiciona_erro(int id, int linha, String nome){
        // Cria a base da mensagem de erro com o número da linha
        String base = "Linha " + linha;

        // Adiciona uma mensagem de erro à lista com base no identificador fornecido
        switch(id)
        {
            case 0:
                erros.add(base + ": identificador " + nome + " nao declarado");
                break;
            case 1:
                erros.add(base + ": identificador " + nome + " ja declarado anteriormente");
                break;
            case 2:
                erros.add(base + ": atribuicao nao compativel para " + nome);
                break;
            case 3:
                erros.add(base + ": tipo " + nome + " nao declarado");
                break;
            case 4:
                erros.add(base + ": incompatibilidade de parametros na chamada de " + nome);
                break;
            case 5:
                erros.add(base + ": comando retorne nao permitido nesse escopo");
                break;
            default:
                // Não faz nada se o identificador do erro não estiver entre os casos definidos
        }           
    }   

    /**
     * Retorna a lista de todos os erros registrados.
     * 
     * @return Lista de erros
     */
    public ArrayList<String> getErros(){
        return erros;
    }
}
