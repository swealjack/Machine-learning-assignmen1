package de.uniba.cogsys.ml.assignment1.dataset;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a example in a {@link DataSet}.
 *
 * Objects of this class are immutable.
 */
public final class Example {

    private final Map<String, String> attributes;

    Example(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    /**
     * Returns all the attributes of this {@link Example}.
     * This method will always return all attributes of the example, even if the containing
     * {@link DataSet} is filtered by {@link Attribute Attributes}.
     *
     * @return the {@link Attribute Attributes} of this {@link Example}.
     */
    public Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    /**
     * Returns the value of the given attribute of this {@link Example}.
     * If the attribute is not present for this {@link Example}, {@code null} will be returned.
     *
     * @param   attribute   the attribute to get the value of.
     *
     * @return  the value of the attribute in this {@link Example}.
     */
    public String getAttribute(String attribute) {
        return attributes.get(attribute);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Example example = (Example) o;
        return Objects.equals(attributes, example.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributes);
    }
}
