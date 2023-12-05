/*
package org.example.grafs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class DijkstraWithArrays {

    public static void main(String[] args) throws IOException {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            //ввод чисел N и K, где N - количество городов, K - количество двухсторонних дорог между ними
            final List<Integer> input = (Arrays.stream(br.readLine().split(" ")).map(Integer::parseInt))
                    .toList();

            //Создаем граф целиком и заполняем его
            List<int[]> graph = new ArrayList<>(input.get(0));
            */
/*for (int i = 1; i <= input.get(0); i++) {
                graph.put(i, new HashMap<>());
            }*//*

            //i это число K, наименование дорог с 1 до числа N
            for (int i = 1; i <= input.get(1); i++) {
                final List<Integer> rebro = (Arrays.stream(br.readLine().split(" ")).map(Integer::parseInt))
                        .toList();
                if (!graph.containsKey(rebro.get(0))) {
                    graph.put(rebro.get(0), new HashMap<>());
                }
                if (!graph.containsKey(rebro.get(1))) {
                    graph.put(rebro.get(1), new HashMap<>());
                }
                //Так как все дороги двусторонние, то добавляем соседей как для одного, так и для другого ребра
                graph.get(rebro.get(0)).put(rebro.get(1), Double .valueOf(rebro.get(2)));
                graph.get(rebro.get(1)).put(rebro.get(0), Double .valueOf(rebro.get(2)));
            }

            //Вводим какой будет маршрут, числа A(0) - начало маршрута, и B(1) - конец маршрута
            final List<Integer> trace = (Arrays.stream(br.readLine().split(" ")).map(Integer::parseInt))
                    .toList();

            //Если нужно выйти и прийти в одну и ту же точку, то сразу выдаем длину пути 0, без дальнейшего расчета
            if (trace.get(0).equals(trace.get(1))) {
                System.out.println(0);
                return;
            }
    }
}
*/
