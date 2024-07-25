package com.bekvon.bukkit.residence.utils;

import com.bekvon.bukkit.residence.protection.ClaimedResidence;

import java.util.*;
import java.util.Map.Entry;

public class Sorting {

    public Map<String, Integer> sortByValueASC(Map<String, Integer> unsortMap) {

        // Convert Map to List
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());

        // Sort list with comparator, to compare the Map values
        list.sort((o1, o2) -> (o1.getValue()).compareTo(o2.getValue()));

        // Convert sorted map back to a Map
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public Map<String, Integer> sortByValueDESC(Map<String, Integer> unsortMap) {

        // Convert Map to List
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());

        // Sort list with comparator, to compare the Map values
        list.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

        // Convert sorted map back to a Map
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public Map<String, Integer> sortByKeyDESC(Map<String, Integer> unsortMap) {

        // Convert Map to List
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());

        // Sort list with comparator, to compare the Map values
        list.sort((o1, o2) -> (o2.getKey()).compareTo(o1.getKey()));

        // Convert sorted map back to a Map
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public List<ClaimedResidence> sortResidences(List<ClaimedResidence> residences) {
        Map<String, Object> map = new HashMap<>();
        for (ClaimedResidence one : residences) {
            if (one == null)
                continue;
            if (one.getName() == null)
                continue;
            map.put(one.getName().toLowerCase(), one);
        }
        map = sortByKeyASC(map);
        residences.clear();
        for (Entry<String, Object> one : map.entrySet()) {
            residences.add((ClaimedResidence) one.getValue());
        }
        return residences;
    }

    public Map<String, Object> sortByKeyASC(Map<String, Object> unsortMap) {

        // Convert Map to List
        List<Map.Entry<String, Object>> list = new LinkedList<>(unsortMap.entrySet());

        // Sort list with comparator, to compare the Map values
        list.sort((o1, o2) -> (o1.getKey()).compareTo(o2.getKey()));

        // Convert sorted map back to a Map
        Map<String, Object> sortedMap = new LinkedHashMap<>();
        for (Entry<String, Object> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public Map<String, String> sortStringByKeyASC(Map<String, String> unsortMap) {

        // Convert Map to List
        List<Map.Entry<String, String>> list = new LinkedList<>(unsortMap.entrySet());

        // Sort list with comparator, to compare the Map values
        list.sort((o1, o2) -> (o1.getKey()).compareTo(o2.getKey()));

        // Convert sorted map back to a Map
        Map<String, String> sortedMap = new LinkedHashMap<>();
        for (Entry<String, String> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public Map<String, Double> sortDoubleDESC(Map<String, Double> unsortMap) {

        // Convert Map to List
        List<Map.Entry<String, Double>> list = new LinkedList<>(unsortMap.entrySet());

        // Sort list with comparator, to compare the Map values
        list.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

        // Convert sorted map back to a Map
        Map<String, Double> sortedMap = new LinkedHashMap<>();
        for (Entry<String, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public Map<String, Integer> sortASC(Map<String, Integer> unsortMap) {

        // Convert Map to List
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());

        // Sort list with comparator, to compare the Map values
        list.sort((o1, o2) -> (o1.getValue()).compareTo(o2.getValue()));

        // Convert sorted map back to a Map
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}
