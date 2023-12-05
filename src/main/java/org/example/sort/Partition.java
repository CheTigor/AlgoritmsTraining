package org.example.sort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Partition {

    /**
     * Задание для нахождения, сколько элементов массива больше опорного и меньше.
     * Тренировочное задание
     */
    public void start() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            int N = Integer.parseInt(br.readLine());
            if (N > 0) {
                List<Integer> arr = Arrays.stream(br.readLine().split(" ")).map(Integer::parseInt).toList();
                Integer pivot = Integer.parseInt(br.readLine());
                br.close();
                List<Integer> answer = minMaxCount(arr, pivot);
                System.out.println(answer.get(0) + "\n" + answer.get(1));
            } else {
                System.out.println("0" + "\n" + "0");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Integer> minMaxCount(List<Integer> arr, Integer pivot) {
        List<Integer> less = new ArrayList<>();
        List<Integer> greater = new ArrayList<>();
        for (Integer integer : arr) {
            if (integer < pivot) {
                less.add(integer);
            } else {
                greater.add(integer);
            }
        }
        return Arrays.asList(less.size(), greater.size());
    }
}
