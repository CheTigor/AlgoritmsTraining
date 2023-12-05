package org.example.sort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegexSort {

    /**
     * Поразрядная сортировка, средняя сложность выполнения: O(M*(N+K),
     * K = 10 - разряды,
     * N - количество элементов,
     * M - M-значные числа, то есть 734 - 3-х значное число
     */

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            //Инициализация количества элементов неотсортированного массива
            final int N1 = Integer.parseInt(br.readLine());
            String[] arr1 = new String[N1];
            //Строка для вывода проинициализированного массива (для проверки практикума)
            StringBuilder initialArray = new StringBuilder();
            if (N1 > 0) {
                for (int i = 0; i < N1; i++) {
                    arr1[i] = br.readLine();
                    initialArray.append(arr1[i]);
                    if (i < N1 - 1) {
                        initialArray.append(", ");
                    }
                }
                System.out.println("Initial array:");
                System.out.println(initialArray);
                System.out.println("**********");
                //Сортируем
                sort(arr1);
                System.out.println("Sorted array:");
                for (int i = 0; i < arr1.length; i++) {
                    System.out.print(arr1[i]);
                    if (i < arr1.length - 1) {
                        System.out.print(", ");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sort(String[] arr1) {
        //Находим максимальную разрядность по длине строки
        int maximumNumber = findMaximumNumberIn(arr1);
        for (int i = 1; i <= maximumNumber; i++) {
            //Это все выводы для красоты и проверки практикума
            System.out.println("Phase " + i);
            sortRadix(arr1, i);
            System.out.println("**********");
        }
    }

    private static void sortRadix(String[] numbers, int placeValue) {
        //Вместо сортировки подсчетом храним для каждого разряда (от 0 до 9) список наших значений, а потом собираем
        //их по порядку. Так сортировка остается стабильной
        Map<Integer, List<String>> buckets = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            buckets.put(i, new ArrayList<>());
        }
        //Берем разряд числа и по нему кладем его в нужное ведро, из-за того, что сортировка стабильная, можно запустить
        //ее для каждого разряда и получить отсортированный список.
        for (String line : numbers) {
            char digit = line.charAt(line.length() - placeValue);
            buckets.get(Character.getNumericValue(digit)).add(line);
        }
        int count = 0;
        //Перезаписываем массив в правильном порядке
        for (Integer number : buckets.keySet()) {
            if (buckets.get(number).size() == 0) {
                System.out.println("Bucket " + number + ": empty");
            } else {
                System.out.print("Bucket " + number + ": ");
                for (int i = 0; i < buckets.get(number).size(); i++) {
                    numbers[count] = buckets.get(number).get(i);
                    System.out.print(numbers[count]);
                    if (i < buckets.get(number).size() - 1) {
                        System.out.print(", ");
                    }
                    count++;
                }
                System.out.println();
            }
        }
    }

    private static int findMaximumNumberIn(String[] numbers) {
        int max = 0;
        for (String number : numbers) {
            if (number.length() > max) {
                max = number.length();
            }
        }
        return max;
    }
}
