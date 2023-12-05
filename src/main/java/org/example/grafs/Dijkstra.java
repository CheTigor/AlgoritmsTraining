package org.example.grafs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Dijkstra {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/input.txt"))) {
            //ввод чисел N и K, где N - количество городов, K - количество двухсторонних дорог между ними
            final List<Integer> input = (Arrays.stream(br.readLine().split(" ")).map(Integer::parseInt))
                    .toList();

            //Рассчитываем величину мапы, с учетом, что коэффициент заполнения 0.75
            double length = (input.get(0) * 1.4);

            //Создаем граф целиком и заполняем его
            Map<Integer, Map<Integer, Long>> graph = new HashMap<>((int) length);
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
                graph.get(rebro.get(0)).put(rebro.get(1), Long.valueOf(rebro.get(2)));
                graph.get(rebro.get(1)).put(rebro.get(0), Long.valueOf(rebro.get(2)));
            }

            //Вводим какой будет маршрут, числа A(0) - начало маршрута, и B(1) - конец маршрута
            final List<Integer> trace = (Arrays.stream(br.readLine().split(" ")).map(Integer::parseInt))
                    .toList();

            //Если нужно выйти и прийти в одну и ту же точку, то сразу выдаем длину пути 0, без дальнейшего расчета
            if (trace.get(0).equals(trace.get(1))) {
                System.out.println(0);
                return;
            }
            //Если из стартовой точки некуда выйти, то сразу возвращаем -1
            if (graph.get(trace.get(0)) == null) {
                System.out.println(-1);
                return;
            }

            //Добавляем в costs величину маршрута до ближайших соседей первого маршрута, это будет отправной точкой
            Map<Integer, Long> costs = new HashMap<>((int) length);
            costs.putAll(graph.get(trace.get(0)));
            //Для сравнения в costs нужно добавить величину веса для конечного пути. Если он уже есть в мапе, то
            //добавлять бесконечность не нужно
            if (!costs.containsKey(trace.get(1))) {
                costs.put(trace.get(1), Long.MAX_VALUE);
            }
            //Добавляем в качестве родителя начальный пункт у соседей, они будут обновляться при появлении более
            //быстрого пути
            Map<Integer, Integer> parents = new HashMap<>((int) length);
            for (Integer neighbor : graph.get(trace.get(0)).keySet()) {
                parents.put(neighbor, trace.get(0));
            }
            //То же самое для родителя конечного пути, если он уже есть, то добавлять -1 не нужно
            if (!parents.containsKey(trace.get(1))) {
                parents.put(trace.get(1), -1);
            }

            //Рассчитываем минимальный путь
            final DijkstraAlg dij = new DijkstraAlg(graph, costs, parents, input.get(0));
            dij.findShortestWay();

            //Если родитель равен -1, значит алгоритм не смог найти путь к конечному пункту
            if (parents.get(trace.get(1)).equals(-1)) {
                System.out.println(-1);
            } else {
                //Выводим кратчайшее расстояние
                System.out.println(costs.get(trace.get(1)).longValue());
            }
        }
    }


    private static class DijkstraAlg {

        private final Map<Integer, Map<Integer, Long>> graph;
        private final Map<Integer, Long> costs;
        private final Map<Integer, Integer> parents;
        Queue<Cost> queue = new PriorityQueue<>(Comparator.comparing(Cost::getCost));
        //Список для отработанных узлов, позволяет рекурсивно пройтись по графу
        private final boolean[] processed;

        public DijkstraAlg(Map<Integer, Map<Integer, Long>> graph, Map<Integer, Long> costs,
                           Map<Integer, Integer> parents, Integer length) {
            this.graph = graph;
            this.costs = costs;
            this.parents = parents;
            if (costs.size() != 0) {
                for (Map.Entry<Integer, Long> cost : costs.entrySet()) {
                    queue.add(new Cost(cost.getKey(), cost.getValue()));
                }
            }
            processed = new boolean[length + 1];
        }

        public void findShortestWay() {
            //Идея в том, чтобы получить всех соседей ребра и сравнивать новые веса с уже имеющимся
            Integer node = queue.poll().getRebro();
            while (node != null) {
                final Long cost = costs.get(node);
                final Map<Integer, Long> neighbors = graph.get(node);
                if (neighbors != null) {
                    for (Integer key : neighbors.keySet()) {
                        final Long newCost = cost + neighbors.get(key);
                        if (processed[key]) {
                            continue;
                        }
                        if (costs.get(key) == null) {
                            costs.put(key, newCost);
                            queue.add(new Cost(key, newCost));
                            parents.put(key, node);
                            continue;
                        }
                        if (costs.get(key) > newCost) {
                            queue.add(new Cost(key, newCost));
                            costs.put(key, newCost);
                            parents.put(key, node);
                        }
                    }
                }
                processed[node] = true;
                boolean flag = true;
                while (processed[node]) {
                    final Cost newCost = queue.poll();
                    if (newCost == null) {
                        return;
                    } else {
                        node = newCost.getRebro();
                    }
                }
            }
        }

        private static class Cost {

            private final Integer rebro;
            private final Long cost;

            public Cost(Integer rebro, Long cost) {
                this.rebro = rebro;
                this.cost = cost;
            }

            public Integer getRebro() {
                return rebro;
            }

            public Long getCost() {
                return cost;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Cost cost1 = (Cost) o;
                return Objects.equals(rebro, cost1.rebro) && Objects.equals(cost, cost1.cost);
            }

            @Override
            public int hashCode() {
                return Objects.hash(rebro, cost);
            }

            @Override
            public String toString() {
                return "Cost{" +
                        "rebro=" + rebro +
                        ", cost=" + cost +
                        '}';
            }
        }
    }
}
