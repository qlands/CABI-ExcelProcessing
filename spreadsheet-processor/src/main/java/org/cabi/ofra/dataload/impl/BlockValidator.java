package org.cabi.ofra.dataload.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.db.DatabaseService;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.model.ICellProcessor;
import org.cabi.ofra.dataload.model.IProcessingContext;
import org.cabi.ofra.dataload.util.Pair;
import org.cabi.ofra.dataload.util.Utilities;

/**
 * Created by equiros on 11/19/2014.
 */
public class BlockValidator extends AbstractProcessor implements ICellProcessor {
  private static final String KEY_TRIALMESSAGE = "trialMessage";
  private static final String KEY_BLOCKMESSAGE = "blockMessage";
  @Override
  public void processCell(IProcessingContext context, Cell cell, IEventCollector eventCollector) throws ProcessorException {
    DatabaseService databaseService = context.getDatabaseService();
    String blockUid = Utilities.getStringCellValue(cell);
    Pair<String, Integer> pair = Utilities.splitBlockUid(blockUid);
    if (!databaseService.existsTrialByUniqueId(pair.car())) {
      throw new ProcessorException(getMessage(KEY_TRIALMESSAGE, "Cell value %s references trial UID %s, which does not exist", blockUid, pair.car()));
    }
    if (!databaseService.existsBlockById(pair.car(), pair.cdr())) {
      throw new ProcessorException(getMessage(KEY_BLOCKMESSAGE, "Cell value %s references block %d for trial UID %s, which does not exist", blockUid, pair.cdr(), pair.car()));
    }
  }
}
