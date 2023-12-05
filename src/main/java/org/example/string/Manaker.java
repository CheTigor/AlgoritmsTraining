package org.example.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Manaker {

    /**
     * Алгоритм Манакера для нахождения всех палиндромов в строке. Идея заключается в том, чтобы найти радиус совпадения
     * символов как для нечетных длин строк, так и для четных. Здесь хэши не используются.
     * Для проверки алгоритма надо ввести строку, пример: aba;
     * Ответом будет все варианты подстрок, которые являются палиндромами, в примере ответ: a, b, a, aba.
     */
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input = br.readLine();
            String formattedInput = "@" + input + "#";
            int[][] radius = new int[2][input.length() + 1];
            List<String> palindromes = new ArrayList<>();
            for (int i = 1; i < formattedInput.length() - 1; i ++) {
                palindromes.add(Character.toString(formattedInput.charAt(i)));
            }
            int max;
            for (int j = 0; j <= 1; j++) {
                radius[j][0] = max = 0;
                int i = 1;
                while (i <= input.length()) {
                    while (formattedInput.charAt(i - max - 1) == formattedInput.charAt(i + j + max)) {
                        max++;
                    }
                    radius[j][i] = max;
                    int k = 1;
                    while ((radius[j][i - k] != max - k) && (k < max)) {
                        radius[j][i + k] = Math.min(radius[j][i - k], max - k);
                        k++;
                    }
                    max = Math.max(max - k, 0);
                    i += k;
                }
            }
            for (int i = 1; i <= input.length(); i++) {
                for (int j = 0; j <= 1; j++) {
                    for (max = radius[j][i]; max > 0; max--) {
                        palindromes.add(input.substring(i - max - 1, max + j + i - 1));
                    }
                }
            }
            System.out.println(palindromes);
        }
    }
}
