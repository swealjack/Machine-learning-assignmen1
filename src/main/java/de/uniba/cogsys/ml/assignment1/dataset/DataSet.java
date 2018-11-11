package de.uniba.cogsys.ml.assignment1.dataset;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * {@link DataSet} represents a whole data set of examples with certain attributes. The target class should be included
 * as attribute.
 *
 * Objects of this class are immutable.
 */
public final class DataSet {

    private final Set<Attribute> attributes;
    private final List<Example> examples;

    DataSet(Set<Attribute> attributes, List<Example> examples) {
        this.attributes = attributes;
        this.examples = examples;
    }

    /**
     * @return all {@link Attribute Attributes} this {@link DataSet} has.
     */
    public Set<Attribute> getAttributes() {
        return Collections.unmodifiableSet(attributes);
    }

    /**
     * @return all {@link Example Examples} this {@link DataSet} contains.
     */
    public List<Example> getExamples() {
        return Collections.unmodifiableList(examples);
    }

    /**
     * Filters the {@link DataSet} by the given {@link Attribute} and returns a new {@link DataSet} without the given
     * {@link Attribute}.
     *
     * @param   attribute   the {@link Attribute} to filter by.
     *
     * @return  the filtered {@link DataSet}.
     */
    public DataSet filterByAttribute(Attribute attribute) {
        return new DataSet(
                attributes.stream()
                          .filter(a -> !a.equals(attribute))
                          .collect(Collectors.toSet()),
                examples);
    }

    /**
     * Filters the {@link DataSet} by the given {@link Attribute} and its value. The returned {@link DataSet} will only
     * contain {@link Example Examples} that have the given value as value for the given {@link Attribute}.
     *
     * @param   attribute   {@link Attribute} to filter by.
     * @param   value       value of the {@link Attribute} to filter by.
     *
     * @return  the filtered {@link DataSet} with the {@link Example Examples} with {@code value} for {@code attribute}.
     */
    public DataSet filterByAttributeValue(Attribute attribute, String value) {
        return new DataSet(
                attributes,
                examples.stream()
                        .filter(ex -> ex.getAttribute(attribute.getName()).equals(value))
                        .collect(Collectors.toList()));
    }

    /**
     * @param   attribute   the {@link Attribute} to check the values of.
     * @return  whether all values of the given {@link Attribute} are the same.
     */
    public boolean isEveryValueEqual(Attribute attribute) {
        Optional<String> value = examples.stream().map(ex -> ex.getAttribute(attribute.getName())).findAny();
        if (!value.isPresent()) {
            // no examples in data set -> all examples have the same value
            return true;
        }

        for (Example example : examples) {
            if (!example.getAttribute(attribute.getName()).equals(value.get())) {
                return false;
            }
        }

        return true;
    }

    /**
     * @param   attribute   the {@link Attribute} to check.
     * @return  the most frequent value of the given {@link Attribute}.
     */
    public String getMostFrequentValue(Attribute attribute) {
        final HashMap<String, Integer> counts = new HashMap<>();

        for (Example example : examples) {
            counts.compute(example.getAttribute(attribute.getName()), (value, count) -> {
                if (count == null) {
                    return 1;
                } else {
                    return count + 1;
                }
            });
        }

        return counts.entrySet().stream()
                     .max(Comparator.comparing(Map.Entry::getValue))
                     .map(Map.Entry::getKey)
                     .orElseThrow(() -> new IllegalStateException("no most frequent values present"));
    }
}
