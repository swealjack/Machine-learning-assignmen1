package de.uniba.cogsys.ml.assignment1.tree;

import java.util.Map;

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
                ", \"children\":[" + children +
                "]}";
    }
}
