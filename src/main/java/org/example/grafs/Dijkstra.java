package org.example.grafs;

import java.io.*;
import java.util.*;

/**
 * Это задача на поиск кратчайшего расстояния с минимальным весом с помощью графа.
 * Для этого нужно составить 3 Map: общий граф, стоимости и родители.
 * Родители и стоимости будут обновляться по ходу работы алгоритма.
 * Общий граф надо составить целиком.
 * Отличие этой задачи от "Комивояжера", то что есть конкретное начало и конец маршрута,
 * поэтому сложность растет не так сильно и составляет О(n^2), в отличии от О(n!)
 */

public class Dijkstra {

    public static void main(String[] args) throws IOException {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            List<Integer> input = (Arrays.stream(br.readLine().split(" ")).map(Integer::parseInt))
                    .toList();
            Map<Integer, Map<Integer, Double>> graph = new HashMap<>();
            for (int i = 1; i <= input.get(0); i++) {
                List<Integer> rebro = (Arrays.stream(br.readLine().split(" ")).map(Integer::parseInt))
                        .toList();
                Map<Integer, Double> neighbors = new HashMap<>();
                for (int j = 1; j <= rebro.size(); j++) {
                    if (!rebro.get(j - 1).equals(0) && !rebro.get(j - 1).equals(-1)) {
                        neighbors.put(j,  Double .valueOf(rebro.get(j - 1)));
                    }
                }
                graph.put(i, neighbors);
            }
            //Если нужно выйти и прийти в одну и ту же точку, то сразу выдаем длину пути 0, без дальнейшего расчета
            if (input.get(1).equals(input.get(2))) {
                System.out.println(input.get(2));
                return;
            }

            Map<Integer, Double> costs = new HashMap<>(graph.get(input.get(1)));
            //Для сравнения в costs нужно добавить величину веса для конечного пути. Если он уже есть в мапе, то
            //добавлять бесконечность не нужно
            if (!costs.containsKey(input.get(2))) {
                costs.put(input.get(2), Double.POSITIVE_INFINITY);
            }
            Map<Integer, Integer> parents = new HashMap<>();
            for (Integer neighbor : graph.get(input.get(1)).keySet()) {
                parents.put(neighbor, input.get(1));
            }
            //То же самое для родителя конечного пути, если он уже есть, то добавлять -1 не нужно
            if (!parents.containsKey(input.get(2))) {
                parents.put(input.get(2), -1);
            }
            final DijkstraAlg dij = new DijkstraAlg(graph, costs, parents);
            //Рассчитываем минимальный путь
            dij.findShortestWay();
            if (parents.get(input.get(2)).equals(-1)) {
                System.out.println(-1);
            } else {
                boolean flag = true;
                Integer parent = input.get(2);
                List<Integer> result = new ArrayList<>();
                while (flag) {
                    result.add(parent);
                    if (parent.equals(input.get(1))) {
                        flag = false;
                    }
                    parent = parents.get(parent);
                }
                Collections.reverse(result);
                for (int i = 0; i < result.size(); i++) {
                    System.out.print(result.get(i));
                    if (i != result.size() - 1) {
                        System.out.print(" ");
                    }
                }
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