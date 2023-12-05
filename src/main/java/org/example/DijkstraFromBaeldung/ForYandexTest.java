package org.example.DijkstraFromBaeldung;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ForYandexTest {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/input.txt"))) {
            //ввод чисел N и K, где N - количество городов, K - количество двухсторонних дорог между ними
            final List<Integer> input = (Arrays.stream(br.readLine().split(" ")).map(Integer::parseInt))
                    .toList();

            //Создаем граф целиком и заполняем его
            Graph graph = new Graph();
            List<Node> nodes = new ArrayList<>();
            for (int i = 1; i <= input.get(0); i++) {
                nodes.add(new Node(String.valueOf(i)));
            }
            //i это число K, наименование дорог с 1 до числа N
            for (int i = 1; i <= input.get(1); i++) {
                final List<Integer> rebro = (Arrays.stream(br.readLine().split(" ")).map(Integer::parseInt))
                        .toList();
                //Так как все дороги двусторонние, то добавляем соседей как для одного, так и для другого ребра
                nodes.get(rebro.get(0) - 1).addDestination(nodes.get(rebro.get(1) - 1), rebro.get(2));
                nodes.get(rebro.get(1) - 1).addDestination(nodes.get(rebro.get(0) - 1), rebro.get(2));
            }

            //Вводим какой будет маршрут, числа A(0) - начало маршрута, и B(1) - конец маршрута
            final List<Integer> trace = (Arrays.stream(br.readLine().split(" ")).map(Integer::parseInt))
                    .toList();

            //Если нужно выйти и прийти в одну и ту же точку, то сразу выдаем длину пути 0, без дальнейшего расчета
            if (trace.get(0).equals(trace.get(1))) {
                System.out.println(0);
                return;
            }

            for (Node node : nodes) {
                graph.addNode(node);
            }

            Dijkstra.calculateShortestPathFromSource(graph, nodes.get(trace.get(0) - 1));
            System.out.println(nodes.get(trace.get(1) - 1));
        }
    }

    private static class Graph {

        private Set<Node> nodes = new HashSet<>();

        public void addNode(Node nodeA) {
            nodes.add(nodeA);
        }

        public Set<Node> getNodes() {
            return nodes;
        }

        public void setNodes(Set<Node> nodes) {
            this.nodes = nodes;
        }

        @Override
        public String toString() {
            return "Graph{" +
                    "nodes=" + nodes +
                    '}';
        }
    }

    private static class Dijkstra {

        public static Graph calculateShortestPathFromSource(Graph graph, Node source) {

            source.setDistance(0);

            Set<Node> settledNodes = new HashSet<>();
            Set<Node> unsettledNodes = new HashSet<>();
            unsettledNodes.add(source);

            while (unsettledNodes.size() != 0) {
                Node currentNode = getLowestDistanceNode(unsettledNodes);
                unsettledNodes.remove(currentNode);
                for (Map.Entry<Node, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                    Node adjacentNode = adjacencyPair.getKey();
                    Integer edgeWeigh = adjacencyPair.getValue();
                    if (!settledNodes.contains(adjacentNode)) {
                        CalculateMinimumDistance(adjacentNode, edgeWeigh, currentNode);
                        unsettledNodes.add(adjacentNode);
                    }
                }
                settledNodes.add(currentNode);
            }
            return graph;
        }

        private static void CalculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
            Integer sourceDistance = sourceNode.getDistance();
            if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
                evaluationNode.setDistance(sourceDistance + edgeWeigh);
                LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
                shortestPath.add(sourceNode);
                evaluationNode.setShortestPath(shortestPath);
            }
        }

        private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
            Node lowestDistanceNode = null;
            int lowestDistance = Integer.MAX_VALUE;
            for (Node node : unsettledNodes) {
                int nodeDistance = node.getDistance();
                if (nodeDistance < lowestDistance) {
                    lowestDistance = nodeDistance;
                    lowestDistanceNode = node;
                }
            }
            return lowestDistanceNode;
        }
    }

    private static class Node {

        private String name;

        private LinkedList<Node> shortestPath = new LinkedList<>();

        private Integer distance = Integer.MAX_VALUE;

        private Map<Node, Integer> adjacentNodes = new HashMap<>();

        public Node(String name) {
            this.name = name;
        }

        public void addDestination(Node destination, int distance) {
            adjacentNodes.put(destination, distance);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<Node, Integer> getAdjacentNodes() {
            return adjacentNodes;
        }

        public void setAdjacentNodes(Map<Node, Integer> adjacentNodes) {
            this.adjacentNodes = adjacentNodes;
        }

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }

        public List<Node> getShortestPath() {
            return shortestPath;
        }

        public void setShortestPath(LinkedList<Node> shortestPath) {
            this.shortestPath = shortestPath;
        }

        @Override
        public String toString() {
            return String.valueOf(distance);
        }
    }
}
