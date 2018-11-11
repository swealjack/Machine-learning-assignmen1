package de.uniba.cogsys.ml.assignment1.dataset;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Provides parsing functionality for CSV files.
 */
public class CsvDataSetParser {

    private final String separator;

    /**
     * Creates a new parser for {@link DataSet DataSets} stored in CSV files.
     *
     * @param   separator   the separator used in the CSV files.
     */
    public CsvDataSetParser(String separator) {
        this.separator = separator;
    }

    /**
     * Parses the given CSV file to a {@link DataSet}. The CSV file is expected to have the separator as specified in
     * the {@link #CsvDataSetParser(String) constructor}.
     *
     * @param   file    the {@link Path} of the file to parse.
     *
     * @return  the parsed {@link DataSet}.
     *
     * @throws  IOException Signals that a IO operation to read the file failed.
     */
    public DataSet parseDataSet(Path file) throws IOException {
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);

        // read attribute names
        // use LinkedHashMap to guarantee iteration order being the insertion order
        final LinkedHashMap<String, Set<String>> attributeValues = new LinkedHashMap<>();
        String[] attributeNames = lines.get(0).split(separator, -1);
        for (String name : attributeNames) {
            attributeValues.put(name, new HashSet<>());
        }

        // process the examples
        List<Example> examples = lines.stream()
                                      .skip(1) // skip the headlines in the CSV
                                      .map(line -> line.split(separator, -1))
                                      .map(values -> buildExample(values, attributeValues))
                                      .collect(Collectors.toList());

        Set<Attribute> attributes = attributeValues.entrySet().stream()
                                                   .map(entry -> new Attribute(entry.getKey(), entry.getValue()))
                                                   .collect(Collectors.toSet());

        return new DataSet(attributes, examples);
    }

    /**
     * Builds an {@link Example} from the values of a line in the input file.
     * The attribute values are stored in the given map.
     *
     * @param   values  The attribute values of the line in the input file.
     *
     * @param   attributeValues A map containing each attribute of the data set in order of the CSV file. The possible
     *                          values of the attributes will be recorded in the values of the map.
     *
     * @return  the build {@link Example}.
     */
    private Example buildExample(String[] values, LinkedHashMap<String, Set<String>> attributeValues) {
        HashMap<String, String> attributes = new HashMap<>();
        int i = 0;

        // iteration order is guaranteed by attributeValues being a LinkedHashMap
        for (String attribute : attributeValues.keySet()) {
            String value = values[i];
            attributes.put(attribute, value);

            // put the value to the possible values for the attribute
            attributeValues.compute(attribute, (a, vs) -> {
                if (vs == null) {
                    vs = new HashSet<>();
                }
                vs.add(value);
                return vs;
            });
            i++;
        }

        return new Example(attributes);
    }
}
