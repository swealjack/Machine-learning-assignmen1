package de.uniba.cogsys.ml.assignment1.dataset;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a {@link Attribute} in a {@link DataSet}.
 *
 * Objects of this class are immutable.
 */
public final class Attribute {

    private final String name;
    private final Set<String> values;

    Attribute(String name, Set<String> values) {
        this.name = name;
        this.values = values;
    }

    /**
     * @return the name of the {@link Attribute}.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the possible values the {@link Attribute} can have.
     */
    public Set<String> getValues() {
        return Collections.unmodifiableSet(values);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attribute attribute = (Attribute) o;
        return Objects.equals(name, attribute.name) &&
                Objects.equals(values, attribute.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, values);
    }
}
