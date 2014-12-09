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
 * Cell processor implementation which provides validation for Trial UIDs. Validates if a cell value represents a valid
 * Trial UID, using the database for validation.
 * Supports the following arguments:
 * - malformedUidMessage: issued when the UID being validated is not well formed. Supports one placeholder for cell value
 * - trialMessage: issued when the trial referenced by the UID is not found. Placeholders: Cell Value, Trial UID
 */
public class TrialValidator extends AbstractProcessor implements ICellProcessor {
  private static final String KEY_TRIALMESSAGE = "trialMessage";
  private static final String KEY_MALFORMEDUIDMESSAGE = "malformedUidMessage";

  protected DatabaseService databaseService;

  @Override
  public void processCell(IProcessingContext context, CellReference cellReference, Cell cell, IEventCollector eventCollector) throws ProcessorException {
    databaseService = context.getDatabaseService();
    String uid = Utilities.getStringCellValue(cell);
    Pair<String, String> pair = Utilities.splitUid(uid);
    if (pair == null) {
      throw new ProcessorException(getMessage(KEY_MALFORMEDUIDMESSAGE, "Cell value %s references a Trial UID which is malformed"));
    }
    if (!databaseService.existsTrialByUniqueId(pair.car())) {
      throw new ProcessorException(getMessage(KEY_TRIALMESSAGE, "Cell value '%s' references Trial UID '%s', which does not exist", uid, pair.car()));
    }
    if (pair.cdr() != null && !pair.cdr().isEmpty()) {
      processPostTrialSegment(pair.car(), pair.cdr());
    }
  }

  protected void processPostTrialSegment(String trialUid, String segment) throws ProcessorException {

  }
}
