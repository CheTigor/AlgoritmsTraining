package org.example.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringHashIsEqual {

    private final static long X = 257;
    private final static long P = (long) Math.pow(10, 9) + 7;

    /**
     * Алгоритм для расчета хэшей всей строки и сравнивание частей данной строки на равенство.
     */
    public static void main(String[] args) throws IOException {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            final String line = ' ' + br.readLine();
            long[] h = new long[line.length()];
            long[] x = new long[line.length()];
            x[0] = 1;
            for (var i = 1; i < line.length(); i++) {
                h[i] = ((h[i - 1] * X) + (long)(line.charAt(i))) % P;
                x[i] = (x[i - 1] * X) % P;
            }
            final int count = Integer.parseInt(br.readLine());
            for (var i = 0; i < count; i++) {
                //В массиве arr содержатся 3 цифры: [0] - длина строки сравнения, [1, 2] - индексы начала строк для сравнения
                List<Integer> arr = new ArrayList<>(Arrays.stream(br.readLine().split(" "))
                        .map(Integer::parseInt).toList());
                System.out.println(((h[arr.get(1) + arr.get(0)] + h[arr.get(2)] * x[arr.get(0)]) % P)
                        == ((h[arr.get(2) + arr.get(0)] + h[arr.get(1)] * x[arr.get(0)]) % P) ? "yes" : "no");
            }
        }
    }
}
