package org.example.sort;

import org.example.service.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeSort {

    /**
     * СЛОЖНОСТЬ:
     * Средняя сложность: O(N(logN))
     * Худшая сложность: O(N(logN))
     * ПАМЯТЬ:
     * Доп. память: O(N)
     * Заключение:
     * Работает стабильнее быстрой сортировки, но требует дополнительной памяти
     * ИДЕЯ:
     * Разбиваем рекурсивно массив пополам и доходим до 1 элемента, далее сливаем сортированные массивы в 1,
     * а массивы из 1 эл. уже отсортированы.
     *
     * @param args
     */
    public static void main(String[] args) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/inputSort.txt"));
        try {
            //Инициализируем количество элементов в массиве
            final int N1 = Integer.parseInt(br.readLine());
            List<Integer> arr1;
            if (N1 > 0) {
                //Инициализируме массив элементов (числа записываем через пробел) и сразу парсим в Integer
                arr1 = new ArrayList<>(Arrays.stream(br.readLine().split(" ")).map(Integer::parseInt).toList());
                //Проводим сортировку и приводим к нужному внешнему виду
                System.out.println(Service.toPresentation(mergeSort(arr1)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Integer> mergeSort(List<Integer> arr) {
        //Пока массив не из 1 элемента, продолжаем разбивать пополам
        if (arr.size() < 2) {
            return arr;
        } else {
            //Проводим сортировку для каждой части
            List<Integer> leftArr = mergeSort(arr.subList(0, arr.size() / 2));
            List<Integer> rightArr = mergeSort(arr.subList(arr.size() / 2, arr.size()));
            //Сливаем уже отсортированные массивы
            return merge(leftArr, rightArr);
        }
    }

    private static List<Integer> merge(List<Integer> arr1, List<Integer> arr2) {
        //Создаем результирующий массив
        List<Integer> out = new ArrayList<>();
        //Если нет ни одного элемента в каком-либо из массивов, то возвращаем другой массив
        if (arr1.size() == 0) {
            return arr2;
        }
        if (arr2.size() == 0) {
            return arr1;
        }
        //Создаем 2 счетчика для каждого массива
        int countArr1 = 0;
        int countArr2 = 0;
        //Флаг, указывающий, что один из массивов закончился
        boolean condition = true;
        while (condition) {
            //Начинаем сравнивать элементы массивов по порядку, если элемент первого массива меньше второго, то двигаем
            //счетчик первого элемента, и наоборот, так сравниваем элементы по порядку
            if (arr1.get(countArr1) < arr2.get(countArr2)) {
                out.add(arr1.get(countArr1));
                countArr1 += 1;
                //Условие, что счетчик дошел до конца первого массива, можно перемещать все оставшиеся значения второго
                //массива в результирующий
                if (countArr1 == arr1.size()) {
                    for (int i = countArr2; i < arr2.size(); i++) {
                        out.add(arr2.get(i));
                    }
                    condition = false;
                }
            } else {
                //То же самое для второго массива
                out.add(arr2.get(countArr2));
                countArr2 += 1;
                if (countArr2 == arr2.size()) {
                    for (int i = countArr1; i < arr1.size(); i++) {
                        out.add(arr1.get(i));
                    }
                    condition = false;
                }
            }
        }
        return out;
    }
}

