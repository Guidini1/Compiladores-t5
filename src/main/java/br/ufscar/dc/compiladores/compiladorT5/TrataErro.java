package br.ufscar.dc.compiladores.compiladorT5;

import br.ufscar.dc.compiladores.compiladorT5.AlgumaLexer;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.misc.ParseCancellationException;

/**
 * Classe personalizada para tratamento de erros durante a análise léxica e sintática.
 * Estende BaseErrorListener para customizar o tratamento de erros do ANTLR.
 */
public class TrataErro extends BaseErrorListener {

    // Instância única do listener de erros
    public static final TrataErro INSTANCE = new TrataErro();

    /**
     * Método chamado quando ocorre um erro de sintaxe ou léxico.
     * 
     * @param recognizer Reconhecedor do ANTLR que detectou o erro.
     * @param offendingSymbol Símbolo que causou o erro.
     * @param line Linha onde o erro ocorreu.
     * @param charPositionInLine Posição do caractere na linha onde o erro ocorreu.
     * @param msg Mensagem de erro.
     * @param e Exceção de reconhecimento que contém detalhes do erro.
     * @throws ParseCancellationException Exceção lançada para interromper a análise e reportar o erro.
     */
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
            int charPositionInLine, String msg, RecognitionException e) 
                throws ParseCancellationException {

        Token token = (Token) offendingSymbol;

        // Cria o padrão de todos os prints de erro.
        String base = "Linha " + token.getLine() + ": ";

        // Verifica se o erro é léxico usando a função eh_erro()
        if (eh_erro(token.getType())) {
            if (token.getType() == AlgumaLexer.Caracter_invalido) {
                throw new ParseCancellationException(base + token.getText() + " - símbolo não identificado");
            } else if (AlgumaLexer.VOCABULARY.getSymbolicName(token.getType()).equals("CADEIA_SEM_FIM")) {
                throw new ParseCancellationException(base + "cadeia literal não fechada");
            } else {
                throw new ParseCancellationException(base + "comentário não fechado");
            }
        } 
        // Se o erro não for léxico, trata casos de erro sintático ou EOF
        else if (token.getType() == Token.EOF) {
            throw new ParseCancellationException(base + "erro sintático próximo ao EOF");
        } else {
            throw new ParseCancellationException(base + "erro sintático próximo a " + token.getText());
        }
    }

    /**
     * Verifica se o tipo de token corresponde a um erro léxico conhecido.
     * 
     * @param tkType Tipo do token a ser verificado.
     * @return Verdadeiro se o token corresponder a um erro léxico, falso caso contrário.
     */
    private static Boolean eh_erro(int tkType) {
        // Retorna verdadeiro se o tipo do token corresponder a um erro léxico
        return tkType == AlgumaLexer.CADEIA_SEM_FIM 
                || tkType == AlgumaLexer.COMENTARIO_SEM_FIM
                || tkType == AlgumaLexer.Caracter_invalido;
    }
}
