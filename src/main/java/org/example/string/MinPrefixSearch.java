package org.example.string;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MinPrefixSearch {

    /**
     * Алгоритм находит равные максимальные суффиксы и префиксы, результатом будет нахождение
     * минимально возможной подстроки, из которой состоит строка с помощью повторов подстроки
     */
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/inputPrefix.txt"))) {
            final String line = ' ' + br.readLine();
            final StringHash stringHash = new StringHash(line);
            int result = line.length() - 1;
            for (int i = 2; i < line.length(); i++) {
                if (stringHash.isEqual(line.length() - i, 1, i)) {
                    result = i - 1;
                    break;
                }
            }
            System.out.println(result);
        }
    }

    private static class StringHash {

        private final long X = 257;
        private final long P = (long) Math.pow(10, 9) + 7;
        private final long[] h;
        private final long[] x;

        public StringHash(String line) {
            h = new long[line.length()];
            x = new long[line.length()];
            x[0] = 1;
            for (var i = 1; i < line.length(); i++) {
                h[i] = ((h[i - 1] * X) + (long) (line.charAt(i))) % P;
                x[i] = (x[i - 1] * X) % P;
            }
        }

        private boolean isEqual(int len, int from1, int from2) {
            return (h[from1 + len - 1] + h[from2 - 1] * x[len]) % P
                    == (h[from2 + len - 1] + h[from1 - 1] * x[len]) % P;
        }
    }
}
