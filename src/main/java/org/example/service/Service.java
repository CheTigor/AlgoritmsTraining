package org.example.service;

import java.util.List;

public class Service {

    public static String toPresentation(List<Integer> arr) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < arr.size(); i++) {
            if (i == arr.size() - 1) {
                sb.append(arr.get(i));
            } else {
                sb.append(arr.get(i));
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}
