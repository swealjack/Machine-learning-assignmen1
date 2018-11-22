package de.uniba.cogsys.ml.assignment1.tree;

import java.util.Map;
import java.util.stream.Collectors;

public class AttributeNode implements Node {

    private final String attribute;
    private final Map<String, Node> children;

    public AttributeNode(String attribute, Map<String, Node> children) {
        this.attribute = attribute;
        this.children = children;
    }

    public String getAttribute() {
        return attribute;
    }

    public Map<String, Node> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "{" +
                "\"attribute\":\"" + attribute + '\"' +
                ", \"values\":{" + mapToJson(children) +
                "}}";
    }

    private static String mapToJson(Map<String, Node> map) {
        return map.entrySet().stream()
                  .map(entry -> "\"" + entry.getKey() + "\":" + entry.getValue().toString())
                  .collect(Collectors.joining(","));
    }
}
