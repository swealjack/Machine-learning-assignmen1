package de.uniba.cogsys.ml.assignment1;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.xml.crypto.Data;

import de.uniba.cogsys.ml.assignment1.dataset.Attribute;
import de.uniba.cogsys.ml.assignment1.dataset.DataSet;
import de.uniba.cogsys.ml.assignment1.tree.AttributeNode;
import de.uniba.cogsys.ml.assignment1.tree.ClassificationNode;
import de.uniba.cogsys.ml.assignment1.tree.Node;

public class ID3 {


    public static Node learn(DataSet dataSet, String targetClass) {
        if (dataSet.isEveryValueEqual(targetClass)) {
            return new ClassificationNode(dataSet.getExamples().stream()
                                                 .map(e -> e.getAttribute(targetClass))
                                                 .findAny()
                                                 .orElse(null));
        }

        if (dataSet.getAttributes().size() <= 1) {
            return new ClassificationNode(dataSet.getMostFrequentValue(targetClass));
        }

        Attribute target = dataSet.getAttributes().stream()
                                  .filter(a -> a.getName().equals(targetClass))
                                  .findAny()
                                  .orElse(null);

        HashMap<Attribute, Double> informationGains = new HashMap<>();
        dataSet.getAttributes().stream()
               .filter(a -> a != target)
               .forEach(attribute -> informationGains.compute(attribute, (a, val) -> informationGain(dataSet, target, a)));


        double infoGain = 0;
        Attribute maxInfoGain = null;

        for (Map.Entry<Attribute, Double> entry : informationGains.entrySet()) {
            if (entry.getValue() > infoGain) {
                infoGain = entry.getValue();
                maxInfoGain = entry.getKey();
                continue;
            }

            if (Math.round(infoGain * 10000) / 10000.0 == Math.round(entry.getValue() * 10000) / 10000.0) {
                if (maxInfoGain != null && maxInfoGain.getName().compareTo(entry.getKey().getName()) > 0) {
                    maxInfoGain = entry.getKey();
                    continue;
                }
            }
        }

        AttributeNode node = new AttributeNode(maxInfoGain.getName(), new HashMap<>());

        for (String value : maxInfoGain.getValues()) {
            DataSet filtered = dataSet.filterByAttributeValue(maxInfoGain, value);

            if (filtered.getExamples().isEmpty()) {
                node.getChildren().put(value, new ClassificationNode(dataSet.getMostFrequentValue(targetClass)));
            } else {
                node.getChildren().put(value, learn(filtered.filterByAttribute(maxInfoGain), targetClass));
            }
        }

        return node;
    }

    public static double entropy(DataSet dataSet, Attribute target) {
        double sum = 0;

        for (String value : target.getValues()) {
            DataSet filtered = dataSet.filterByAttributeValue(target, value);

            if (filtered.getExamples().isEmpty()) {
                continue;
            }

            double pi = ((double) filtered.getExamples().size()) / dataSet.getExamples().size();

            sum -= pi * log2(pi);
        }

        return sum;
    }

    public static double informationGain(DataSet dataSet, Attribute target, Attribute attribute) {
        double result = entropy(dataSet, target);

        for (String value : attribute.getValues()) {
            DataSet filtered = dataSet.filterByAttributeValue(attribute, value);

            result -= ((double) filtered.getExamples().size()) / dataSet.getExamples().size() * entropy(filtered, target);
        }

        return result;
    }

    private static double log2(double value) {
        return Math.log(value) / Math.log(2);
    }

}
