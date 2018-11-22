package de.uniba.cogsys.ml.assignment1.tree;

public class ClassificationNode implements Node {

    private final String classification;

    public ClassificationNode(String classification) {
        this.classification = classification;
    }

    public String getClassification() {
        return classification;
    }

    @Override
    public String toString() {
        return "{" +
                "\"classification\":\"" + classification + '\"' +
                '}';
    }
}
