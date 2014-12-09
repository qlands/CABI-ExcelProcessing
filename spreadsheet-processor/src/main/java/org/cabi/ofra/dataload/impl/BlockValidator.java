package org.cabi.ofra.dataload.impl;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.db.DatabaseService;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.model.ICellProcessor;
import org.cabi.ofra.dataload.model.IProcessingContext;
import org.cabi.ofra.dataload.util.Pair;
import org.cabi.ofra.dataload.util.Utilities;

/**
 * Class for validating block UIDs. Validation happens against the database.
 * This class also supports extensibility to allow a post-block identifier to contain character data that can be
 * processed.
 * This class, as well as inheritors, supports the following arguments:
 * - malformedUidMessage: issued when the UID being validated is not well formed. Supports one placeholder for cell value
 * - trialMessage: issued when the trial referenced by the UID is not found. Placeholders: Cell Value, Trial UID
 * - blockMessage: issued when the block referenced by the UID is not found. Placeholders: Cell Value, Trial UID, Block ID
 */
public class BlockValidator extends AbstractProcessor implements ICellProcessor {
  private static final String KEY_MALFORMEDUIDMESSAGE = "malformedUidMessage";
  private static final String KEY_TRIALMESSAGE = "trialMessage";
  private static final String KEY_BLOCKMESSAGE = "blockMessage";
  protected DatabaseService databaseService;

  @Override
  public void processCell(IProcessingContext context, CellReference cellReference, Cell cell, IEventCollector eventCollector) throws ProcessorException {
    databaseService = context.getDatabaseService();
    String blockUid = Utilities.getStringCellValue(cell);
    StringBuffer postBlockSegment = new StringBuffer();
    Pair<String, Integer> pair = Utilities.splitBlockUid(blockUid, (segment, matcher) -> postBlockSegment.append(segment));
    if (pair == null) {
      throw new ProcessorException(getMessage(KEY_MALFORMEDUIDMESSAGE, "Cell value %s references a Block UID which is malformed", blockUid));
    }
    if (!databaseService.existsTrialByUniqueId(pair.car())) {
      throw new ProcessorException(getMessage(KEY_TRIALMESSAGE, "Cell value %s references trial UID %s, which does not exist", blockUid, pair.car()));
    }
    if (!databaseService.existsBlockById(pair.car(), pair.cdr())) {
      throw new ProcessorException(getMessage(KEY_BLOCKMESSAGE, "Cell value %s references block %d for trial UID %s, which does not exist", blockUid, pair.cdr(), pair.car()));
    }
    processPostBlockSegment(pair.car(), pair.cdr(), postBlockSegment.toString());
  }

  /**
   * This method allows processing of a string segment after the Block ID. The idea is to provide an extensibility point
   * for block-based identifiers (such as soil/plant samples)
   * @param trialUid The Trial portion of the identifier
   * @param blockId The Block portion of the identifier
   * @param segment The post-block string segment
   * @throws ProcessorException
   */
  protected void processPostBlockSegment(String trialUid, int blockId, String segment) throws ProcessorException {

  }
}
