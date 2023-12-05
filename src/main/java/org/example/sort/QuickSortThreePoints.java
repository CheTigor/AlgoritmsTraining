package org.example.sort;

import org.example.service.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class QuickSortThreePoints {

    /**
     * Сортировка похожа на быструю, но мы не создаем новые массивы, а делаем перестановки в текущем.
     * @param args
     */

    public static void start(String[] args) {
        Random rnd = new Random();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            //Инициализируем количество элементов массива (не обязательно, но нужно было для практикума)
            final int N = Integer.parseInt(br.readLine());
            if (N > 0) {
                //Вводим числа через пробел и сразу парсим в int
                List<Integer> arr = new ArrayList<>(Arrays.stream(br.readLine().split(" "))
                        .map(Integer::parseInt).toList());
                //Сортируем
                sort(arr, 0, arr.size(), rnd);
                System.out.println(Service.toPresentation(arr));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Для сортировки понадобиться 4 переменные:
     * @param arr - наш исходный массив, который мы будем разбивать в помощью счетчиков начала и конца, таким образом
     *            не создавая новый массив.
     * @param start - счетчик начала массива, указывающий с каким подмассивом работаем
     * @param stop - счетчик конца массива, указывающий с каким подмассивом работаем
     * @param rnd - класс, помогающий выбрать рандомный опорный элемент (pivot)
     */
    private static void sort(List<Integer> arr, Integer start, Integer stop, Random rnd) {
        if ((stop - start) < 2) {
            return;
        }
        //Берем рандомный опорный элемент, чтобы получить правильный индекс из подмассива, мы берем рандомное число из
        //длины подмассива, а потом к нему прибавляем стартовый индекс, получаем случайный индекс из подмассива.
        //bound работает так, он берет рандомное число от 0 (включ) до start (не включ)
        final Integer pivot = arr.get(rnd.nextInt(stop-start) + start);
        //Счетчик начала значений, которые равны опорному
        int E = start;
        //Счетчик начала значений, которые больше опорного
        int G = start;
        //Флаг, указывающий, что начальная серия меньших значений закончилась и можно начинать делать перестановки, если
        //они возникнут.
        boolean seriesMin = true;
        //Флаг, указывающий, что начальная серия равных значений закончилась и можно начинать делать перестановки, если
        //они возникнут.
        boolean seriesEqual = true;
        for (int i = start; i < stop; i++) {
            if (arr.get(i) < pivot) {
                if (!seriesMin) {
                    final int cur = arr.get(i);
                    arr.set(i, arr.get(G));
                    arr.set(G, arr.get(E));
                    arr.set(E, cur);
                }
                E += 1;
                G += 1;
            } else if (arr.get(i).equals(pivot)) {
                if (seriesMin) {
                    seriesMin = false;
                }
                if (!seriesEqual) {
                    final int cur = arr.get(i);
                    arr.set(i, arr.get(G));
                    arr.set(G, cur);
                }
                G += 1;
            } else {
                if (seriesMin) {
                    seriesMin = false;
                }
                if (seriesEqual) {
                    seriesEqual = false;
                }
            }
        }
        //Повторяем сортировку значений, меньших опорного
        sort(arr, start, E, rnd);
        //Повторяем сортировку значений, больших опорного
        sort(arr, G, stop, rnd);
    }
}
