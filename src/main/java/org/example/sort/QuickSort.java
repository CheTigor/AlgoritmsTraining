package org.example.sort;

import org.example.service.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class QuickSort {


    /**
     * СЛОЖНОСТЬ:
     * Средняя сложность: O(Nlog(N)
     * Худшая сложность: O(N^2)
     * NlogN - так как количество элементов N, а количество делений массива на подмассивы: log(N)
     * В худшем случае деление массива будет N раз, поэтому N^2 - худшая сложность
     * ПАМЯТЬ:
     * Доп. память: О(log(N) или O(N)) -
     * ИДЕЯ:
     * Берем в массиве рандомный опорный элемент, далее сравниваем каждый элемент с ним и составляем 3 массива,
     * один - элементы которого меньше опорного (less), второй - больше (greater), третий - равны (equal).
     * Повторяем до одного элемента, потом соединяем в результирующий.
     *
     * @param args
     */
    public static void main(String[] args) throws FileNotFoundException {
        Random rnd = new Random();
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/inputSort.txt"));
        try {
            //Инициализируем количество элементов массива (в данном задание почти не используется)
            final int N = Integer.parseInt(br.readLine());
            if (N > 0) {
                //Перечисляем элементы массива через пробел и тут же парсим в Integer
                List<Integer> arr = Arrays.stream(br.readLine().split(" ")).map(Integer::parseInt).toList();
                br.close();
                //Проводим сортировку
                System.out.println(Service.toPresentation(sort(arr, rnd)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param arr - это массив, который разделяем на 3 подмассива. Это не исходный массив, а новый.
     * @param rnd - класс для создания случайного опорного элемента
     * @return - возвращаем отсортированный массив. В рекурсивном случае в массиве будет 1 элемент.
     */
    private static List<Integer> sort(List<Integer> arr, Random rnd) {
        //Рекурсивный случай - длина массива меньше 2. То есть сортируем массив на большие, меньшие и равные части,
        // пока не останется 1 элемент в массиве
        if (arr.size() < 2) {
            return arr;
        }
        //Берем рандомный элемент массива
        final Integer pivot = arr.get(rnd.nextInt(arr.size()));
        List<Integer> less = new ArrayList<>();
        List<Integer> greater = new ArrayList<>();
        List<Integer> equal = new ArrayList<>();
        //Все просто, заполняем 3 массива, далее проводим для каждого сортировку и соединяем в финале
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) < pivot) {
                less.add(arr.get(i));
            } else if (arr.get(i).equals(pivot)) {
                equal.add(arr.get(i));
            } else {
                greater.add(arr.get(i));
            }
        }
        less = sort(less, rnd);
        less.addAll(equal);
        greater = sort(greater, rnd);
        less.addAll(greater);
        return less;
    }
}
