package br.bemobi.utils;

public class Base62 {
    private static final char[]  ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private static final int BASE = ALPHABET.length;
    public static String base10To62(long number)
    {
        if (number == 0) {
            return "0";
        }
        StringBuilder buf = new StringBuilder();
        while (number != 0) {
            buf.append(ALPHABET[(int) (number % BASE)]);
            number /= BASE;
        }
        return buf.reverse().toString();
    }

}