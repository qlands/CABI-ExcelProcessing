package org.cabi.ofra.dataload;

import org.apache.commons.cli.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * Main entry-point class for the Spreadsheet Processor
 * Expects the following parameters from the command line:
 * - input: the input Excel file (required)
 * - config: the configuration file. If not specified, name 'default-configuration.xml' is assumed, and the file is searched for in
 *           the current directory, or else it will be load as resource from the classpath (JAR file)
 * - database: database configuration file. Assumes database.properties as default, and search logic is the same as config file
 * - template: template name in the configuration file that will be used to process the input (required)
 * - user: user name to be used for the creation of trial data
 * - ckanorg: CKAN organization tame to be used for the creation of trial data
 */
public class Main {
  // SLF4J Logger
  private static Logger logger = LoggerFactory.getLogger(Main.class);

  /**
   * Creates the program options for command line parsing
   * @return The {@link org.apache.commons.cli.Options} cto be used for command line parsing
   */
  private static Options createOptions() {
    Options options = new Options();
    options.addOption(OptionBuilder.withArgName("file").hasArg().withDescription("use the given file as input for processing").isRequired().create("input"));
    options.addOption(OptionBuilder.withArgName("file").hasArg().withDescription("use the given file as configuration").create("config"));
    options.addOption(OptionBuilder.withArgName("file").hasArg().withDescription("use the given file as database configuration properties").create("database"));
    options.addOption(OptionBuilder.withArgName("template").hasArg().withDescription("process input file using the given template").isRequired().create("template"));
    options.addOption(OptionBuilder.withArgName("username").hasArg().withDescription("user the given user in all database operations").isRequired().create("user"));
    options.addOption(OptionBuilder.withArgName("ckanorg").hasArg().withDescription("Organization the a trial or legacy data belong to").isRequired().create("ckanorg"));
    return options;
  }

  /**
   * Loads the program.properties file which includes the program name and version. This is for internal use
   * during the creation of the final assembly by Maven
   * @return A {@link java.util.Properties} with the program name and version properties
   * @throws IOException
   */
  private static Properties loadProgramProperties() throws IOException {
    Properties properties = new Properties();
    properties.load(Main.class.getClassLoader().getResourceAsStream("program.properties"));
    return properties;
  }

  private static CommandLineParser parser = new GnuParser();
  private static HelpFormatter helpFormatter = new HelpFormatter();

  /**
   * Main entry point
   * @param args The command line arguments to be processed using Commons CLI
   */
  public static void main(String[] args) {
    Options options = createOptions();
    try {
      Properties programProperties = loadProgramProperties();
      logger.info(String.format("%1$s - Version %2$s", programProperties.getProperty("program.name"), programProperties.getProperty("program.version")));
      CommandLine commandLine = parser.parse(options, args);
      start(commandLine);
    }
    catch (ParseException e) {
      helpFormatter.printHelp("ssprocessor", options);
    }
    catch (Exception e) {
      logger.error("Error processing spreadsheet", e);
    }
  }

  /**
   * Main program driver
   * @param commandLine The {@link org.apache.commons.cli.CommandLine} created from the actual command line arguments
   * @throws IOException
   * @throws ProcessorException
   */
  private static void start(CommandLine commandLine) throws IOException, ProcessorException {
    Reader configReader;
    // check presence of optional command line arguments
    if (commandLine.hasOption("config")) {
      configReader = new BufferedReader(new FileReader(commandLine.getOptionValue("config")));
    }
    else {
      configReader = new BufferedReader(new InputStreamReader(Main.class.getClassLoader().getResourceAsStream("default-configuration.xml")));
    }
    // create the main processing object (SpreadSheetProcessor)
    SpreadsheetProcessor processor = new SpreadsheetProcessor(configReader, new BufferedInputStream(new FileInputStream(commandLine.getOptionValue("input"))),
            commandLine.getOptionValue("template"), commandLine.getOptionValue("database"), commandLine.getOptionValue("user"), commandLine.getOptionValue("ckanorg"));
    processor.process();
  }
}
