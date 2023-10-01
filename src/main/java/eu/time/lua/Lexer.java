package eu.time.lua;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private PeekableIterator iter;
    private Token lookahead = null;

    public Lexer(String input) {
        iter = new PeekableIterator(new StringCharacterIterator(input));
    }

    public Token next() {
        if (lookahead != null) {
            Token next = lookahead;
            lookahead = null;
            return next;
        }
        consumeWhitespace();
        if (!iter.hasNext()) {
            return new Token.EOF();
        }
        char c = iter.next();

        try {
            return switch (c) {
                case '{' -> new Token.LCURLY();
                case '}' -> new Token.RCURLY();
                case '[' -> new Token.LSQUARE();
                case ']' -> new Token.RSQUARE();
                case '=' -> new Token.ASSIGN();
                case ',' -> new Token.COMMA();
                case '"' -> lexString();
                default -> lexDefault(c);
            };
        } catch (LiteralDoesNotExistException | UnterminatedStringException | NotPermittedCharException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Token peek(){
        Token token = next();
        lookahead = token;
        return token;
    }

    private Token lexString() throws UnterminatedStringException, NotPermittedCharException {
        StringBuilder result = new StringBuilder();
        List<Integer> unescapedUnicodeChars = new ArrayList<>();
        int index = -1;
        char c;
        while (iter.hasNext()) {
            c = iter.next();
            index++;
            
            if (c < 32) {
                throw new NotPermittedCharException("Unescaped ASCII control characters are not permitted.");
            } else if (c == '\\') {
                c = iter.next();
                switch (c) {
                    case '\\' -> result.append('\\');
                    case '"' -> result.append('"');
                    case '/' -> result.append('/');
                    case 'b' -> result.append('\b');
                    case 't' -> result.append('\t');
                    case 'n' -> result.append('\n');
                    case 'r' -> result.append('\r');
                    case 'u' -> { //unicode escape
                        int charCode;
                        var temp = "";
                        for (int i = 0; i < 3;i++) {
                            c = iter.next();
                            charCode = c;

                            //https://www.torsten-horn.de/techdocs/ascii.htm
//                            if (!((charCode in 48..57) || (charCode in 97..102) || (charCode in 65..70))) {
//                                throw InvalidUnicodeEscapeSequenceException("Invalid Unicode escape sequence.")
//                            }
                            temp += c;
                        }

                        unescapedUnicodeChars.add(index);
                        result.append(Integer.parseInt(temp, 16));
                    }
                }
            } else {
                if (c == '"') { // end of string
                    return unescapedUnicodeChars.isEmpty() ? new Token.TString(result.toString()) :
                     new Token.TString(result.toString(), unescapedUnicodeChars);
                }

                result.append(c);
            }
        }

        throw new UnterminatedStringException("The String is not terminated");
    }

    private Token lexDefault(char c) throws LiteralDoesNotExistException {
        return switch (c) {
            case 't', 'f', 'n' -> lexLiteral(c);
            default -> lexNumber(c);
        };
    }

    private Token lexLiteral(char c) throws LiteralDoesNotExistException {
        StringBuilder resultBuilder = new StringBuilder(c);

        for (int i = 0; i < 2; i++) {
            if (!iter.hasNext()) {
                throw new LiteralDoesNotExistException("$resultBuilder Literal does not exist");
            }
            resultBuilder.append(iter.next());
        }

        String result = resultBuilder.toString();

        if (result.equals("true")) {
            return new Token.TBoolean(true);
        } else if (result.equals("null")) {
            return new Token.Null();
        } else if (result.equals("fals") && iter.next() == 'e') {
            return new Token.TBoolean(false);
        }
        throw new LiteralDoesNotExistException("$result Literal does not exist");
    }

    private Token lexNumber(char c) {
        StringBuilder result = new StringBuilder(String.valueOf(c));
        char value;
        var isDecimal = false;

        while (iter.hasNext()) {
            value = iter.peek();

            if (!(Character.isDigit(value) || (value == '.' || value == 'e' || value == 'E' || value == '+' || value == '-'))) {
                if (value == '.' && isDecimal) {
                    throw new NumberFormatException("Number contains multiple dots");
                }
                break;
            }
            iter.next();
            if (value == '.' || value == 'e' || value == 'E') {
                isDecimal = true;
            }

            result.append(value);
        }

        return new Token.TNumber(result.toString());
    }

    private void consumeWhitespace() {
        while (iter.hasNext()) {
            char c = iter.peek();
            if (!Character.isWhitespace(c)) {
                break;
            }
            iter.next();
        }
    }

    class LiteralDoesNotExistException extends Exception {
        public LiteralDoesNotExistException(String message) {
            super(message);
        }
    }

    class NotPermittedCharException extends Exception{
        public NotPermittedCharException(String message) {
            super(message);
        }
    }
    
    class UnterminatedStringException extends Exception{
        public UnterminatedStringException(String message) {
            super(message);
        }
    }

    class PeekableIterator {
        private CharacterIterator iter;
        private Character lookahead = null;

        public PeekableIterator(CharacterIterator  iter) {
            this.iter = iter;
        }

        public Character next() {
            if (lookahead != null) {
                Character next = lookahead;
                lookahead = null;
                return next;
            }
            return iter.next();
        }

        public Character peek() {
            Character token = next();
            lookahead = token;
            return token;
        }

        public boolean hasNext() {
            return lookahead != null || iter.getIndex() != iter.getEndIndex();
        }
    }
}
