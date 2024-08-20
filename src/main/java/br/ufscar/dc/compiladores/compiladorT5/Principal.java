package br.ufscar.dc.compiladores.compiladorT5;

import br.ufscar.dc.compiladores.compiladorT5.AlgumaParser.ProgramaContext;
import java.io.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class Principal {
    public static void main(String[] args) throws IOException {
        // Verifica se o número correto de parâmetros foi passado para o programa.
        // Caso contrário, exibe uma mensagem de erro e encerra a execução.
        if (args.length < 2) {
            System.out.println("Falha na execução.\nNúmero de parâmetros inválidos.");
            System.exit(0);
        }

        // Cria o analisador léxico e sintático a partir do arquivo de entrada.
        AlgumaLexer entrada = new AlgumaLexer(CharStreams.fromFileName(args[0]));
        AlgumaParser parser = new AlgumaParser(new CommonTokenStream(entrada));

        // Remove os ouvintes de erro padrão e adiciona um ouvinte personalizado.
        parser.removeErrorListeners();
        parser.addErrorListener(TrataErro.INSTANCE);

        // Cria o analisador semântico.
        Visitante analisador = new Visitante();

        // Abre o arquivo de saída para escrever os resultados.
        try (PrintWriter saida = new PrintWriter(args[1])) {

            try {
                // Faz a análise sintática e semântica do programa.
                ProgramaContext contexto = parser.programa();
                analisador.visitPrograma(contexto);
                
                // Verifica se não há erros na lista de erros.
                if (analisador.errorlist.getErros().isEmpty()) {
                    // Gera o código e escreve no arquivo de saída.
                    GeraCodigo gerador = new GeraCodigo(analisador.getEscopo());
                    gerador.visit(contexto);
                    saida.print(gerador.outputFinal.toString());
                } else {
                    // Caso haja erros, escreve todos os erros no arquivo de saída.
                    for (String erro : analisador.errorlist.getErros()) {
                        saida.println(erro);
                    }
                    saida.println("Fim da compilacao");
                }
                saida.close();
                
            } catch (ParseCancellationException exception) {
                // Captura exceções de cancelamento de análise e escreve no arquivo de saída.
                saida.println(exception.getMessage());
                saida.println("Fim da compilacao");
                saida.close();
            }

        } catch (IOException exception) {
            // Captura exceções de entrada/saída e exibe uma mensagem de erro.
            System.out.println("Falha na execução.\nO programa não conseguiu abrir o arquivo: " + args[1] + ".");
        }
    }
}
