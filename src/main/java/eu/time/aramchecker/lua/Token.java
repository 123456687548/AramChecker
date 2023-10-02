package eu.time.aramchecker.lua;

import java.util.List;

public class Token {
    public static class LCURLY extends Token {
        @Override
        public String toString() {
            return "{";
        }
    }

    public static class RCURLY extends Token {
        @Override
        public String toString() {
            return "}";
        }
    }

    public static class COMMA extends Token {
        @Override
        public String toString() {
            return ",";
        }
    }

    public static class LSQUARE extends Token {
        @Override
        public String toString() {
            return "[";
        }
    }

    public static class RSQUARE extends Token {
        @Override
        public String toString() {
            return "]";
        }
    }

    public static class ASSIGN extends Token {
        @Override
        public String toString() {
            return "=";
        }
    }

    public static class TString extends Token {
        String value;
        List<Integer> unescapedUnicodeChars;

        public TString(String value) {
            this.value = value;
        }

        public TString(String value, List<Integer> unescapedUnicodeChars) {
            this.value = value;
            this.unescapedUnicodeChars = unescapedUnicodeChars;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public static class TBoolean extends Token {
        boolean value;

        public TBoolean(boolean value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    public static class TNumber extends Token {
        String value;

        public TNumber(String value) {
            this.value = value;
        }
        @Override
        public String toString() {
            return value;
        }
    }

    public static class Null extends Token {
        @Override
        public String toString() {
            return "null";
        }
    }
    
    public static class EOF extends Token {
        @Override
        public String toString() {
            return "EOF";
        }
    }
}
