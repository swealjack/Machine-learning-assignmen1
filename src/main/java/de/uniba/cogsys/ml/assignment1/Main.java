package de.uniba.cogsys.ml.assignment1;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;

import de.uniba.cogsys.ml.assignment1.dataset.Attribute;
import de.uniba.cogsys.ml.assignment1.dataset.CsvDataSetParser;
import de.uniba.cogsys.ml.assignment1.dataset.DataSet;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {

    private static final String DEFAULT_SEPARATOR = ",";

    public static void main(String[] args) throws IOException {
        Options options = new Options();

        Option dataSetOption = new Option("d", "dataset", true, "input data set");
        dataSetOption.setRequired(true);
        options.addOption(dataSetOption);

        Option targetClassOption = new Option("c", "targetclass", true, "target class in the data set");
        targetClassOption.setRequired(true);
        options.addOption(targetClassOption);

        Option separatorOption = new Option("s", "separator", true, "separator of the data set CSV file");
        options.addOption(separatorOption);

        CommandLineParser cmdParser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        CommandLine cmd;
        try {
            cmd = cmdParser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("id3", options);
            System.exit(2);
            return;
        }

        Path datasetFile = Paths.get(cmd.getOptionValue("dataset"));
        String targetClass = cmd.getOptionValue("targetclass");
        String separator = Optional.ofNullable(cmd.getOptionValue("separator")).orElse(DEFAULT_SEPARATOR);

        CsvDataSetParser parser = new CsvDataSetParser(separator);
        DataSet dataSet = parser.parseDataSet(datasetFile);

        // test output
        System.out.println(dataSet.getAttributes().stream().map(Attribute::getName).collect(Collectors.joining("\t")));
        dataSet.getExamples().forEach(ex -> System.out.println(dataSet.getAttributes().stream().map(Attribute::getName).map(ex::getAttribute).collect(Collectors.joining("\t"))));

        // TODO: actually run ID3 on the data set
    }
}
