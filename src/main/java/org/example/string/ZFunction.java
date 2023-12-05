package org.example.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ZFunction {

    /**
     * Z - функция отражает, что начиная с i элемента подряд совпало столько то символов с началом строки.
     * Например, для строки abacaba, z - функция будет иметь соедующий вид: 0 0 1 0 3 0 1.
     * Первый элемент z функции является неопределенным, так как до него нет ни одного похожего элемента. У нас
     * он равен 0.
     * Z - функция, так же как и хэши, помогают организовывать поиск по строке.
     */
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            final String line = ' ' + br.readLine();
            final StringHash stringHash = new StringHash(line);
            StringBuilder result = new StringBuilder();
            int n = line.length();
            int[] z = new int[n - 1];
            for (int i = 2; i < n; i++) {
                int left = 1, right = n - i; // текущий интервал значений
                result.append(z[i - 2]).append(" ");
                while (left <= right) {
                    // находим middle - середину интервала
                    int middle = (left + right) / 2;
                    // и проверяем, совпадают ли подстроки s[0..middle-1] и s[i..i+middle-1]
                    if (stringHash.isEqual(middle, 1, i)) {
                        // если совпадают, то ответ как минимум равен middle, и надо проверить большие значения
                        z[i - 1] = middle;
                        left = middle + 1;
                    } else {
                        // если не совпадают, надо проверить меньшие значения
                        right = middle - 1;
                    }
                }
            }
            result.append(z[z.length - 1]);
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
