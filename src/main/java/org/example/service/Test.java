package org.example.service;

import java.util.*;

public class Test {
    public static void main(String[] args) {

        Set<Cost> set = new TreeSet<>(Comparator.comparing(Cost::getCost));
        set.add(new Cost(5, 10.0, false));
        set.add(new Cost(6, 9.0, false));
        set.add(new Cost(3, 11.0, false));
        set.add(new Cost(2, 2.0, false));
        Queue<Cost> queue = new PriorityQueue<>(Comparator.comparing(Cost::getCost));
        queue.add(new Cost(5, 10.0, false));
        queue.add(new Cost(6, 9.0, false));
        queue.add(new Cost(3, 100.0, false));
        queue.add(new Cost(2, 2.0, false));
        System.out.println(queue);
        System.out.println(queue.peek());
        queue.remove(new Cost(5, 10.0, false));
        /*SortedSet<Map.Entry<Integer, Double>> sortedset = new TreeSet<>(Map.Entry.comparingByValue());
        SortedMap<Integer, Double> myMap = new TreeMap<>();
        myMap.put(5, 10.0);
        myMap.put(6, 9.0);
        myMap.put(3, 11.0);
        myMap.put(2, 2.0);
        sortedset.addAll(myMap.entrySet());
        sortedset.remove(new );
        System.out.println(sortedset);*/
    }

    static class CustomIntegerComparator implements Comparator<Cost> {

        @Override
        public int compare(Cost o1, Cost o2) {
            return o2.getCost().compareTo(o1.getCost());
        }
    }

    public static class Cost {

        private final Integer rib;
        private final Double cost;
        private Boolean visited;

        public Cost(Integer rib, Double cost, Boolean visited) {
            this.rib = rib;
            this.cost = cost;
            this.visited = visited;
        }

        public Integer getRib() {
            return rib;
        }

        public Double getCost() {
            return cost;
        }

        public Boolean getVisited() {
            return visited;
        }

        public void setVisited(Boolean visited) {
            this.visited = visited;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cost cost1 = (Cost) o;
            return Objects.equals(rib, cost1.rib) && Objects.equals(cost, cost1.cost);
        }

        @Override
        public int hashCode() {
            return Objects.hash(rib, cost);
        }

        @Override
        public String toString() {
            return "Cost{" +
                    "rebro=" + rib +
                    ", cost=" + cost +
                    '}';
        }
    }
}
