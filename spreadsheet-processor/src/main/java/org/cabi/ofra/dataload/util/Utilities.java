package org.cabi.ofra.dataload.util;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.db.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public class Utilities {
  private static Logger logger = LoggerFactory.getLogger(Utilities.class);

  public static boolean validateCellReference(CellReference ref, Sheet sheet) {
/*
    if (ref.getRow() >= sheet.getLastRowNum()) return false;
    Row row = sheet.getRow(ref.getRow());
    if (ref.getCol() >= row.getLastCellNum()) return false;
*/
    return true;
  }

  private static InputStream getPropertiesStream(String propertiesFile) throws FileNotFoundException {
    if (propertiesFile != null && new File(propertiesFile).exists()) {
      logger.info("Attempting to load database properties from file '%s'");
      return new FileInputStream(propertiesFile);
    }
    if (new File("database.properties").exists()) {
      logger.info("Attempting to load database properties from 'database.properties' in current directory");
      return new FileInputStream("database.properties");
    }
    logger.info("Attempting to load database properties from JAR resource 'database.properties'");
    return Utilities.class.getClassLoader().getResourceAsStream("database.properties");
  }

  public static Properties loadDatabaseProperties(String propertiesFile) throws IOException {
    InputStream is = getPropertiesStream(propertiesFile);
    if (is != null) {
      logger.info("Database properties successfully loaded!");
      Properties properties = new Properties();
      properties.load(is);
      return properties;
    }
    else {
      logger.error("Unable to load database properties");
      return null;
    }
  }

  private static Serializable evaluateCell(Cell cell, int type) {
    switch (type) {
      case Cell.CELL_TYPE_STRING:
        return cell.getStringCellValue();
      case Cell.CELL_TYPE_NUMERIC:
        return cell.getNumericCellValue();
      case Cell.CELL_TYPE_BOOLEAN:
        return cell.getBooleanCellValue();
      case Cell.CELL_TYPE_BLANK:
        return "";
      case Cell.CELL_TYPE_FORMULA:
        return evaluateCell(cell, cell.getCachedFormulaResultType());
      default:
        // case error
        return cell.getErrorCellValue();
    }
  }

  private static int getCellType(Cell cell) {
    return cell.getCellType() == Cell.CELL_TYPE_FORMULA ? cell.getCachedFormulaResultType() : cell.getCellType();
  }

  public static boolean isBlank(Cell cell) {
    return (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK);
  }

  public static Serializable getCellValue(Cell cell) {
    return evaluateCell(cell, cell.getCellType());
  }

  public static String getStringCellValue(Cell cell) {
    if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
      return "";
    }
    Serializable v = getCellValue(cell);
    return v.getClass().equals(String.class) ? (String) v : String.valueOf(v);
  }

  public static Date getDateCellValue(Cell cell) throws ParseException {
    switch (getCellType(cell)) {
      case Cell.CELL_TYPE_BLANK:
        return new Date(0);
      case Cell.CELL_TYPE_NUMERIC:
        return cell.getDateCellValue();
      case Cell.CELL_TYPE_STRING:
        return parseDate(cell.getStringCellValue());
      default:
        return null;
    }
  }

  private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
  private static Date parseDate(String dateString) throws ParseException {
    return formatter.parse(dateString);
  }

  public static double getDoubleCellValue(Cell cell) {
    if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
      return Double.MIN_VALUE;
    }
    Serializable v = getCellValue(cell);
    if (v.getClass().equals(Double.class) || v.getClass().equals(Float.class)) {
      return (Double) v;
    }
    else if (v.getClass().equals(Boolean.class)) {
      Boolean b = (Boolean) v;
      return b ? 1.0 : 0.0;
    }
    else {
      return Double.valueOf(v.toString());
    }
  }

  public static boolean getBooleanCellValue(Cell cell) {
    switch(getCellType(cell)) {
      case Cell.CELL_TYPE_BLANK:
        return false;
      case Cell.CELL_TYPE_NUMERIC:
        return cell.getNumericCellValue() != 0.0;
      case Cell.CELL_TYPE_STRING:
        return "y".equals(cell.getStringCellValue().toLowerCase());
      case Cell.CELL_TYPE_BOOLEAN:
        return cell.getBooleanCellValue();
      default:
        return false;
    }
  }

  public static int getIntegerCellValue(Cell cell) {
    if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
      return Integer.MIN_VALUE;
    }
    Serializable v = getCellValue(cell);
    if (v.getClass().equals(Double.class) || v.getClass().equals(Float.class)) {
      return ((Double) v).intValue();
    }
    else if (v.getClass().equals(Boolean.class)) {
      Boolean b = (Boolean) v;
      return b ? 1 : 0;
    }
    else {
      return Integer.valueOf(v.toString());
    }
  }
  private static Pattern uidPattern = Pattern.compile("([a-zA-Z0-9]+)_([a-zA-Z0-9]+)_([a-zA-Z0-9]+)_([a-zA-Z0-9]+)_([a-zA-Z0-9]+)_([\\d]{4})([a-zA-Z0-9]{2})[_]?([a-zA-Z0-9]*)");

  public static Matcher matchUid(String uid) {
    Matcher m = uidPattern.matcher(uid);
    if (m.matches()) return m;
    return null;
  }

  /**
   * Splits a UID (internal unique identifier) in a Trial UID and a rest (block, plot, etc.)
   * @param uid
   * @return A <code>Pair</code> instance with the <code>car</code> set to the Trial UID, and the <code>cdr</code> set to the rest of the UID
   */
  public static Pair<String, String> splitUid(String uid) {
    Matcher m = matchUid(uid);
    if (m != null) {
      return new Pair<>(String.format("%s_%s_%s_%s_%s_%s%s", m.group(1), m.group(2), m.group(3), m.group(4), m.group(5), m.group(6), m.group(7)), m.group(8));
    }
    return null;
  }

  public static String extractTrialUniqueId(String uid) {
    Pair<String, String> p = splitUid(uid);
    if (p == null) return null;
    return p.car();
  }

  public static Triplet<String, Integer, Integer> validatePlot(DatabaseService databaseService, String plotUid) throws ProcessorException {
    Triplet<String, Integer, Integer> triplet = splitPlotUid(plotUid);
    if (triplet == null) {
      throw new ProcessorException(String.format("Plot UID '%s' is malformed", plotUid));
    }
    if (!databaseService.existsPlotById(triplet.getFirst(), triplet.getSecond(), triplet.getThird())) {
      throw new ProcessorException(String.format("Referenced plot '%s' does not exist in the database", plotUid));
    }
    return triplet;
  }

  public static Pair<String, Integer> validateBlock(DatabaseService databaseService, String blockUid) throws ProcessorException {
    Pair<String, Integer> pair = splitBlockUid(blockUid);
    if (pair == null) {
      throw new ProcessorException(String.format("Block UID '%s' is malformed", blockUid));
    }
    if (!databaseService.existsBlockById(pair.car(), pair.cdr())) {
      throw new ProcessorException(String.format("Referenced block '%s' does not exist in the database", blockUid));
    }
    return pair;
  }


  public static void validateTrial(DatabaseService databaseService, String trialUid) throws ProcessorException {
    if (!databaseService.existsTrialByUniqueId(trialUid)) {
      throw new ProcessorException(String.format("Referenced trial '%s' does not exist in database", trialUid));
    }
  }

  private static Pattern blockPlotPattern = Pattern.compile("B([\\d]+)_([\\d])");
  public static Triplet<String, Integer, Integer> splitPlotUid(String plotUid) {
    Pair<String, String> base = splitUid(plotUid);
    if (base == null) return null;
    Matcher matcher = blockPlotPattern.matcher(base.cdr());
    if (matcher.matches()) {
      return new Triplet<>(base.car(), Integer.valueOf(matcher.group(1)), Integer.valueOf(matcher.group(2)));
    }
    else {
      return null;
    }
  }


  /**
   * Validates that a block UID is valid in the system
   * @param databaseService The database service to use for validation
   * @param blockUid The Block UID to validate
   * @throws ProcessorException When the referenced blockUid is not valid, either syntactically or because it references
   * an block that does not exist
   */
  private static Pattern blockPattern = Pattern.compile("B([\\d]+)");

  /**
   * Splits a Block UID into a Trial UID and an integer block number. Internally separates the block reference (in the form B###), and
   * uses the number for return
   * @param blockUid
   * @return A {@link org.cabi.ofra.dataload.util.Pair} with the Trial UID in the {@code car} and the integer block number in the {@code cdr}
   * @throws ProcessorException
   */
  public static Pair<String, Integer> splitBlockUid(String blockUid) throws ProcessorException {
    Pair<String, String> pair = splitUid(blockUid);
    if (pair == null) return null;
    Matcher matcher = blockPattern.matcher(pair.cdr());
    if (matcher.matches()) {
      return new Pair<>(pair.car(), Integer.valueOf(matcher.group(1)));
    }
    else {
      return null;
    }
  }

  public static int validateAndExtractBlock(DatabaseService databaseService, String blockUid) throws ProcessorException {
    Pair<String, String> p = splitUid(blockUid);
    if (p == null) {
      throw new ProcessorException(String.format("Unique identifier %s is invalid for block reference", blockUid));
    }
    // first, validate the trial
    validateTrial(databaseService, p.car());
    Matcher matcher = blockPattern.matcher(p.cdr());
    if (matcher.matches()) {
      return Integer.valueOf(matcher.group(1));
    }
    else {
      throw new ProcessorException(String.format("Unique identifier %s references block %s, which is an invalid identifier", blockUid, p.cdr()));
    }
  }

  public static Pattern defaultSampleCodePattern = Pattern.compile("([\\D]+)([\\d]+)");
  private static int defaultSampleCodeGroup = 2;
  private static final String KEY_PATTERN = "regex";
  private static final String KEY_GROUP = "group";

  public static int extractSampleId(Map<String, Object> args, String sampleIdStr) {
    Pattern pattern = defaultSampleCodePattern;
    int group = defaultSampleCodeGroup;
    if (args.containsKey(KEY_PATTERN)) {
      pattern = Pattern.compile(args.get(KEY_PATTERN).toString());
      if (args.containsKey(KEY_GROUP)) {
        defaultSampleCodeGroup = Integer.valueOf(args.get(KEY_GROUP).toString());
      }
    }
    Matcher matcher = pattern.matcher(sampleIdStr);
    if (!matcher.matches()) {
      // if the regex does not match the imput stream, we just assume the string can be converted to integer directly
      return Integer.valueOf(sampleIdStr);
    }
    else {
      return Integer.valueOf(matcher.group(group));
    }
  }

  public static Pattern BlockSoilSamplePattern = Pattern.compile("B([\\d]+)_BSS([\\d]+)");
}
