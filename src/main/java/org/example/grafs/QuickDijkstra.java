package org.example.grafs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class QuickDijkstra {

    public static void main(String[] args) throws IOException {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            //ввод чисел N и K, где N - количество городов, K - количество двухсторонних дорог между ними
            final List<Integer> input = (Arrays.stream(br.readLine().split(" ")).map(Integer::parseInt))
                    .toList();

            //Создаем граф целиком и заполняем его
            Map<Integer, Map<Integer, Double>> graph = new HashMap<>();
            /*for (int i = 1; i <= input.get(0); i++) {
                graph.put(i, new HashMap<>());
            }*/
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

            //Добавляем в costs величину маршрута до ближайших соседей первого маршрута, это будет отправной точкой
            Map<Integer, Double> costs = new HashMap<>(graph.get(trace.get(0)));
            //Для сравнения в costs нужно добавить величину веса для конечного пути. Если он уже есть в мапе, то
            //добавлять бесконечность не нужно
            if (!costs.containsKey(trace.get(1))) {
                costs.put(trace.get(1), Double.POSITIVE_INFINITY);
            }
            //Добавляем в качестве родителя начальный пункт у соседей, они будут обновляться при появлении более
            //быстрого пути
            Map<Integer, Integer> parents = new HashMap<>();
            for (Integer neighbor : graph.get(trace.get(0)).keySet()) {
                parents.put(neighbor, trace.get(0));
            }
            //То же самое для родителя конечного пути, если он уже есть, то добавлять -1 не нужно
            if (!parents.containsKey(trace.get(1))) {
                parents.put(trace.get(1), -1);
            }

            //Рассчитываем минимальный путь
            final DijkstraAlg dij = new DijkstraAlg(graph, costs, parents);
            dij.findShortestWay();

            //Если родитель равен -1, значит алгоритм не смог найти путь к конечному пункту
            if (parents.get(trace.get(1)).equals(-1)) {
                System.out.println(-1);
            } else {
                //Выводим кратчайшее расстояние
                System.out.println(costs.get(trace.get(1)).intValue());
            }
        }
    }



    private static class DijkstraAlg {

        private final Map<Integer, Map<Integer, Double>> graph;
        private final Map<Integer, Double> costs;
        private final Map<Integer, Integer> parents;
        //Список для отработанных узлов, позволяет рекурсивно пройтись по графу
        private final List<Integer> processed = new ArrayList<>();

        public DijkstraAlg(Map<Integer, Map<Integer, Double>> graph, Map<Integer, Double> costs, Map<Integer, Integer> parents) {
            this.graph = graph;
            this.costs = costs;
            this.parents = parents;
        }

        public void findShortestWay() {
            Integer node = findLowestCostNode();
            while (node != null) {
                final Double cost = costs.get(node);
                final Map<Integer, Double> neighbors = graph.get(node);
                for (Integer key : neighbors.keySet()) {
                    final Double newCost = cost + neighbors.get(key);
                    if (costs.get(key) == null) {
                        costs.put(key, newCost);
                        parents.put(key, node);
                        continue;
                    }
                    if (costs.get(key) > newCost) {
                        costs.put(key, newCost);
                        parents.put(key, node);
                    }
                }
                processed.add(node);
                node = findLowestCostNode();
            }
        }

        private Integer findLowestCostNode() {
            Double lowestCost = Double.POSITIVE_INFINITY;
            Integer lowestCostNode = null;
            for (Integer node : costs.keySet()) {
                Double cost = costs.get(node);
                if (cost < lowestCost && !processed.contains(node)) {
                    lowestCost = cost;
                    lowestCostNode = node;
                }
            }
            return lowestCostNode;
        }
    }
}
