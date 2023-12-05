package org.example.string;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CubsInTheMirror {

    /**
     * Представим, что в зеркале отображаются все числа, которые мы введем + несколько, которые перед зеркалом.
     * Задача найти какое количество цифр есть в действительности.
     * Для работы алгоритма надо ввести в первой строке 2 числа: 1 - количество цифр, 2 - количество различных цифр.
     * *Эти данные не требуются для работы алгоритма, они были нужны для задания Яндекс Практикума.
     * Во второй строке вводим через пробел числа. Ответом будет несколько чисел через пробел - количество
     * возможных чисел.
     * Реализован поиск зеркального отражения всех префиксов по хэшам с реверсом.
     */
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/inputCubs.txt"))) {
            String counts = br.readLine();
            List<Integer> digits = new ArrayList<>(Arrays.stream(br.readLine().split(" "))
                    .map(Integer::parseInt).toList());
            final StringHash sH = new StringHash(digits);
            StringBuilder result = new StringBuilder();
            for (int i = digits.size() / 2; i > 0; i--) {
                if (sH.isEqualRev(i, 0, i)) {
                    result.append(digits.size() - i).append(" ");
                }
            }
            result.append(digits.size());
            System.out.println(result);
        }
    }

    private static class StringHash {

        private final long X = 257;
        private final long P = (long) Math.pow(10, 9) + 7;
        private final long[] hDir;
        private final long[] hRev;
        private final long[] x;

        public StringHash(List<Integer> digits) {
            hDir = new long[digits.size() + 1];
            hRev = new long[digits.size() + 1];
            x = new long[digits.size() + 1];
            x[0] = 1;
            for (var i = 1; i < digits.size() + 1; i++) {
                hDir[i] = ((hDir[i - 1] * X) + digits.get(i - 1)) % P;
                hRev[i] = ((hRev[i - 1] * X) + (long) digits.get(digits.size() - i)) % P;
                x[i] = (x[i - 1] * X) % P;
            }
        }

        private boolean isEqual(int len, int from1, int from2) {
            return (hDir[from1 + len] + hDir[from2] * x[len]) % P
                    == (hDir[from2 + len] + hDir[from1] * x[len]) % P;
        }

        private boolean isEqualRev(int len, int from1, int from2) {
            long one = (hDir[from1 + len] + hRev[hRev.length - 1 - from2 - len] * x[len]) % P;
            long two = (hRev[hRev.length - 1 - len] + hDir[from1] * x[len]) % P;
            return one == two;
        }
    }
}





